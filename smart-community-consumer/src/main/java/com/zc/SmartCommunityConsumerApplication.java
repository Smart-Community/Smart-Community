package com.zc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableDiscoveryClient
@EnableAsync
@EnableFeignClients
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class SmartCommunityConsumerApplication {

	public static void main(String[] args) {
		if (args.length == 0){
			args = new String[] { "--spring.profiles.active=dev" };
		}
		SpringApplication.run(SmartCommunityConsumerApplication.class, args);
	}

	@Bean
	public Executor myAsync() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(6);
		executor.setMaxPoolSize(16);
		executor.setQueueCapacity(8192);
		executor.setThreadNamePrefix("MyExecutor-");

		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

}
