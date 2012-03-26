package com.CodeGears.AuntiAnne.function;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class NetworkThreadUtil2 {

	public static final int RESULT_RAW = 1;
	public static final int RESULT_XML = 2;

	private int resultType;
	private String referenceKey;
	private NetworkThread2Listener listener;

	public NetworkThreadUtil2( String referenceKey ) {
		this.referenceKey = referenceKey;
		resultType = RESULT_RAW;
	}

	public void setListener( NetworkThread2Listener listener ) {
		this.listener = listener;
	}

	public void sendRawBody( final String urlString, final String raw ) {
		new Thread() {
			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost( urlString );
					httpPost.setEntity( new StringEntity( raw ) );
					httpPost.setHeader( "Content-Type", "application/xml" );
					HttpResponse response = httpClient.execute( httpPost );
					InputStream is = response.getEntity().getContent();
					generateResult( is );
				} catch ( UnsupportedEncodingException e ) {
					listener.onNetworkThreadFail( referenceKey );
					e.printStackTrace();
				} catch ( ClientProtocolException e ) {
					listener.onNetworkThreadFail( referenceKey );
					e.printStackTrace();
				} catch ( IOException e ) {
					listener.onNetworkThreadFail( referenceKey );
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void generateResult( InputStream iStream ) {
		if ( resultType == RESULT_RAW ) {
			String rawData = "";
			BufferedReader bReader = new BufferedReader( new InputStreamReader( iStream ) );
			try {
				String line = bReader.readLine();
				while ( line != null ) {
					rawData += line;
					line = bReader.readLine();
				}
				if ( listener != null ) {
					listener.onNetworkThreadComplete( referenceKey, rawData );
				}
			} catch ( IOException e ) {
				listener.onNetworkThreadFail( referenceKey );
				e.printStackTrace();
			}
		} else if ( resultType == RESULT_XML ) {
			try {
				Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( iStream );
				if ( listener != null ) {
					listener.onNetworkThreadComplete( referenceKey, d );
				}
			} catch ( SAXException e ) {
				listener.onNetworkThreadFail( referenceKey );
				e.printStackTrace();
			} catch ( IOException e ) {
				listener.onNetworkThreadFail( referenceKey );
				e.printStackTrace();
			} catch ( ParserConfigurationException e ) {
				listener.onNetworkThreadFail( referenceKey );
				e.printStackTrace();
			}
		}
	}
}
