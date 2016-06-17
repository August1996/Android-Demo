package demo.august1996.top.databingdingusage.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import demo.august1996.top.databingdingusage.R;
import demo.august1996.top.databingdingusage.bean.User;
import demo.august1996.top.databingdingusage.databinding.ActivityViewBind2DataBinding;
import demo.august1996.top.databingdingusage.watcher.UserWatcher;

public class ViewBind2DataAndDataBind2ViewActivity extends AppCompatActivity {

    private User user;
    private UserWatcher watcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bind2_data);

        user = new User();
        user.firstName.set("Yu");
        user.lastName.set("jianbin");

        watcher = new UserWatcher(user);

        ActivityViewBind2DataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_view_bind2_data);
        binding.setUser(user);
        binding.setWatcher(watcher);

    }

    public void test(View v) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();


        user.firstName.set("August");
        user.lastName.set("1996");
    }
}

