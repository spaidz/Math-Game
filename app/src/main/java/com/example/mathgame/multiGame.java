package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Locale;
import java.util.Random;

public class multiGame extends AppCompatActivity {

    TextView scoreText;
    TextView lifeText;
    TextView timeText;
    TextView questionText;
    EditText answer;
    Button confirm;
    Button next;

    Random random = new Random();
    int num1;
    int num2;
    int userAnswer;
    int realAnswer;
    int userScore = 0;
    int userLife = 3;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 60000;
    Boolean timer_running;
    long time_left_in_milis = START_TIMER_IN_MILIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game);

        scoreText = findViewById(R.id.score);
        lifeText = findViewById(R.id.life);
        timeText = findViewById(R.id.time);
        questionText = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        confirm = findViewById(R.id.buttonConfirm);
        next = findViewById(R.id.buttonNext);

        gameContinue();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.getText().toString().isEmpty())
                {

                    Toast.makeText(getApplicationContext(), "Type in a value.", Toast.LENGTH_LONG).show();

                }
                else
                {

                    userAnswer = Integer.valueOf(answer.getText().toString());
                    pauseTimer();

                    if (userAnswer == realAnswer) {
                        userScore = userScore + 10;
                        scoreText.setText("" + userScore);
                        questionText.setText("Congratulations.. Your Answer is Correct!!");
                    } else {
                        userLife = userLife - 1;
                        lifeText.setText("" + userLife);
                        questionText.setText("Sorry Your Answer is Wrong");

                    }
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answer.setText("");
                gameContinue();
                resetTimer();

                if (userLife == 0)
                {
                    Toast.makeText(getApplicationContext(), "GAME OVER!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(multiGame.this, resultPage.class);
                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    gameContinue();
                }

            }
        });

    }

    public void gameContinue()
    {

        num1 = random.nextInt(12);
        num2 = random.nextInt(12);

        realAnswer = num1 * num2;

        questionText.setText(num1 + "*" + num2);
        startTimer();

    }

    public void startTimer()
    {
        timer = new CountDownTimer(time_left_in_milis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time_left_in_milis = millisUntilFinished;
                updateText();

            }

            @Override
            public void onFinish() {

                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                userLife = userLife - 1;
                lifeText.setText(""+ userLife);
                questionText.setText("Sorry, Time is up!");

            }
        }.start();

        timer_running = true;

    }

    public void pauseTimer()
    {
        timer.cancel();
        timer_running = false;
    }
    public void resetTimer()
    {
        time_left_in_milis = START_TIMER_IN_MILIS;
        updateText();
    }
    public void updateText()
    {
        int second = (int) (time_left_in_milis/1000) % 60;
        String time_left = String.format(Locale.getDefault(), "%02d", second);
        timeText.setText(time_left);
    }


}