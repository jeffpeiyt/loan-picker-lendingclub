package com.ypei.loanpicker.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;

import com.ypei.loanpicker.App;
import com.ypei.loanpicker.utils.DateUtils;

public class InvestJob implements Job {

	private static Logger logger = Logger.getLogger(InvestJob.class);

	public static int LOOP_SIZE = 20;
	public static int CHECK_INTERVAL_SEC = 15;

	public void execute(JobExecutionContext context)
			{

		try {
			JobKey jobKey = context.getJobDetail().getKey();
			logger.info("-----------------------------------------------");
			logger.info("-----------------------------------------------");
			logger.info("-----------------------------------------------");
			logger.info("-----------------------------------------------");
			logger.info("INVEST_JOB_START_THIS_ROUND_OF_LOOP_IN_INVEST_JOB:" + jobKey + " executing at "
					+ DateUtils.getDateTimeStrSdsm(new Date()));

			for (int i = 1; i <= LOOP_SIZE; i++) {

				logger.info("-----------------------------------------------");
				logger.info("LOOP_" + i + "/" + LOOP_SIZE + " STARTS_NOW...");

				App.loadPickPurchaseLoans();

				logger.info("INVEST_JOB_INTERVAL:SLEEP now for " + CHECK_INTERVAL_SEC
						+ " Seconds...");
				Thread.sleep(CHECK_INTERVAL_SEC * 1000L);
			}

			logger.info("INVEST_JOB_FINISH_THIS_ROUND_OF_LOOP_IN_INVEST_JOB:" + jobKey + " executing at "
					+ DateUtils.getDateTimeStrSdsm(new Date()));
			logger.info("-----------------------------------------------");
			logger.info("-----------------------------------------------");
			logger.info("-----------------------------------------------");
			logger.info("-----------------------------------------------");
			
		} catch (Throwable e) {
			logger.error("Fails", e);
		}
	}

}
