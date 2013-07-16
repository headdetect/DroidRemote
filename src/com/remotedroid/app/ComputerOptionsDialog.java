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
package com.remotedroid.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.headdetect.computerremote.R;
import com.remotedroid.core.utils.Computer;

/**
 * Dialog interface for choosing options attached to a computer object. Activity
 * holding dialog must implement @see ComputerOptionClickedListener.
 */
public class ComputerOptionsDialog extends DialogFragment {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The computer. */
	private Computer computer;

	static ComputerOptionClickedListener mListener;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(computer.getName() + " Options");

		builder.setItems(R.array.options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (mListener != null)
					mListener.onComputerOptionClicked(which, computer);
				
				dismiss();
			}
		});

		return builder.create();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Creates a new instance with a computer item embedded.
	 * 
	 * @param item
	 *            the item
	 * @return the dialog fragment
	 */
	public static ComputerOptionsDialog newInstance(Activity act, Computer item) {
		if (item == null)
			throw new NullPointerException("Computer cannot be null");

		ComputerOptionsDialog dialog = new ComputerOptionsDialog();
		dialog.computer = item;

		try {
			mListener = (ComputerOptionClickedListener) act;
		} catch (ClassCastException e) {
			throw new ClassCastException(act.toString() + " must implement ComputerOptionClickedListener");
		}

		return dialog;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The listener interface for receiving the dialog click events. The class
	 * that is interested in processing a computerOptionClicked event implements
	 * this interface, and the object created with that class is registered with
	 * a component using the component's
	 * <code>setComputerOptionClickedListener<code> method. When
	 * the computerOptionClicked event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 */
	public interface ComputerOptionClickedListener {

		/**
		 * On option clicked.
		 * 
		 * @param index
		 *            the index
		 * @param comp
		 *            the computer
		 */
		public void onComputerOptionClicked(int index, Computer comp);
	}
}
