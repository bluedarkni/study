package com.nijunyang.redis;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Bean
	public Redisson redisson() {
		Config config = new Config();

		config.useSingleServer().setAddress("redis://111.229.53.45:6379").setPassword("Wbqsphr.1114redis");

		/**
		 * 哨兵
		 */
		//config.useSentinelServers().addSentinelAddress("");

		/**
		 * 集群
		 */
		//config.useClusterServers().addNodeAddress("redis://111.229.53.45:6379");
		return (Redisson) Redisson.create(config);
	}

}
