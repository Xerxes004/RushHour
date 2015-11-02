/**
 * This class defines the Moves for the Rush Hour game.
 *
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: this class is used to store information about a Move taken by
 * a car. This class is extremely useful for moving vehicles in the GUI.
 */

package rushhour;

public class Move
{
    public Move(String color, int spaces, String direction)
    {
        this.color = color;
        this.spaces = spaces;
        this.direction = direction;
    }
    
    private final String color;
    private final int spaces;
    private final String direction;
    
    /**
     * Returns the inverse of this move so that the GUI can step backwards.
     * @return the opposite move
     */
    public Move invert()
    {
        String oppositeDirection = "invalid";
        
        switch(this.direction)
        {
            case "u":
            case "U":
                oppositeDirection = "D";
                break;
            case "d":
            case "D":
                oppositeDirection = "U";
                break;
            case "l":
            case "L":
                oppositeDirection = "R";
                break;
            case "r":
            case "R":
                oppositeDirection = "L";
                break;
            default:
                return null;
        }
        
        return new Move (this.color, this.spaces, oppositeDirection);
    }
    
    public String color()
    {
        return this.color;
    }
    
    public int spaces()
    {
        return this.spaces;
    }
    
    public String direction()
    {
        return this.direction;
    }
}
