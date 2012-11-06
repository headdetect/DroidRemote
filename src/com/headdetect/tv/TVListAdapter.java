package com.headdetect.tv;

import java.util.ArrayList;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.headdetect.computerremote.R;

public class TVListAdapter extends BaseAdapter {


	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	/** The inflater. */
	private LayoutInflater inflater;
	
	/** The activity. */
	private Activity activity;
	
	/** The items. */
	private ArrayList<TVItem> items;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	
	/**
	 * Instantiates a new chat list adapter.
	 *
	 * @param activity the activity
	 * @param items the items
	 */
	public TVListAdapter(Activity activity, ArrayList<TVItem> items) {
		this.inflater = LayoutInflater.from(activity.getApplicationContext());
		this.activity = activity;
		this.items = items;
	}
	
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override 
	public View getView(final int i, View covertView, final ViewGroup parent){
		if(covertView == null){
			covertView = this.inflater.inflate(R.layout.comp_chat_item, null);
		}
		
		TVItem chat = items.get(i);
		
		TextView lblMessage = (TextView)covertView.findViewById(R.id.lblChatContents);
		TextView lblFrom = (TextView)covertView.findViewById(R.id.lblDate);
		
		lblMessage.setText(Html.fromHtml(chat.getFile()));
		lblFrom.setText(chat.getLength());
		return covertView;
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return items.size();
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public TVItem getItem(int position) {
		return items.get(position);
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) throws IndexOutOfBoundsException {
		if(position < getCount() && position >= 0 ){
            return position;
        }
		return 0;
	}
	

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Adds the item.
	 *
	 * @param item the item
	 */
	public void addItem(TVItem item){
		items.add(item);
		this.activity.runOnUiThread(updateDataSetChanged);
	}
	
	/**
	 * Removes the item.
	 *
	 * @param item the item
	 */
	public void removeItem(TVItem item){
		items.remove(item);
		this.activity.runOnUiThread(updateDataSetChanged);
	}
	
	/**
	 * Removes the item.
	 *
	 * @param index the index
	 */
	public void removeItem (int index){
		items.remove(index);
		this.activity.runOnUiThread(updateDataSetChanged);
	}
	
	/** The update data set changed. */
	private final Runnable updateDataSetChanged = new Runnable(){

		@Override
		public void run() {
			notifyDataSetChanged();
		}
		
	};

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}


