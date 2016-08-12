package top.august1996.gifengine.core;

import java.io.File;

import android.graphics.Movie;
import android.os.AsyncTask;
import top.august1996.gifengine.util.HTTPUtils;
import top.august1996.gifengine.util.MD5Utils;

public class LoadGIFAsyncTask extends AsyncTask<String, Void, String> {
	
	private GIFDrawer	mGIFDrawer;
	private String		mURL;
	
	public LoadGIFAsyncTask(GIFDrawer gifDrawer) {
		mGIFDrawer = gifDrawer;
	}
	
	@Override
	protected String doInBackground(String... params) {
		mURL = params[0];
		/**
		 * 获取下载图片后的本地路径
		 */
		return HTTPUtils.downloadFromURL(mURL, new File(GIFLoader.getCacheDir(), MD5Utils.md5(mURL)).getAbsolutePath());
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (mGIFDrawer != null) {
			Movie movie = Movie.decodeFile(result);
			mGIFDrawer.setMovie(movie);
			GIFLoader.getInstance().addBitmap(result, movie);
		}
	}
}
