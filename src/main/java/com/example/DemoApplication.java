package com.example;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CamelAutoConfiguration.class)
public class DemoApplication {

	@Value("${group.id}")
	private String groupId;

	@Bean
	RouteBuilder route() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("kafka:localhost:9092?Topic=test&groupId={{group.id}}&autoOffsetReset=earliest&autoCommitEnable=false").log(LoggingLevel.ERROR, "Message ${headers} ${body}");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
