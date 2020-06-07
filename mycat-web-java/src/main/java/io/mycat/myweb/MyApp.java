package io.mycat.myweb;

import cn.gingo.global.result.handler.EnableGlobalResultHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"io.mycat"})
@EnableGlobalResultHandler
public class MyApp {

	public static void main(String[] args) {
		SpringApplication.run(MyApp.class, args);
	}

}
