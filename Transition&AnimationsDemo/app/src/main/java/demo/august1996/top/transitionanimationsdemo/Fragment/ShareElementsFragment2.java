package demo.august1996.top.transitionanimationsdemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.august1996.top.transitionanimationsdemo.R;

/**
 * Created by August on 16/9/8.
 */
public class ShareElementsFragment2 extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_elements2,container,false);
        return view;
    }
}
