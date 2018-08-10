package com.song;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.song.mapper")
public class JwtApplication {

	/**
	 * http://www.cnblogs.com/HowieYuan/p/9259650.html
	 * https://my.oschina.net/liughDevelop/blog/1811524
	 *
	 */

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
}
