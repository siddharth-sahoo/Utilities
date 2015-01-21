package com.awesome.pro.utilities;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;

/**
 * This allows JavaScripts to be executed using javax script engine.
 * The script is not executed in the browser context. 
 * @author siddharth.s
 * @review pending
 */
public class JavaScriptEngine {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			JavaScriptEngine.class);
	
	/**
	 * Script engine which is used to execute JavaScripts.
	 */
	private ScriptEngine engine;

	/**
	 * Java Script Engine constructor.
	 */
	public JavaScriptEngine() {
		this.engine = new ScriptEngineManager().getEngineByName("JavaScript");
	}
	
	/**
	 * @param script String JavaScript to be executed.
	 * @return Return value of the specified script.
	 * @throws ScriptException When there is an error in the script.
	 */
	private Object executeJS(String script) throws ScriptException {
		Object object = null;
		try {
			object = this.engine.eval(script);
		}
		catch (ScriptException e) {
			LOGGER.error("Error in executing script.", e);
			throw e;
		}
		return object;
	}
	
	/**
	 * @param script String JavaScript to be executed.
	 * @return String formatted return value of the script.
	 * @throws ScriptException When there is an error in the script.
	 */
	public String getStringOutput(String script) throws ScriptException {
		return (String) executeJS(script);
	}
	
	/**
	 * @param script String JavaScript to be executed.
	 * @return Double formatted return value of the script.
	 * @throws ScriptException When there is an error in the script.
	 * @throws NumberFormatException When there is an error in parsing the
	 * output as a double.
	 */
	public double getDoubleOutput(String script) throws NumberFormatException, ScriptException {
		return Double.parseDouble(getStringOutput(script));
	}
	
	/**
	 * @param script String JavaScript to be executed.
	 * @return Integer formatted return value of the script.
	 * @throws ScriptException When there is an error in the script.
	 * @throws NumberFormatException When there is an error in parsing the
	 * output as an integer.
	 */
	public int getIntegerOutput(String script) throws NumberFormatException, ScriptException {
		return Integer.parseInt(getStringOutput(script));
	}
	
	/**
	 * @param script String JavaScript to be executed.
	 * @return Boolean formatted return value of the script.
	 * @throws ScriptException When there is an error in the script.
	 */
	public boolean getBooleanOutput(String script) throws ScriptException {
		String out = getStringOutput(script).toLowerCase();
		return Boolean.parseBoolean(out);
	}

}
