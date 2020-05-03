package brickbreaker;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

/**
 * Encapsulates the generation of images.
 */
public class Image {
    // attributes
    protected BufferedImage image;

    /**
     * Constructs an Image.
     * @param file image file
     */
    public Image(File file) {
        try {
            this.image = ImageIO.read(file);
        } catch (IOException ex) {          
            System.out.println(ex.getMessage() + " " + file.getName());
        }        
    }
}
