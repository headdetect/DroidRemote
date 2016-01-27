/*

﻿ *    Copyright 2012 Brayden (headdetect)
 *    
 *    Dual-licensed under the Educational Community License, Version 2.0 and
 *	the GNU General Public License Version 3 (the "Licenses"); you may
 *	not use this file except in compliance with the Licenses. You may
 *	obtain a copy of the Licenses at
 *
 *		http://www.opensource.org/licenses/ecl2.php
 *		http://www.gnu.org/licenses/gpl-3.0.html
 *
 *		Unless required by applicable law or agreed to in writing
 *	software distributed under the Licenses are distributed on an "AS IS"
 *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *	or implied. See the Licenses for the specific language governing
 *	permissions and limitations under the Licenses.
 * 
 */


package me.mrlopez.droidremote.core.utils;

import me.mrlopez.droidremote.BuildConfig;


/**
 * The Class Logger.
 */
public class Logger {
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	/** The Constant TAG. */
	private static final String TAG = "ComputerRemote::ChatClient";

	// ===========================================================
	// Fields
	// ===========================================================
	
	
	/** The log listener. */
	private static LogListener logListener;
	
	

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	/**
	 * Sets the log listener.
	 *
	 * @param listener the new on log listener
	 */
	public static void setLogListener(LogListener listener){
		Logger.logListener = listener;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Logs a message.
	 *
	 * @param message the message
	 */
	public static void log(String message){
		if(logListener != null){
			logListener.OnLog(message);
		}
		
		android.util.Log.d(TAG, message);
	}
	
	/**
	 * Logs the specified exception.
	 *
	 * @param ex the exception.
	 */
	public static void log(Exception ex){
		if(logListener != null){
			logListener.OnLog(ex.getMessage());
		}
		
		android.util.Log.e(TAG, ex.getMessage(), ex);
	}
	
	/**
	 * Log a message using a String format method.
	 *
	 * @param format the format
	 * @param args the args
	 */
	public static void logF(String format, Object... args){
		log(String.format(format, args));
	}

	/**
	 * if BuildConfig.Debug is enabled, a message will be logged.
	 *
	 * @param message the message
	 */
	public static void logDebug(String message){
		if(!BuildConfig.DEBUG)
			return;
		
		log(message);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	/**
	 * The listener interface for receiving log events.
	 * The class that is interested in processing a log
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>setLogListener<code> method. When
	 * the log event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see LogEvent
	 */
	public interface LogListener {
		
		/**
		 * When a log is received.
		 *
		 * @param message the message
		 */
		void OnLog(String message);
	}
	
}


