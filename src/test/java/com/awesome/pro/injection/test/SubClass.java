package com.awesome.pro.injection.test;

import com.awesome.pro.utilities.injection.InjectRecursively;

public class SubClass extends SuperClass {

	public SampleField2 field21;
	private SampleField2 field22;
	protected SampleField2 field23;

	public String field24;
	private String field25;
	protected String field26;

	// Inject to super class fields with inner members.
	@InjectRecursively(field = "field11", member = "member11")
	private String member21 = "1111";

	@InjectRecursively(field = "field11", member = "member12")
	private String member22 = "1112";

	@InjectRecursively(field = "field11", member = "member13")
	private String member23 = "1113";

	@InjectRecursively(field = "field12", member = "member11")
	private String member24 = "1211";

	@InjectRecursively(field = "field12", member = "member12")
	private String member25 = "1212";

	@InjectRecursively(field = "field12", member = "member13")
	private String member26 = "1213";

	@InjectRecursively(field = "field13", member = "member11")
	private String member27 = "1311";

	@InjectRecursively(field = "field13", member = "member12")
	private String member28 = "1312";

	@InjectRecursively(field = "field13", member = "member13")
	private String member29 = "1313";

	// Inject to primitive fields in same class.
	@InjectRecursively(field = "field24")
	private String member30 = "24";

	@InjectRecursively(field = "field25")
	private String member31 = "25";

	@InjectRecursively(field = "field26")
	private String member32 = "26";

	// Inject to primitive fields in super class.
	@InjectRecursively(field = "field14")
	private String member33 = "14";

	@InjectRecursively(field = "field15")
	private String member34 = "15";

	@InjectRecursively(field = "field16")
	private String member35 = "16";

	// Inject to same class fields with inner members.
	@InjectRecursively(field = "field21", member = "member21")
	private String member36 = "2121";

	@InjectRecursively(field = "field21", member = "member22")
	private String member37 = "2122";

	@InjectRecursively(field = "field21", member = "member23")
	private String member38 = "2123";

	@InjectRecursively(field = "field22", member = "member21")
	private String member39 = "2221";

	@InjectRecursively(field = "field22", member = "member22")
	private String member40 = "2222";

	@InjectRecursively(field = "field22", member = "member23")
	private String member41 = "2223";

	@InjectRecursively(field = "field23", member = "member21")
	private String member42 = "2321";

	@InjectRecursively(field = "field23", member = "member22")
	private String member43 = "2322";

	@InjectRecursively(field = "field23", member = "member23")
	private String member44 = "2323";

	/**
	 * @return the field21
	 */
	public SampleField2 getField21() {
		return field21;
	}

	/**
	 * @return the field22
	 */
	public SampleField2 getField22() {
		return field22;
	}

	/**
	 * @return the field23
	 */
	public SampleField2 getField23() {
		return field23;
	}

	/**
	 * @return the field24
	 */
	public String getField24() {
		return field24;
	}

	/**
	 * @return the field25
	 */
	public String getField25() {
		return field25;
	}

	/**
	 * @return the field26
	 */
	public String getField26() {
		return field26;
	}

}
