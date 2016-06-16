
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MutiDownloadHepler {
	
	public interface DownloadListener {
		public void beforeDownload(long totalSize, long eachSize);
		
		public void onDownload(long cntProgress);
		
		public void afterDownload();
		
		public void downloadFail(Exception e);
		
		public void onCancle();
	}
	
	class DownloadThread extends Thread {
		
		private String				url;
		private String				fileName;
		private long				startIndex;
		private long				endIndex;
		private HttpURLConnection	connection;
		private RandomAccessFile	raf;
		private InputStream			inputStream;
									
		public DownloadThread(String url, String fileName, long startIndex, long endIndex) {
			super();
			this.url = url;
			this.fileName = fileName;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		@Override
		public void run() {
			try {
				if (!isStop) {
					HttpURLConnection connection = (HttpURLConnection) new URL(
							url + "?ts=" + System.currentTimeMillis()).openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					connection.setRequestProperty("RANGE", "bytes=" + startIndex + "-" + endIndex);
					
					if (connection.getResponseCode() == 206) {
						inputStream = connection.getInputStream();
						byte[] bs = new byte[1024];
						int len;
						raf = new RandomAccessFile(fileName, "rwd");
						raf.seek(startIndex);
						
						while ((len = inputStream.read(bs)) != -1) {
							if (!isStop) {
								mTotalProgress += len;
								if (mDownloadListener != null) {
									mDownloadListener.onDownload(mTotalProgress);
								}
								raf.write(bs, 0, len);
							} else {
								break;
							}
						}
						if (mDownloadListener != null) {
							if (isStop) {
								mDownloadListener.onCancle();
							} else {
								mDownloadListener.afterDownload();
							}
						}
					}
					
				}
			} catch (Exception e) {
				if (mDownloadListener != null) {
					mDownloadListener.downloadFail(e);
				}
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.disconnect();
						connection = null;
					}
					if (raf != null) {
						raf.close();
						raf = null;
					}
					if (inputStream != null) {
						inputStream.close();
						inputStream = null;
					}
				} catch (Exception e2) {
					if (mDownloadListener != null) {
						mDownloadListener.downloadFail(e2);
					}
					e2.printStackTrace();
				}
			}
		}
	}
	
	private String				downloadUrl;
	private String				saveFileName;
	private int					threadCount;
	private long				fileSize;
	private boolean				isStop	= false;
										
	private DownloadListener	mDownloadListener;
	private long				mTotalProgress;
								
	public MutiDownloadHepler(String downloadUrl, String saveFileName, int threadCount) {
		this.downloadUrl = downloadUrl;
		this.saveFileName = saveFileName;
		this.threadCount = threadCount;
		mTotalProgress = 0;
	}
	
	public void setmDownloadListener(DownloadListener mDownloadListener) {
		this.mDownloadListener = mDownloadListener;
	}
	
	public void startDownload() {
		
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(downloadUrl).openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			
			if (connection.getResponseCode() == 200) {
				fileSize = connection.getContentLength();
				long eachSize = fileSize / threadCount;
				RandomAccessFile raf = new RandomAccessFile(saveFileName, "rwd");
				
				raf.setLength(fileSize);
				raf.close();
				
				if (mDownloadListener != null) {
					mDownloadListener.beforeDownload(fileSize, eachSize);
				}
				
				for (int i = 0; i < threadCount; i++) {
					long startIndex = i * eachSize;
					long endIndex = (i + 1) * eachSize - 1;
					if (i == threadCount - 1) {
						endIndex = fileSize;
					}
					
					new DownloadThread(downloadUrl, saveFileName, startIndex, endIndex).start();
				}
				
			}
		} catch (Exception e) {
			if (mDownloadListener != null) {
				mDownloadListener.downloadFail(e);
			}
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
	}
	
	public void stopDownload() {
		isStop = true;
	}
	
	public static void main(String[] args) {
		MutiDownloadHepler hepler = new MutiDownloadHepler(
				"http://s1.music.126.net/download/osx/NeteaseMusic_1.4.3_452_web.dmg",
				"/Users/August/Desktop/NeteaseMusic_1.4.3_452_web.dmg", 10);
				
		hepler.setmDownloadListener(new DownloadListener() {
			
			@Override
			public void onDownload(long cntProgress) {
				System.out.println("cntProgress:" + cntProgress);
			}
			
			@Override
			public void onCancle() {
				System.out.println("Cancle download");
			}
			
			@Override
			public void downloadFail(Exception e) {
				e.printStackTrace();
			}
			
			@Override
			public void beforeDownload(long totalSize, long eachSize) {
				System.out.println("TotalSize:" + totalSize);
			}
			
			@Override
			public void afterDownload() {
				System.out.println("Download success");
				
			}
		});
		hepler.startDownload();
	}
}
