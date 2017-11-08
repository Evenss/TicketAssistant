package com.graduation.even.graduationclient.application;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Even on 2017/11/1.
 */

public class MyApplication extends Application{
    private List<Activity> activityList;

    public MyApplication(){
        activityList = new ArrayList<>();
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public void backToLoginActivity(){
        removeAll();

//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }
    public void removeAll(){
        for (Activity activity : activityList){
            activity.finish();
        }
        activityList.clear();
    }

}
