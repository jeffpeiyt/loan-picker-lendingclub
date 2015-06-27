Loan Picker for Lending Club
===================
Cherry pick and auto invest the loans with your own customized rules and scheduler.  (Simple Java / Maven Build)
http://jeffpeiyt.github.io/loan-picker-lendingclub/

## Motivation
Current lending club investment filters have limitations and cannot specify every details. e.g. can only choose delinquency > 60 months, however I always like to choose loans that never have any delinquency history. Or to avoid certain keywords in borrowers jobs.   Also, interestingly some loan attributes seem only available thru API rather than the web interface (e.g. The following are only availabel  numTl30dpd: Number of accounts currently 30 days past due (updated in past 2 months) or expDefaultRate: expected default rate) . 

Initially I just want to have a fine grain control on the filter and try to leverage existing libraries. However, seems none of them can satisfy my need after reviewing these available packages.  There comes this LoanPicker. The following were investigated but they have limited filtering options.

1. Lending club saved filters
2. https://github.com/jgillick/LendingClubAutoInvestor  
3. https://pypi.python.org/pypi/lcinvestor 
4. https://pypi.python.org/pypi/lendingclub/0.1.8  



## Declaration
**USE IT AT YOUR OWN RISK. Protect your credentials.** It is open source, free, so simple, and everyone can inspect the code. This is a primitive version but is fully functional. You have to change the [**PickRule.java**](https://github.com/jeffpeiyt/loan-picker-lendingclub/blob/master/src/main/java/com/ypei/loanpicker/PickRule.java) to edit the right rule.  Later we may like to make the rule as YAML file if needed.  Although this is hard coded, it is pretty straightforward to edit it.


##Features
  - Auto excludes already purchased loans in note picking.
  - On demand or scheduled auto investing at loan published time.
  - Config based token / inverstor id / portfolio id input (application.conf).
  - Fine-grained cherry picking your loans with your own rules. 
  - Able to set maximum number of loan purchase each time.
  - Simple change in  [**PickRule.java**](https://github.com/jeffpeiyt/loan-picker-lendingclub/blob/master/src/main/java/com/ypei/loanpicker/PickRule.java) to change **any** rules in details as you wish. TODO: change to YAML based rules
  - Logging with rotations to file and also to console. 
  - Dry run mode: IN_DEBUG set in application.conf: will not actually purchase the notes in this mode.

----------  

##Usage

Clone/Download this repo. 

1. Get Authorization Token, Investor ID, and Portfolio ID, update them in the **application.conf** file
	- **Token**: Enable API access and obtain tokens from here: https://www.lendingclub.com/developers/lc-api.action .  
	- **Investor ID**: Log into lending club and go to the account summary, the account ID is is this investor ID. 
	- **Portfolio ID**: Go https://www.lendingclub.com/developers/portfolios-owned.action to get your portfolio ID. Your ordered notes will be saved here. Or create a new one using this API: https://www.lendingclub.com/developers/create-portfolio.action 
2. Edit the [**PickRule.java**](https://github.com/jeffpeiyt/loan-picker-lendingclub/blob/master/src/main/java/com/ypei/loanpicker/PickRule.java) file with your own rules.
3. Build the app with maven command or import as existing maven project into Eclipse/Intellij to run. 
4. Auto Invest with scheduling: Put it into crontab(http://unixhelp.ed.ac.uk/CGI/man-cgi?crontab+5) to auto run it with cron expression.


###Build and Run

The simpliest way to run is import to Eclipse/Intellij as existing maven project. The main class is in App.java

To build and run, make sure to copy the application.conf with correct credentials in the current directory! Assuming the application.conf is in the current directory of running.

	mvn clean compile assembly:single;
	java -jar ./target/loan-picker-lendingclub-1.0.0-SNAPSHOT-jar-with-dependencies.jar 

###Scheduler
- Turn on scheduler by setting RUN_AS_SCHEDULER=true in application.conf.  
- It will run at 6,10,14,18 pacific time following LC timing. (this expression can be changed) note that timezone is America/Los_Angeles.   
- Everytime it will run 20 times after :00 ; each with 15 sec interval.  So it will run around :00 to :06 every 4 hours during the day time.  Duplicated will be avoided so multiple run is fine. This multiple run is performed because loans may not come out at exactly the same time.  Check InvestJob.java to change these interval/loop times.

###Sample Config Files

- Turn on scheduler by setting RUN_AS_SCHEDULER=true . Otherwise will run as one-time executor
- If IN_DEBUG=true: will not submit orders. 
- CRON_EXPRESSION controls the scheduler if it is turned on. Details please see http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger

**Sample application.conf**

	AUTHORIZATION_TOKEN=YOUR_TOKEN
	PORTFOLIO_ID=1111
	INVESTOR_ID=2222
	PURCHASE_LIMIT=10
	IN_DEBUG=false
	RUN_AS_SCHEDULER=false
	CRON_EXPRESSION=0 0 6,10,14,18 * * ?



**Sample [PickRule.java](https://github.com/jeffpeiyt/loan-picker-lendingclub/blob/master/src/main/java/com/ypei/loanpicker/PickRule.java)**

I know this is still hard-coded, but changing it is so easy :-) Check the following link to understand meaning of each attribute. 
https://www.lendingclub.com/developers/listed-loans.action

	public class PickRule {
	
		public static boolean pickBasedOnRule (Loan l){
			
			return  (l.term == 36
					&& (l.grade.contains("D") || l.grade.contains("E") || l.grade
							.contains("C"))
					// do not want to have C1
					&& (!l.subGrade.contains("C1"))
	
					// debt income ratio
					&& l.dti <= 40.0
					&& l.ficoRangeLow > 660
					&& l.mthsSinceLastMajorDerog == 0
					&& l.loanAmount <= 40000
					&& l.pubRecBankruptcies == 0
					&& l.mthsSinceLastRecord == 0
					&& l.accNowDelinq == 0
					&& l.delinq2Yrs == 0
					&& l.pubRec == 0
					&& l.annualInc >= 30000
					&& l.empLength >= 1
					&& l.revolBal <= 35000
					&& l.mthsSinceLastDelinq == 0
					&& l.revolUtil <= 85.0
					&& (l.inqLast6Mths <= 2)
					&& (!l.addrState.contains("NV") )
					&& (l.purpose.equalsIgnoreCase("debt_consolidation")
							|| l.purpose.equalsIgnoreCase("credit_card") )
					);
		}
	}

----------

##Internals

###System
- Google HTTP Client in Java with REST/JSON Object Mapper
- Maven Build
- Quartz Job Scheduler


###API Used
Follow this: https://www.lendingclub.com/developers/lc-api.action

    // get the recent listed new loans
	https://api.lendingclub.com/api/investor/v1/loans/listing
	// get your own notes: in order to filter out already purchased ones
	https://api.lendingclub.com/api/investor/v1/accounts/<account_id>/notes
	// make orders with loan IDs 
	https://api.lendingclub.com/api/investor/v1/accounts/<account_id>/orders

----------

##About and Contribute

###Version
1.0.0

### Support LoanPicker
Contribution is welcome! Submit a pull request or add a star. 


###Author
Yuanteng (Jeff) Pei 
http://www.jeffpei.com

###License

Apache License v2.0


##Run Example

Note that One already purchased loan is skipped in the purchase process.


	***********Welcome to Loan Picker for Lendng Club. By Yuanteng Jeff Pei***********
	***********Make sure application.conf is under current directory. ***********
	***********Github: https://github.com/jeffpeiyt/loan-picker-lendingclub***********
	***********Change application.conf to run as a scheduler or a one time executor ***********
	Config loaded: Key : INVESTOR_ID, Value : ****
	Config loaded: Key : AUTHORIZATION_TOKEN, Value : ****
	Config loaded: Key : IN_DEBUG, Value : false
	Config loaded: Key : PURCHASE_LIMIT, Value : 2
	Config loaded: Key : RUN_AS_SCHEDULER, Value : false
	Config loaded: Key : PORTFOLIO_ID, Value : ****
	Config loaded: Key : CRON_EXPRESSION, Value : 0 * 6/10/14/18 * * ?

	***********START_AS_ONE_TIME_EXECUTOR***********
	***********START_NEW_LOAN_PICKER_PROCESS***********

	-----------------------------------------------
	REQEST_LC_LIST_RECENT_LOANS....
	-----------------------------------------------
	RECENT_LOANS_FOUND: 82
	
	-----------------------------------------------
	PRE_PICKED_LOANS_1/2 :Loan [id=53995605, memberId=57536332, loanAmount=12000, fundedAmount=1200, term=36, intRate=8.18, expDefaultRate=2.2, serviceFeeRate=0.85, installment=377.04, grade=B, subGrade=B1, empLength=24, homeOwnership=RENT, annualInc=84000, isIncV=NOT_VERIFIED, acceptD=2015-06-25T16:26:24.000-07:00, expD=2015-07-09T16:33:33.000-07:00, listD=2015-06-26T10:00:00.000-07:00, creditPullD=2015-06-25T16:15:17.000-07:00, reviewStatusD=2015-06-25T16:50:39.000-07:00
	, reviewStatus=APPROVED, desc=, purpose=debt_consolidation, addrZip=751xx, addrState=TX, investorCount=0, ilsExpD=2015-06-26T10:00:00.000-07:00, initialListStatus=F, empTitle=Driver, accNowDelinq=0, accOpenPast24Mths=4, bcOpenToBuy=1473, percentBcGt75=50.0, bcUtil=75.0, dti=13.84, delinq2Yrs=0, delinqAmnt=0, earliestCrLine=2012-02-24T16:00:00.000-08:00, ficoRangeLow=695, ficoRangeHigh=699
	, inqLast6Mths=1, mthsSinceLastDelinq=0, mthsSinceLastRecord=0, mthsSinceRecentInq=3, mthsSinceRecentRevolDelinq=0, mthsSinceRecentBc=12, mortAcc=0, openAcc=7, pubRec=0, totalBalExMort=39655, revolBal=8047, revolUtil=42.4, totalBcLimit=5900, totalAcc=8, totalIlHighCreditLimit=46762, numRevAccts=6
	, mthsSinceRecentBcDlq=0, pubRecBankruptcies=0, numAcctsEver120Ppd=0, chargeoffWithin12Mths=0, collections12MthsExMed=0, taxLiens=0, mthsSinceLastMajorDerog=0, numSats=7, numTlOpPast12m=2, moSinRcntTl=3, totHiCredLim=65762, totCurBal=39655, avgCurBal=5665, numBcTl=2, numActvBcTl=1, numBcSats=2, pctTlNvrDlq=100, numTl90gDpd24m=0, numTl30dpd=0, numTl120dpd2m=0, numIlTl=2, moSinOldIlAcct=40, numActvRevTl=5, moSinOldRevTlOp=26, moSinRcntRevTlOp=3, totalRevHiLim=19000, numRevTlBalGt0=5, numOpRevTl=6, totCollAmt=0]
	
	-----------------------------------------------
	PRE_PICKED_LOANS_2/2 :Loan [id=54017825, memberId=57558571, loanAmount=12000, fundedAmount=725, term=36, intRate=9.17, expDefaultRate=3.05, serviceFeeRate=0.85, installment=382.55, grade=B, subGrade=B2, empLength=24, homeOwnership=RENT, annualInc=55000, isIncV=NOT_VERIFIED, acceptD=2015-06-25T15:41:45.000-07:00, expD=2015-07-09T16:03:44.000-07:00, listD=2015-06-26T10:00:00.000-07:00, creditPullD=2015-06-25T14:52:16.000-07:00, reviewStatusD=
	, reviewStatus=NOT_APPROVED, desc=, purpose=debt_consolidation, addrZip=330xx, addrState=FL, investorCount=0, ilsExpD=2015-06-26T10:00:00.000-07:00, initialListStatus=F, empTitle=ASM, accNowDelinq=0, accOpenPast24Mths=5, bcOpenToBuy=20344, percentBcGt75=16.7, bcUtil=36.0, dti=21.5, delinq2Yrs=0, delinqAmnt=0, earliestCrLine=2007-02-24T16:00:00.000-08:00, ficoRangeLow=755, ficoRangeHigh=759
	, inqLast6Mths=0, mthsSinceLastDelinq=0, mthsSinceLastRecord=0, mthsSinceRecentInq=19, mthsSinceRecentRevolDelinq=0, mthsSinceRecentBc=12, mortAcc=0, openAcc=12, pubRec=0, totalBalExMort=21369, revolBal=11563, revolUtil=32.9, totalBcLimit=31800, totalAcc=15, totalIlHighCreditLimit=22799, numRevAccts=10
	, mthsSinceRecentBcDlq=0, pubRecBankruptcies=0, numAcctsEver120Ppd=0, chargeoffWithin12Mths=0, collections12MthsExMed=0, taxLiens=0, mthsSinceLastMajorDerog=0, numSats=12, numTlOpPast12m=2, moSinRcntTl=12, totHiCredLim=58548, totCurBal=21369, avgCurBal=1781, numBcTl=7, numActvBcTl=4, numBcSats=6, pctTlNvrDlq=100, numTl90gDpd24m=0, numTl30dpd=0, numTl120dpd2m=0, numIlTl=4, moSinOldIlAcct=41, numActvRevTl=5, moSinOldRevTlOp=100, moSinRcntRevTlOp=12, totalRevHiLim=35100, numRevTlBalGt0=5, numOpRevTl=8, totCollAmt=0]
	-----------------------------------------------
	PRE_PICKED_LOANS_SIZE: 2 OUT_OF 82 RECENT_LISTED_LOANS
	-----------------------------------------------
	REQEST_LC_OWNED_NOTES....
	-----------------------------------------------
	OWNED_NOTES_FOUND: 100
	PURCHASE_LIMIT: Only consider first 2 picked loans to order.
	DUPLCIATED_LOANS: Note with Loan id 53995605 was already purchased!. Skip.
	-----------------------------------------------
	FINAL_LOAN_PURCHASE_COUNT: 1
	-----------------------------------------------
	SUBMIT_LC_LIST_ORDERS....
	
	-----------------------------------------------
	OrderConfirmation [loanId=54017825, requestedAmount=25, investedAmount=25, executionStatus=[ORDER_FULFILLED, NOTE_ADDED_TO_PORTFOLIO]]
	-----------------------------------------------
	ORDER_CONFIRMATION_COUNT: 1
	***********END_NEW_LOAN_PICKER_PROCESS***********
	

**Sample Output With Scheduler Turned On**

	***********Welcome to Loan Picker for Lendng Club. By Yuanteng Jeff Pei***********
	***********Make sure application.conf is under current directory. ***********
	***********Github: https://github.com/jeffpeiyt/loan-picker-lendingclub***********
	***********Change application.conf to run as a scheduler or a one time executor ***********
	Config loaded: Key : INVESTOR_ID, Value : ****
	Config loaded: Key : AUTHORIZATION_TOKEN, Value : ****
	Config loaded: Key : IN_DEBUG, Value : false
	Config loaded: Key : PURCHASE_LIMIT, Value : 2
	Config loaded: Key : RUN_AS_SCHEDULER, Value : true
	Config loaded: Key : PORTFOLIO_ID, Value : ****
	Config loaded: Key : CRON_EXPRESSION, Value : 0 * 6/10/14/18 * * ?
	***********START_AS_SCHEDULER***********
	START_THIS_ROUND_OF_LOOP_IN_INVEST_JOB:group1.investJob executing at 2015.06.26.16.19.30.010-0700
	LOOP_1/2 STARTS_NOW...
	***********START_NEW_LOAN_PICKER_PROCESS***********
	...
	***********END_NEW_LOAN_PICKER_PROCESS***********
	SLEEP now for 10 Seconds...
	LOOP_2/2 STARTS_NOW...
	***********START_NEW_LOAN_PICKER_PROCESS***********
	...
	***********END_NEW_LOAN_PICKER_PROCESS***********
