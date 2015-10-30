
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
import java.util.ArrayList;
import java.util.Scanner;

public class RushHour
{
    private static int numCars;
    private static ArrayList<Vehicle> vehicles;
    
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

        Vehicle red = new Vehicle(Vehicle.Type.car, "red", 1, 2, Vehicle.Orientation.horizontal);
        Vehicle lime = new Vehicle(Vehicle.Type.car, "lime", 0, 0, Vehicle.Orientation.horizontal);
        Vehicle purple = new Vehicle(Vehicle.Type.truck, "purple", 0, 1, Vehicle.Orientation.vertical);
        Vehicle orange = new Vehicle(Vehicle.Type.car, "orange", 0, 4, Vehicle.Orientation.vertical);
        Vehicle blue = new Vehicle(Vehicle.Type.truck, "blue", 3, 1, Vehicle.Orientation.vertical);
        Vehicle yellow = new Vehicle(Vehicle.Type.truck, "yellow", 5, 0, Vehicle.Orientation.vertical);
        Vehicle lightBlue = new Vehicle(Vehicle.Type.car, "light blue", 4, 4, Vehicle.Orientation.horizontal);
        Vehicle aqua = new Vehicle(Vehicle.Type.truck, "aqua", 2, 5, Vehicle.Orientation.horizontal);

        board.addVehicle(red);
        board.addVehicle(lime);
        board.addVehicle(purple);
        board.addVehicle(orange);
        board.addVehicle(blue);
        board.addVehicle(yellow);
        board.addVehicle(lightBlue);
        board.addVehicle(aqua);
        
    }
    
    public void parseInput(String fileName)
    {
        Scanner scan = new Scanner(fileName);
        if (scan.hasNextInt())
        {
            this.numCars = scan.nextInt();
        }
        for (int i = 0; i < numCars; i++)
        {
            String type = "";
            
            if (scan.hasNextLine())
            {
                type = scan.nextLine();
            }
            
            String color = "";
            
            if (scan.hasNextLine())
            {
                color = scan.nextLine();
            }
            
            String orientation = "";
            
            if (scan.hasNextLine())
            {
                orientation = scan.nextLine();
            }
            
            int y = 0;
            
            if (scan.hasNextInt())
            {
                y = scan.nextInt() - 1;
            }
            
            int x = 0;
            
            if (scan.hasNextInt())
            {
                x = scan.nextInt() - 1;
            }
            
            // TODO add vehicles to internal variable
        }
    }
}
