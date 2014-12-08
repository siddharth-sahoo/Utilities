package com.awesome.pro.utilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.apache.log4j.Logger;

/**
 * Wrapper on Javassist compiler tools. This can be used to dynamically
 * declare classes.
 * @author siddharth.s
 */
public class CompilerTools {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			CompilerTools.class);

	/**
	 * Javassist class loader.
	 */
	private static final JavassistClassLoader classLoader =
			new JavassistClassLoader();

	/**
	 * @param name Fully qualified name of the class.
	 * @param fields Set of source code for all the fields in the class.
	 * @param methods Set of source code for all the methods in the class.
	 */
	public static final void compile(final String name,
			final Set<String> fields, final Set<String> methods) {
		ClassPool pool = ClassPool.getDefault();
		CtClass compileTimeClass = pool.makeClass(name);

		final Iterator<String> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			final String field = fieldIterator.next();
			try {
				compileTimeClass.addField(CtField.make(
						field, compileTimeClass));
			} catch (CannotCompileException e) {
				LOGGER.error("Compile error.", e);
				System.exit(1);
			}
		}

		final Iterator<String> methodIterator = methods.iterator();
		while (methodIterator.hasNext()) {
			final String method = methodIterator.next();
			try {
				compileTimeClass.addMethod(CtNewMethod.make(
						method, compileTimeClass));
			} catch (CannotCompileException e) {
				LOGGER.error("Compile error.", e);
				System.exit(1);
			}
		}

		try {
			compileTimeClass.writeFile();
		} catch (CannotCompileException | NotFoundException e) {
			LOGGER.error("Compiler error.", e);
			System.exit(1);
		} catch (IOException e) {
			LOGGER.error("Error writing to file.", e);
			System.exit(1);
		}

		classLoader.findClass(name);
	}

	/**
	 * @param <T> Class of the instance being sought.
	 * @param name Fully qualified name of the class.
	 * @return Instance of the class.
	 */
	public static final <T> T instantiate(final String name) {
		CtClass cc1 = null;
		try {
			cc1 = ClassPool.getDefault().get(name);
		} catch (NotFoundException e) {
			LOGGER.error("Class not found: " + name, e);
			return null;
		}

		try {
			T obj = (T) cc1.toClass().newInstance();
			return obj;
		} catch (InstantiationException | IllegalAccessException
				| CannotCompileException e) {
			LOGGER.error("Error creating a new instance.", e);
			return null;
		}
	}

	// Private constructor.
	private CompilerTools() { }

}
