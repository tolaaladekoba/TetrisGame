/*
 * Name: Adetola Aladekoba
 * Purpose: This is the TetrisDisplay class that creates the display for the
            the tetris board.
 * Date: 26/04/2024
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisDisplay extends JPanel 
{
    private TetrisGame game;
    private int start_x= 150;
    private int start_y= 100;
    private int cell_size= 15;
    private int delay = 200;
    private Timer timer;
    private Color[] colors= {Color.orange, Color.yellow, Color.green
            ,Color.red, Color.blue, Color.MAGENTA, Color.cyan};
    private boolean pause=false;
    
    public TetrisDisplay(TetrisGame ga)
    {
        game = ga;
        
        timer = new Timer(delay, new ActionListener(){ 
            public void actionPerformed(ActionEvent ae)
            {
                cycleMove();
            } 
        });
        timer.start();
        
        this.addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent ke) 
            {
                translateKey(ke);
            }
        });
        
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }
      
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        drawBackground(g);
        drawWell(g);
        drawScore(g);
        drawFallingBrick(g);
        drawScore(g);
        drawGameOver(g);
    }
   
    private void drawWell(Graphics g)
    {
        int rows = game.getRows();
        int cols = game.getCols();
        
        int wallHeight = rows*cell_size;
        int wallWidth = cols*cell_size;
      
        g.setColor(Color.BLACK);
        g.fillRect(start_x-cell_size,start_y,cell_size,wallHeight);
        
        g.setColor(Color.BLACK);
        g.fillRect(start_x+wallWidth,start_y,cell_size,wallHeight);
        
        g.setColor(Color.BLACK);
        g.fillRect(start_x-cell_size,start_y+wallHeight,wallWidth+2*cell_size,
                cell_size);
    }
    
    private void drawFallingBrick(Graphics g)
    {
        for(int dex = 0; dex < game.getNumberSegs(); dex++)
        {
            int col = game.getSegCol(dex);
            int row = game.getSegRow(dex);
           
            g.setColor(colors[game.getFallingBrickColor()]);
            g.fillRect(start_x+(cell_size*col),start_y+(row*cell_size), 
                    cell_size, cell_size);
            g.setColor(Color.BLACK);
                    g.drawRect(start_x+col*cell_size,
                    start_y+row*cell_size, cell_size, cell_size);
        }    
    }
    
    private void drawBackground(Graphics g)
    {
        for(int row=0; row< game.getRows(); row++){
            for(int col =0; col<game.getCols();col++){
                if(game.fetchBoardPosition(row, col)!=-1){
                    g.setColor(colors[game.fetchBoardPosition(row, col)]);
                }
                else
                {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(start_x+col*cell_size,start_y+row*cell_size,
                        cell_size,cell_size);
                
                if(game.fetchBoardPosition(row, col)!=-1){
                    g.setColor(Color.BLACK);
                    g.drawRect(start_x+col*cell_size,start_y+row*cell_size,
                        cell_size,cell_size);
                }
            }
        }
    }
    private void drawGameOver(Graphics g) {
        if (game.gameOver) {
            g.setColor(Color.RED);
            String gameOverMess = "Game Over!";

            int messageX = start_x + (cell_size * game.getCols() -
                                    gameOverMess.length() * 10) / 2;
            int messageY = start_y + (cell_size * game.getRows() / 2);

            int boxWidth = gameOverMess.length() * 15;
            int boxHeight = 40;

            g.fillRect(messageX -10, messageY - 30, boxWidth, boxHeight);
            g.setColor(Color.BLACK);
            g.drawRect(messageX - 10, messageY - 30, boxWidth, boxHeight);

            g.setColor(Color.WHITE);
            g.drawString(gameOverMess, messageX, messageY);
        }
        
    }
    
    private void translateKey(KeyEvent ke)
    {
        int code = ke.getKeyCode();
        switch(code) {
            case KeyEvent.VK_RIGHT: 
                game.makeMove('r'); 
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove('l');
                break;
            case KeyEvent.VK_UP:
                game.makeMove('u');
                break;
            case KeyEvent.VK_SPACE:
                if(pause){
                    timer.start();
                }
                else{
                    timer.stop();
                }
                pause=!pause;
                break; 
            case KeyEvent.VK_N:
                game.makeMove('n');
                break;
        }           
        repaint();
    }
         
    private void cycleMove()
    { 
        setFocusable(true);
        if (game.getState() == 1)
        {
            game.makeMove('a');
        } 
        else
        {
            game.makeMove('d');
        }
        repaint();   
    }  
    
     public void drawScore(Graphics g) {
        
        int boxX = 15;
        int boxY = 15;
        int boxOffset = 5;
        int fontSize = 20;
        String scoreString = "Score: " + game.getScore();
        
        int boxWidth = scoreString.length() * 12;
        int boxHeight = 30;

        g.setColor(Color.WHITE);
        g.fillRect(boxX - 5, boxY - 20, boxWidth, boxHeight);
        g.setColor(Color.BLACK);
        g.drawRect(boxX - 5, boxY - 20, boxWidth, boxHeight);

        
        g.setColor(Color.BLACK);
        g.drawString(scoreString, boxX + boxOffset, boxY + boxOffset);
    }
}
