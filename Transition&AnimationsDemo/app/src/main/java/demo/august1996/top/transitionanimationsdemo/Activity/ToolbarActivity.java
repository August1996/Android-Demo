package demo.august1996.top.transitionanimationsdemo.Activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import demo.august1996.top.transitionanimationsdemo.Activity.BaseActivity;
import demo.august1996.top.transitionanimationsdemo.R;


/**
 * Created by August on 16/9/5.
 */
public abstract class ToolbarActivity extends BaseActivity {

    protected Toolbar mToolbar;


    @Override
    protected void beforeInitView() {
        super.beforeInitView();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getToolbarTitle());

        if (canBack()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishAfterTransition();
                }
            });
        }
    }

    protected boolean canBack() {
        return false;
    }

    protected abstract String getToolbarTitle();
}
