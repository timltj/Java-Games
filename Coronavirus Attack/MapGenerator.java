package brickbreaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import java.io.File;

/**
 * Encapsulates the generation of the brick map.
 */
public class MapGenerator {
    // static final fields
    private static final Image flag = new Image(new File("redcross.png"));  

    // attributes
    protected boolean[][] map;
    protected final int brickWidth;
    protected final int brickHeight;

    /**
     * Constructs a MapGenerator.
     * @param row number of rows in brick map
     * @param col number of columns in brick map
     */
    public MapGenerator(int row, int col) {        
        this.map = new boolean[row][col];     
        this.brickWidth = 540 / col;
        this.brickHeight = 150 / row;
    }
    
    /**
     * Draws the brick map.
     * @param g java Graphics2D object
     */
    public void draw(Graphics2D g) {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {
                if (!map[i][j]) {
                    // draw bricks
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.drawImage(flag.image, j * brickWidth + 80, i * brickHeight + 50, null);
                }
            }
        }
    }

    // method to set brick value
    public void setBrickValue(int row, int col) {
        map[row][col] = !map[row][col];
    }
}
