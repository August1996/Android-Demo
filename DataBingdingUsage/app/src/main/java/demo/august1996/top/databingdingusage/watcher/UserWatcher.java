package demo.august1996.top.databingdingusage.watcher;

import android.text.Editable;
import android.text.TextWatcher;

import demo.august1996.top.databingdingusage.bean.User;

/**
 * Created by August on 16/6/17.
 */
public class UserWatcher {

    private User user;

    public UserWatcher(User user) {
        this.user = user;
    }

    public TextWatcher firstNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            user.firstName.set(editable.toString());
        }
    };
    public TextWatcher lastNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            user.lastName.set(editable.toString());
        }
    };
}
