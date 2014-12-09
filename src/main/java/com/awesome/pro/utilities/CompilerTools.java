package com.awesome.pro.utilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

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
	 * This is a separator for specifying fully qualified enumeration class
	 * name and enumeration constant value.
	 * E.g. org.openqa.selenium.support.How~NAME,
	 * where the class name is org.openqa.selenium.support.How
	 * and the constant is NAME.
	 */
	public static final char ENUM_PARAM_SEPARATOR = '~';

	/**
	 * @param className Fully qualified name of the class.
	 * @param fields Set of source code for all the fields in the class.
	 * @param methods Set of source code for all the methods in the class.
	 */
	public static final void compile(final String className,
			final Set<String> fields, final Set<String> methods) {
		ClassPool pool = ClassPool.getDefault();
		CtClass compileTimeClass = pool.makeClass(className);

		if (fields != null) {
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
		}

		if (methods != null) {
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

		classLoader.findClass(className);
	}

	/**
	 * @param className Fully qualified name of the class.
	 * @param fieldName Name of the field to annotate.
	 * @param annotationName Fully qualified name of the annotation.
	 * @param members Members of the annotation. Outer key represents member
	 * name. Value is a pair of string parameter and member value data type.
	 * <br />For boolean types, parameter is true or false.
	 * <br />For class types, parameter is fully qualified name of the class.
	 * <br />For enum types, parameter is fully qualified name of the
	 * annotation concatenated with the constant enum value separated by ~.
	 * <br />For integer types, parameter is the integer itself.
	 * <br />For string types, parameter is the string value itself.
	 */
	public static final void annotateField(final String className,
			final String fieldName, final String annotationName,
			final Map<String, Entry<String, AnnotationMemberType>> members) {
		final ClassPool pool = ClassPool.getDefault();

		// Retrieve reference to class.
		CtClass classRef = null;
		try {
			classRef = pool.get(className);
		} catch (NotFoundException e) {
			LOGGER.error("Class not found: " + className);
			return;
		}

		// Defrost to allow changes.
		classRef.defrost();

		// Retrieve reference to field.
		CtField field = null;
		try {
			field = classRef.getDeclaredField(fieldName);
		} catch (NotFoundException e) {
			LOGGER.error("Field not found: " + fieldName);
			return;
		}

		final ConstPool constantPool = classRef.getClassFile().getConstPool();
		final AnnotationsAttribute attribute = new AnnotationsAttribute(
				constantPool, AnnotationsAttribute.visibleTag);
		final Annotation annotation = new Annotation(
				annotationName, constantPool);

		// Add annotation members.
		if (members != null && members.size() != 0) {
			final Iterator<Entry<String, Entry<String, AnnotationMemberType>>> iter =
					members.entrySet().iterator();
			while (iter.hasNext()) {
				final Entry<String, Entry<String, AnnotationMemberType>> entry =
						iter.next();

				// Switch case for annotation member value type.
				switch (entry.getValue().getValue()) {
					case BOOLEAN:
						annotation.addMemberValue(entry.getKey(),
								new BooleanMemberValue(
										Boolean.valueOf(entry.getValue().getKey()),
										constantPool));
						break;

					case STRING:
						annotation.addMemberValue(entry.getKey(),
								new StringMemberValue(
										entry.getValue().getKey(),
										constantPool));
						break;

					case INTEGER:
						try {
							annotation.addMemberValue(entry.getKey(),
									new IntegerMemberValue(
											Integer.parseInt(entry.getValue().getKey()),
											constantPool));
						} catch (NumberFormatException e) {
							LOGGER.error("Illegal arguments provided, annotation not added.", e);
							return;
						}
						break;

					case CLASS:
						annotation.addMemberValue(entry.getKey(), 
								new ClassMemberValue(entry.getValue().getKey(), constantPool));
						break;

					case ENUM:
						final EnumMemberValue member =
							new EnumMemberValue(constantPool);
						final String[] params = StringUtils.split(
								entry.getValue().getKey(),
								ENUM_PARAM_SEPARATOR);

						if (params.length != 2) {
							LOGGER.error("Invalid arguments provided, annotation not added.");
							return;
						}
						member.setType(params[0]);
						member.setValue(params[1]);
						annotation.addMemberValue(entry.getKey(), member);
						break;
				}
			}
		}

		// Add annotation to attribute and attribute to field.
		attribute.addAnnotation(annotation);
		field.getFieldInfo().addAttribute(attribute);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Annotated " + fieldName + " field of class "
					+ className);
		}
	}

	/**
	 * @param <T> Class of the instance being sought.
	 * @param className Fully qualified name of the class.
	 * @return Instance of the class.
	 */
	public static final <T> T instantiate(final String className) {
		CtClass cc1 = null;
		try {
			cc1 = ClassPool.getDefault().get(className);
		} catch (NotFoundException e) {
			LOGGER.error("Class not found: " + className, e);
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
