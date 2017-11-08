package com.graduation.even.graduationclient.util;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Even on 2017/11/2.
 * Toolbar等相关设置
 */

public class ToolbarUtil {
    public void initToolbar(final AppCompatActivity activity,Toolbar toolbar){
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
