package com.example.restservice;

import org.json.simple.JSONObject;

/**
 * The type Greeting.
 */
public class Greeting {

	private final String token;
	private final JSONObject data;
	private final JSONObject decrypt;

	/**
	 * Instantiates a new Greeting.
	 *  @param token   the token
	 * @param data    the data
	 * @param decrypt the decrypt
	 */
	public Greeting(String token, JSONObject data, JSONObject decrypt) {
		this.token = token;
		this.data = data;
		this.decrypt = decrypt;
	}

	/**
	 * Gets token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Gets data.
	 *
	 * @return the data
	 */
	public JSONObject getData() {
		return data;
	}

	/**
	 * Gets decrypt.
	 *
	 * @return the decrypt
	 */
	public JSONObject getDecrypt() {
		return decrypt;
	}
}
