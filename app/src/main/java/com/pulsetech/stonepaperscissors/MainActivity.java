package com.pulsetech.stonepaperscissors;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView playerScore, resultText, AIScore;
    ImageView robot;
    Dialog dialog;
    Button Return, PlayAgain;

    int EnemyScore = 0;
    int PlayerScore = 0;
    int playerChoice;
    CardView stone, paper, scissor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        stone = findViewById(R.id.stone);
        paper = findViewById(R.id.paper);
        robot = findViewById(R.id.robot);
        scissor = findViewById(R.id.scissor);
        playerScore = findViewById(R.id.playerScore);
        AIScore = findViewById(R.id.AIScore);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Return = dialog.findViewById(R.id.btnReturn);
        PlayAgain = dialog.findViewById(R.id.btnPlayAgain);
        resultText = dialog.findViewById(R.id.result);

        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(MainActivity.this, MainMenu.class);
                startActivity(back);
            }
        });

        PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        stone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = 0;
                AI();
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = 1;
                AI();
            }
        });

        scissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = 2;
                AI();
            }
        });
    }

    private void AI() {
        Random rnd = new Random();
        int AIChoice = rnd.nextInt(3);

        switch (AIChoice) {
            case 0:
                robot.setImageResource(R.drawable.stone);
                break;
            case 1:
                robot.setImageResource(R.drawable.paper);
                break;
            case 2:
                robot.setImageResource(R.drawable.scissor);
                break;
        }

        Results(playerChoice, AIChoice);
    }

    private void Results(int playerChoice, int AIChoice) {
        String result;
        if (playerChoice == AIChoice) {
            result = "Berabere!";
        } else if ((playerChoice == 0 && AIChoice == 2) || (playerChoice == 1 && AIChoice == 0) || (playerChoice == 2 && AIChoice == 1)) {
            result = "Siz Kazandınız!";
            PlayerScore++;
            playerScore.setText(String.valueOf(PlayerScore));
        } else {
            result = "Kaybettiniz!";
            EnemyScore++;
            AIScore.setText(String.valueOf(EnemyScore));
        }
        resultText.setText(result);
        dialog.show();
    }
}
