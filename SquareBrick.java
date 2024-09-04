/*
 * Name: Adetola Aladekoba
 * Purpose: The source code for the square brick subclass
 * Date: 26/04/2024
 */


public class SquareBrick extends TetrisBrick
{
    public SquareBrick(int center_column)
    {
        super();
        colorNum=4;
        this.initPosition(center_column);
    }
    
    public void initPosition(int center_column)
    {
        position = new int[][] {
                                {center_column,0},
                                {center_column+1,0},
                                {center_column,1},
                                {center_column+1,1},
                                };
    }
    
    public void rotate()
    {
        
    }
    
    public void unrotate()
    {
       
    }
}