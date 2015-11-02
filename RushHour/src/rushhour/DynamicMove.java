/*
 * This class defines a Dynamic Move for the Rush Hour game.
 *
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class stores the previous dynamic value that resulted in
 * the move stored within. Using this class and a HashMap, a user can step back
 * through time and recreate the optimal solution to the RushHour game. This
 * class is a simple container and contains no error checking.
 */

package rushhour;

public class DynamicMove
{
    public DynamicMove(String dynamicString, Move move)
    {
        this.dynamicString = dynamicString;
        this.move = move;
    }
    
    final private String dynamicString;
    final private Move move;
    
    public String parent()
    {
        return this.dynamicString;
    }
    
    public Move move()
    {
        return this.move;
    }
}
