package com.example.new_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class word_game_1 extends AppCompatActivity {

    private int presCounter = 0;
    private int maxPresCounter = 3;
    private String[] keys = {"S", "A", "B", "D"};
    private String textAnswer = "SAD";
    private boolean isGameEnabled = true;
    TextView textScreen, textQuestion, textTitle;
    Animation smallbigforth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game1);

        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        keys = shuffleArray(keys);

        for (String key : keys) {
            addView(((LinearLayout) findViewById(R.id.layoutParent)), key, ((EditText) findViewById(R.id.editText)));
        }

        maxPresCounter = 3;
    }

    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutParams.rightMargin = 30;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOne-Regular.ttf");

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textScreen = (TextView) findViewById(R.id.textScreen);
        textTitle = (TextView) findViewById(R.id.textTitle);

        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);

        // Check if the first row is full (has 6 keys)
        if (viewParent.getChildCount() >= 4) {
            // If the first row is full, add the key to the second row
            viewParent = findViewById(R.id.layoutParent2);
        }

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isGameEnabled && presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        editText.setText("");

                    editText.setText(editText.getText().toString() + text);
                    textView.startAnimation(smallbigforth);
                    textView.animate().alpha(0).setDuration(300);
                    textView.setClickable(false);
                    presCounter++;

                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });
        viewParent.addView(textView);
    }

    private void doValidate() {
        presCounter = 0;

        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);

        if (editText.getText().toString().equals(textAnswer)) {
            Toast.makeText(word_game_1.this, "Correct!", Toast.LENGTH_SHORT).show();
            isGameEnabled = false; // Disable game keys

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Correct answer logic
                    Intent a = new Intent(word_game_1.this, word_game_2.class);
                    startActivity(a);
                    editText.setText("");
                    isGameEnabled = true; // Enable game keys after delay
                }
            }, 1000);
        } else {
            // Wrong answer logic
            Toast.makeText(word_game_1.this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");

            isGameEnabled = false; // Disable game keys

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recreate();
                    isGameEnabled = true; // Enable game keys after delay
                }
            }, 1000);
        }
    }

}