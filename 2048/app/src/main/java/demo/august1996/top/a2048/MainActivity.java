package demo.august1996.top.a2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {

    private MatrixView mMatrixView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMatrixView = new MatrixView(this);
        setContentView(mMatrixView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mMatrixView.onKeyDown(keyCode, event);
    }
}
