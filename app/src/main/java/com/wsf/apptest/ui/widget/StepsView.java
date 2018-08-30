package com.wsf.apptest.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.wsf.apptest.R;
import com.wsf.apptest.bean.StepBean;
import com.wsf.apptest.utils.CalcUtils;

import java.util.ArrayList;
import java.util.List;

public class StepsView extends View {
    public StepsView(Context context) {
        this(context,null);
    }

    public StepsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StepsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private static final int ANIMATION_TIME=230;

    private static final int ANIMATION_INTERVAL=10;

    private List<StepBean> stepBeanList;
    private  int stepNum=0;

    private List<Float> circleCenterPosList;

    private Paint unCompletedPaint;
    private Paint completedPaint;
    private Paint textNumPaint;

    private Drawable completedIcon;
    private Drawable defaultIcon;
    private Drawable attentionIcon;
    private Drawable upIcon;


    private float lineHeight=
            CalcUtils.dp2px(
                    getContext(),
                    2f
            );

    private float lineWidth=
            CalcUtils.dp2px(
                    getContext(),
                    23f
            );

    private float iconHeight=
            CalcUtils.dp2px(
                    getContext(),
                    24f
            );
    private float iconWidth=
            CalcUtils.dp2px(
                    getContext(),
                    21.5f
            );
    private float upHeight=
            CalcUtils.dp2px(
                    getContext(),
                    12.0f
            );
    private float upWidth=
            CalcUtils.dp2px(
                    getContext(),
                    20.5f
            );



    private int unCompletedLineColor=
            ContextCompat.getColor(
                    getContext(),
                    R.color.c_999999
            );

    private int unCompletedTextColor=
            ContextCompat.getColor(
                    getContext(),
                    R.color.c_cccccc
            );

    private  int currentTextColor=
            ContextCompat.getColor(
                    getContext(),
                    R.color.c_f7b93c
            );

    private  int completedLineColor=
            ContextCompat.getColor(
                    getContext(),
                    R.color.c_41c961
            );

    private volatile boolean isAnimation=false;

    private float centerY;

    private float leftY;

    private float rightY;

    private float animationWidth=(lineWidth/ANIMATION_TIME)*ANIMATION_INTERVAL;

    private int count;

    private int pos;

    private int[] max;

    private void init(){
        stepBeanList=new ArrayList<>();
        circleCenterPosList=new ArrayList<>();

        unCompletedPaint=new Paint();
        unCompletedPaint.setAntiAlias(true);
        unCompletedPaint.setColor(unCompletedLineColor);
        unCompletedPaint.setStrokeWidth(2);
        unCompletedPaint.setStyle(Paint.Style.FILL);

        completedPaint=new Paint();
        completedPaint.setAntiAlias(true);
        completedPaint.setColor(completedLineColor);
        completedPaint.setStrokeWidth(2);
        completedPaint.setStyle(Paint.Style.FILL);

        textNumPaint=new Paint();
        textNumPaint.setAntiAlias(true);
        textNumPaint.setColor(unCompletedTextColor);
        textNumPaint.setTextSize(CalcUtils.sp2px(getContext(),8f));
        textNumPaint.setStyle(Paint.Style.FILL);

        attentionIcon=
                ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.ic_sign_unfinish
                );
        completedIcon=
                ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.ic_sign_finish
                );
        defaultIcon=
                ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.ic_sign_unfinish

                );
        upIcon=
                ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.ic_sign_up
                );


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec)
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isAnimation){
            drawSign(canvas);
        }else {
            drawUnSign(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerY=CalcUtils.dp2px(getContext(),28f)+iconHeight/2;
        leftY=centerY-lineHeight/2;
        rightY=centerY+lineHeight/2;
        circleCenterPosList.clear();
        float size=CalcUtils.dp2px(getContext(),14.5f)+iconWidth/2;
        circleCenterPosList.add(size);
        for (int i=1;i<stepNum;i++){
            size=size+iconWidth+lineWidth;
            circleCenterPosList.add(size);
        }
    }

    private void drawSign(Canvas canvas){
        for (int i = 0; i <circleCenterPosList.size(); i++) {
            float preCompletedXPos=circleCenterPosList.get(i)+iconWidth/2;

            if(i!=circleCenterPosList.size()-1){
                if(stepBeanList.get(i+1).getState()==StepBean.COMPLETED){
                    canvas.drawRect(
                                    preCompletedXPos,
                                    leftY,
                                    preCompletedXPos+lineWidth,
                                    rightY,
                                    completedPaint
                            );
                }else{
                    if(i==(pos-1)){
                        float endX=preCompletedXPos+
                                animationWidth*(count/ANIMATION_INTERVAL);
                        canvas.drawRect(preCompletedXPos,leftY,endX,rightY,completedPaint);
                        canvas.drawRect(endX,leftY,preCompletedXPos+lineWidth,rightY,unCompletedPaint);


                    }else{
                        canvas.drawRect(preCompletedXPos,leftY,preCompletedXPos+lineWidth,rightY,unCompletedPaint);

                    }
                }
            }


            float currentCompletedXPos=circleCenterPosList.get(i);

            Rect rect=
                    new Rect(
                            (int)(currentCompletedXPos-iconWidth/2),
                            (int)(centerY-iconHeight/2),
                            (int)(currentCompletedXPos+iconWidth/2),
                            (int)(centerY+iconHeight/2)
                    );
            StepBean stepBean=stepBeanList.get(i);
            if(i==pos&&count==ANIMATION_TIME){
                completedIcon.setBounds(rect);
                completedIcon.draw(canvas);
            }else {
                switch (stepBean.getState()){
                    case StepBean.UNDO:
                        defaultIcon.setBounds(rect);
                        defaultIcon.draw(canvas);
                        break;
                    case StepBean.CURRENT:
                        attentionIcon.setBounds(rect);
                        attentionIcon.draw(canvas);
                        break;
                    case StepBean.COMPLETED:
                        completedIcon.setBounds(rect);
                        completedIcon.draw(canvas);
                        break;
                    default:
                        defaultIcon.setBounds(rect);
                        defaultIcon.draw(canvas);
                        break;
                }
            }




            if(stepBean.getState()==StepBean.COMPLETED||
                    (i==pos&&count==ANIMATION_TIME)){
                if(max[0]==i||max[1]==i){
                    textNumPaint.setColor(currentTextColor);
                }else{
                    textNumPaint.setColor(completedLineColor);
                }
            }else {
                textNumPaint.setColor(unCompletedTextColor);
            }

            canvas.drawText(
                    "+"+stepBean.getNumber(),
                    currentCompletedXPos+CalcUtils.dp2px(getContext(),2f),
                    centerY-iconHeight/2-CalcUtils.dp2px(getContext(),0.5f),
                    textNumPaint

            );


            if(i==max[0]||i==max[1]){
                Rect rectUp=
                        new Rect(
                                (int) (currentCompletedXPos-upWidth/2),
                                (int) (centerY-iconHeight/2-CalcUtils.dp2px(getContext(),8f)-upHeight),
                                (int)(currentCompletedXPos+upWidth/2),
                                (int) (centerY-iconHeight/2-CalcUtils.dp2px(getContext(),8f))
                        );
                upIcon.setBounds(rectUp);
                upIcon.draw(canvas);
            }





        }

        count=count+ANIMATION_INTERVAL;
        if(count<=ANIMATION_TIME){
            postInvalidate();
        }else {
            isAnimation=false;
            count=0;
        }



    }

    private void drawUnSign(Canvas canvas){
        for (int i = 0; i <circleCenterPosList.size(); i++) {
            float preCompletedXPos=circleCenterPosList.get(i)+iconWidth/2;

            if(i!=circleCenterPosList.size()-1){
                if(stepBeanList.get(i+1).getState()==StepBean.COMPLETED){
                    canvas.drawRect(preCompletedXPos,
                            leftY,
                            preCompletedXPos+lineWidth,
                            rightY,
                            completedPaint
                    );
                }else{

                    canvas.drawRect(preCompletedXPos,leftY,preCompletedXPos+lineWidth,rightY,unCompletedPaint);


                }
            }


            float currentCompletedXPos=circleCenterPosList.get(i);

            Rect rect=
                    new Rect(
                            (int)(currentCompletedXPos-iconWidth/2),
                            (int)(centerY-iconHeight/2),
                            (int)(currentCompletedXPos+iconWidth/2),
                            (int)(centerY+iconHeight/2)
                    );
            StepBean stepBean=stepBeanList.get(i);

            switch (stepBean.getState()){
                case StepBean.UNDO:
                    defaultIcon.setBounds(rect);
                    defaultIcon.draw(canvas);
                    break;
                case StepBean.CURRENT:
                    attentionIcon.setBounds(rect);
                    attentionIcon.draw(canvas);
                    break;
                case StepBean.COMPLETED:
                    completedIcon.setBounds(rect);
                    completedIcon.draw(canvas);
                    break;
                default:
                    defaultIcon.setBounds(rect);
                    defaultIcon.draw(canvas);
                    break;

            }





            if(stepBean.getState()==StepBean.COMPLETED){
                if(max[0]==i||max[1]==i){
                    textNumPaint.setColor(currentTextColor);
                }else{
                    textNumPaint.setColor(completedLineColor);
                }
            }else {
                textNumPaint.setColor(unCompletedTextColor);
            }

            canvas.drawText(
                    "+"+stepBean.getNumber(),
                    currentCompletedXPos+CalcUtils.dp2px(getContext(),2f),
                    centerY-iconHeight/2-CalcUtils.dp2px(getContext(),0.5f),
                    textNumPaint

            );


            if(i==max[0]||i==max[1]){
                Rect rectUp=
                        new Rect(
                                (int) (currentCompletedXPos-upWidth/2),
                                (int) (centerY-iconHeight/2-CalcUtils.dp2px(getContext(),8f)-upHeight),
                                (int)(currentCompletedXPos+upWidth/2),
                                (int) (centerY-iconHeight/2-CalcUtils.dp2px(getContext(),8f))
                        );
                upIcon.setBounds(rectUp);
                upIcon.draw(canvas);
            }

        }

    }


    public void setStepNum(List<StepBean> stepBeanList){
        if(stepBeanList==null)return;

        this.stepBeanList=stepBeanList;
        stepNum=stepBeanList.size();
        max=CalcUtils.findMax(stepBeanList);

        postInvalidate();
    }

    public void startSignAnimation(int pos){
        isAnimation=true;
        this.pos=pos;
        postInvalidate();

    }

}
