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
