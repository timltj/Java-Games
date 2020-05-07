package brickbreaker;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;

import java.io.File;

/**
 * Encapsulates the gameplay events.
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {
    // static final fields
    private static final int delay = 8;
    
    private static final Font scoreFont = new Font("serif", Font.BOLD, 25);
    private static final Font endFont = new Font("serif", Font.BOLD, 30);
    private static final Font restartFont = new Font("serif", Font.BOLD, 20);

    private static final Image background = new Image(new File("darkcity.jpg"));
    private static final Image virus = new Image(new File("coronavirus.png"));
    private static final Image paddle = new Image(new File("petridish.png"));

    // attributes
    private final Timer timer;    
    
    private boolean play = false;
    private int totalBricks = 21;    
    private int score = 0;    

    private int playerX = 310;
    private int ballposX = 345;
    private int ballposY = 510;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private MapGenerator map;

    /**
     * Constructs a Gameplay.
     */
    public Gameplay() {
        this.map = new MapGenerator(3, 7);
        this.timer = new Timer(delay, this);
        this.timer.start();        
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);        
    }
    
    /**
     * Paints the graphics canvas.
     */
    public void paint(Graphics g) {
        // background
        g.fillRect(1, 1, 692, 592);
        g.drawImage(background.image, 0, 0, null);      

        // borders 
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // draw brick map
        map.draw((Graphics2D) g);

        // draw paddle
        g.drawImage(paddle.image, playerX, 515, null);

        // draw ball
        g.drawImage(virus.image, ballposX, ballposY, null);

        // draw player score
        g.setColor(Color.WHITE);
        g.setFont(scoreFont);
        g.drawString("Score: " + score, 560, 30);

        // draw win and lose screens      
        if (totalBricks <= 0 || ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;    
            g.setFont(endFont);

            if (totalBricks <= 0) { // win
                g.setColor(Color.GREEN);
                g.drawString("Mission Success!", 235, 300);
            } else if (ballposY > 570) { // lose
                g.setColor(Color.RED);        
                g.drawString("Mission Failed, Score: " + Integer.toString(score), 180, 300);
            }

            g.setFont(restartFont);
            g.drawString("Press 'Enter' to Restart", 240, 350);
        } 

        g.dispose();
    }

    /* player movement methods */
    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    /* override action and key listeners */
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start(); // start game timer
        
        if (play) {
            // paddle-ball collision detection
            if (new Rectangle(ballposX, ballposY, 20, 20)
                    .intersects(new Rectangle(playerX, 530, 100, 8))) {
                ballYdir = -ballYdir;
            }
            // ball-brick collision detection
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (!map.map[i][j]) { // brick is not broken
                        // collidable brick attributes
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        // create collidable bricks
                        Rectangle brickRect = new Rectangle(brickX, brickY, 
                                brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        
                        // if collision with ball
                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(i, j);
                            totalBricks--;
                            score += 5;
                            // rebound ball off bricks
                            if (ballposX + 19 <= brickRect.x ||
                                    ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            /* ball movement */
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) { // rebound off left border
                ballXdir = -ballXdir;   
            } else if (ballposY < 0) { // rebound off top border
                ballYdir = -ballYdir;   
            } else if (ballposX > 660) { // rebound off right border
                ballXdir = -ballXdir;   
            }           
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // right key pressed
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) { // left key pressed
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER &&
                !play) { // game over, reset to default           
            play = true;
            ballposX = 120;
            ballposY = 350;
            ballXdir = -1;
            ballYdir = -2;
            playerX = 310;
            score = 0;
            totalBricks = 21;
            map = new MapGenerator(3, 7);
            
            repaint(); // redraw map
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
