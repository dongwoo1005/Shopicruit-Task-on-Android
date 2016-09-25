package com.willdson.shopifywintershipapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.willdson.shopifywintershipapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private MainHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel viewModel = new MainViewModel();
        mBinding.setViewModel(viewModel);

        mHandler = new MainHandler(mBinding.getViewModel());
        mBinding.setHandler(mHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.onDestroy();
        mBinding.unbind();
    }
}
