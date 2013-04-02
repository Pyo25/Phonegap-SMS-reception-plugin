SMS reception plugin for Phonegap
===============================
By Pierre-Yves Orban

This Android Phonegap plugin allows you to receive incoming SMS. You have the possibility to stop the message broadcasting and, thus, avoid theincoming message native popup.

This plugin was successfully tested with Phonegap 2.5 and Android 4.2.2 (on a Samsung Galaxy Nexus device).

## Adding this plugin to your project ##
0. (Make sure you are using Phonegap > 2.0)
1. Move SmsInboxPlugin.js to your project's www folder and include a reference to it in your html files. 
2. Add the java files from src to your project's src hierarchy
3. Reference the plugin in your res/config.xml file
```<plugin name="SendInboxPlugin" value="org.apache.cordova.plugin.SendInboxPlugin"/>```
4. Ensure that your manifest contains the necessary permissions to send SMS messages:
```<uses-permission android:name="android.permission.RECEIVE_SMS" />```
5. Add the receiver in your manifest
```
<receiver android:name="org.apache.cordova.plugin.SmsReceiver" android:exported="true" > 
  <intent-filter android:priority="1000"> 
    <action android:name="android.provider.Telephony.SMS_RECEIVED" />
  </intent-filter> 
</receiver>
```

## Using the plugin ##
To instantiate the plugin object:
```javascript
var smsInboxPlugin = cordova.require('cordova/plugin/smsinboxplugin');
```

### isSupported ###
Check if the SMS technology is supported by the device.

Example:
```javascript
  smsInboxPlugin.isSupported ((function(supported) {
    if(supported) 
      alert("SMS supported !");
    else
      alert("SMS not supported");
  }), function() {
    alert("Error while checking the SMS support");
  });
```

### startReception ###
Start the SMS receiver waiting for incoming message
The success callback function will be called everytime a new message is received.
The given parameter is the received message formatted such as: phoneNumber>message (Exemple: +32472345678>Hello World)
The error callback is called if an error occurs.

Example:
```javascript
  smsInboxPlugin.startReception (function(msg) {
    alert(msg);
  }, function() {
    alert("Error while receiving messages");
  });
```

### stopReception ###
Stop the SMS receiver

Example:
```javascript
  smsInboxPlugin.stopReception (function() {
    alert("Correctly stopped");
  }, function() {
    alert("Error while stopping the SMS receiver");
  });
```
  
## Licence ##

The MIT License

Copyright (c) 2013 Pierre-Yves Orban

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
