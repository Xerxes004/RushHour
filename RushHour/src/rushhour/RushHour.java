/**
 * Title : Project 3, CS3410 
 * Description: A breadth-first-search solution to the Rush Hour game.
 *
 * Copyright : Copyright (c) 2015 Wesley Kelly, James Von Eiff, Cedarville
 * University
 *
 * @author : Wesley Kelly, James Von Eiff
 * @version 1.0
 */

package rushhour;

import java.awt.Color;
public class RushHour
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InvalidMovementException
    {
        final GameBoard board = new GameBoard();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               board.setVisible(true);
            }
        });
        
        Vehicle vehicle = new Vehicle(Vehicle.Type.car, Color.red, 1, 2, Vehicle.Orientation.horizontal);
        
        board.drawOneVehicle(vehicle);
        vehicle.right();
        board.drawOneVehicle(vehicle);
        
    }
}
