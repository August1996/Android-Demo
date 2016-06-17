package demo.august1996.top.databingdingusage.bean;

import android.databinding.ObservableField;

/**
 * Created by August on 16/6/17.
 */
public class User {
    public ObservableField<String> firstName = new ObservableField<>();
    public ObservableField<String> lastName = new ObservableField<>();

    public User() {

    }

    public User(String firstName, String lastName) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }

    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }
}
