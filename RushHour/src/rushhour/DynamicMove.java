/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
