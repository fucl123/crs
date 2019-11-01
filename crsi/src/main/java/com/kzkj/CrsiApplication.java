package com.kzkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.kzkj.mapper")
@SpringBootApplication
public class CrsiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsiApplication.class, args);
	}

}
