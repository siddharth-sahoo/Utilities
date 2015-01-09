package com.awesome.pro.utilities.network;

import java.io.IOException;
import java.security.GeneralSecurityException;


public interface IHTTPCallActions {

	/**
	 * @param url URL on which the GET request will be made, without the parameters.
	 * @param parameters String parameters to be passed on in the request.
	 * @param cookieValue Cookie data in a string format. This format is
	 * similar to that of request parameters.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makeGetCall(String url, String parameters, String cookieValue) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the GET request will be made, without the parameters.
	 * @param parameters String parameters to be passed on in the request.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makeGetCall(String url, String parameters) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the GET request will be made, without the parameters.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makeGetCall(String url) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the POST request will be made, without the parameters.
	 * @param parameters String parameters to be passed on in the request.
	 * @param cookieValue Cookie data in a string format. This format is
	 * similar to that of request parameters.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makePostCall(String url, String parameters, String cookieValue) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the POST request will be made, without the parameters.
	 * @param parameters String parameters to be passed on in the request.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makePostCall(String url, String parameters) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the PUT request will be made, without the parameters.
	 * @param parameters String parameters to be passed on in the request.
	 * @param cookieValue Cookie data in a string format. This format is
	 * similar to that of request parameters.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makePutCall(String url, String parameters, String cookieValue) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the PUT request will be made, without the parameters.
	 * @param parameters String parameters to be passed on in the request.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makePutCall(String url, String parameters) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the DELETE request will be made, without the parameters.
	 * @param cookieValue Cookie data in a string format. This format is
	 * similar to that of request parameters.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makeDeleteCall(String url, String cookieValue) throws IOException, GeneralSecurityException;

	/**
	 * @param url URL on which the DELETE request will be made, without the parameters.
	 * @param parameters String parameters to be passed on in the request.
	 * @return HTTP response object.
	 * @throws IOException When the URL is malformed or the host cannot be
	 * resolved or connected to.
	 * @throws GeneralSecurityException When there is an error in ignoring
	 * certificate errors.
	 */
	public IHTTPResponse makeDeleteCall(String url) throws IOException, GeneralSecurityException;

}
