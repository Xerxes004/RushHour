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
public class DynamicMove
{
    public DynamicMove(String dynamicString, Move move)
    {
        this.dynamicString = dynamicString;
        this.move = move;
    }
    
    private String dynamicString;
    private Move move;
    
    public String dynamicString()
    {
        return this.dynamicString;
    }
    
    public Move move()
    {
        return this.move;
    }
}
