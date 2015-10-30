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
public class Position
{
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    private int x;
    private int y;
    
    public int x()
    {
        return this.x;
    }
    public void x(int x)
    {
        this.x = x;
    }
    public int y()
    {
        return y;
    }
    public void y(int y)
    {
        this.y = y;
    }
    
}
