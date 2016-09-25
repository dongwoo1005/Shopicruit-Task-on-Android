package com.willdson.shopifywintershipapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.willdson.shopifywintershipapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private Brain mBrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel viewModel = new MainViewModel();
        mBinding.setViewModel(viewModel);

        mBrain = new Brain(mBinding.getViewModel());
        mBinding.setBrain(mBrain);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBrain.onDestroy();
        mBinding.unbind();
    }
}
