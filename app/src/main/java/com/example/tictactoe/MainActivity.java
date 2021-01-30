package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView tvPlayer1;
    private TextView tvPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPlayer1 = (TextView) findViewById(R.id.activity_main_tv_p1);
        tvPlayer2 = (TextView) findViewById(R.id.activity_main_tv_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "activity_main_btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button reset = (Button) findViewById(R.id.activity_main_btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }


    @Override
    public void onClick(View v) {
        //if button already clicked
        if (!(((Button) v).getText().toString().equals(""))) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] fields = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = buttons[i][j].getText().toString();
            }
        }
        //fields[rows][columns]
        //check for columns
        for (int i = 0; i < 3; i++) {
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && !fields[i][0].equals("")) {
                return true;
            }
        }
        //check for rows
        for (int i = 0; i < 3; i++) {
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && !fields[0][i].equals("")) {
                return true;
            }
        }
        //check for diagonals
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && !fields[0][0].equals("")) {
            return true;
        }
        if (fields[0][2].equals(fields[1][1]) && fields[0][2].equals(fields[2][0]) && !fields[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins !", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 Wins !", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        tvPlayer1.setText("Player 1: " + player1Points);
        tvPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points=0;
        player2Points=0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount=savedInstanceState.getInt("roundCount");
        player1Points=savedInstanceState.getInt("player1Points");
        player2Points=savedInstanceState.getInt("player2Points");
        player1Turn=savedInstanceState.getBoolean("player1Turn");
    }
}