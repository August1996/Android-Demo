package demo.august1996.top.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by August on 16/5/28.
 */
public abstract class GenericRecyclerAdapter<T> extends RecyclerView.Adapter<GenericRecyclerAdapter.ViewHolder> {

    private List<T> mDatas;
    private int layoutID;
    private OnItemClickListener mOnItemClickListener;

    public GenericRecyclerAdapter(List<T> mDatas, int layoutID) {
        this.mDatas = mDatas;
        this.layoutID = layoutID;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = position;
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
        setData(holder, mDatas, position);
    }

    public abstract void setData(ViewHolder holder, List<T> mDatas, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> views;

        public ViewHolder(View itemView) {
            super(itemView);
        }


        public View getView(int viewID) {
            View view = views.get(viewID);
            if (view == null) {
                view = itemView.findViewById(viewID);
                views.put(viewID, view);
            }
            return view;
        }

        public void setText(int viewID, String text) {
            View view = getView(viewID);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
        }

        public void setBitmapImage(int viewID, Bitmap bitmap) {
            View view = getView(viewID);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            }
        }

        public void setRescouceImage(int viewID, int resID) {
            View view = getView(viewID);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(resID);
            }
        }

        public void setOnClickListener(int viewID, View.OnClickListener listener) {
            View view = getView(viewID);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }

        public void setOnLongClickListener(int viewID, View.OnLongClickListener listener) {
            View view = getView(viewID);
            if (view != null) {
                view.setOnLongClickListener(listener);
            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

}

