package top.august1996.gifengine.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.widget.ImageView;

public class GIFDrawer {
	private ImageView			mImageView;
	private Movie				mMovie;
	private boolean				isStop;
	private static final int	refreshDuration	= 16;
	
	private Canvas				mCanvas;
	private Bitmap				mBitmap;
	
	private Runnable			mGIFRunnable	= new Runnable() {
													
													@Override
													public void run() {
														if (!isStop) {
															if (mMovie == null || mImageView == null) {
																return;
															}
															draw();
															/**
															 * 一直刷帧
															 */
															new Handler().postDelayed(mGIFRunnable, refreshDuration);
														}
													}
												};
	
	public ImageView getImageView() {
		return mImageView;
	}
	
	public void into(ImageView imageView) {
		this.mImageView = imageView;
		if (imageView == null) {
			isStop = true;
			return;
		}
		if (mMovie != null) {
			startGIF();
		}
	}
	
	public Movie getMovie() {
		return mMovie;
	}
	
	public void setMovie(Movie movie) {
		this.mMovie = movie;
		if (movie == null) {
			return;
		}
		if (mImageView != null) {
			startGIF();
		}
	}
	
	/**
	 * 启动gif动态模式
	 */
	private void startGIF() {
		
		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
			mCanvas = null;
		}
		mBitmap = Bitmap.createBitmap(mMovie.width(), mMovie.height(), Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		new Handler().post(mGIFRunnable);
	}
	
	/**
	 * 画当前帧的bitmap
	 */
	private void draw() {
		mMovie.setTime((int) (System.currentTimeMillis() % mMovie.duration()));
		mCanvas.save();
		mMovie.draw(mCanvas, 0, 0);
		mCanvas.restore();
		mImageView.setImageBitmap(mBitmap);
	}
	
}
