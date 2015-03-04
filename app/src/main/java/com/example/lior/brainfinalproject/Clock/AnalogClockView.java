package com.example.lior.brainfinalproject.Clock;

import java.util.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.example.lior.brainfinalproject.R;

public class AnalogClockView extends RelativeLayout{
    private Context mContext;

    /* views */
    private ImageView mHourHand;
    private ImageView mMinuteHand;

    /* state */
    private boolean isRunning = false;
    private boolean isFirstTick = true;

    /* angle */
    private int mHourAngle = INVALID_ANGLE;
    private int mMinuteAngle = INVALID_ANGLE;

    /* resources */
    private int mDialBackgroundResource = R.drawable.clock_dial_typical;
    private int mHourBackgroundResource = R.drawable.clock_hand_hour;
    private int mMinuteBackgroundResource = R.drawable.clock_hand_minute;

    private static final int INVALID_ANGLE = -1;
    private static final int DEGREE_MINUTE = 6;
    private static final int MINUTE_TO_HOUR_DEGREE = 12;
    private static final int HOUR_TO_HOUR_DEGREE = 30;

    /* clock */
    private Calendar calendar = null;

    public AnalogClockView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }
    public AnalogClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setIcons(context, attrs);
        init(context);
    }
    public AnalogClockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        setIcons(context, attrs);
        init(context);
    }

    public void setTime(int hour, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(1,1,1,hour, minute, 0);
    }

    private void setIcons(Context context, AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AnalogClockView);

        mDialBackgroundResource = array.getResourceId(R.styleable.AnalogClockView_dial, R.drawable.clock_dial_typical);
        mHourBackgroundResource = array.getResourceId(R.styleable.AnalogClockView_hand_hour, R.drawable.clock_hand_hour);
        mMinuteBackgroundResource = array.getResourceId(R.styleable.AnalogClockView_hand_minute, R.drawable.clock_hand_minute);
        array.recycle();
    }

    private void init(Context con){
        this.mContext = con;

        RelativeLayout.LayoutParams lp;

        mHourHand = new ImageView(mContext);
        setHourResource(mHourBackgroundResource);
        mHourHand.setScaleType(ScaleType.CENTER_INSIDE);
        lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.addRule(CENTER_IN_PARENT);
        this.addView(mHourHand, lp);

        mMinuteHand = new ImageView(mContext);
        setMinuteResource(mMinuteBackgroundResource);
        mMinuteHand.setScaleType(ScaleType.CENTER_INSIDE);
        lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.addRule(CENTER_IN_PARENT);
        this.addView(mMinuteHand, lp);

        lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.addRule(CENTER_IN_PARENT);

        setBackgroundResource(mDialBackgroundResource);
    }

    public void toggle(){
        if (isRunning)
            stop();
        else
            start();
    }
    public void start(){
        if (isRunning)
            return;

        isRunning = true;
        isFirstTick = true;

        Calendar tempCal = Calendar.getInstance();
        int timeToWaitInMilli = (int)tempCal.getTimeInMillis()%1000;

        mHandler.sendEmptyMessageDelayed(0, timeToWaitInMilli);
    }
    public void stop(){
        if (!isRunning)
            return;
        isRunning = false;
    }
    private void proceed(){
        if (!isRunning)
            return;

        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        // Update seconds
        calendar.add(Calendar.SECOND, 1);
//		Log.i(TAG, "hour : " + hour + ", minute : " + min + ", sec : " + sec);

        int newHourAngle = hour * HOUR_TO_HOUR_DEGREE + (min/MINUTE_TO_HOUR_DEGREE)*DEGREE_MINUTE;
        int newMinuteAngle = min * DEGREE_MINUTE;

        if (isFirstTick){
            if (mHourAngle != INVALID_ANGLE && mMinuteAngle != INVALID_ANGLE ){
                rotate(mHourHand, mHourAngle, newHourAngle);
                rotate(mMinuteHand, mMinuteAngle, newMinuteAngle);
            }
            else{
                rotate(mHourHand, newHourAngle, newHourAngle);
                rotate(mMinuteHand, newMinuteAngle, newMinuteAngle);
            }
            isFirstTick = false;
        }
        else{
            if (min == 0 && sec == 0)
                rotate(mHourHand, newHourAngle - DEGREE_MINUTE, newHourAngle);
            if (sec == 0)
                rotate(mMinuteHand, newMinuteAngle - DEGREE_MINUTE, newMinuteAngle);

        }
        mHourAngle = newHourAngle;
        mMinuteAngle = newMinuteAngle;
//		Log.i(TAG, "hourAngle : " + mHourAngle + ", minuteAngle : " + mMinuteAngle + ", secAngle : " + mSecondAngle);
    }

    private void rotate(ImageView view, int fromAngle, int toAngle){
        Animation anim;
        anim = new RotateAnimation(fromAngle, toAngle,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        int gap = Math.abs(toAngle - fromAngle);
        if (gap != DEGREE_MINUTE){
            anim.setDuration(600);
//            anim.setInterpolator(AnimationUtils.loadInterpolator(mContext, android.R.anim.accelerate_interpolator));
        }
        else{
            anim.setDuration(150);
//            anim.setInterpolator(AnimationUtils.loadInterpolator(mContext,
//                    android.R.anim.overshoot_interpolator));
        }
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    public void setDialResource(int id){
        this.mDialBackgroundResource = id;
        setBackgroundResource(mDialBackgroundResource);
    }
    public void setHourResource(int id){
        this.mHourBackgroundResource = id;
        mHourHand.setImageResource(id);
    }
    public void setMinuteResource(int id){
        this.mMinuteBackgroundResource = id;
        mMinuteHand.setImageResource(id);
    }
    private Handler mHandler = new Handler(){
        public void handleMessage( Message msg ){
            if (isRunning){
                proceed();
                mHandler.sendEmptyMessageDelayed( 0 , 1000 );
            }
        }
    };
}