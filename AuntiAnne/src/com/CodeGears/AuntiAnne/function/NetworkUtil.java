package com.CodeGears.AuntiAnne.function;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	public static Document getXml( String urlString, String postData ) {
		try {
			URL url = new URL( urlString );
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput( true );
			connection.setUseCaches( false );
			if ( postData != null ) {
				connection.setRequestMethod( "POST" );
				connection.setDoOutput( true );
				DataOutputStream wr = new DataOutputStream( connection.getOutputStream() );
				wr.writeBytes( postData );
				wr.flush();
				wr.close();
			}
			connection.connect();
			Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.parse( connection.getInputStream() );
			connection.disconnect();
			return d;
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( SAXException e ) {
			e.printStackTrace();
		} catch ( ParserConfigurationException e ) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document getXml( String url ) {
		return getXml( url, null );
	}

	public static String getRawData( String urlString, String postData ) {
		String rawData = "";
		try {
			URL url = new URL( urlString );
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput( true );
			connection.setUseCaches( false );
			if ( postData != null ) {
				connection.setRequestMethod( "POST" );
				connection.setDoOutput( true );
				DataOutputStream wr = new DataOutputStream( connection.getOutputStream() );
				wr.writeBytes( postData );
				wr.flush();
				wr.close();
			}
			connection.connect();
			InputStream i = connection.getInputStream();
			BufferedReader bReader = new BufferedReader( new InputStreamReader( i ) );
			String line = bReader.readLine();
			while ( line != null ) {
				rawData += line;
				line = bReader.readLine();
			}
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return rawData;
	}

	public static String createPostData( Map< String, String > data ) {
		String returnVal = "";
		Set< String > keySet = data.keySet();
		for ( String key : keySet ) {
			String value = data.get( key );
			returnVal += key + "=" + URLEncoder.encode( value ) + "&";
		}
		returnVal = returnVal.substring( 0, returnVal.length() - 1 );
		return returnVal;
	}

	public static boolean isInternetConnection( Context context ) {
		ConnectivityManager cManager = (ConnectivityManager) context
						.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if ( info == null ) {
			return false;
		}
		if ( info.isAvailable() && info.isConnected() ) {
			return true;
		}
		return false;
	}
}
