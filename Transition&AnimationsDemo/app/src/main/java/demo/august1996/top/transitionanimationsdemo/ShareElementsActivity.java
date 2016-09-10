package demo.august1996.top.transitionanimationsdemo;

import android.support.v4.app.Fragment;
import android.transition.Slide;

import demo.august1996.top.transitionanimationsdemo.Activity.ToolbarActivity;
import demo.august1996.top.transitionanimationsdemo.Fragment.ShareElementsFragment1;

public class ShareElementsActivity extends ToolbarActivity {


    @Override
    protected String getToolbarTitle() {
        return "共享元素动画";
    }

    @Override
    protected void initView() {
        Fragment fragment = new ShareElementsFragment1();
        Slide slide = new Slide();
        slide.setDuration(1000);
        fragment.setExitTransition(slide);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_share_elements;
    }

    @Override
    protected boolean canBack() {
        return true;
    }
}
