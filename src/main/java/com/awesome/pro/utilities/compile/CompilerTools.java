package com.awesome.pro.utilities.compile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;


/**
 * Wrapper on JDK compiler tools. This can be used to dynamically
 * declare classes.
 * @author siddharth.s
 */
public class CompilerTools {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			CompilerTools.class);

	private static StandardJavaFileManager FILE_MANAGER;

	/**
	 * Initializes the compiler tools.
	 */
	public static final synchronized void init() {
		FILE_MANAGER = ToolProvider.getSystemJavaCompiler().
					getStandardFileManager(null, null, null);
	}

	/**
	 * @param source Java source code to compile.
	 * @param name Full name of the class.
	 */
	public static final void compile(final String name, final String source) {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<JavaFileObject> files = new LinkedList<>();
		files.add(new CharSequenceJavaFileObject(name, source));
		compiler.getTask(null, FILE_MANAGER, null, null, null, files).call();
		LOGGER.info("Compiled " + name);
	}

	public static final Object instantiate(final String name) {
		try {
			return FILE_MANAGER.getClassLoader(null)
					.loadClass(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("Unable to instantiate class: " + name, e);
			System.exit(1);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Class not found: " + name, e);
			System.exit(1);
		}
		return null;
	}

	public static final void shutdown() {
		try {
			FILE_MANAGER.close();
		} catch (IOException e) {
			LOGGER.error("Unable to close file manager.", e);
			System.exit(1);
		}
	}

	// Private constructor.
	private CompilerTools() { }

}
