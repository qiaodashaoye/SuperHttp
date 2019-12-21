package com.qpg.superhttpdemo;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.Lifecycle;

public class Test {
    public Test(ComponentActivity activity){
        Lifecycle lifecycle= activity.getLifecycle();
        activity.getLifecycle().addObserver(new MyObserver(activity.getLifecycle(),activity));
    }
}
