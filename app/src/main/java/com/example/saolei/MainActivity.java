package com.example.saolei;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int MINES = 15;

    private Button[][] buttons = new Button[ROWS][COLS];
    private boolean[][] mines = new boolean[ROWS][COLS];
    private boolean[][] revealed = new boolean[ROWS][COLS];
    private boolean[][] flagged = new boolean[ROWS][COLS];
    private int minesFound = 0;
    private int cellsRevealed = 0;
    private TextView minesCountTextView;
    private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        minesCountTextView = findViewById(R.id.minesCount);
        Button resetButton = findViewById(R.id.resetButton);

        gridLayout.setRowCount(ROWS);
        gridLayout.setColumnCount(COLS);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setLayoutParams(new GridLayout.LayoutParams());
                buttons[i][j].setTextSize(16);
                buttons[i][j].setPadding(10, 10, 10, 10);
                final int row = i;
                final int col = j;

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!gameOver && !revealed[row][col] && !flagged[row][col]) {
                            revealCell(row, col);
                        }
                    }
                });

                buttons[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!gameOver && !revealed[row][col]) {
                            toggleFlag(row, col);
                            return true;
                        }
                        return false;
                    }
                });

                gridLayout.addView(buttons[i][j]);
            }
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        resetGame();
    }

    private void resetGame() {
        minesFound = 0;
        cellsRevealed = 0;
        gameOver = false;
        
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                mines[i][j] = false;
                revealed[i][j] = false;
                flagged[i][j] = false;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackgroundColor(getResources().getColor(R.color.buttonColor));
            }
        }

        placeMines();
        updateMinesCount();
    }

    private void placeMines() {
        int placedMines = 0;
        while (placedMines < MINES) {
            int row = (int) (Math.random() * ROWS);
            int col = (int) (Math.random() * COLS);
            if (!mines[row][col]) {
                mines[row][col] = true;
                placedMines++;
            }
        }
    }

    private void revealCell(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS || revealed[row][col] || flagged[row][col]) {
            return;
        }

        revealed[row][col] = true;
        cellsRevealed++;

        if (mines[row][col]) {
            buttons[row][col].setText("💣");
            buttons[row][col].setBackgroundColor(getResources().getColor(R.color.mineColor));
            gameOver();
            return;
        }

        int adjacentMines = countAdjacentMines(row, col);
        if (adjacentMines > 0) {
            buttons[row][col].setText(String.valueOf(adjacentMines));
            buttons[row][col].setBackgroundColor(getResources().getColor(R.color.revealedColor));
        } else {
            buttons[row][col].setText("");
            buttons[row][col].setBackgroundColor(getResources().getColor(R.color.revealedColor));
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revealCell(row + i, col + j);
                }
            }
        }

        checkWin();
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS && mines[newRow][newCol]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void toggleFlag(int row, int col) {
        flagged[row][col] = !flagged[row][col];
        if (flagged[row][col]) {
            buttons[row][col].setText("🚩");
            if (mines[row][col]) {
                minesFound++;
            }
        } else {
            buttons[row][col].setText("");
            if (mines[row][col]) {
                minesFound--;
            }
        }
        updateMinesCount();
        checkWin();
    }

    private void updateMinesCount() {
        minesCountTextView.setText("Mines: " + (MINES - minesFound));
    }

    private void gameOver() {
        gameOver = true;
        Toast.makeText(this, "Game Over! You hit a mine!", Toast.LENGTH_LONG).show();
        revealAllMines();
    }

    private void revealAllMines() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (mines[i][j]) {
                    buttons[i][j].setText("💣");
                    buttons[i][j].setBackgroundColor(getResources().getColor(R.color.mineColor));
                }
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void checkWin() {
        if (minesFound == MINES || cellsRevealed == ROWS * COLS - MINES) {
            gameOver = true;
            Toast.makeText(this, "Congratulations! You won!", Toast.LENGTH_LONG).show();
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }
}