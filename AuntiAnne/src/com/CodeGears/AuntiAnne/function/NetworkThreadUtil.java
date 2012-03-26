package com.CodeGears.AuntiAnne.function;

import org.w3c.dom.Document;

public class NetworkThreadUtil {

	public static void getXml(final String urlString, final String postData,
			final NetworkThreadListener listener) {
		new Thread() {
			@Override
			public void run() {
				Document doc = NetworkUtil.getXml(urlString, postData);
				if (doc != null) {
					listener.onNetworkDocSuccess(urlString, doc);
				} else {
					listener.onNetworkFail(urlString);
				}
			}
		}.start();
	}

	public static void getXml(final String urlString,
			final NetworkThreadListener listener) {
		new Thread() {
			@Override
			public void run() {
				Document doc = NetworkUtil.getXml(urlString, null);
				if (doc != null) {
					listener.onNetworkDocSuccess(urlString, doc);
				} else {
					listener.onNetworkFail(urlString);
				}
			}
		}.start();
	}

	public static void getRawData(final String urlString,
			final String postData, final NetworkThreadListener listener) {
		new Thread() {
			@Override
			public void run() {
				String raw = NetworkUtil.getRawData(urlString, postData);
				if (!raw.equals("")) {
					listener.onNetworkRawSuccess(urlString, raw);
				} else {
					listener.onNetworkFail(urlString);
				}
			}
		}.start();
	}

	public static interface NetworkThreadListener {
		public void onNetworkDocSuccess(String urlString, Document document);

		public void onNetworkRawSuccess(String urlString, String result);

		public void onNetworkFail(String urlString);
	}

}
