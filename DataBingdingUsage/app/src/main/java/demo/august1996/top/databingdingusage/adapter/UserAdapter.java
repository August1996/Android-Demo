package demo.august1996.top.databingdingusage.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import demo.august1996.top.databingdingusage.BR;
import demo.august1996.top.databingdingusage.R;
import demo.august1996.top.databingdingusage.bean.Student;
import demo.august1996.top.databingdingusage.databinding.ItemUserBinding;

/**
 * Created by August on 16/6/17.
 */
public class UserAdapter extends BaseAdapter {

    private ArrayList<Student> mDatas;

    public UserAdapter(ArrayList<Student> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ItemUserBinding binding;

        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_user, viewGroup, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemUserBinding) convertView.getTag();
        }

        binding.setVariable(BR.stu, mDatas.get(i));
        binding.executePendingBindings();
        convertView.findViewById(R.id.testBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatas.get(i).setFirstName(mDatas.get(i).getFirstName() + i);
                mDatas.get(i).setLastName(mDatas.get(i).getLastName() + i);
            }
        });
        return convertView;
    }

}
