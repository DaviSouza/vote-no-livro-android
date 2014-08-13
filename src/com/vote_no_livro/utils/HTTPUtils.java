package com.vote_no_livro.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HTTPUtils {

	public static void POST(String url, String json) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(url);
			StringEntity input;
			input = new StringEntity(json);
			input.setContentType("application/json");
			postRequest.setEntity(input);
			httpClient.execute(postRequest);
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(HTTPUtils.class.getName()).log(Level.SEVERE, null,ex);
		} catch (IOException ex) {
			Logger.getLogger(HTTPUtils.class.getName()).log(Level.SEVERE, null,ex);
		}
	}

	public static String GET(String url) {
		InputStream inputStream = null;
		String result = "";
		try {
			inputStream = getInputStream(url);
			// convert inputstream to string
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
			} else {
				result = "Did not work!";
			}

		} catch (Exception e) {
			Logger.getLogger(HTTPUtils.class.getName()).log(Level.SEVERE, null,
					e);
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, "ISO-8859-1"));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}
		inputStream.close();
		return result;
	}

	public static InputStream getInputStream(String url) {
		try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// httpclient.getParams().setIntParameter("http.socket.timeout",10000);
			// httpclient.getParams().setParameter("User-Agente", "Leo");
			// httpclient.getParams().setIntParameter("http.connection.timeout",4000);
			// make GET request to the given URL
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpclient.execute(httpGet);
			// receive response as inputStream
			return httpResponse.getEntity().getContent();
			// } catch (Exception e) {
			// Logger.getLogger(HTTPUtils.class.getName()).log(Level.SEVERE,
			// null,e);
			// return null;
			// }

		} catch (ClientProtocolException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		} catch (IOException e) {
			Log.d("HTTPCLIENT", e.getLocalizedMessage());
		}
		return null;
	}

}
