package com.dippy.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuqj
 * @date 2023年07月29日 10:32
 */
@Configuration
@EnableAsync
public class ThreadConfig {

	private int corePoolSize = 10;
	private int maxPoolSize = 20;
	private int keepAliveSeconds = 0;
	private String namePrefix = "Custom-thread-pool-in-thread-%d";


	@Bean
	public Executor myThreadExecutor() {
		return new ThreadPoolExecutor(getCorePoolSize(), getMaxPoolSize(), getKeepAliveSeconds(), TimeUnit.MINUTES, new SynchronousQueue<>(), new BasicThreadFactory.Builder().namingPattern("").daemon(true).build(), new ThreadPoolExecutor.DiscardPolicy());
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getKeepAliveSeconds() {
		return keepAliveSeconds;
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
}
