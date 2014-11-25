package com.awesome.pro.compile.test;

import com.awesome.pro.utilities.compile.CompilerTools;

public class Test1 {

	public static void main(String[] args) {
		CompilerTools.init();
		String name = "com.awesome.Dyna";
		String source = "public class Dyna {\n"
				+ "		public String toString() {\n"
				+ "			return \"Hello, world from \""
				+ " 				+ this.getClass().getSimpleName();\n"
				+ "		}\n"
				+ "}\n";
		CompilerTools.compile(name, source);
		Object obj = CompilerTools.instantiate(name);
		System.out.println(obj);
		CompilerTools.shutdown();
	}

}
