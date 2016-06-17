package demo.august1996.top.databingdingusage.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import demo.august1996.top.databingdingusage.BR;


/**
 * Created by August on 16/6/17.
 */
public class Student extends BaseObservable {
    private String firstName;
    private String lastName;
    private boolean isHidden;


    public Student(String firstName, String lastName, boolean isHidden) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isHidden = isHidden;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
