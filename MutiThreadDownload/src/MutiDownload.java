
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MutiDownload {
	
	private static final int	THREAD_COUNT	= 5;
	private static final String	DOWNLOAD_URL	= "http://s1.music.126.net/download/osx/NeteaseMusic_1.4.3_452_web.dmg";
	private static final String	fileName		= "/Users/August/Desktop/NeteaseMusic_1.4.3_452_web.dmg";
												
	public static void main(String[] args) {
		
		long fileSize;
		HttpURLConnection connection = null;
		try {
			
			connection = (HttpURLConnection) new URL(DOWNLOAD_URL).openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			
			if (connection.getResponseCode() == 200) {
				fileSize = connection.getContentLength();
				long eachSize = fileSize / THREAD_COUNT;
				RandomAccessFile raf = new RandomAccessFile(fileName, "rwd");
				
				raf.setLength(fileSize);
				raf.close();
				
				for (int i = 0; i < THREAD_COUNT; i++) {
					long startIndex = i * eachSize;
					long endIndex = (i + 1) * eachSize - 1;
					if (i == THREAD_COUNT - 1) {
						endIndex = fileSize;
					}
					
					new DownloadThread(DOWNLOAD_URL, fileName, i, startIndex, endIndex).start();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
	}
	
}

class DownloadThread extends Thread {
	
	private String				url;
	private String				fileName;
	private int					threadID;
	private long				startIndex;
	private long				endIndex;
	private HttpURLConnection	connection;
	private RandomAccessFile	raf;
	private InputStream			inputStream;
								
	/**
	 * 进度文件
	 */
	private RandomAccessFile	progressRaf;
								
	public DownloadThread(String url, String fileName, int threadID, long startIndex, long endIndex) {
		super();
		this.url = url;
		this.fileName = fileName;
		this.threadID = threadID;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	@Override
	public void run() {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url + "?ts=" + System.currentTimeMillis())
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			connection.setRequestProperty("RANGE", "bytes=" + startIndex + "-" + endIndex);
			
			File progressFile = new File(String.valueOf(threadID));
			if (progressFile.exists()) {
				progressRaf = new RandomAccessFile(progressFile, "rwd");
			} else {
				progressFile.createNewFile();
				progressRaf = new RandomAccessFile(progressFile, "rwd");
				progressRaf.write(String.valueOf(startIndex).getBytes());
			}
			/**
			 * 判断进度文件是否存在,不存在则创建,并且存入startIndex的值
			 */
			
			progressRaf.seek(0);
			startIndex = Long.valueOf(progressRaf.readLine());
			/**
			 * 这时进度文件一定存在,就读取上次结束为止 若为第一次下载,读取的依旧是startIndex的值
			 */
			
			if (connection.getResponseCode() == 206) {
				inputStream = connection.getInputStream();
				byte[] bs = new byte[1024];
				int len;
				raf = new RandomAccessFile(fileName, "rwd");
				raf.seek(startIndex);
				
				long total = 0;
				long position = startIndex + total;
				while ((len = inputStream.read(bs)) != -1) {
					total += len;
					System.out.println("线程" + threadID + ":" + total);
					raf.write(bs, 0, len);
					progressRaf.seek(0);
					progressRaf.write(String.valueOf(position).getBytes());
					/**
					 * 实时同步进度 必须写到raf.write(bs, 0, len);之后.
					 * 因为如果先写到进度再去写目标文件的话,那么当写完进度后被停掉程序,目标文件那部分是没有数据的
					 * 下次续传的时候还是从上次结束那里开始的话,数据依旧是丢失的
					 * 
					 * 考虑若先同步目标文件再去同步进度文件,即使同步文目标文件程序被停掉,
					 * 那么下次续传顶多就把没有写入进度那部分再重新下载一次.对整个程序影响不大
					 */
				}
				progressFile.delete();
				/**
				 * 下载完毕就把进度文件删除
				 */
			}
			
		} catch (Exception e) {
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
				if (progressRaf != null) {
					progressRaf.close();
					progressRaf = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
