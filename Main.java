import brickbreaker.Gameplay;
import brickbreaker.MapGenerator;
import brickbreaker.Image;

import javax.swing.JFrame;

/** 
 * Provides a main client to start gameplay.
 */
public class Main {
    /**
     * Invokes the running of the Gameplay class to run the gameplay events.
     * Creates a JFrame for the Gameplay object to display the game graphics.  
     */    
    public static void main(String[] args) {
        /* JFrame window */
        JFrame frame = new JFrame(); 
        Gameplay gamePlay = new Gameplay();   
        
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Coronavirus Attack");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        frame.add(gamePlay);
    }
}
