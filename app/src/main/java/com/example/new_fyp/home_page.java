package com.example.new_fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class home_page extends AppCompatActivity {

    private ImageView myButton_1, myButton_2, myButton_3, myButton_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        myButton_1 = findViewById(R.id.wordgame);
        myButton_2 = findViewById(R.id.secondgame);
        myButton_3 = findViewById(R.id.third_game);
        myButton_4 = findViewById(R.id.fourth_game);

        myButton_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, word_game_1.class);
                startActivity(intent);
            }
        });


        myButton_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, ball_game_2.class);
                startActivity(intent);
            }
        });

        myButton_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, catch_ball.class);
                startActivity(intent);
            }
        });

        myButton_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, match_image_1.class);
                startActivity(intent);
            }
        });

    }

}