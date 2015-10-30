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
public class InvalidMovementException extends Exception
{
    public InvalidMovementException(String message)
    {
        super("Invalid movement: " + message);
    }
}
