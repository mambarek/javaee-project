package com.it2go.employee.scheduler;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@ApplicationScoped
public class CDIJobFactory implements JobFactory {

    @Inject
    private BeanManager beanManager;

    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {

        final Class<? extends Job> jobClazz = triggerFiredBundle.getJobDetail().getJobClass();
        Bean bean = beanManager.getBeans(jobClazz).iterator().next();
        final CreationalContext creationalContext = beanManager.createCreationalContext(bean);

        return (Job) beanManager.getReference(bean, jobClazz, creationalContext);
    }
}
