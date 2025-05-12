package com.haw.se1lab;

import org.springframework.boot.system.JavaVersion;

/**
 * Contains utility methods for Java version handling.
 * 
 * @author Arne Busch
 */
public class JavaVersionUtil {

	/** The Java runtime version the application is supposed to run with. */
	public static JavaVersion REQUIRED_VERSION = JavaVersion.SEVENTEEN;

	/**
	 * Checks if the Java runtime version used for execution matches the required one and throws an exception if not.
	 * 
	 * @throws IllegalStateException in case the used runtime environment doesn't match the required one
	 */
	public static void checkJavaRuntimeVersion() throws IllegalStateException {
		if (JavaVersion.getJavaVersion() != REQUIRED_VERSION) {
			throw new IllegalStateException(
					"Wrong version of Java Runtime Environment used for execution! Required version: "
							+ REQUIRED_VERSION);
		}
	}

}
