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
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

public class SmsInboxPlugin extends CordovaPlugin {
	public final String ACTION_HAS_SMS_POSSIBILITY = "HasSMSPossibility";
	public final String ACTION_RECEIVE_SMS = "StartReception";
	public final String ACTION_STOP_RECEIVE_SMS = "StopReception";
	
	private CallbackContext callback_receive;
	private SmsReceiver smsReceiver = null;
	private boolean isReceiving = false;
	
	public SmsInboxPlugin() {
		super();
	}
	
	@Override
	public boolean execute(String action, JSONArray arg1,
			final CallbackContext callbackContext) throws JSONException {
		
		if (action.equals(ACTION_HAS_SMS_POSSIBILITY)) {
			
			Activity ctx = this.cordova.getActivity();
			if(ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)){
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
			} else {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
			}
			return true;
		}
		else if (action.equals(ACTION_RECEIVE_SMS)) {
			
			// if already receiving (this case can happen if the startReception is called
			// several times
			if(this.isReceiving) {
				// close the already opened callback ...
				PluginResult pluginResult = new PluginResult(
						PluginResult.Status.NO_RESULT);
				pluginResult.setKeepCallback(false);
				this.callback_receive.sendPluginResult(pluginResult);
				
				// ... before registering a new one to the sms receiver
			}
			this.isReceiving = true;
				
			if(this.smsReceiver == null) {
				this.smsReceiver = new SmsReceiver();
				IntentFilter fp = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
			    fp.setPriority(1000);
			    // fp.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
			    this.cordova.getActivity().registerReceiver(this.smsReceiver, fp);
			}
			
			this.smsReceiver.startReceiving(callbackContext);
	
			PluginResult pluginResult = new PluginResult(
					PluginResult.Status.NO_RESULT);
			pluginResult.setKeepCallback(true);
			callbackContext.sendPluginResult(pluginResult);
			this.callback_receive = callbackContext;
			
			return true;
		}
		else if(action.equals(ACTION_STOP_RECEIVE_SMS)) {
			
			if(this.smsReceiver != null) {
				smsReceiver.stopReceiving();
			}

			this.isReceiving = false;
			
			// 1. Stop the receiving context
			PluginResult pluginResult = new PluginResult(
					PluginResult.Status.NO_RESULT);
			pluginResult.setKeepCallback(false);
			this.callback_receive.sendPluginResult(pluginResult);
			
			// 2. Send result for the current context
			pluginResult = new PluginResult(
					PluginResult.Status.OK);
			callbackContext.sendPluginResult(pluginResult);
			
			return true;
		}

		return false;
	}
}
