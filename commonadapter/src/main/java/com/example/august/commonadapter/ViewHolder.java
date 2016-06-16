package com.example.august.commonadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by August on 16/4/9.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertview;

    public ViewHolder(View converview) {
        this.mConvertview = converview;
        mViews = new SparseArray<View>();
    }


    public static ViewHolder getHolder(Context context, View converview, int layoutID, ViewGroup parent) {
        ViewHolder holder = null;
        if (converview == null) {
            converview = LayoutInflater.from(context).inflate(layoutID, parent, false);
            holder = new ViewHolder(converview);
            converview.setTag(holder);
        } else {
            holder = (ViewHolder) converview.getTag();
        }
        return holder;
    }

    public <T extends View> T getView(int viewID) {

        T view = (T) mViews.get(viewID);

        if (view == null) {
            view = (T) mConvertview.findViewById(viewID);
            mViews.put(viewID, view);
        }

        return view;
    }

    public View getConverview() {
        return this.mConvertview;
    }

    public void setText(int viewID, String text) {
        View childView = getView(viewID);
        if (childView instanceof TextView) {
            ((TextView) childView).setText(text);
        }
    }

    public void setBitmap(int viewID, Bitmap bitmap) {
        View childView = getView(viewID);
        if (childView instanceof ImageView) {
            ((ImageView) childView).setImageBitmap(bitmap);
        }
    }
}
