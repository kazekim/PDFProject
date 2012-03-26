package com.CodeGears.AuntiAnne.layout;

import com.CodeGears.AuntiAnne.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ConfirmCouponDialog extends Dialog {
	private Button confirmButton;
	private Button cancelButton;
	private ReadyListener readyListener;

	public interface ReadyListener {
        public void ready(String name);
    }
	
public ConfirmCouponDialog(Context context, 
        ReadyListener readyListener) {
	super(context);

	requestWindowFeature(Window.FEATURE_NO_TITLE);

	 this.readyListener = readyListener;
}

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.couponconfirmdialog);

    confirmButton = (Button) findViewById(R.id.confirmbutton);
	confirmButton.setOnClickListener(new OKListener());
	
	cancelButton = (Button) findViewById(R.id.cancelbutton);
	cancelButton.setOnClickListener(new OKListener());
	

}

private class OKListener implements android.view.View.OnClickListener {
    @Override
    public void onClick(View v) {
        readyListener.ready(String.valueOf("ssss"));
        ConfirmCouponDialog.this.dismiss();
    }
}

private class CancelListener implements android.view.View.OnClickListener {
    @Override
    public void onClick(View v) {
    	ConfirmCouponDialog.this.dismiss();
    }
}

}