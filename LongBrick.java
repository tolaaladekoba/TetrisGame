/*
 * Name: Adetola Aladekoba
 * Purpose: Basic game tutorial
 * Date: 26/04/2024
 */


public class LongBrick extends TetrisBrick
{
    public LongBrick(int center_column)
    {
        super();
        colorNum=3;
        this.initPosition(center_column);
    }
    
    public void initPosition(int center_column)
    {
        position = new int[][] {
                                {center_column-1,0},
                                {center_column,0},
                                {center_column+1,0},
                                {center_column+2,0},
                                };
    } 
    
    public void rotate()
    {
        int centCol = position[1][0];
        int centRow = position[1][1];
        
        if(orientation == 0){
            position = new int[][]{ { centCol, centRow-1},
                                    { centCol   , centRow},
                                    { centCol, centRow+1},
                                    { centCol, centRow+2},
                                    };
            orientation+=1;
         }
        else if(orientation ==1){
            position = new int[][]{ { centCol+1, centRow},
                                    { centCol   , centRow},
                                    { centCol-1, centRow},
                                    { centCol-2, centRow},
                                    };
            orientation+=1;
        }
        else if(orientation ==2){
            position = new int[][]{ { centCol, centRow+1},
                                    { centCol   , centRow},
                                    { centCol, centRow-1},
                                    { centCol, centRow-2},
                                    };
            orientation+=1;
        }
        else if(orientation == 3)
        {
            position = new int[][]{ { centCol-1, centRow},
                                    { centCol   , centRow},
                                    { centCol+1, centRow},
                                    { centCol+2, centRow},
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