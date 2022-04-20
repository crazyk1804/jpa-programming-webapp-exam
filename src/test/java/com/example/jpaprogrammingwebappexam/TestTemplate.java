package com.example.jpaprogrammingwebappexam;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class TestTemplate {
	@FunctionalInterface
	interface TestJob {
		void doJob();
	}

	protected TestInfo currentTestInfo = null;

	@BeforeEach
	void setCurrentTestInfo(TestInfo testInfo) {
		this.currentTestInfo = testInfo;
	}

	protected void _(TestJob job) {
		try {
			System.out.println("========================================================================");
			System.out.println(this.currentTestInfo.getDisplayName() + " BEGIN");
			System.out.println("========================================================================");
			job.doJob();
		} catch(Exception e) {
			throw e;
		} finally {
			System.out.println("////////////////////////////////////////////////////////////////////////");
			System.out.println(this.currentTestInfo.getDisplayName() + " FINISHED");
			System.out.println("////////////////////////////////////////////////////////////////////////");
		}
	}
}
