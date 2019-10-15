package com.kzkj;

import com.google.common.eventbus.AsyncEventBus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

@SpringBootApplication
@MapperScan("com.kzkj.mapper")
public class CrseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrseApplication.class, args);
	}

}
