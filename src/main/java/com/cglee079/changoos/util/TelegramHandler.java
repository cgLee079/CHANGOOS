package com.cglee079.changoos.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TelegramHandler {
	private String defaultUrl = "https://api.telegram.org/bot";
	
	@Value("#{telegram['bot.token']}") 		private String botToken;
	@Value("#{telegram['bot.username']}") 	private String botUsername;
	@Value("#{telegram['my.chat.id']}")  	private String chatId;

	public JSONObject sendMessage(String text) throws IOException {
		String sendUrl = defaultUrl + botToken;
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
