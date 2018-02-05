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

public class TelegramHandler {
	private String defaultUrl = "https://api.telegram.org/bot";
	private String botToken;
	private String botUsername;
	private String chatId;

	public TelegramHandler(String botToken, String botUsername, String chatId) {
		this.botToken = botToken;
		this.botUsername = botUsername;
		this.chatId = chatId;
		defaultUrl += botToken;
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

		return new JSONObject(buffer.toString());
	}

}
