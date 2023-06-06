package com.example.new_fyp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;


public class ball_game_2 extends AppCompatActivity {

    private ImageView balloonImageView1, balloonImageView2, pumpButton1, pumpButton2, startButton;
    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 3000; // 3 seconds
    private int pumpCount1, pumpCount2;
    private boolean isGameEnded = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_game2);



        balloonImageView1 = findViewById(R.id.balloonImageView1);
        balloonImageView2 = findViewById(R.id.balloonImageView2);
        pumpButton1 = findViewById(R.id.pumpButton1);
        pumpButton2 = findViewById(R.id.pumpButton2);
        countdownText = findViewById(R.id.countdown_text);
        startButton = findViewById(R.id.startButton);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom layout for the dialog
        View view = LayoutInflater.from(this).inflate(R.layout.ball_game_2_1st_user_message, null);
        builder.setView(view);

        pumpCount1 = 0;
        pumpCount2 = 0;

        pumpButton1.setEnabled(false);
        pumpButton2.setEnabled(false);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
                startButton.setVisibility(View.GONE);
            }
        });

        pumpButton1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pumpButton1.setBackgroundResource(R.drawable.push2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pumpButton1.setBackgroundResource(R.drawable.push1);
                }
                return false;
            }
        });

        pumpButton2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pumpButton2.setBackgroundResource(R.drawable.push2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pumpButton2.setBackgroundResource(R.drawable.push1);
                }
                return false;
            }
        });

        pumpButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isGameEnded) {
                    pumpCount1++;


                    if (pumpCount1 == 6) {
                        balloonImageView1.setImageResource(R.drawable.plant1);
                    } else if (pumpCount1 == 12) {
                        balloonImageView1.setImageResource(R.drawable.plant2);
                    } else if (pumpCount1 == 18) {
                        balloonImageView1.setImageResource(R.drawable.plant3);
                    } else if (pumpCount1 == 24) {
                        balloonImageView1.setImageResource(R.drawable.plant4);
                        isGameEnded = true;
                        pumpButton1.setEnabled(false);
                        pumpButton2.setEnabled(false);

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                display_message_1();
                            }
                        }, 1000); // 1 seconds delay
                    }
                }
            }
        });

        pumpButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isGameEnded) {
                    pumpCount2++;
//                    scoreTextView2.setText("Player 2 score: " + pumpCount2);

                    if (pumpCount2 == 6) {
                        balloonImageView2.setImageResource(R.drawable.plant1);
                    } else if (pumpCount2 == 12) {
                        balloonImageView2.setImageResource(R.drawable.plant2);
                    } else if (pumpCount2 == 18) {
                        balloonImageView2.setImageResource(R.drawable.plant3);
                    } else if (pumpCount2 == 24) {
                        balloonImageView2.setImageResource(R.drawable.plant4);
                        isGameEnded = true;
                        pumpButton1.setEnabled(false);
                        pumpButton2.setEnabled(false);

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                display_message_2();
                            }
                        }, 1000); // 1 seconds delay
                    }
                }
            }
        });
    }
    private void display_message_1(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.message_box_reset_game, (LinearLayout) findViewById(R.id.ball_game_2_1st));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.titleTextView)).setText("Congratulation");
        ((TextView) view.findViewById(R.id.messageTextView)).setText("Player 1 Win!!!");

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.playagin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(false);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        view.findViewById(R.id.backtomanu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ball_game_2.this, home_page.class);
                startActivity(intent);
            }
        });

        if (alertDialog.getWindow() !=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void display_message_2(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.message_box_reset_game, (LinearLayout) findViewById(R.id.ball_game_2_1st));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.titleTextView)).setText("Congratulation");
        ((TextView) view.findViewById(R.id.messageTextView)).setText("Player 2 Win!!!");

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.playagin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(false);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        view.findViewById(R.id.backtomanu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ball_game_2.this, home_page.class);
                startActivity(intent);
            }
        });

        if (alertDialog.getWindow() !=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
        alertDialog.setCancelable(false);

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                countdownText.setText("Game Start!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pumpButton1.setEnabled(true);
                        pumpButton2.setEnabled(true);
                        countdownText.setVisibility(View.INVISIBLE);
                    }
                }, 1000); // 1 second
            }
        }.start();
    }

    private void updateTimer() {
        int seconds = (int) (timeLeftInMilliseconds / 1000);
        countdownText.setText(Integer.toString(seconds));
    }

}


