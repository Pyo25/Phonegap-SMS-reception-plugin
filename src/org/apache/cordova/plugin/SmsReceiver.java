/*
Copyright (C) 2013 by Pierre-Yves Orban

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package org.apache.cordova.plugin;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.PluginResult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
	

	public static final String SMS_EXTRA_NAME = "pdus";
	private CallbackContext callback_receive;
	private boolean isReceiving = true;
	
	// This broadcast boolean is used to continue or not the message broadcast
	// to the other BroadcastReceivers waiting for an incoming SMS (like the native SMS app)
	private boolean broadcast = false;
	
	@Override
	public void onReceive(Context ctx, Intent intent) {
		
		// Get the SMS map from Intent
	    Bundle extras = intent.getExtras();
	    if (extras != null)
	    {
		   // Get received SMS Array
			Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);

			for (int i=0; i < smsExtra.length; i++)
			{
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
				if(this.isReceiving && this.callback_receive != null) {
					String formattedMsg = sms.getOriginatingAddress() + ">" + sms.getMessageBody();
		        	PluginResult result = new PluginResult(PluginResult.Status.OK, formattedMsg);
		           	result.setKeepCallback(true);
		            callback_receive.sendPluginResult(result);
				}
			}

			// If the plugin is active and we don't want to broadcast to other receivers
			if (this.isReceiving && !broadcast) {
				this.abortBroadcast();
			}
	     }
	}
	
	public void broadcast(boolean v) {
		this.broadcast = v;
	}
	
	public void startReceiving(CallbackContext ctx) {
		this.callback_receive = ctx;
		this.isReceiving = true;
	}

	public void stopReceiving() {
		this.callback_receive = null;
		this.isReceiving = false;
	}
}