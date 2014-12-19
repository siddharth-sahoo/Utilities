package com.awesome.pro.utilities.injection;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation enables injection of dependencies recursively within
 * a class hierarchy. This injection is reverse in the manner that this
 * annotation is placed on the field that contributes the value and not
 * where it is injected.
 * @author siddharth.s
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectRecursively {

	/**
	 * @return Name of the target field. This field could be a declared field
	 * in the same class or one of its super classes.
	 */
	String field();

	/**
	 * @return Name of the member variable of the target field class where the
	 * value will be injected.
	 */
	String member() default "";

}
