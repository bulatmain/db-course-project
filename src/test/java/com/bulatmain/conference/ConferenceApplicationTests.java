package com.bulatmain.conference;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ConferenceApplicationTests {

	@Test
	void contextLoads() {
	}

}
