/*
 * Name: Adetola Aladekoba
 * Purpose: The source code for the EL brick subclass
 * Date: 26/04/2024
 */


public class ElBrick extends TetrisBrick
{
    public ElBrick(int center_column)
    {
        super();
        this.initPosition(center_column);
        colorNum=0;
    }
    
    public void initPosition(int center_column)
    {
        position = new int[][] {
                                {center_column,0},
                                {center_column,1},
                                {center_column,2},
                                {center_column+1,2},
                                };
    }
    
    public void rotate()
    {
        int centCol = position[1][0];
        int centRow = position[1][1];
        
        if(orientation == 0){
            position = new int[][]{ {centCol+1,centRow},
                                    {centCol, centRow},
                                    {centCol-1,centRow},
                                    {centCol-1,centRow+1}
                                    };
            orientation+=1;
        }
        else if(orientation ==1){
            position = new int[][]{ {centCol, centRow+1},
                                    {centCol, centRow},
                                    {centCol, centRow-1},
                                    {centCol-1,centRow-1}
                                    };
            orientation+=1;
        }
        else if(orientation ==2){
            position = new int[][]{ {centCol-1,centRow,},
                                    {centCol, centRow},
                                    {centCol+1,centRow},
                                    {centCol+1, centRow-1}
                                    };
            orientation+=1;
        }
        else if(orientation == 3)
        {
            position = new int[][]{ {centCol,centRow-1},
                                    {centCol, centRow},
                                    {centCol,centRow+1},
                                    {centCol+1,centRow+1},
                                    };
            orientation=0;
        }
    }
    
    public void unrotate()
    {
        rotate();
        rotate();
        rotate();
    }
}