package com.headdetect.computerremote.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TextDialog extends DialogFragment {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The computer. */
	private String mText;


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

		builder.setTitle("Result");
		builder.setMessage(mText);
		
		builder.setPositiveButton("OK", new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dismiss();
			}});
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
	public static TextDialog newInstance(String item) {
		if (item == null)
			throw new NullPointerException("Message cannot be null");

		TextDialog dialog = new TextDialog();
		dialog.mText = item;

		return dialog;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
