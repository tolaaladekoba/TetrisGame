/*
 * Name: Adetola Aladekoba
 * Purpose: This class creates an instance of the game
 * Date: 26/04/2024
 */


import java.util.*;
import java.io.*;
import javax.swing.*;

public class TetrisGame
{
    private TetrisBrick fallingBrick;
    private int rows;
    private int cols;
    private int numBrickTypes= 7;
    private Random randomGen;
    protected boolean gameOver = false;
    private int[][] background;
    protected int state;
    private int score;
    private TetrisLeaderBoard leaderBoard;
    private boolean nameCollected = false;
    
    public TetrisGame(int row,int col)
    {
        rows= row;
        cols= col;
        randomGen = new Random();
        background = new int[cols][rows];
        
        initBoard();
        spawnBrick();
    }
    
    public void initBoard()
    {
        
        for(int col=0; col<cols;col++){
            for(int row=0; row<rows;row++){
                background[col][row]=-1;
            }
        }
    }
    
    public void newGame()
    {
        score = 0;
        state = 1;
        nameCollected = false;
        gameOver = false;
        initBoard();
        spawnBrick();
        
        if (!validateMove()) {
            fallingBrick.moveUp(); 
            spawnBrick();
        }
        
        if (gameOver == true) {
            endGame(gameOver);
            nameCollected = true;                            
        }
    }
    
    public int fetchBoardPosition(int row, int col)
    {
        return background[col][row];
    }
    
    private void transferColor()
    {
        for(int dex =0;dex<getNumberSegs();dex++)
        {
            background[getSegCol(dex)][getSegRow(dex)]=
                                                        getFallingBrickColor();
        }
    }
    
    private void spawnBrick()
    {
        int center = (int)(cols/2);
        center = center-1;
        int typeBrick = randomGen.nextInt(numBrickTypes);
        
        int numSeg = 4;

        if (typeBrick == 0 || typeBrick == 1 || typeBrick == 2 || typeBrick==6){
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col< cols; col++) {
                    if (background[col][row] != -1) {
                        gameOver = true;
                        state = 0;
                        if (nameCollected == false) {
                            endGame(gameOver);
                            nameCollected = true;
                        }
                    return;
                    }
                }
            }
        } 
        
        else if (typeBrick == 3 || typeBrick == 4 || typeBrick == 5) {
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < cols; col++) {
                    if (background[col][row] != -1) {
                        gameOver = true;
                        state = 0;
                        if (nameCollected == false) {
                            endGame(gameOver);
                            nameCollected = true;                    
                        }
                        return;
                    }
                }
            }
        }

        int startingCol = cols / 2;
        
        if(typeBrick ==0)
        {
            fallingBrick = new ElBrick(center);
        }
        else if(typeBrick ==1)
        {
            fallingBrick = new JayBrick(center);
        }
        else if(typeBrick ==2)
        {
            fallingBrick = new LongBrick(center);
        }
        else if(typeBrick ==3)
        {
            fallingBrick = new SquareBrick(center);
        }
        else if(typeBrick ==4)
        {
            fallingBrick = new StackBrick(center);
        }
        else if(typeBrick ==5)
        {
            fallingBrick = new ZeeBrick(center);
        }
        else if(typeBrick ==6)
        {
            fallingBrick = new EssBrick(center);
        }
    }
    
    public void makeMove(char code)
    {  
        switch(code)
        {
            case 'd':
                fallingBrick.moveDown();
                if(validateMove()==false){
                    fallingBrick.moveUp();
                    transferColor();
                    clearRows();
                    spawnBrick();
                }
                break;
            case 'r':
                fallingBrick.moveRight();
                if(validateMove()==false)
                    fallingBrick.moveLeft();
                break;
            case 'l'  :
                fallingBrick.moveLeft();
                if(validateMove()==false)
                    fallingBrick.moveRight();
                break;
            case 'u':
                fallingBrick.rotate();
                if(validateMove()==false)
                    fallingBrick.unrotate();
                break;
            case 'n':
                newGame();
                
            case 'a':
                spawnBrick();
                break;
        }
        
        if(validateSpawn()){
            state = 2;
        }
        else{
            state =3;
            checkForEnd();    
        }
    } 
    
    private boolean validateMove()
    {
        for(int dex = 0; dex <getNumberSegs(); dex++)
        {
            if(getSegRow(dex)>=rows ||getSegRow(dex)<0||
                  getSegCol(dex)>=cols||  getSegCol(dex)<0 )
                return false;
            
            if(background[getSegCol(dex)][getSegRow(dex)]!=-1)
            {
                return false;
            }
        }
        return true;
    }
    public boolean validateSpawn(){
        
        for(int seg =0; seg< fallingBrick.numSegments; seg ++){
            if(getSegCol(seg)<=0){
                return false;
            }
        } 
        return true;
    }
    
     public boolean rowHasSpace(int row_num){
        
        boolean rowFull = true;
    
        for(int col = 0; col< cols; col++){
            if(background[col][row_num]== -1){
                rowFull = false;   
                return true;
            }
        }
        return false;
    }
    
    public void copyRow(int row_num){
        
        for(int col =0; col<getCols();col++){
            
            if(row_num ==getRows()-1){
                background[col][row_num]= 0;
            }
            else{
                background[col][row_num+1]= background[col][row_num];
                if (row_num<getRows()-1 &&rowHasColor(row_num,col) 
                                                                ==false){
                    background[col][row_num+1]= background[col][row_num];
                    row_num++;
                }
            }
        }
    }
    
    public boolean rowHasColor(int rowNum,int col){
        boolean rowColored = false;

            if(background[col][rowNum +1]!=0&& rowNum<rows){
                rowColored = true;
            }
        return rowColored;
    }
    
    public void copyAllRows(int rowNum)
    {
        for(int seg =rowNum-1; seg !=0;seg--)
        {
            copyRow(seg);
        }
    }
    
    public void clearRows(){    
       
        int clearedRows = 0;
       for(int row = getRows()-1;row>=0; row--)
       {
           if(rowHasSpace(row)==false)
           {
               copyAllRows(row);
               row++;
               clearedRows++;
           }
        }
        switch(clearedRows)
        {
           case 1: 
               score +=100;
               break;
           case 2:
               score += 300;
               break;
           case 3:
               score += 600;
               break;
           case 4:
               score+= 1200;
               break;
        }
    }
    
    public boolean checkForEnd(){
        boolean end = false;
        for(int seg=0; seg < getCols(); seg++){
            if(background[0][seg] !=-1)
            {
                end = true;
            }
        }
        return end;
    }
    
    public void endGame(boolean gameStatus) {
        
        if (gameStatus) {
            TetrisLeaderBoard leaderBoard = new TetrisLeaderBoard(this);
            gameOver = false;
        }
    }
    
     public String toString(){
        
        String gameString = ""+background.length+" "+background[0].length+" "+
                                                                score+"\n";
        for(int col = 0; col < background.length;col++)
        {
            for(int row = 0; row < background[0].length;row++)
            {
                gameString += background[col][row]+ " ";
            }
            gameString += "\n";
        }
        gameString = gameString.substring(0,gameString.length()-1 );
        return gameString;
    }
    

    public void saveToFile() {
    JFileChooser fileChooser = new JFileChooser();
    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File outFile = fileChooser.getSelectedFile();
        String fileName = outFile.getAbsolutePath();
        
        // Append .gam extension to the file name
        if (!fileName.toLowerCase().endsWith(".gam")) {
            fileName += ".gam";
            outFile = new File(fileName);
        }

        try {
            if (outFile.exists() && !outFile.canWrite()) {
                System.err.println("Error: Cannot write to the selected file.");
                return;
            }
            
            FileWriter outWriter = new FileWriter(outFile);
            outWriter.write(this.toString());
            outWriter.close();
        } catch (IOException ioe) {
            System.err.println("Error: Failed to save the file.");
        }
    }
}
    
    public void retrieveFromFile() {
        
        gameOver=false;
        state = 1;
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                Scanner inScan = new Scanner(selectedFile);
                cols = inScan.nextInt();
                rows = inScan.nextInt();
                score = inScan.nextInt();
                background = new int[cols][rows];

                for (int col = 0; col < cols; col++) {
                    for (int row = 0; row < rows; row++) {
                        background[col][row] = inScan.nextInt();
                    }
                }

                inScan.close();                
            } 
            else {
                System.out.println("No file selected.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error occurred trying to retrieve from file: " 
                    + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error occurred trying to retrieve from file: "
                    + e.getMessage());
        }
    }
    
    public int getState()
    {
        return state;
    }

    public int getScore()
    {
        return score;
    }
     
    public int getFallingBrickColor()
    {
        return fallingBrick.getColorNumber();
    }
    
    public int getNumberSegs()
    {
        return fallingBrick.numSegments;
    }
    
    public int getSegRow(int segNum) 
    {
        return fallingBrick.position[segNum][1];
    }

    public int getSegCol(int segNum)
    {
        return fallingBrick.position[segNum][0];
    }   
    
    public int getRows()
    {
        return rows;
    }
    
    public int getCols()
    {
        return cols;
    }
}

