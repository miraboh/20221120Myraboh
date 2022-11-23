package com.fiona.a20221120_myrabohuche_nycschools;
import android.app.Application;

import in.myinnos.customfontlibrary.TypefaceUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/gta.ttf");

    }

}
