package demo.august1996.top.timelineviewdemo;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import demo.august1996.top.timelineviewdemo.widget.TimeLineView;

public class MainActivity extends AppCompatActivity implements TimeLineView.HeightLineProvider {

    @Override
    public boolean isHeightLine(int position) {
        if (position % 4 == 0) {
            return true;
        }
        return false;
    }

    private TimeLineView tlv;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tlv = (TimeLineView) findViewById(R.id.tlv);
        tlv.setHeightLineProvider(this);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_ok);
        tlv.setHeightBitmap(bitmapDrawable.getBitmap());

    }

    private void addItem() {
        View v = LayoutInflater.from(this).inflate(R.layout.item_test, tlv, false);
        ((TextView) v.findViewById(R.id.tx_action)).setText(tlv.getChildCount() + "探访了你");
        ((TextView) v.findViewById(R.id.tx_action_time)).setText(sdf.format(new Date()));
        ((TextView) v.findViewById(R.id.tx_action_status)).setText("查看");
        tlv.addView(v);
    }

    public void addView(View v) {
        addItem();
    }

    public void removeView(View v) {
        if (tlv.getChildCount() != 0) {
            tlv.removeViewAt(tlv.getChildCount() - 1);
        }
    }
}
