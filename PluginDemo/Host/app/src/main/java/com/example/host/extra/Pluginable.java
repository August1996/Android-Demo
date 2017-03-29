package com.example.host.extra;

import android.os.Bundle;

/**
 * Created by August on 2017/3/28.
 */

public interface Pluginable {



    void onCreate(Bundle savedInstanceState);

    void onRestart();


    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
