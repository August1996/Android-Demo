package top.august1996.imageloader.core;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;
import top.august1996.imageloader.util.BitmapUtils;
import top.august1996.imageloader.util.MD5Utils;

/**
 * 图片加载类
 * 
 * @author August
 *
 */
public class ImageLoader {
	/**
	 * 单例
	 */
	private static ImageLoader				instance;
	
	/**
	 * 最大内存缓存
	 */
	private static int						mMaxCacheMemery	= (int) (Runtime.getRuntime().freeMemory() / 4);
	
	/**
	 * 磁盘缓存目录
	 */
	private static File						mCacheDir;
	
	/**
	 * lru缓存
	 */
	private static LruCache<String, Bitmap>	mLruCache;
	private static Context					mContext;
	
	/**
	 * 初始化
	 * 
	 * @param context
	 * @return
	 */
	public static ImageLoader init(Context context) {
		mContext = context;
		mCacheDir = mContext.getCacheDir();
		return getInstance();
	}
	
	/**
	 * 私有化构造函数
	 */
	private ImageLoader() {
		mLruCache = new LruCache<String, Bitmap>(mMaxCacheMemery);
	}
	
	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}
	
	/**
	 * 设置磁盘缓存目录
	 * 
	 * @param file
	 * @return
	 */
	public ImageLoader setCacheDir(File file) {
		if (file != null) {
			mCacheDir = file;
		}
		return getInstance();
	}
	
	/**
	 * 设置最大内存缓存大小
	 * 
	 * @param size
	 * @return
	 */
	public ImageLoader setMaxCacheMemory(int size) {
		if (size >= 0) {
			mMaxCacheMemery = size;
		}
		return getInstance();
	}
	
	/**
	 * 根据地址和宽度、高度加载图片
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageDrawer load(String path, int width, int height) {
		ImageDrawer imageDrawer = new ImageDrawer();
		String filaneme = MD5Utils.md5(path);
		/**
		 * 先从内存中取
		 */
		Bitmap bitmap = mLruCache.get(filaneme);
		/**
		 * 如果内存中没有，则判断是否为本地图片。 非本地图片有另外一个文件命名规则，从磁盘中取缓存过的网络图片
		 */
		if (bitmap == null && !path.startsWith(File.separator)) {
			bitmap = BitmapUtils.getAdjustBitmap(new File(getCacheDir(), MD5Utils.md5(path)), width, height);
		}
		/**
		 * 如果内存中没有盖图片或者是网络图片未曾缓存则进一步判断
		 */
		if (bitmap == null) {
			File file = new File(path);
			/**
			 * 本地图片，取出
			 */
			if (file.exists()) {
				bitmap = BitmapUtils.getAdjustBitmap(file, width, height);
				imageDrawer.setBitmap(bitmap);
				mLruCache.put(filaneme, bitmap);
			} else {
				/**
				 * 网络图片，开启异步任务获取
				 */
				LoadImageAsyncTask asyncTask = new LoadImageAsyncTask(imageDrawer);
				asyncTask.execute(path);
			}
		} else {
			imageDrawer.setBitmap(bitmap);
		}
		return imageDrawer;
	}
	
	public ImageDrawer load(String path) {
		return load(path, 0, 0);
	}
	
	public ImageDrawer load(Uri uri) {
		return load(uri, 0, 0);
	}
	
	public ImageDrawer load(Uri uri, int width, int height) {
		if (mContext == null) {
			throw new RuntimeException("ImageLoader must be inited");
		}
		String filename = BitmapUtils.getRealFilePath(mContext, uri);
		return load(new File(filename).getAbsolutePath(), width, height);
	}
	
	/**
	 * 提供接口给ImageDrawer去添加缓存图片
	 * 
	 * @param key
	 * @param value
	 */
	public static void addBitmap(String key, Bitmap value) {
		if (key != null && value != null) {
			mLruCache.put(key, value);
		}
	}
	
	public static String getCacheDir() {
		return mCacheDir.getAbsolutePath();
	}
}
