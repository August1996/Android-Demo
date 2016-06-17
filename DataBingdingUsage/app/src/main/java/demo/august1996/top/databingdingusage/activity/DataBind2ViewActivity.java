package demo.august1996.top.databingdingusage.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import demo.august1996.top.databingdingusage.R;
import demo.august1996.top.databingdingusage.bean.User;
import demo.august1996.top.databingdingusage.databinding.ActivityDataBind2ViewBinding;

public class DataBind2ViewActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bind2_view);

        user = new User();
        user.firstName.set("Yu");
        user.lastName.set("jianbin");

        ActivityDataBind2ViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind2_view);
        binding.setUser(user);

    }

    public void test(View v) {
        user.firstName.set("August");
        user.lastName.set("1996");
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
    }
}
