package top.august1996.gifenginedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import top.august1996.gifengine.core.GIFLoader;
import top.august1996.gifengine.util.FileUtils;

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
		Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
		imageIntent.setType("image/*");
		startActivityForResult(imageIntent, 100);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100 && resultCode == RESULT_OK) {
			GIFLoader.getInstance().load(data.getData()).into(mImageView1);
			GIFLoader.getInstance().load(FileUtils.getRealFilePath(this, data.getData())).into(mImageView2);
			GIFLoader.getInstance().load("http://img1.imgtn.bdimg.com/it/u=998216620,3976144567&fm=21&gp=0.jpg")
					.into(mImageView3);
		}
	}
}
