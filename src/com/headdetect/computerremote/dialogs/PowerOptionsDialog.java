package com.headdetect.computerremote.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.headdetect.computerremote.R;
import com.headdetect.computerremote.Utils.Computer;

/**
 * Dialog interface for choosing options attached to a computer object. Activity
 * holding dialog must implement @see ComputerOptionClickedListener.
 */
public class PowerOptionsDialog extends DialogFragment {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The computer. */
	private Computer computer;

	static PowerOptionsClickedListener mListener;

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

		builder.setTitle("Power Options");

		builder.setItems(R.array.power_options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (mListener != null)
					mListener.onPowerOptionClicked(which, computer);
				
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
	public static PowerOptionsDialog newInstance(Activity act, Computer item) {
		if (item == null)
			throw new NullPointerException("Computer cannot be null");

		PowerOptionsDialog dialog = new PowerOptionsDialog();
		dialog.computer = item;

		try {
			mListener = (PowerOptionsClickedListener) act;
		} catch (ClassCastException e) {
			throw new ClassCastException(act.toString() + " must implement PowerOptionsClickedListener");
		}

		return dialog;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The listener interface for receiving the dialog click events. The class
	 * that is interested in processing a computerOptionClicked event implements
	 * this interface. When
	 * the computerOptionClicked event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 */
	public interface PowerOptionsClickedListener {

		/**
		 * On option clicked.
		 * 
		 * @param index
		 *            the index
		 * @param comp
		 *            the computer
		 */
		public void onPowerOptionClicked(int index, Computer comp);
	}
}


