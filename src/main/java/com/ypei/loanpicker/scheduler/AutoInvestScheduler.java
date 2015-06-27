package com.ypei.loanpicker.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.ypei.loanpicker.Cfg;

public class AutoInvestScheduler {
	private static Logger  logger = Logger.getLogger(AutoInvestScheduler.class);
	public static void main(String[] args) {
		
		scheduleAutoInvest();
	}
	
	public static void scheduleAutoInvest() {

		try {
			// Grab the Scheduler instance from the Factory
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();

			String group = "group1";
			JobDetail job = newJob(InvestJob.class).withIdentity("investJob",
					group).build();

			CronTrigger trigger = newTrigger()
					.withIdentity("investTrigger", group)
					.withSchedule(
							CronScheduleBuilder.cronSchedule(Cfg.m
									.get(Cfg.T.CRON_EXPRESSION.name())))
					.build();

			sched.scheduleJob(job, trigger);

			sched.start();

			//sched.shutdown(true);

		} catch (SchedulerException se) {
			logger.error("SchedulerException", se);
		} catch (Throwable e) {
			logger.error("Fails", e);
		}
	}//end func	
	
}
