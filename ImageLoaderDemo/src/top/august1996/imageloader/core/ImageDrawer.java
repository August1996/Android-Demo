package top.august1996.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageDrawer {
	private ImageView	mImageView;
	private Bitmap		mBitmap;
	
	public void into(ImageView imageView) {
		if (imageView == null)
			return;
		mImageView = imageView;
		if (mBitmap != null) {
			mImageView.setImageBitmap(mBitmap);
		}
	}
	
	public void setBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return;
		mBitmap = bitmap;
		if (mImageView != null) {
			mImageView.setImageBitmap(mBitmap);
		}
	}
	
}
