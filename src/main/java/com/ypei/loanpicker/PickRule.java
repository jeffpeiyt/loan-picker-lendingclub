package com.ypei.loanpicker;

import com.ypei.loanpicker.beans.Loan;

/**
 * 
 * check here for what does each attribute means:
 * https://www.lendingclub.com/developers/listed-loans.action
 * example:
 * 
 * 	"loans": [
	{
		"id":111111,
		"memberId":222222,
		"loanAmount":1750.0,
		"fundedAmount":25.0,
		"term":36,
		"intRate":10.99,
		"expDefaultRate":3.5,
		"serviceFeeRate":0.85,
		"installment":57.29,
		"grade":"B",
		"subGrade":"B3",
		"empLength":0,
		"homeOwnership":"OWN",
		"annualInc":123432.0,
		"isIncV":"Requested",
		"acceptD":"2014-08-25T10:56:29.000-07:00",
		"expD":"2014-09-08T10:57:13.000-07:00",
		"listD":"2014-08-25T10:50:20.000-07:00",
		"creditPullD":"2014-08-25T10:56:18.000-07:00",
		"reviewStatusD":"2014-09-03T14:41:53.957-07:00",
		"reviewStatus":"NOT_APPROVED",
		"desc":"Loan description",
		"purpose":"debt_consolidation",
		"addrZip":"904xx",
		"addrState":"CA",
		"investorCount":"",
		"ilsExpD":"2014-08-25T11:00:00.000-07:00",
		"initialListStatus":"F",
		"empTitle":"",
		"accNowDelinq":"",
		"accOpenPast24Mths":23,
		"bcOpenToBuy":30000,
		"percentBcGt75":23.0,
		"bcUtil":23.0,
		"dti":0.0,
		"delinq2Yrs":1,
		"delinqAmnt":0.0,
		"earliestCrLine":"1984-09-15T00:00:00.000-07:00",
		"ficoRangeLow":750,
		"ficoRangeHigh":754,
		"inqLast6Mths":0,
		"mthsSinceLastDelinq":90,
		"mthsSinceLastRecord":0,
		"mthsSinceRecentInq":14,
		"mthsSinceRecentRevolDelinq":23,
		"mthsSinceRecentBc":23,
		"mortAcc":23,
		"openAcc":3,
		"pubRec":0,
		"totalBalExMort":13944,
		"revolBal":1.0,
		"revolUtil":0.0,
		"totalBcLimit":23,
		"totalAcc":4,
		"totalIlHighCreditLimit":12,
		"numRevAccts":28,
		"mthsSinceRecentBcDlq":52,
		"pubRecBankruptcies":0,
		"numAcctsEver120Ppd":12,
		"chargeoffWithin12Mths":0,
		"collections12MthsExMed":0,
		"taxLiens":0,
		"mthsSinceLastMajorDerog":12,
		"numSats":8,
		"numTlOpPast12m":0,
		"moSinRcntTl":12,
		"totHiCredLim":12,
		"totCurBal":12,
		"avgCurBal":12,
		"numBcTl":12,
		"numActvBcTl":12,
		"numBcSats":7,
		"pctTlNvrDlq":12,
		"numTl90gDpd24m":12,
		"numTl30dpd":12,
		"numTl120dpd2m":12,
		"numIlTl":12,
		"moSinOldIlAcct":12,
		"numActvRevTl":12,
		"moSinOldRevTlOp":12,
		"moSinRcntRevTlOp":11,
		"totalRevHiLim":12,
		"numRevTlBalGt0":12,
		"numOpRevTl":12,
		"totCollAmt":12
	}]
 * @author ypei
 *
 */
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
