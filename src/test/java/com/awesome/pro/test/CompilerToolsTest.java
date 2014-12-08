package com.awesome.pro.test;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.awesome.pro.utilities.Assert;
import com.awesome.pro.utilities.CompilerTools;


public class CompilerToolsTest {

	@Test
	public void testCompilation() {
		Set<String> fields = new HashSet<>();
		Set<String> methods = new HashSet<>();

		fields.add("public String field1;");
		fields.add("public int field2;");

		methods.add("public String toString() {"
				+ "		return field1 + \" \" + field2;"
				+ "}");

		CompilerTools.compile("com.example.DynamicClass", fields, methods);
		try {
			Class.forName("com.example.DynamicClass");
		} catch (ClassNotFoundException e) {
			Assert.assertEquals("Class not found.", "Class should have been found.");
		}
	}

	@Test (dependsOnMethods = {"testCompilation"})
	public void testInstantiation() {
		Object object = CompilerTools.instantiate("com.example.DynamicClass");
		Field[] fields = object.getClass().getDeclaredFields();
		Assert.assertEquals(fields.length, 2);

		for (int i = 0; i < fields.length; i ++) {
			if (fields[i].getName().equals("field1")) {
				Assert.assertEquals(fields[i].getType().equals(String.class), true);
			}
			else if (fields[i].getName().equals("field2")) {
				Assert.assertEquals(fields[i].getType().equals(Integer.class), true);
			}
			else {
				Assert.assertEquals("Extra field found: " + fields[i].getName(),
						"Extra fields should not be found.");
			}
		}
	}

	

}
