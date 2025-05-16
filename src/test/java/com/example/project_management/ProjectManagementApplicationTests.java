package com.example.project_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	 @Test
    void mainRunsWithoutError() {
        // Call your Spring Boot entry point; if it throws, the test fails.
        ProjectManagementApplication.main(new String[] {});
    }

}
