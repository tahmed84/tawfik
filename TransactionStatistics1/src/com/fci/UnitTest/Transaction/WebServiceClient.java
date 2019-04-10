package com.fci.UnitTest.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServiceClient {
	public static void main(String[] args) {

		HttpURLConnection conn = null;
		try {

			URL url = new URL(
					"http://127.0.0.1:8080/transactionWs/transaction/pushTransaction?trdate=xxxxxxxxx&trvalue=xyz");// your
																																// url
																																// i.e
																																// fetch
																																// data
																																// from
																																// .
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			InputStreamReader in = null;

			if (conn.getResponseCode() == 200) {
				in = new InputStreamReader(conn.getInputStream());
			} else {

				in = new InputStreamReader(conn.getErrorStream());

			}

			BufferedReader br = new BufferedReader(in);
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	public static int testTransactionWS(String trDate, String trValue) {

		HttpURLConnection conn = null;
		try {
			trDate = trDate.replace(" ", "%20");
			// URL url = new
			// URL("http://127.0.0.1:8080/transactionWs/transaction/pushTransaction?trdate="+trDate+"&trvalu="+trValue);//your
			// url i.e fetch data from .
			URL url = new URL("http://127.0.0.1:8080/transactionWs/transaction/pushTransaction?trdate=" + trDate
					+ "&trvalue=" + trValue);// your url i.e fetch data from .
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			InputStreamReader in = null;

			return conn.getResponseCode();

		} catch (MalformedURLException e) {
			return -1;
		} catch (IOException e) {
			return -1;
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	public static String testGetTransactionWS() {

		HttpURLConnection conn = null;
		try {

			URL url = new URL("http://127.0.0.1:8080/transactionWs/transaction/transactionStatistics");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			 InputStreamReader in=null;
	            
	            if (conn.getResponseCode() == 200) {
	            	in = new InputStreamReader(conn.getInputStream());
	            }else{
	            	
	            	in = new InputStreamReader(conn.getErrorStream());
	            	
	            }
	            
	            BufferedReader br = new BufferedReader(in);
	            String output=br.readLine();

			return output;

		} catch (MalformedURLException e) {
			return "generl error";
		} catch (IOException e) {
			return "general error";
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

}