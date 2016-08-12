package top.august1996.imageloader.core;

import java.io.File;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import top.august1996.imageloader.util.BitmapUtils;
import top.august1996.imageloader.util.HTTPUtils;
import top.august1996.imageloader.util.MD5Utils;

public class LoadImageAsyncTask extends AsyncTask<String, Void, String> {
	
	private ImageDrawer	mImageDrawer;
	private String		mURL;
	
	public LoadImageAsyncTask(ImageDrawer imageDrawer) {
		mImageDrawer = imageDrawer;
	}
	
	@Override
	protected String doInBackground(String... params) {
		mURL = params[0];
		/**
		 * 获取下载图片后的本地路径
		 */
		return HTTPUtils.downloadFromURL(mURL,
				new File(ImageLoader.getCacheDir(), MD5Utils.md5(mURL)).getAbsolutePath());
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (mImageDrawer != null) {
			Bitmap bitmap = BitmapUtils.getAdjustBitmap(result, 0, 0);
			mImageDrawer.setBitmap(bitmap);
			ImageLoader.addBitmap(result, bitmap);
		}
	}
}
