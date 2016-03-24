/*
Copyright (C) 2011 by Pierre-Yves Orban

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

cordova.define("cordova/plugin/smsinboxplugin", function(require, exports, module) {
  var exec = require('cordova/exec');
  
  var SmsInboxPlugin = function() {};


  /**
   * Check if the device has a possibility to send and receive SMS
   */
  SmsInboxPlugin.prototype.isSupported = function(successCallback,failureCallback) {
    return exec(successCallback, failureCallback, 'SmsInboxPlugin', 'HasSMSPossibility', []);
  }

  /**
   * Check if the device has a possibility to send and receive SMS
   * the successCallback function receives one string as parameter
   * formatted such as: [phonenumber]>[message].
   * Example: +32472345678>Hello World
   */
  SmsInboxPlugin.prototype.startReception = function(successCallback,failureCallback) {
    return exec(successCallback, failureCallback, 'SmsInboxPlugin', 'StartReception', []);
  }

  /**
   * Stop the receiving sms.
   */
  SmsInboxPlugin.prototype.stopReception = function(successCallback,failureCallback) {
    return exec(successCallback, failureCallback, 'SmsInboxPlugin', 'StopReception', []);
  }

  var smsinboxplugin = new SmsInboxPlugin();
  module.exports = smsinboxplugin;
});