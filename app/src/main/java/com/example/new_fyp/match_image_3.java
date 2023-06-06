package com.example.new_fyp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class match_image_3 extends AppCompatActivity {

    private ImageView answer_1, answer_2, answer_3, question, settingbtn;
    private boolean correctAnswerSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_image3);

        answer_1 = findViewById(R.id.answer1);
        answer_2 = findViewById(R.id.answer2);
        answer_3 = findViewById(R.id.answer3);
        question = findViewById(R.id.question);
        settingbtn = findViewById(R.id.settingbtn);

        answer_1.setOnTouchListener(new MyTouchListener());
        answer_2.setOnTouchListener(new MyTouchListener());
        answer_3.setOnTouchListener(new MyTouchListener());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.message_box_reset_game, null);

        question.setOnDragListener(new match_image_3.MyDragListener());

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !correctAnswerSelected) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    ImageView droppedView = (ImageView) view;
                    ImageView targetView = (ImageView) v;
                    if (targetView.getId() == R.id.question && droppedView.getId() == R.id.answer1) {
                        // change the image of answer_1
                        answer_1.setImageResource(R.drawable.happy_mouth_muth);

                        // disable touch events for answer_2 and answer_3
                        answer_2.setOnTouchListener(null);
                        answer_3.setOnTouchListener(null);

                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
//                                endgame();
                                resetGame();
                            }
                        }, 2000); // 1 seconds delay
                    } else if (targetView.getId() == R.id.question && droppedView.getId() == R.id.answer2) {
                        answer_2.setImageResource(R.drawable.happy);
                        Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_SHORT).show();

                        // disable touch events for answer_1 and answer_3
                        answer_1.setOnTouchListener(null);
                        answer_3.setOnTouchListener(null);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
//                                resetGame();
                                endgame();
                            }
                        }, 2000); // 1 seconds delay
                    } else if (targetView.getId() == R.id.question && droppedView.getId() == R.id.answer3) {
                        answer_3.setImageResource(R.drawable.happy_sad_eye);
                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();

                        // disable touch events for answer_2 and answer_3
                        answer_2.setOnTouchListener(null);
                        answer_3.setOnTouchListener(null);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                resetGame();
//                                endgame();
                            }
                        }, 2000); // 1 seconds delay
                    }
                    targetView.setImageDrawable(droppedView.getDrawable());
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(getResources().getDrawable(R.drawable.sad_no_eye));
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private void resetGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.message_box_reset_game, (LinearLayout) findViewById(R.id.ball_game_2_1st));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.titleTextView)).setText("Oh No");
        ((TextView) view.findViewById(R.id.messageTextView)).setText("You are wrong");

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.playagin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(match_image_3.this, match_image_3.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.backtomanu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(match_image_3.this, home_page.class);
                startActivity(intent);
            }
        });
        alertDialog.show();
        alertDialog.setCancelable(false);
    }


    private void endgame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.message_box_next_game, (LinearLayout) findViewById(R.id.ball_game_2_1st));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.titleTextView)).setText("Congratulation");
        ((TextView) view.findViewById(R.id.messageTextView)).setText("Correct!!");

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.playagin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(match_image_3.this, home_page.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.backtomanu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(match_image_3.this, home_page.class);
                startActivity(intent);
            }
        });
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void setting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.message_box_pause_game, (LinearLayout) findViewById(R.id.ball_game_2_1st));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.messageTextView)).setText("");
        ((TextView) view.findViewById(R.id.titleTextView)).setText("Pause");


        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.playagin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.backtomanu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(match_image_3.this, home_page.class);
                startActivity(intent);
            }
        });
        alertDialog.show();
        alertDialog.setCancelable(false);
    }
}