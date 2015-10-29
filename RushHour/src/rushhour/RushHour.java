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
import javax.swing.JLabel;
public class RushHour
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final GameBoard board = new GameBoard();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               board.setVisible(true);
            }
        });
        
        // TODO code application logic here
        
        JLabel grid = (JLabel)board.getComponentWithName("Grid00");
        grid.setBackground(Color.blue);
        
        grid.setText("Hello World!");
    }
    
    

}
