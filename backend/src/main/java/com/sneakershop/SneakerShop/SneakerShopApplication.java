package com.sneakershop.SneakerShop;

import com.sneakershop.SneakerShop.security.jwt.JwtProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperty.class})
public class SneakerShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SneakerShopApplication.class, args);
	}

}
