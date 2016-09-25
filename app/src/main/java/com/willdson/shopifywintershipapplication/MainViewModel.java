package com.willdson.shopifywintershipapplication;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by dwson on 9/24/16.
 */

public class MainViewModel extends BaseObservable {

    public String currentState = "";

    @Bindable
    public String getCurrentState() {
        return this.currentState;
    }

    public void addState(String state) {
        currentState += state + "\n";
        notifyPropertyChanged(BR.currentState);
    }
}
