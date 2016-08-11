package demo.august1996.top.videocompressdemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lalongooo.videocompressor.video.MediaController;

public class MainActivity extends Activity implements View.OnClickListener, TextWatcher, MediaController.VideoConvertListener {

    public static final int OPEN_VIDEO_PICKER = 100;

    private TextView mFilenameTV;
    private Button mConvertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFilenameTV = (TextView) findViewById(R.id.filenameTV);
        mConvertBtn = (Button) findViewById(R.id.convertBtn);
        mFilenameTV.addTextChangedListener(this);
        mConvertBtn.setOnClickListener(this);
        findViewById(R.id.selectBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectBtn:
                openPicker();
                break;
            case R.id.convertBtn:
                convert();
                break;
        }
    }

    private void openPicker() {
        Intent videoPicker = new Intent(Intent.ACTION_PICK);
        videoPicker.setType("video/*");
        startActivityForResult(videoPicker, OPEN_VIDEO_PICKER);
    }

    private void convert() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaController mc = MediaController.getInstance();
                mc.setVideoConvertListener(MainActivity.this);
                mc.convertVideo(mFilenameTV.getText().toString(), System.currentTimeMillis() + ".mp4", 800, 600);
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_VIDEO_PICKER && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null && data.getData().getPath() != null) {
                Cursor cr = getContentResolver().query(data.getData(), null, null, null, null);
                if (cr != null && cr.moveToFirst()) {
                    String filename = cr.getString(cr.getColumnIndex("_data"));
                    mFilenameTV.setText(filename);
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mConvertBtn.setEnabled(!"未选择文件".equals(mFilenameTV.getText().toString()));
    }

    @Override
    public void onSuccess(String src, String dest) {
        Log.i("VideoCompress", dest);
    }

    @Override
    public void onError(Exception e) {
        Log.i("VideoCompress", e.getMessage());
    }
}
