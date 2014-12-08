package com.awesome.pro.utilities;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.apache.log4j.Logger;

/**
 * @author siddharth.s
 *
 */
public class JavassistClassLoader extends ClassLoader {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			JavassistClassLoader.class);

	/**
	 * Javassist class pool.
	 */
	private ClassPool pool;

	// Constructor.
	public JavassistClassLoader() {
		pool = ClassPool.getDefault();
		try {
			pool.insertClassPath("./class");
		} catch (NotFoundException e) {
			LOGGER.error("Class path could not be located.", e);
			System.exit(1);
		}
		// MyApp.class must be there.
	}

	/* Finds a specified class.
	 * The bytecode for that class can be modified.
	 */

	protected Class<?> findClass(String name) {
		try {
			CtClass cc = pool.get(name);
			// modify the CtClass object here
			byte[] b = cc.toBytecode();
			return defineClass(name, b, 0, b.length);
		} catch (NotFoundException | IOException | CannotCompileException e) {
			LOGGER.error("Error in finding the class: " + name, e);
			System.exit(1);
		}
		return null;
	}

}
