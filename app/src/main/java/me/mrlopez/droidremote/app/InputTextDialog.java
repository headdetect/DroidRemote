package me.mrlopez.droidremote.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class InputTextDialog extends DialogFragment {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private String mTitle;

	private Activity mAct;

	static TextEnteredListener mListener;

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

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				getActivity());
		final EditText txtInput = new EditText(mAct);

		builder.setTitle(mTitle);
		builder.setView(txtInput);
		builder.setPositiveButton("Ok", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				String text = txtInput.getText().toString();

				if (mListener != null) {
					mListener.onTextReceived(text);
				}

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
	 * @param title
	 *            the item
	 * @return the dialog fragment
	 */
	public static InputTextDialog newInstance(Activity act, String title) {
		if (act == null)
			throw new NullPointerException("Activity cannot be null");

		InputTextDialog dialog = new InputTextDialog();
		dialog.mTitle = title;
		dialog.mAct = act;

		try {
			mListener = (TextEnteredListener) act;
		} catch (ClassCastException e) {
			throw new ClassCastException(act.toString()
					+ " must implement TextEnteredListener");
		}

		return dialog;
	}

	/**
	 * Creates a new instance with a computer item embedded.
	 *
	 * @param title
	 *            the item
	 * @return the dialog fragment
	 */
	public static InputTextDialog newInstance(Activity act, String title, TextEnteredListener listener) {
		if (act == null)
			throw new NullPointerException("Activity cannot be null");

		InputTextDialog dialog = new InputTextDialog();
		dialog.mTitle = title;
		dialog.mAct = act;

		try {
			mListener = listener;
		} catch (ClassCastException e) {
			throw new ClassCastException(act.toString()
					+ " must implement TextEnteredListener");
		}

		return dialog;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * The listener interface for receiving the dialog click events. The class
	 * that is interested in processing a computerOptionClicked event implements
	 * this interface. When the computerOptionClicked event occurs, that
	 * object's appropriate method is invoked.
	 * 
	 */
	public interface TextEnteredListener {

		/**
		 * On option clicked.
		 * 
		 * @param result
		 *            the text given
		 */
		public void onTextReceived(String result);
	}

}
