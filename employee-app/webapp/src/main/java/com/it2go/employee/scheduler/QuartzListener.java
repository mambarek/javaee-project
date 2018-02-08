package com.it2go.employee.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.Date;

@WebListener
public class QuartzListener extends QuartzInitializerListener {

    @Inject
    CDIJobFactory jobFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        if(jobFactory == null) System.out.println("WAAAAAAAAAARNUNG jobFactory not initilized!!!");
        ServletContext ctx = sce.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
        try {
            Scheduler scheduler = factory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(EmployeesInitJob.class).build();
            //LocalTime lt = new LocalTime();
            Date startTime = new Date((new Date()).getTime() + 50000L);
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple").withSchedule(
                    SimpleScheduleBuilder.repeatSecondlyForTotalCount(100,2)).startAt(startTime).build();
                    //SimpleScheduleBuilder.repeatSecondlyForTotalCount(5,2)).startNow().build();
                    //SimpleScheduleBuilder.repeatSecondlyForever(2)).startNow().build();
                    //CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).startNow().build();

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            ctx.log("There was an error scheduling the job.", e);
        }
    }
}
