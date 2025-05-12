package com.haw.se1lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test class which secures that running all tests with the wrong Java version can't be successful.
 * 
 * @author Arne Busch
 */
@SpringBootTest(classes = Application.class) // environment
@ExtendWith(SpringExtension.class) // required to use Spring TestContext Framework in JUnit 5
@ActiveProfiles("test") // causes exclusive creation of general and test-specific beans (marked by @Profile("test"))
public class JavaVersionTest {

	@Test
	public void checkJavaRuntimeVersion() {
		// prevent running the application with an inappropriate Java version
		JavaVersionUtil.checkJavaRuntimeVersion();
	}

}
