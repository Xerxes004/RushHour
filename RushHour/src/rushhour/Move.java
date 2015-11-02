/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rushhour;

/**
 *
 * @author wes
 */
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
