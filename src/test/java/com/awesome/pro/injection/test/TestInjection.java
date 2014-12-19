package com.awesome.pro.injection.test;

import org.junit.Before;
import org.junit.Test;

import com.awesome.pro.utilities.Assert;
import com.awesome.pro.utilities.injection.InjectionUtils;

/**
 * Tests annotation driven dependency injection.
 * @author siddharth.s
 */
public class TestInjection {

	private SubClass object;

	@Before
	public void setUp() throws Exception {
		object = new SubClass();
		object = InjectionUtils.inject(object);
	}

	@Test
	public void test1() {
		Assert.assertEquals(object.getField11().getMember11(), "1111");
	}

	@Test
	public void test2() {
		Assert.assertEquals(object.getField11().getMember12(), "1112");
	}

	@Test
	public void test3() {
		Assert.assertEquals(object.getField11().getMember13(), "1113");
	}

	@Test
	public void test4() {
		Assert.assertEquals(object.getField12().getMember11(), "1211");
	}

	@Test
	public void test5() {
		Assert.assertEquals(object.getField12().getMember12(), "1212");
	}

	@Test
	public void test6() {
		Assert.assertEquals(object.getField12().getMember11(), "1211");
	}

	@Test
	public void test7() {
		Assert.assertEquals(object.getField13().getMember11(), "1311");
	}

	@Test
	public void test8() {
		Assert.assertEquals(object.getField13().getMember12(), "1312");
	}

	@Test
	public void test9() {
		Assert.assertEquals(object.getField13().getMember13(), "1313");
	}

	@Test
	public void test10() {
		Assert.assertEquals(object.getField21().getMember21(), "2121");
	}

	@Test
	public void test11() {
		Assert.assertEquals(object.getField21().getMember22(), "2122");
	}

	@Test
	public void test12() {
		Assert.assertEquals(object.getField21().getMember23(), "2123");
	}

	@Test
	public void test13() {
		Assert.assertEquals(object.getField22().getMember21(), "2221");
	}

	@Test
	public void test14() {
		Assert.assertEquals(object.getField22().getMember22(), "2222");
	}

	@Test
	public void test15() {
		Assert.assertEquals(object.getField22().getMember23(), "2223");
	}

	@Test
	public void test16() {
		Assert.assertEquals(object.getField23().getMember21(), "2321");
	}

	@Test
	public void test17() {
		Assert.assertEquals(object.getField23().getMember22(), "2322");
	}

	@Test
	public void test18() {
		Assert.assertEquals(object.getField23().getMember23(), "2323");
	}

	@Test
	public void test19() {
		Assert.assertEquals(object.getField24(), "24");
	}

	@Test
	public void test20() {
		Assert.assertEquals(object.getField25(), "25");
	}

	@Test
	public void test21() {
		Assert.assertEquals(object.getField26(), "26");
	}

	@Test
	public void test22() {
		Assert.assertEquals(object.getField14(), "14");
	}

	@Test
	public void test23() {
		Assert.assertEquals(object.getField15(), "15");
	}

	@Test
	public void test24() {
		Assert.assertEquals(object.getField16(), "16");
	}

}
