package com.lzj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@EnableBatchProcessing
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.lzj.mybatis.dao")
@ImportResource({"classpath:mybatis-config.xml","classpath:batch-config.xml","classpath:quartz-config.xml","applicationContext.xml"})
@MapperScan("com.lzj.mybatis.dao")
public class DemoApplication {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

//		Set<Object> set = new HashSet<>();
//		set.add("classpath:batch-config.xml");
//		set.add("classpath:quartz-config.xml");
//		set.add("applicationContext.xml");
//		SpringApplication app = new SpringApplication(DemoApplication.class);
//		app.setSources(set);
//		ApplicationContext context = app.run(args);

		 SpringApplication.run(DemoApplication.class, args);
	}
}
