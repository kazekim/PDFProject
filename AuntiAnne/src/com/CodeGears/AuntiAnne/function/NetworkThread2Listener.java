package com.CodeGears.AuntiAnne.function;

import org.w3c.dom.Document;

public interface NetworkThread2Listener {

	public void onNetworkThreadComplete(String referenceKey, Document doc);
	public void onNetworkThreadComplete(String referenceKey, String raw);
	public void onNetworkThreadFail(String referenceKey);
}
