package com.cglee079.portfolio.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;

import com.google.gson.JsonObject;
import com.sun.org.apache.xerces.internal.util.URI;

public class MyTelegramHandler {
	private String defaultUrl = "https://api.telegram.org/bot";
	private String botToken;
	private String botUsername;
	private String chatId;
	// https://api.telegram.org/bot485435157:AAGKQGOxBebqXEPTt0Zovfy9S48JfSqGt0s/sendmessage?chat_id=503609560&text=dfdfdfd

	public MyTelegramHandler(String botToken, String botUsername, String chatId) {
		this.botToken = botToken;
		this.botUsername = botUsername;
		this.chatId = chatId;
		defaultUrl += botToken;
	}

	public Map<String, String> getQueryParser(String query) {

		Map<String, String> returnData = new HashMap<String, String>();
		// query is from getQuery()
		StringTokenizer st = new StringTokenizer(query, "&", false);

		while (st.hasMoreElements()) {
			// First Pass to retrive the
			// "parametername=value" combo
			String paramValueToken = st.nextElement().toString();
			// StringTokenizer stParamVal = new
			// StringTokenizer(paramValueToken,"=", false );

			// 방식 변경
			String[] strParamVal = paramValueToken.split("=", 2);
			String paramName = strParamVal[0];
			String paramValue = strParamVal[1];
			returnData.put(paramName, paramValue);

			/*
			 * int i = 0; while (stParamVal.hasMoreElements()) { //Second pass
			 * to separate the "paramname" and "value". // 1st token is param
			 * name // 2nd token is param value
			 * 
			 * String separatedToken = stParamVal.nextElement().toString();
			 * 
			 * if ( i== 0) { //This indicates that it is the param name : ex
			 * val4,val5 etc paramName = separatedToken; } else { // This will
			 * hold value of the parameter paramValue = separatedToken; }
			 * 
			 * i++; }
			 */
		}
		return returnData;

	}

	public JSONObject sendMessage(String text) throws IOException {
		String sendUrl = defaultUrl;
		sendUrl += "/sendmessage";
		sendUrl += "?";
		sendUrl += "chat_id=";
		sendUrl += chatId;
		sendUrl += "&text=";
		sendUrl += URLEncoder.encode(text, "UTF-8");

		HttpURLConnection connection = null;
		
		URL url = new URL(sendUrl);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(3000);
		connection.setReadTimeout(3000);

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line).append("\r\n");
		}
		reader.close();

		JSONObject result = new JSONObject(buffer.toString());

		return result;
	}

}
