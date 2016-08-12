package top.august1996.imageloaderdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import top.august1996.imageloader.core.ImageLoader;
import top.august1996.imageloader.util.BitmapUtils;

public class MainActivity extends Activity implements OnClickListener {
	
	private ImageView	mImageView1;
	private ImageView	mImageView2;
	private ImageView	mImageView3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageView1 = (ImageView) findViewById(R.id.imageView1);
		mImageView2 = (ImageView) findViewById(R.id.imageView2);
		mImageView3 = (ImageView) findViewById(R.id.imageView3);
		findViewById(R.id.showImageBtn).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, 100);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100 && resultCode == RESULT_OK) {
			ImageLoader.getInstance().load(data.getData(), mImageView1.getWidth(), mImageView1.getHeight())
					.into(mImageView1);
			ImageLoader.getInstance().load(BitmapUtils.getRealFilePath(getApplicationContext(), data.getData()),
					mImageView2.getWidth(), mImageView2.getHeight()).into(mImageView2);
			ImageLoader.getInstance()
					.load("http://upload-images.jianshu.io/upload_images/1643058-56cd967a79c0be9e.jpg?imageMogr2/auto-orient/strip%7CimageView2/1/w/300/h/300",
							mImageView3.getWidth(), mImageView3.getHeight())
					.into(mImageView3);
		}
	}
	
}
