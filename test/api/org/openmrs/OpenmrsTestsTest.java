/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.openmrs.util.OpenmrsUtil;

/**
 * Runs tests on the openmrs junit tests TODO: add unit test to make sure all tests have a call to
 * assert* in them. This would help prevent people from making tests that just print results to the
 * screen
 */
@SuppressWarnings("unchecked")
public class OpenmrsTestsTest {
	
	private ClassLoader classLoader = this.getClass().getClassLoader();
	
	private List<Class> testClasses = null;
	
	/**
	 * Make sure there is at least one _other_ test case out there
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldHaveAtLeastOneTest() throws Exception {
		List<Class> classes = getTestClasses();
		
		assertTrue("There should be more than one class but there was only " + classes.size(), classes.size() > 1);
	}
	
	/**
	 * Makes sure all test methods in org.openmrs.test start with the word "should"
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldStartWithShould() throws Exception {
		
		List<Class> classes = getTestClasses();
		
		for (Class<TestCase> currentClass : classes) {
			for (Method method : currentClass.getMethods()) {
				
				// make sure every "test" method (determined by having 
				// the @Test annotation) starts with "testShould"
				if (method.getAnnotation(Test.class) != null) {
					String methodName = method.getName();
					
					boolean passes = methodName.startsWith("should") || methodName.contains("_should");
					assertTrue(currentClass.getName() + "#" + methodName
					        + " is supposed to either 1) start with 'should' or 2) contain '_should' but it doesn't", passes);
				}
			}
		}
	}
	
	/**
	 * Makes sure all "should___" methods in org.openmrs.test have an "@Test" annotation on it. This
	 * is to help prevent devs from forgetting to put the annotation and then seeing all tests pass
	 * because the new test wasn't actually ran
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldHaveTestAnnotationWhenStartingWithShould() throws Exception {
		// loop over all methods in all test classes
		for (Class<TestCase> currentClass : getTestClasses()) {
			for (Method method : currentClass.getMethods()) {
				String methodName = method.getName();
				
				// make sure every should___ method has an @Test annotation
				if (methodName.startsWith("should") || methodName.contains("_should")) {
					assertTrue(currentClass.getName() + "#" + methodName
					        + " does not have the @Test annotation on it even though the method name starts with 'should'",
					    method.getAnnotation(Test.class) != null);
				}
			}
		}
	}
	
	/**
	 * Get all classes in the org.openmrs.test package
	 * 
	 * @return list of TestCase classes in org.openmrs.test
	 */
	private List<Class> getTestClasses() {
		if (testClasses != null)
			return testClasses;
		
		URL url = classLoader.getResource("org/openmrs/test");
		File directory = OpenmrsUtil.url2file(url);
		// make sure we get a directory back
		assertTrue("org.openmrs.test should be a directory", directory.isDirectory());
		
		testClasses = getTestClassesInDirectory(directory);
		
		// check to see if the web layer is also included.  Skip it if its not there
		url = classLoader.getResource("org/openmrs/web/test");
		if (url != null) {
			directory = OpenmrsUtil.url2file(url);
			// make sure we get a directory back
			assertTrue("org.openmrs.web.test should be a directory", directory.isDirectory());
			
			testClasses.addAll(getTestClassesInDirectory(directory));
		}
		
		return testClasses;
	}
	
	/**
	 * Recurses into the given directory checking that all test methods start with "testShould"
	 * 
	 * @param directory to loop through the files of
	 */
	private List<Class> getTestClassesInDirectory(File directory) {
		
		List<Class> currentDirTestClasses = new ArrayList<Class>();
		
		for (File currentFile : directory.listFiles()) {
			
			// if looking at a folder, recurse into it
			if (currentFile.isDirectory()) {
				currentDirTestClasses.addAll(getTestClassesInDirectory(currentFile));
			}
			
			if (currentFile.getName().endsWith("class")) {
				// strip off the ending
				String className = currentFile.getAbsolutePath().replace(".class", "");
				
				// switch to dot separation
				className = className.replace("/", ".");
				
				// strip out the beginning up to org.openmrs.
				className = className.substring(className.indexOf("org.openmrs."));
				
				try {
					Class<?> currentClass = classLoader.loadClass(className);
					
					currentDirTestClasses.add(currentClass);
					
				}
				catch (ClassNotFoundException e) {
					System.out.println("Unable to load class: " + className + " error: " + e.getMessage());
				}
			}
		}
		
		return currentDirTestClasses;
	}
	
}
