package com.example.TenantEase;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * NOTE: This test is disabled because it loads the full Spring ApplicationContext
 * including a live MySQL connection + Flyway migrations.
 *
 * To run this test:
 *   1. Make sure MySQL is running and tenantease_testdb exists.
 *   2. Remove the @Disabled annotation temporarily.
 *
 * For CI/CD builds, always use: mvn clean install -DskipTests
 * OR keep this @Disabled and write focused unit/slice tests instead.
 */
@Disabled("Requires live MySQL + Flyway schema. Run manually after DB setup.")
@SpringBootTest
class TenantEaseApplicationTests {

	@Test
	void contextLoads() {
		// Verifies that the Spring ApplicationContext loads without errors.
		// Re-enable this after running V1 + V2 Flyway migrations on a fresh DB.
	}

}
