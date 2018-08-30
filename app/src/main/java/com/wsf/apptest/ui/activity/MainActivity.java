package com.wsf.apptest.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wsf.apptest.R;
import com.wsf.apptest.bean.StepBean;
import com.wsf.apptest.ui.widget.StepsView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        {


    private StepsView stepsView;
    private TextView textSign;
    private List<StepBean> stepBeanList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();
        initListener();



    }



    private void initView(){
        stepsView=findViewById(R.id.steps_view);
        textSign=findViewById(R.id.text_sign);


    }

    private void initData(){
        stepBeanList.add(new StepBean(StepBean.COMPLETED, 2));
        stepBeanList.add(new StepBean(StepBean.COMPLETED, 4));
        stepBeanList.add(new StepBean(StepBean.CURRENT, 10));
        stepBeanList.add(new StepBean(StepBean.UNDO, 2));
        stepBeanList.add(new StepBean(StepBean.UNDO, 4));
        stepBeanList.add(new StepBean(StepBean.UNDO, 4));
        stepBeanList.add(new StepBean(StepBean.UNDO, 30));

        stepsView.setStepNum(stepBeanList);

    }

    private void initListener(){
        textSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepsView.startSignAnimation(2);
            }
        });
    }



}
