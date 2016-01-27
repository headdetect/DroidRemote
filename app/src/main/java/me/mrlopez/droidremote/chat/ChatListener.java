/*

﻿ *    Copyright 2012 Brayden (headdetect) Lopez
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
package me.mrlopez.droidremote.chat;

/**
 * The listener interface for receiving chat events.
 * The class that is interested in processing a chat
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>setOnChatListener<code> method. When
 * the chat event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ChatEvent
 */
public interface ChatListener {
	
	/**
	 * When a chat message has been received
	 *
	 * @param message the message
	 */
	void onChat (String message);

}


