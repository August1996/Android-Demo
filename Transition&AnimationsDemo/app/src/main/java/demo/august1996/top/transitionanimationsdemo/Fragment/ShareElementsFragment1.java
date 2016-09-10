package demo.august1996.top.transitionanimationsdemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.august1996.top.transitionanimationsdemo.R;

/**
 * Created by August on 16/9/8.
 */
public class ShareElementsFragment1 extends Fragment {


    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_elements1, container, false);
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(false);
            }
        });
        view.findViewById(R.id.nextWithOverlap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(true);
            }
        });
        this.view = view.findViewById(R.id.img);
        return view;
    }

    private void replaceFragment(boolean isOverlap) {
        Fragment fragment = new ShareElementsFragment2();
        Slide slide = new Slide();
        slide.setDuration(1000);
        slide.setSlideEdge(Gravity.LEFT);
        fragment.setAllowEnterTransitionOverlap(isOverlap);
        fragment.setAllowReturnTransitionOverlap(isOverlap);
        fragment.setEnterTransition(slide);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(1000);
        fragment.setSharedElementEnterTransition(changeBounds);

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .addSharedElement(view, view.getTransitionName())
                .commit();

    }
}
