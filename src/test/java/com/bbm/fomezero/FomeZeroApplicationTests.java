package com.bbm.fomezero;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FomeZeroApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
	void contextLoads() {
	}

	@Test
	void testPasswordEncoder() {
		String rawPassword = "belmiro";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println(rawPassword + "\n" + encodedPassword);
		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
	}

}
