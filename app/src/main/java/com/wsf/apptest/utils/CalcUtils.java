package com.wsf.apptest.utils;

import android.content.Context;

import com.wsf.apptest.bean.StepBean;

import java.util.List;

public class CalcUtils {

    public final static int[] findMax(List<StepBean> steps){
        int[] value=new int[2];
        int[] pos=new int[2];
        for (int i = 0; i < steps.size(); i++) {
            if(steps.get(i).getNumber()>value[1]){
                value[1]=steps.get(i).getNumber();
                pos[1]=i;

                if(value[0]<value[1]){
                    value[0]^=value[1];
                    value[1]^=value[0];
                    value[0]^=value[1];

                    pos[0]^=pos[1];
                    pos[1]^=pos[0];
                    pos[0]^=pos[1];

                }
            }
        }

        return pos;
    }

    public static  int sp2px(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return  (int)(spValue*fontScale+0.5f);
    }
    public static  int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return  (int)(dpValue*scale+0.5f);
    }
}
