package com.awesome.pro.utilities;

/**
 * Enumeration of annotation member value types.
 * @author siddharth.s
 */
public enum AnnotationMemberType {

	BOOLEAN,

	INTEGER,

	STRING,

	ENUM,

	CLASS;

	/**
	 * @param type String formatted member type.
	 * @return Parsed enumeration type.
	 */
	public static final AnnotationMemberType parseAnnotationMemberType(
			final String type) {

		if (type == null || type.length() == 0) {
			return STRING;
		}

		switch (type) {
			case "INTEGER":
			case "integer":
				return INTEGER;

			case "BOOLEAN":
			case "boolean":
				return BOOLEAN;

			case "ENUM":
			case "enum":
				return ENUM;

			case "CLASS":
			case "class":
				return CLASS;

			case "STRING":
			case "string":
			default:
				return STRING;
		}
	}

}
