package com.joanzapata.android;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * Allows an abstraction of the ViewHolder pattern.<br>
 * <br>
 * 
 * <b>Usage</b>
 * 
 * <pre>
 * return BaseAdapterHelper.get(context, convertView, parent, R.layout.item)
 * 		.setText(R.id.tvName, contact.getName())
 * 		.setText(R.id.tvEmails, contact.getEmails().toString())
 * 		.setText(R.id.tvNumbers, contact.getNumbers().toString())
 * 		.getView();
 * </pre>
 * 
 * @author jzapata
 * 
 */
public class BaseAdapterHelper {

	/** Views indexed with their IDs */
	private final SparseArray<View> views;

	private View convertView;

	/**
	 * This method is the only entry point to get a BaseAdapterHelper.
	 * 
	 * @param context
	 *            The current context
	 * @param convertView
	 *            the convertView arg passed to the getView() method
	 * @param parent
	 *            the parent arg passed to the getView() method
	 * @return A BaseAdapterHelper
	 */
	public static BaseAdapterHelper get(Context context, View convertView,
			ViewGroup parent, int layoutId) {
		if (convertView == null) {
			return new BaseAdapterHelper(context, parent, layoutId);
		} else {
			return (BaseAdapterHelper) convertView.getTag();
		}
	}

	private BaseAdapterHelper(Context context, ViewGroup parent, int layoutId) {
		this.views = new SparseArray<View>();
		convertView = LayoutInflater.from(context) //
				.inflate(layoutId, parent, false);
		convertView.setTag(this);
	}

	/**
	 * Add an action to set the text of a text view. Can be called multiple
	 * times.
	 */
	public BaseAdapterHelper setText(int viewId, String value) {
		TextView view = retrieveView(viewId);
		view.setText(value);
		return this;
	}

	/**
	 * Add an action to set the alpha of a view. Can be called multiple times.
	 * Alpha between 0-1.
	 */
	public BaseAdapterHelper setAlpha(int viewId, float value) {
		retrieveView(viewId).setAlpha(value);
		return this;
	}

	/** Retrieve the convertView */
	public View getView() {
		return convertView;
	}

	@SuppressWarnings("unchecked")
	private <T extends View> T retrieveView(int viewId) {
		View view = views.get(viewId);
		if (view == null) {
			view = convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}

}