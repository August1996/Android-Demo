package top.august1996.imageloader.util;

import java.io.File;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore;

public class BitmapUtils {
	
	/**
	 * 根据宽度和高度获取自适应的Bitmap
	 * 
	 * @param is
	 * @param destWidth
	 * @param destHeight
	 * @return
	 */
	public static Bitmap getAdjustBitmap(String filepath, float destWidth, float destHeight) {
		if (filepath == null) {
			return null;
		}
		
		if (destHeight < 0 || destWidth < 0) {
			return null;
		}
		Bitmap bitmap;
		if (destHeight == 0 || destWidth == 0) {
			bitmap = BitmapFactory.decodeFile(filepath);
		} else {
			Options opt = new Options();
			opt.inJustDecodeBounds = true;
			
			bitmap = BitmapFactory.decodeFile(filepath, opt);
			double scaleW = Math.max(destWidth, opt.outWidth) / (Math.min(destWidth, opt.outWidth) * 1.0);
			double scaleH = Math.max(destHeight, opt.outHeight) / (Math.min(destHeight, opt.outHeight) * 1.0);
			opt.inSampleSize = (int) Math.max(scaleW, scaleH);
			
			opt.inJustDecodeBounds = false;
			
			bitmap = BitmapFactory.decodeFile(filepath, opt);
		}
		return bitmap;
		
	}
	
	/**
	 * 重载getAdjustBitmap(InputStream is, float destWidth, float destHeight);
	 * 
	 * @param file
	 * @param destWidth
	 * @param destHeight
	 * @return
	 */
	public static Bitmap getAdjustBitmap(File file, float destWidth, float destHeight) {
		Bitmap bitmap = null;
		if (file == null) {
			return null;
		}
		bitmap = getAdjustBitmap(file.getAbsolutePath(), destWidth, destHeight);
		
		return bitmap;
	}
	
	/**
	 * 重载getAdjustBitmap(InputStream is, float destWidth, float destHeight);
	 * 
	 * @param context
	 * @param uri
	 * @param destWidth
	 * @param destHeight
	 * @return
	 */
	public static Bitmap getAdjustBitmap(Context context, Uri uri, float destWidth, float destHeight) {
		if (context == null || uri == null) {
			return null;
		}
		
		Bitmap bitmap = null;
		bitmap = getAdjustBitmap(new File(getRealFilePath(context, uri)), destWidth, destHeight);
		return bitmap;
	}
	
	/**
	 * 根据Uri获取文件
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}
	
}
