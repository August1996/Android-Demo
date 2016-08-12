package top.august1996.imageloader.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtils {
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param saveFile
	 * @return
	 */
	public static String downloadFromURL(String url, String saveFile) {
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		FileOutputStream fos = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			inputStream = conn.getInputStream();
			fos = new FileOutputStream(new File(saveFile));
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return saveFile;
	}
}
