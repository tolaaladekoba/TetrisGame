/*
 * Name: Adetola Aladekoba
 * Purpose: The super class for all tetris brick(An abstract class)
 * Date: 26/04/2024
 */


public abstract class TetrisBrick 
{
    protected int numSegments = 4;
    protected int[][] position= new int[numSegments][2];
    protected int colorNum;
    protected int orientation =0;
    
    public TetrisBrick()
    {
     
    }
    
    public void moveDown()
    {
        for(int dex=0;dex <numSegments; dex++)
            position[dex][1] +=1;
    }
    
    public void moveUp()
    {
        for(int dex=0;dex <numSegments; dex++)
            position[dex][1] -=1;
    }
    
    public void moveLeft()
    {
        for(int dex=0;dex <numSegments; dex++)
            position[dex][0] -=1;
    }
    
    public void moveRight()
    {
        for(int dex=0;dex <numSegments; dex++)
            position[dex][0] +=1;
    }
    
    public int getColorNumber()
    {
       return colorNum;
    }
    
    public String toString() {
        String brickString = "";
        for (int seg = 0; seg < numSegments; seg++) {
            brickString += "(" + position[seg][0] + ", " + position[seg][1] + ") ";
        }
        brickString += "Color: " + colorNum + ", Orientation: " + orientation;
        return brickString;
    }
    
    public abstract void rotate();
    
    public abstract void unrotate();
    
    public abstract void initPosition(int center_column);
}

