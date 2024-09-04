/*
 * Name: Adetola Aladekoba
 * Purpose: The executable class that creates the window for the tetris game
 * Date: 26/04/2024
 */


import javax.swing.*;
import java.awt.event.*;

public class TetrisWindow extends JFrame 
{
    private TetrisGame game;
    private TetrisDisplay display;
    private int win_width = 500;
    private int win_height = 500;
    private int game_rows= 20;
    private int game_cols= 12;
    private TetrisLeaderBoard leaderBoard;
    
    public TetrisWindow()
    {
        game = new TetrisGame(game_rows,game_cols);
        leaderBoard= new TetrisLeaderBoard(game);
        display = new TetrisDisplay(game);
        
        this.setTitle("Tetris Game    Adetola Aladekoba");
        this.setSize(win_width,win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(display);
        initMenus();
        this.setVisible(true);  
    }

    public void initMenus()
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        
        JMenuItem saveGame = new JMenuItem("Save");
        gameMenu.add(saveGame); 
        
        JMenuItem retrieveGame = new JMenuItem("Retrieve");
        gameMenu.add(retrieveGame); 
        
        JMenuItem newGame = new JMenuItem("New");
        gameMenu.add(newGame); 
        
        JMenu score = new JMenu("Score");
        menuBar.add(score);
        
        JMenuItem highScore = new JMenuItem("High Score");
        score.add(highScore); 
        
        JMenuItem clearScore = new JMenuItem("Clear Score");
        score.add(clearScore); 
        
        saveGame.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                      game.saveToFile();
                       return;
                    }
                });
        
        
        retrieveGame.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                       game.retrieveFromFile();
                       return;
                    }
                });
        highScore.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                JOptionPane.showMessageDialog(null,
                        leaderBoard.scoreBoard(),
                        "High Scores", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        newGame.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                       game.newGame();
                    }
                });
        
        clearScore.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                leaderBoard.checkAndUpdateScores();
            }
        });
        this.setJMenuBar(menuBar);    
        
    }
    
    public static void main(String[] args)
    {
        new TetrisWindow();
    }
}