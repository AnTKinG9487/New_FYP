package com.example.new_fyp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import java.util.Random;

public class backup_catch_ball extends AppCompatActivity implements View.OnTouchListener{

    private ImageView bottom_imageView, top_imageView, heart1, heart2, heart3;
    private FrameLayout top_frame;
    private Drawable bottom_imageBoxLeft, bottom_imageBoxRight, top_imageBoxLeft, top_imageBoxRight;
    private float bottom_boxX, top_boxX;
    private boolean bottom_action_left, bottom_action_right, top_action_left, top_action_right;
    private TextView scoreTextView;
    private Timer timer = new Timer();
    private Handler handler = new Handler();
    private int lives = 3;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_ball);

        bottom_imageView = findViewById(R.id.bottom_image);
        bottom_imageBoxLeft = getResources().getDrawable(R.drawable.pump_button1);
        bottom_imageBoxRight = getResources().getDrawable(R.drawable.pump_button2);

        top_imageView = findViewById(R.id.top_image);
        top_frame = findViewById(R.id.top_frame);
        top_imageBoxLeft = getResources().getDrawable(R.drawable.pump_button1);
        top_imageBoxRight = getResources().getDrawable(R.drawable.pump_button2);

        scoreTextView = findViewById(R.id.score);

        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

        findViewById(R.id.bottom_left).setOnTouchListener(this);
        findViewById(R.id.bottom_right).setOnTouchListener(this);
        findViewById(R.id.top_left).setOnTouchListener(this);
        findViewById(R.id.top_right).setOnTouchListener(this);

        handler.postDelayed(createNewImageViewRunnable, 3000);
        handler.postDelayed(createNewBombImageViewRunnable, 3000);
        final int[] score = {0};


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bottom_changePos();
                        top_changePos();

                        for (int i = 0; i < top_frame.getChildCount(); i++) {
                            View childView = top_frame.getChildAt(i);
                            if (childView.getTag() != null && childView.getTag().equals("ball")) {
                                if (isViewOverlap(childView, top_imageView)) {
                                    // 接触到移动图片，向下移动并从 top_frame 中移除
                                    float y = top_frame.getHeight() - childView.getHeight(); // 获取子 View 底部的 Y 坐标
                                    ObjectAnimator animator = ObjectAnimator.ofFloat(childView, "y", childView.getY(), y);
                                    animator.setDuration(500);
                                    animator.setInterpolator(new LinearInterpolator());
                                    animator.start();
                                    if (y > top_frame.getHeight()) {
                                        // 移出屏幕，从 top_frame 中移除
                                        top_frame.removeView(childView);
                                    } else {
                                        childView.setY(y);
                                    }
                                } else if (isViewOverlap(childView, bottom_imageView)) {
                                    // 接触到底部图片，从 top_frame 中移除
                                    top_frame.removeView(childView);
                                    score[0]++;
                                    scoreTextView.setText(String.format("%03d", score[0])); // Update score text
                                }
                            }

                            // Inside the timer task
                            if (childView.getTag() != null && childView.getTag().equals("bomb")) {
                                if (isViewOverlap(childView, top_imageView)) {
                                    reduceLives();
                                    top_frame.removeView(childView); // Remove the bomb after reducing lives
                                } else if (isViewOverlap(childView, bottom_imageView)) {
                                    reduceLives();
                                    top_frame.removeView(childView); // Remove the bomb after reducing lives
                                }
                            }

                        }
                    }
                });
            }
        }, 0, 20);

    }

    public void bottom_changePos(){
        bottom_boxX = bottom_imageView.getX();

        //left
        if(bottom_action_left){
            bottom_boxX -= 20;
            bottom_imageView.setImageDrawable(bottom_imageBoxLeft);
        }
        //right
        if(bottom_action_right){
            bottom_boxX += 20;
            bottom_imageView.setImageDrawable(bottom_imageBoxRight);
        }

        if (bottom_boxX < 0){
            bottom_boxX = 0;
            bottom_imageView.setImageDrawable(bottom_imageBoxRight);
        }
        if (bottom_boxX > top_frame.getWidth() - bottom_imageView.getWidth()){
            bottom_boxX = top_frame.getWidth() - bottom_imageView.getWidth();
            bottom_imageView.setImageDrawable(bottom_imageBoxLeft);
        }

        bottom_imageView.setX(bottom_boxX);
    }

    public void top_changePos(){
        top_boxX = top_imageView.getX();

        //left
        if(top_action_left){
            top_boxX -= 20;
            top_imageView.setImageDrawable(top_imageBoxLeft);
        }
        //right
        if(top_action_right){
            top_boxX += 20;
            top_imageView.setImageDrawable(top_imageBoxRight);
        }

        if (top_boxX < 0){
            top_boxX = 0;
            top_imageView.setImageDrawable(top_imageBoxRight);
        }
        if (top_boxX > top_frame.getWidth() - top_imageView.getWidth()){
            top_boxX = top_frame.getWidth() - top_imageView.getWidth();
            top_imageView.setImageDrawable(top_imageBoxLeft);
        }

        top_imageView.setX(top_boxX);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            switch(view.getId()){
                case R.id.bottom_left:
                    bottom_action_left = true;
                    break;
                case R.id.bottom_right:
                    bottom_action_right = true;
                    break;
            }
        }else{
            bottom_action_left = false;
            bottom_action_right = false;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            switch(view.getId()){
                case R.id.top_left:
                    top_action_left = true;
                    break;
                case R.id.top_right:
                    top_action_right = true;
                    break;
            }
        }else{
            top_action_left = false;
            top_action_right = false;
        }
        return true;
    }

    private Runnable createNewImageViewRunnable = new Runnable() {
        @Override
        public void run() {
            ImageView imageView = new ImageView(backup_catch_ball.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
            imageView.setImageResource(getRandomImageResource());
            imageView.setX(random.nextInt(top_frame.getWidth() - imageView.getWidth()));
            imageView.setY(top_frame.getHeight() / 2 - imageView.getHeight() / 2 - 750);
            imageView.setTag("ball"); // 添加 Tag

            top_frame.addView(imageView);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    top_frame.removeView(imageView);
                }
            }, 7000);

            handler.postDelayed(createNewImageViewRunnable, 3000);
        }
    };

    private Runnable createNewBombImageViewRunnable = new Runnable() {
        @Override
        public void run() {
            ImageView imageView = new ImageView(backup_catch_ball.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
            imageView.setImageResource(getBombImageResource());
            imageView.setY(top_frame.getHeight() / 2 - imageView.getHeight() / 2 - 750);
            imageView.setTag("bomb"); // Add Tag

            top_frame.addView(imageView);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    top_frame.removeView(imageView);
                }
            }, 3000);
            int delay = random.nextInt(3000) + 3000;

            handler.postDelayed(createNewBombImageViewRunnable, delay);
        }
    };

    private int getBombImageResource() {
        int[] imageResources = {R.drawable.happy};
        return imageResources[random.nextInt(imageResources.length)];
    }
    private int getRandomImageResource() {
        int[] imageResources = {R.drawable.sad, R.drawable.sad_no_eye, R.drawable.sad_no_mouth};
        return imageResources[random.nextInt(imageResources.length)];
    }
    // 判断两个 View 是否重叠
    private boolean isViewOverlap(View view1, View view2) {
        Rect rect1 = new Rect();
        view1.getHitRect(rect1);
        Rect rect2 = new Rect();
        view2.getHitRect(rect2);
        return Rect.intersects(rect1, rect2);
    }

    private void reduceLives() {
        lives--;
        if (lives == 2) {
            heart3.setImageResource(R.drawable.heart_empty);
        } else if (lives == 1) {
            heart2.setImageResource(R.drawable.heart_empty);
        } else if (lives == 0) {
            heart1.setImageResource(R.drawable.heart_empty);
            gameOver();
        }
    }

    private void gameOver() {
        timer.cancel();
        handler.removeCallbacks(createNewImageViewRunnable);
        showGameOverDialog();
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
//        builder.setMessage("Your score: " + scoreTextView.setText(String.format("%03d", score[0])));
        builder.setCancelable(false);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}



