package top.august1996.gifengine.core;

import java.io.File;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v4.util.LruCache;
import top.august1996.gifengine.util.FileUtils;
import top.august1996.gifengine.util.MD5Utils;

public class GIFLoader {
	
	private static Context			mContext;
	private static GIFLoader		instance;
	private static int				mMaxCacheMemery	= (int) (Runtime.getRuntime().freeMemory() / 4);
	private static File				mCacheDir;
	private LruCache<String, Movie>	mLruCache;
	
	/**
	 * 初始化GIF引擎
	 * 
	 * @param context
	 * @return
	 */
	public static GIFLoader init(Context context) {
		mContext = context;
		mCacheDir = context.getCacheDir();
		return getInstance();
	}
	
	private GIFLoader() {
		mLruCache = new LruCache<String, Movie>(mMaxCacheMemery);
	}
	
	public GIFDrawer load(String path) {
		GIFDrawer gifDrawer = new GIFDrawer();
		/**
		 * 先从内存缓存中加载
		 */
		Movie movie = mLruCache.get(MD5Utils.md5(path));
		/**
		 * 内存缓存加载失败，判断是否为网络图片。是的话就看有没有缓存过
		 */
		if (movie == null && !path.startsWith(File.separator)) {
			movie = Movie.decodeFile(new File(getCacheDir(), MD5Utils.md5(path)).getAbsolutePath());
		}
		
		if (movie == null) {
			/**
			 * 没有缓存过，判断是否是本地文件，本地文件直接加载
			 */
			if (new File(path).exists()) {
				movie = Movie.decodeFile(path);
				if (movie == null)
					return null;
				gifDrawer.setMovie(movie);
				mLruCache.put(MD5Utils.md5(path), movie);
			} else {
				/**
				 * 网络文件，开启异步任务加载
				 */
				LoadGIFAsyncTask asyncTask = new LoadGIFAsyncTask(gifDrawer);
				asyncTask.execute(path);
			}
		} else {
			gifDrawer.setMovie(movie);
		}
		return gifDrawer;
	}
	
	public GIFDrawer load(Uri uri) {
		if (mContext == null) {
			throw new RuntimeException("GIFLoader must be inited");
		}
		return load(FileUtils.getRealFilePath(mContext, uri));
	}
	
	public static GIFLoader getInstance() {
		if (instance == null) {
			instance = new GIFLoader();
		}
		return instance;
	}
	
	public static File getCacheDir() {
		return mCacheDir;
	}
	
	/**
	 * 提供GIFDrawer添加缓存接口
	 * 
	 * @param md5
	 * @param movie
	 */
	public void addBitmap(String md5, Movie movie) {
		mLruCache.put(md5, movie);
	}
}
