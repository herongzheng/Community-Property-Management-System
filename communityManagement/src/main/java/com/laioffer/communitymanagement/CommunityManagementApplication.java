package com.laioffer.communitymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CommunityManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityManagementApplication.class, args);
	}

}
