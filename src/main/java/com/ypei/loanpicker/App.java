package com.ypei.loanpicker;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ypei.loanpicker.beans.Loan;
import com.ypei.loanpicker.scheduler.AutoInvestScheduler;

/**
 * Starting point 1. load config 2. load recent loans 3. pick loans 4. load
 * owned notes 5. purchase after filtering duplicates
 */
public class App {
	private static Logger logger = Logger.getLogger(App.class);

	public static void loadPickPurchaseLoans() {

		logger.info("***********START_NEW_LOAN_PICKER_PROCESS***********");

		List<Loan> loansLoaded = LoanLoader.loadLoans();

		List<Loan> loansPicked = LoanPicker.pickLoans(loansLoaded);

		Set<Long> ownedLoanIds = OwnedNoteLoader.loadOwnedNotes();

		LoanPurchaser.purchaseLoans(loansPicked, ownedLoanIds);

		logger.info("***********END_NEW_LOAN_PICKER_PROCESS***********");
	}

	public static void runWrapper() {

		logger.info("***********Welcome to Loan Picker for Lendng Club. By Yuanteng Jeff Pei***********");
		logger.info("***********Make sure application.conf is under current directory. ***********");
		logger.info("***********Github: https://github.com/jeffpeiyt/loan-picker-lendingclub***********");
		logger.info("***********Change application.conf to run as a scheduler or a one time executor ***********");

		ConfigLoader.loadConfig();

		Boolean runAsScheduler = new Boolean(Cfg.m.get(Cfg.T.RUN_AS_SCHEDULER
				.name()));

		if (runAsScheduler) {

			logger.info("***********START_AS_SCHEDULER***********");
			AutoInvestScheduler.scheduleAutoInvest();

		} else {
			logger.info("***********START_AS_ONE_TIME_EXECUTOR***********");
			loadPickPurchaseLoans();
		}

	}

	public static void main(String[] args) {

		runWrapper();
	}
}
