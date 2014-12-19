package com.awesome.pro.utilities.injection;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;


/**
 * Utility class for annotation driven dependency injection.
 * @author siddharth.s
 */
public class InjectionUtils {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			InjectionUtils.class);

	/**
	 * @param t Object to modify.
	 * @return Modified object after completing all injections. It scans for
	 * all value contributing fields in the class and sets the value extracted
	 * into the specified target field in the same class or one of the super
	 * classes.
	 */
	public static <T> T inject(T t) {
		final Field[] fields = t.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i ++) {
			fields[i].setAccessible(true);
			final InjectRecursively ann = fields[i].getAnnotation(
					InjectRecursively.class);

			if (ann == null) {
				continue;
			}

			Object value = null;
			try {
				value = fields[i].get(t);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				LOGGER.warn("Unable to access field value.", e);
				continue;
			}

			if (value == null) {
				LOGGER.warn("Value to be injected is null, ignoring. Field: "
						+ fields[i].getName());
				continue;
			}

			// Locate the target field.
			final String targetFieldName = ann.field();
			final Field targetField = findField(t.getClass(), targetFieldName);
			targetField.setAccessible(true);

			// Set value depending on whether trying to set a primitive field.
			if (ann.member() == null || ann.member().length() == 0) {
				// Primitive type, directly set value.
				try {
					targetField.set(t, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.warn("Unable to access field " + targetFieldName
							+ " in " + t.getClass(), e);
					continue;
				}
			} else {
				// Custom type, inner member is to be set.
				Object targetValue = null;
				try {
					targetValue = targetField.get(t);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.warn("Unable to access " + targetFieldName, e);
					continue;
				}

				// Field value is null, set a new instance.
				if (targetValue == null) {
					try {
						targetValue = targetField.getType().newInstance();
						targetField.set(t, targetValue);
					} catch (IllegalArgumentException | IllegalAccessException
							| InstantiationException e) {
						LOGGER.warn("Unable to instantiate target field "
							+ targetFieldName);
						continue;
					}
				}

				// Set inner field
				try {
					final Field innerTargetField = targetValue.getClass()
							.getDeclaredField(ann.member());
					innerTargetField.setAccessible(true);
						innerTargetField.set(targetValue, value);
				} catch (NoSuchFieldException e) {
					LOGGER.warn("Inner field not found " + ann.member());
					continue;
				} catch (SecurityException e) {
					LOGGER.warn("Unable to access inner field " + ann.member());
					continue;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.warn("Unable to set inner field value " + ann.member());
					continue;
				}
			}
		}
		return t;
	}

	/**
	 * @param klass Class reference.
	 * @param fieldName Name of the field to find in the given class and all
	 * its super classes.
	 * @return Reference to field if found, else null.
	 */
	private static Field findField(Class<?> klass, final String fieldName) {
		while (klass != klass.getSuperclass()) {
			try {
				return klass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				klass = klass.getSuperclass();
				continue;
			} catch (SecurityException e) {
				LOGGER.warn("Unable to access field: " + fieldName
						+ " in " + klass.getName(), e);
				return null;
			}
		}

		LOGGER.warn(fieldName + " field not found.");
		return null;
	}

	// Private constructor for utility class.
	private InjectionUtils() { }

}
