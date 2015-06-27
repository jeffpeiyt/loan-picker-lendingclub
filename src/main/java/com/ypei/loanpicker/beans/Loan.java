package com.ypei.loanpicker.beans;

import com.google.api.client.util.Key;

/**
 * https://www.lendingclub.com/developers/listed-loans.action
 * @author ypei
 *
 */
public class Loan {

	
	@Key
	public Long id;
	@Key
	public Integer memberId;
	@Key
	public Integer loanAmount;
	@Key
	public Integer fundedAmount;
	@Key
	public Integer term;
	@Key
	public Double intRate;
	@Key
	public Double expDefaultRate;
	@Key
	public Double serviceFeeRate;
	@Key
	public Double installment;
	@Key
	public String grade;
	@Key
	public String subGrade;
	@Key
	public Integer empLength;
	@Key
	public String homeOwnership;
	@Key
	public Integer annualInc;
	@Key
	public String isIncV;
	@Key
	public String acceptD;
	@Key
	public String expD;
	@Key
	public String listD;
	@Key
	public String creditPullD;
	@Key
	public String reviewStatusD;
	@Key
	public String reviewStatus;
	@Key
	public String desc;
	@Key
	public String purpose;
	@Key
	public String addrZip;
	@Key
	public String addrState;
	@Key
	public Integer investorCount;
	@Key
	public String ilsExpD;
	@Key
	public String initialListStatus;
	@Key
	public String empTitle;
	@Key
	public Integer accNowDelinq;
	@Key
	public Integer accOpenPast24Mths;
	@Key
	public Integer bcOpenToBuy;
	@Key
	public Double percentBcGt75;
	@Key
	public Double bcUtil;
	@Key
	public Double dti;
	@Key
	public Integer delinq2Yrs;
	@Key
	public Integer delinqAmnt;
	@Key
	public String earliestCrLine;
	@Key
	public Integer ficoRangeLow;
	@Key
	public Integer ficoRangeHigh;
	@Key
	public Integer inqLast6Mths;
	@Key
	public Integer mthsSinceLastDelinq;
	@Key
	public Integer mthsSinceLastRecord;
	@Key
	public Integer mthsSinceRecentInq;
	@Key
	public Integer mthsSinceRecentRevolDelinq;
	@Key
	public Integer mthsSinceRecentBc;
	@Key
	public Integer mortAcc;
	@Key
	public Integer openAcc;
	@Key
	public Integer pubRec;
	@Key
	public Integer totalBalExMort;
	@Key
	public Integer revolBal;
	@Key
	public Double revolUtil;
	@Key
	public Integer totalBcLimit;
	@Key
	public Integer totalAcc;
	@Key
	public Integer totalIlHighCreditLimit;
	@Key
	public Integer numRevAccts;
	@Key
	public Integer mthsSinceRecentBcDlq;
	@Key
	public Integer pubRecBankruptcies;
	@Key
	public Integer numAcctsEver120Ppd;
	@Key
	public Integer chargeoffWithin12Mths;
	@Key
	public Integer collections12MthsExMed;
	@Key
	public Integer taxLiens;
	@Key
	public Integer mthsSinceLastMajorDerog;
	@Key
	public Integer numSats;
	@Key
	public Integer numTlOpPast12m;
	@Key
	public Integer moSinRcntTl;
	@Key
	public Integer totHiCredLim;
	@Key
	public Integer totCurBal;
	@Key
	public Integer avgCurBal;
	@Key
	public Integer numBcTl;
	@Key
	public Integer numActvBcTl;
	@Key
	public Integer numBcSats;
	@Key
	public Integer pctTlNvrDlq;
	@Key
	public Integer numTl90gDpd24m;
	@Key
	public Integer numTl30dpd;
	@Key
	public Integer numTl120dpd2m;
	@Key
	public Integer numIlTl;
	@Key
	public Integer moSinOldIlAcct;
	@Key
	public Integer numActvRevTl;
	@Key
	public Integer moSinOldRevTlOp;
	@Key
	public Integer moSinRcntRevTlOp;
	@Key
	public Integer totalRevHiLim;
	@Key
	public Integer numRevTlBalGt0;
	@Key
	public Integer numOpRevTl;
	@Key
	public Integer totCollAmt;
	@Override
	public String toString() {
		return "Loan [id=" + id + ", memberId=" + memberId + ", loanAmount="
				+ loanAmount + ", fundedAmount=" + fundedAmount + ", term="
				+ term + ", intRate=" + intRate + ", expDefaultRate="
				+ expDefaultRate + ", serviceFeeRate=" + serviceFeeRate
				+ ", installment=" + installment + ", grade=" + grade
				+ ", subGrade=" + subGrade + ", empLength=" + empLength
				+ ", homeOwnership=" + homeOwnership + ", annualInc="
				+ annualInc + ", isIncV=" + isIncV + ", acceptD=" + acceptD
				+ ", expD=" + expD + ", listD=" + listD + ", creditPullD="
				+ creditPullD + ", reviewStatusD=" + reviewStatusD
				
				
				+ "\n, reviewStatus=" + reviewStatus + ", desc=" + desc
				+ ", purpose=" + purpose + ", addrZip=" + addrZip
				+ ", addrState=" + addrState + ", investorCount="
				+ investorCount + ", ilsExpD=" + ilsExpD
				+ ", initialListStatus=" + initialListStatus + ", empTitle="
				+ empTitle + ", accNowDelinq=" + accNowDelinq
				+ ", accOpenPast24Mths=" + accOpenPast24Mths + ", bcOpenToBuy="
				+ bcOpenToBuy + ", percentBcGt75=" + percentBcGt75
				+ ", bcUtil=" + bcUtil + ", dti=" + dti + ", delinq2Yrs="
				+ delinq2Yrs + ", delinqAmnt=" + delinqAmnt
				+ ", earliestCrLine=" + earliestCrLine + ", ficoRangeLow="
				+ ficoRangeLow + ", ficoRangeHigh=" + ficoRangeHigh
				
				+ "\n, inqLast6Mths=" + inqLast6Mths + ", mthsSinceLastDelinq="
				+ mthsSinceLastDelinq + ", mthsSinceLastRecord="
				+ mthsSinceLastRecord + ", mthsSinceRecentInq="
				+ mthsSinceRecentInq + ", mthsSinceRecentRevolDelinq="
				+ mthsSinceRecentRevolDelinq + ", mthsSinceRecentBc="
				+ mthsSinceRecentBc + ", mortAcc=" + mortAcc + ", openAcc="
				+ openAcc + ", pubRec=" + pubRec + ", totalBalExMort="
				+ totalBalExMort + ", revolBal=" + revolBal + ", revolUtil="
				+ revolUtil + ", totalBcLimit=" + totalBcLimit + ", totalAcc="
				+ totalAcc + ", totalIlHighCreditLimit="
				+ totalIlHighCreditLimit + ", numRevAccts=" + numRevAccts
				
				+ "\n, mthsSinceRecentBcDlq=" + mthsSinceRecentBcDlq
				+ ", pubRecBankruptcies=" + pubRecBankruptcies
				+ ", numAcctsEver120Ppd=" + numAcctsEver120Ppd
				+ ", chargeoffWithin12Mths=" + chargeoffWithin12Mths
				+ ", collections12MthsExMed=" + collections12MthsExMed
				+ ", taxLiens=" + taxLiens + ", mthsSinceLastMajorDerog="
				+ mthsSinceLastMajorDerog + ", numSats=" + numSats
				+ ", numTlOpPast12m=" + numTlOpPast12m + ", moSinRcntTl="
				+ moSinRcntTl + ", totHiCredLim=" + totHiCredLim
				+ ", totCurBal=" + totCurBal + ", avgCurBal=" + avgCurBal
				+ ", numBcTl=" + numBcTl + ", numActvBcTl=" + numActvBcTl
				+ ", numBcSats=" + numBcSats + ", pctTlNvrDlq=" + pctTlNvrDlq
				+ ", numTl90gDpd24m=" + numTl90gDpd24m + ", numTl30dpd="
				+ numTl30dpd + ", numTl120dpd2m=" + numTl120dpd2m
				+ ", numIlTl=" + numIlTl + ", moSinOldIlAcct=" + moSinOldIlAcct
				+ ", numActvRevTl=" + numActvRevTl + ", moSinOldRevTlOp="
				+ moSinOldRevTlOp + ", moSinRcntRevTlOp=" + moSinRcntRevTlOp
				+ ", totalRevHiLim=" + totalRevHiLim + ", numRevTlBalGt0="
				+ numRevTlBalGt0 + ", numOpRevTl=" + numOpRevTl
				+ ", totCollAmt=" + totCollAmt + "]";
	}
	
}
