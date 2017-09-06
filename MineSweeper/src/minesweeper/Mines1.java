package minesweeper;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import static minesweeper.MineSweeper.PopulateBombs;
import static minesweeper.MineSweeper.amountOfBombs;
import static minesweeper.MineSweeper.board;
import static minesweeper.MineSweeper.boardSize;
import static minesweeper.MineSweeper.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yisroel
 */
public class Mines1 extends JButton
{

    boolean isBomb = false;
    boolean isUncovered = false;
    boolean clickableLeftClick = true;
    boolean clickableRightClick = true;
    int bombnexttoit;
    int rightclickCounter;
    int minetoOpen = (boardSize * boardSize) - amountOfBombs;
    public static int clickCounter;
    public static int minesOpenedCounter = 0;
    public static Score score = new Score();
    public static HighScore hs = new HighScore();

    public Mines1(int x, int y)
    {

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setOpaque(true);
        this.setBackground(Color.yellow);
        this.setText("");
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent me)
            {
                //checking if able to click on button
                if (clickableLeftClick)
                {
                    //right click
                    if (me.getButton() == 1)
                    {
                        //if its a bomb
                        if (isBomb == true)
                        {
                            setBackground(Color.LIGHT_GRAY);
                            setText("X");
                            clickableRightClick = false;
                            steppedOnBomb();
                        } //if not a bomb uncover cells next to it
                        else
                        {
                            clickableRightClick = false;
                            clickCounter++;
                            unCoverMines(x, y);
                            isWinner();

                        }

                    }
                }

                //left click
                if (clickableRightClick)
                {
                    if (me.getButton() == 3)
                    {
                        rightclickCounter++;
                        switch (rightclickCounter)
                        {
                            //one click sets warning that a bomb
                            case 1:
                                setText("!");
                                clickableLeftClick = false;
                                break;
                            //second click your not sure
                            case 2:
                                setText("?");
                                clickableLeftClick = false;
                                break;
                            //third click resetes to nothing
                            case 3:
                                setText("");
                                clickableLeftClick = true;
                                rightclickCounter = 0;
                                break;

                        }

                    }
                }
            }
        }
        );
    }

    public static void readHighScore(File file)
    {
        try
        {
            DataInputStream dataIn = new DataInputStream(new FileInputStream(file));
            while (dataIn.available() > 0)
            {

                String name = dataIn.readUTF();
                int score = dataIn.readInt();

                System.out.print(name + ", " + score);
                //  hs.add(new HighScore(name, score));
            }
        } catch (IOException ex)
        {
            Logger.getLogger(Mines1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void writeHighScore(File file)
    {
        FileOutputStream fw = null;
        try
        {
            fw = new FileOutputStream(file, true);
            DataOutputStream dataOut = new DataOutputStream(fw);
            dataOut.writeUTF(score.getName());
            dataOut.writeInt(score.getScore());
            dataOut.flush();
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Mines1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Mines1.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                fw.close();
            } catch (IOException ex)
            {
                Logger.getLogger(Mines1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static File highscorefile()
    {
        File n = null;
        try
        {
            String property = "user.home";
            String userPath = System.getProperty(property);
            File file = new File(userPath + File.separatorChar + "Documents/iotester/newfolder/minesweeperscores");
            n = file;
            File folder = new File(userPath + File.separatorChar + "Documents/iotester/newfolder");
            boolean mkdir = folder.mkdir();
            boolean sucess = file.createNewFile();
            SimpleDateFormat sdf = new SimpleDateFormat();
            writeHighScore(file);

        } catch (IOException ex)
        {
            Logger.getLogger(Mines1.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    public void isWinner()
    {

        if (minesOpenedCounter >= minetoOpen)
        {
            gameIsPlaying = false;
            score.setScore(clickCounter);
            Object frame = null;
            String name = JOptionPane.showInputDialog(frame, "What's your name?");
            score.setName(name);
            File file = highscorefile();
            writeHighScore(file);
            System.out.println("winner");
            final int n = JOptionPane.showConfirmDialog(
                    null,
                    "You Win!!! \n your time is: " + gameCounter + "\n play again?",
                    "Winner",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION)
            {
                reset();
                //  new MineSweeper().setVisible(true);
            } else
            {
                System.exit(0);
            }

        }
    }

    public void steppedOnBomb()
    {
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                if (board[i][j].isIsBomb() == true)
                {
                    board[i][j].setText("x");

                }
            }
        }
        final int n = JOptionPane.showConfirmDialog(
                null,
                "draw \n play again?",
                "draw",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION)
        {
            reset();
            // new MineSweeper().setVisible(true);
        } else
        {
            System.exit(0);
        }

    }

    public static void reset()
    {
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {

                board[i][j].setIsBomb(false);
                board[i][j].isUncovered = false;
                board[i][j].clickableLeftClick = true;
                board[i][j].clickableRightClick = true;
                board[i][j].bombnexttoit = 0;
                board[i][j].rightclickCounter = 0;
                minesOpenedCounter = 0;
                board[i][j].setBackground(Color.yellow);
                board[i][j].setText("");

            }

        }
        gameCounter = 0;
        gameIsPlaying = false;
        gameTimer();
         gameIsPlaying = true;
        PopulateBombs();
    }

    public void unCoverMines(int x, int y)
    {
        int numberbombs = getBombsNearby(x, y);
        board[x][y].unCover(x, y);
        board[x][y].clickableLeftClick = false;
        board[x][y].clickableRightClick = false;
        //checking how many bomb next to it
        if (numberbombs != 0)
        {
            //if has bombs next to it tells you how many
            board[x][y].setText("" + numberbombs);
        } else
        {
            //if no bombs next to it uncover mines next to it
            for (int i = -1; i <= 1; i++)
            {
                for (int j = -1; j <= 1; j++)
                {
                    int row = (x + i);
                    int col = (y + j);
                    if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col].isUncovered == false)
                    {
                        unCoverMines(row, col);
                    }
                }

            }
        }

    }

    public void unCover(int x, int y)
    {
        isUncovered = true;
        minesOpenedCounter++;

        this.setMinesOpenedCounter(minesOpenedCounter);

        board[x][y].setBackground(Color.WHITE);

    }

    public int getBombsNearby(int x, int y)
    {
        //checking how mant bombs next to it
        int numberOfBombs = 0;
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                int row = (x + i);
                int col = (y + j);
                // checking that not arrayoutofbounds and not stackoverflow
                if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col].isUncovered == false)
                {
                    if (board[row][col].isIsBomb())
                    {
                        numberOfBombs++;

                    }
                }
            }

        }
        return numberOfBombs;
    }

    public int getMinesOpenedCounter()
    {
        return minesOpenedCounter;
    }

    public void setMinesOpenedCounter(int minesOpenedCounter)
    {
        this.minesOpenedCounter = minesOpenedCounter++;
    }

    public boolean isIsBomb()
    {
        return isBomb;
    }

    public void setIsBomb(boolean isBomb)
    {
        this.isBomb = isBomb;
    }

    public int getBombnexttoit()
    {
        return bombnexttoit;
    }

    public void setBombnexttoit(int bombnexttoit)
    {
        this.bombnexttoit = bombnexttoit;
    }

}
