package com.example.new_fyp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Handler;


public class ball_game extends AppCompatActivity {

    private Button leftButton, rightButton, topLeftButton, topRightButton;
    private ImageView controllableObject, topControllableObject;

    private final int MOVE_AMOUNT = 10; // The amount to move the controllable object
    private int moveDirection = 0; // -1 for left, 0 for stationary, 1 for right
    private int moveDirectionY = 0; // -1 for left, 0 for stationary, 1 for right
    private final int UPDATE_DELAY = 0; // Update the position every 50 milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_game);

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        controllableObject = findViewById(R.id.controllable_object);

        topLeftButton = findViewById(R.id.top_left_button);
        topRightButton = findViewById(R.id.top_right_button);
        topControllableObject = findViewById(R.id.top_controllable_object);


        // Set an OnClickListener for the left button
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Move the controllable object to the left
                        moveDirection = -1;
                        break;
                    case MotionEvent.ACTION_UP:
                        // Stop moving the controllable object
                        moveDirection = 0;
                        break;
                }
                return true;
            }
        });

        // Set an OnClickListener for the right button
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Move the controllable object to the right
                        moveDirection = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        // Stop moving the controllable object
                        moveDirection = 0;
                        break;
                }
                return true;
            }
        });

        topLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Move the controllable object upwards
                        moveDirectionY = -1;
                        break;
                    case MotionEvent.ACTION_UP:
                        // Keep moving the controllable object upwards
                        moveDirectionY = 1;
                        break;
                }
                return true;
            }
        });



/// Set an OnClickListener for the top right button
        topRightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Move the controllable object to the right
                        moveDirectionY = -1; // Updated to move up instead of down
                        break;
                    case MotionEvent.ACTION_UP:
                        // Stop moving the controllable object
                        moveDirectionY = 0;
                        break;
                }
                return true;
            }
        });



        // Set a Handler to update the position of the controllable object
        final Handler handler = new Handler();
        final Runnable updatePositionRunnable = new Runnable() {
            @Override
            public void run() {
                // Get the current X position of the controllable object
                float currentX = controllableObject.getX();

                // Calculate the new X position after moving left or right
                float newX = currentX + moveDirection * MOVE_AMOUNT;

                // Check if the new X position is within the bounds of the screen
                if (newX < 0) {
                    // If not, set the X position to 0 (left edge of the screen)
                    newX = 0;
                } else if (newX > (findViewById(android.R.id.content)).getWidth() - controllableObject.getWidth()) {
                    // If not, set the X position to the right edge of the screen
                    newX = (findViewById(android.R.id.content)).getWidth() - controllableObject.getWidth();
                }

                // Set the new X position of the controllable object
                controllableObject.setX(newX);


                // Get the current Y position of the top controllable object
                float currentY = topControllableObject.getY();

                // Calculate the new Y position after moving up or down
                float newY = currentY + moveDirectionY * MOVE_AMOUNT;

                // Check if the new Y position is within the bounds of the screen
                if (newY < 0) {
                    // If not, set the Y position to 0 (top edge of the screen)
                    newY = 0;
                } else if (newY > (findViewById(android.R.id.content)).getHeight() - topControllableObject.getHeight()) {
                    // If not, set the Y position to the bottom edge of the screen
                    newY = (findViewById(android.R.id.content)).getHeight() - topControllableObject.getHeight();
                }

                // Set the new Y position of the top controllable object
                topControllableObject.setY(newY);

                // Schedule the next update of the objects' positions
                handler.postDelayed(this, UPDATE_DELAY);
            }
        };

// Start updating the positions of the objects
        handler.postDelayed(updatePositionRunnable, UPDATE_DELAY);

    }
}
