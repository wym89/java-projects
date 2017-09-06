/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static minesweeper.Mines1.*;

/**
 *
 * @author yisroel
 */
public class MineSweeper extends JFrame
{

    public static int boardSize = 10;
    public static int amountOfBombs = 3;
    public static int gameCounter = 0;

    /**
     *
     */
    public static boolean gameIsPlaying = false;

    final static public int mineSize = 30;
    final static int easyboardSize = 10;
    final static int easybombamount = 10;
    int medboardSize = 20;
    int medbombamount = 20;
    int hardboardSize = 30;
    int hardbombamount = 30;
    //  ArrayList<ArrayList<Mines1>> board = new ArrayList<ArrayList<Mines1>>();
    public static Mines1[][] board = new Mines1[boardSize][boardSize];
    //  public static Mines1[][] board;
    public static JPanel panel = new JPanel();
    public static JLabel timer = new JLabel();

    public MineSweeper()
    {
//        this.setSize(boardSize* mineSize, boardSize* mineSize);
        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        JButton reset = new JButton();
        reset.setText("Restart");
        reset.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent me)
            {
                reset();
            }
        });
        buttonPanel.add(reset);
        JButton highscores = new JButton();
        highscores.setText("highscores");
        highscores.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent me)
            {

                File file = highscorefile();
                readHighScore(file);
            }

        });
        buttonPanel.add(highscores);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(buttonPanel, BorderLayout.PAGE_START);
        this.add(buttonPanel2, BorderLayout.PAGE_END);
        //  JPanel panel = new JPanel(); 

//        panel.setLayout(new GridLayout(boardSize, boardSize));
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        for (int i = 0; i < boardSize; i++)
//        { 
//            for (int j = 0; j < boardSize; j++)
//            {
//                Mines1 mine = new Mines1(i, j);
//                board[i][j] = mine;              
//                panel.add(board[i][j]);
//               
//             //   mine.addActionListener(al);
//                
//            }
//            
//          
//        }
        set();
        String[] levels =
        {
            "Easy", "Medium", "Hard"
        };

        JComboBox difficulty = new JComboBox(levels);
        difficulty.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                switch (difficulty.getSelectedIndex())
                {
                    case 0:
                        boardSize = easyboardSize;
                        amountOfBombs = easybombamount;
                        set();
                        reset();
                        break;
                    case 1:
                        boardSize = medboardSize;
                        amountOfBombs = medbombamount;
                        set();
                        reset();
                        break;
                    case 2:
                        boardSize = hardboardSize;
                        amountOfBombs = hardbombamount;
                        set();
                        reset();
                        break;
                }
            }

        });
        buttonPanel.add(difficulty);
        // JLabel timer = new JLabel();
        //  timer.setText("time: " + gameCounter);
        // timer.setSize(10, 10);
        buttonPanel2.setSize(10, 10);
        timer.setOpaque(true);
        buttonPanel2.add(timer);
//         this.setSize(boardSize* mineSize, boardSize* mineSize);
        PopulateBombs();
        add(panel);
    }

    public void set()
    {
        this.setSize(boardSize * mineSize + 10, boardSize * mineSize + 10);
        //  board = new Mines1[boardSize][boardSize];
        panel.setLayout(new GridLayout(boardSize, boardSize));

        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                Mines1 mine = new Mines1(i, j);
                board[i][j] = mine;
                panel.add(board[i][j]);

                //   mine.addActionListener(al);
            }
        }
        System.out.println("before gametimer" + gameCounter);
        gameIsPlaying = true;
        gameTimer();
        System.out.println("after gametimer" + gameCounter);
    }

    public static void PopulateBombs()
    {

        for (int i = 0; i < amountOfBombs; i++)
        {
            int x = (int) (Math.random() * boardSize);
            int y = (int) (Math.random() * boardSize);
            if (board[x][y].isIsBomb() == true)
            {
                i--;
            } else
            {
                board[x][y].setIsBomb(true);
            }
        }
    }

    public static void gameTimer()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (gameIsPlaying == true)
                {
                    try
                    {
                        Thread.sleep(1000);
                        gameCounter++;
                        String time = String.format("%02d:%02d", gameCounter/60 ,gameCounter);

                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run()
                            {
                                timer.setText("time: " + time);
                            }
                            
                        });
                        System.out.println("timer: " + time);
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(MineSweeper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();

      
    }

    public static void main(String[] args)
    {
        new MineSweeper().setVisible(true);

    }

}
