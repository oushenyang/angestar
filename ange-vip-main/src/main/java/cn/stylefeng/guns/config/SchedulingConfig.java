package cn.stylefeng.guns.config;

import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 定时任务自动配置(需要定时任务的可以放开注释)
 *
 * @author shenyang.ou
 * @Date 2019/2/24 16:23
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JobFactory jobFactory;

//    @Bean(name="SchedulerFactory")
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//        //获取配置属性
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//        //在quartz.properties中的属性被读取并注入后再初始化对象
//        propertiesFactoryBean.afterPropertiesSet();
//        //创建SchedulerFactoryBean
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setQuartzProperties(propertiesFactoryBean.getObject());
//        //使用数据源
////        factory.setDataSource(dataSource);
//        factory.setJobFactory(jobFactory);
//        return factory;
//    }

    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
//        schedulerFactoryBean.setDataSource(dataSource);
        //使用数据源
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(jobFactory);

        return schedulerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

}
