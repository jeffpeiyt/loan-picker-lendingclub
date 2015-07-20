package com.ypei.loanpicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ypei.loanpicker.beans.Loan;

public class LoanPicker {

	private static Logger logger = Logger.getLogger(LoanPicker.class);

	public static List<Loan> pickLoans(List<Loan> loansLoaded) {

		List<Loan> loansPicked = new ArrayList<Loan>();

		try {

			for (Loan l : loansLoaded) {

				if (PickRule.pickBasedOnRule(l)) {
					loansPicked.add(l);
				}
			}

			logger.info("-----------------------------------------------");
			logger.info("Randomized notes order in the list...");
			long seed = System.nanoTime();
			Collections.shuffle(loansPicked, new Random(seed));
			
			int i = 1, len = loansPicked.size();
			for (Loan l : loansPicked) {

				logger.info("");
				logger.info("-----------------------------------------------");
				logger.info("PRE_PICKED_LOANS_" + i + "/" + len + " :"
						+ l.toString());
				i++;
			}

			logger.info("-----------------------------------------------");
			logger.info("PRE_PICKED_LOANS_SIZE: " + loansPicked.size() + " OUT_OF "
					+ loansLoaded.size()  + " RECENT_LISTED_LOANS");

		} catch (Throwable e) {

			logger.error("fail", e);

		}

		return loansPicked;

	}// end func

}
