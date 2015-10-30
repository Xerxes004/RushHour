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
class VehicleConstructorError extends Exception
{
    public VehicleConstructorError(String string)
    {
        super ("Vehicle error: " + string);
    }
}
