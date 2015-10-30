
/**
 * Title : Project 3, CS3410 Description: A breadth-first-search solution to the
 * Rush Hour game.
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
     * @throws rushhour.InvalidMovementException
     */
    public static void main(String[] args)
        throws InvalidMovementException
    {
        final GameBoard board = new GameBoard();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                board.setVisible(true);
            }
        });

        Vehicle red = new Vehicle(Vehicle.Type.car, Color.red, 1, 2, Vehicle.Orientation.horizontal);
        Vehicle lime = new Vehicle(Vehicle.Type.car, Color.green, 0, 0, Vehicle.Orientation.horizontal);
        Vehicle purple = new Vehicle(Vehicle.Type.truck, Color.magenta, 0, 1, Vehicle.Orientation.vertical);
        Vehicle orange = new Vehicle(Vehicle.Type.car, Color.orange, 0, 4, Vehicle.Orientation.vertical);
        Vehicle blue = new Vehicle(Vehicle.Type.truck, Color.blue, 3, 1, Vehicle.Orientation.vertical);
        Vehicle yellow = new Vehicle(Vehicle.Type.truck, Color.yellow, 5, 0, Vehicle.Orientation.vertical);
        Vehicle lightBlue = new Vehicle(Vehicle.Type.car, Color.cyan, 4, 4, Vehicle.Orientation.horizontal);
        Vehicle aqua = new Vehicle(Vehicle.Type.truck, Color.lightGray, 2, 5, Vehicle.Orientation.horizontal);

        board.addVehicle(red);
        board.addVehicle(lime);
        board.addVehicle(purple);
        board.addVehicle(orange);
        board.addVehicle(blue);
        board.addVehicle(yellow);
        board.addVehicle(lightBlue);
        board.addVehicle(aqua);
    }
}
