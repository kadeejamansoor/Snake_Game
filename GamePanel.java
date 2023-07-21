import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {       //GamePanel class extends properties of JPanel class and implements properties of interface ActionListner also

    static final int SCREEN_WIDTH = 600;                          //width of our panel where we set field for our game
    static final int SCREEN_HEIGHT = 600;                       //height of our panel
    static final int UNIT_SIZE = 25;                           //size of each unit
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);     //find the no. of cells
    static final int DELAY = 200;                                  //delay is parameter for timer class that pass the delay in milliseconds.
    final int[] x = new int[GAME_UNITS];                               //declaring an array for x values in our field nd give size as Game_units
    final int[] y = new int[GAME_UNITS];                                  //declaring an array for y values also helps in giving the length and dimensions of our snake
    int snake_length = 6;                             //initially set blocks for snake body
    int foodEaten = 0;                                     //food items eaten by snake initially set to 0
    int snakeFoodX;                             //x value for snake's food
    int snakeFoodY;                                       //y value for snake's food
    char dir = 'R';                                       //declaring a variable dir for giving our snake direction that is first set to 'R' means our snake initially starts moving to right direction
    boolean running = false;                         //a boolean value for checking condition of our game initially set to false
    Timer timer;                                        //a timer variable from Timer class helps in moving speed of our snake
    Random random;                                        //a random variable from Random function helps in giving the random location for our snake's food

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); //set dimensions for our panel
        this.setBackground(Color.darkGray); //set background color
        this.setFocusable(true); //it mainly activate the components inside ur panel by passing boolean value inside it
        this.addKeyListener(new MyKeyAdapter()); //we create a class MyKeyAdapter which extends the properties from KeyAdapter class from java and adds a keylistner which took the response.
        startGame();
    }

    public void startGame() {   //startGame class first run newFood class

        newFood();
        running = true; //sets running to true as through running we define the condition of our game
        timer = new Timer(DELAY, this); //Timer is a swing class that takes DELAY as parameter and a listener
        timer.start(); //starts the timer that we create from Timer class
    }

    public void paintComponent(Graphics g) { //we take a parameter g from Graphics class uswd in drawing the graphical structure in java
        super.paintComponent(g); //we create the paintComponent class in which we inherit the method name paintComponent with the help of super keyword nd which perform its function with the help of graphic g that we create and passed in it
        draw(g); //now we call draw method
    }

    public void draw(Graphics g) {

        if (running) {  //this method first checks for runnning status of our game that if it is true then we can mave inside the if block
            /* below for loop is just for understanding the panel via rows and columns you can also keep this in your code or you can run your code without it   

            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {  //this for loop is used for making our panel looks like grid that helps us to then understanding how our program works
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); //now drawLine is a method in awt.Graphics class that helps us in draw line as it takes for parameter (x1,y1,x2,y2) where x1 and y1 are for initial point and x2 and y2 are for final point
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);  //where it draw line between these two points that we pass in our method as parameters
            }*/
            //now we give colour , dimensions and shape for our snake's food
            g.setColor(Color.red); //setColor method gave our g Graphics an colour
            g.fillOval(snakeFoodX, snakeFoodY, UNIT_SIZE, UNIT_SIZE); //fillOval defines an oval shape for g and then inside fillOVal method we can pass 4 parameters (x-axis value , y-axis value ,width ,height)

            //this is for our snake body
            for (int i = 0; i < snake_length; i++) {
                // here i gave my snake's head a different colour from body
                if (i == 0) {
                    g.setColor(Color.cyan); //setting color to cyan

                } else {
                    // in next if else block we give two colour to our snake
                    if (i % 2 == 0) {
                        g.setColor(Color.black); //for even blocks of snake's body set black colour
                    } else {
                        g.setColor(Color.white); //else provide white colour to the remaining body
                    }

                }
                g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //and give oval shape
            }

            //now there are also more things that we need in our panel that can be drawn with the help of graphics class
            g.setColor(Color.GREEN); //set colour green
            g.setFont(new Font("Serif", Font.ITALIC, 40)); //setFont method in awt.Graphics class pass a parameter where we declare a new font ("String Font Name",Font.type,int value for size of font)
            FontMetrics metrics = getFontMetrics(g.getFont()); //FontMetrices clas inside awt package is used to encapsulate a given font and here we declare "metrics" as FontMetrices and then get font we gave to g Graphics
            g.drawString("Score: " + foodEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize()); //drawString method draw the String on our panel by taking the following parameters("String that we want to draw ",x-length,y-length)
        } else {
            gameOver(g); //else if running is false then we go to the gameOver function with parameter g
        }

    }

    //newFood() function create the new SnakeFood at given x and y axis
    public void newFood() {
        snakeFoodX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE; //here it takes x-axis for creating new food for snake through random function
        snakeFoodY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE; //here it takes y-axis

    }

    //this function now make our snake move
    public void move() {
        for (int i = snake_length; i > 0; i--) { //starting from snake length it continously dec. the values of x and y axis which are arrays where we store the length of snake
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        //switching direction for our snake's movement when we press keys
        switch (dir) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE; //denote 'U' for Upper direction as decrease y-axis value by one which helps our snake to move in above y-axis
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    //this function checks whether the snake eat food or not
    public void checkFood() {
        if ((x[0] == snakeFoodX) && (y[0] == snakeFoodY)) { //here as x and y array includes the dimensins of our snake therefore if dimension of head of our snake is equal to the position of food then it must eat it
            snake_length++; //now as our snake eat it's food then we increase snake's length by 1
            foodEaten++; // and also increase the value of food eaten byb our snake to one  that will help further in showing score
            newFood(); //and then a call for newFood that will help in generation of newFood for our snake
        }
    }

    //this function checks the collision of our snake in the field
    public void checkCollisions() {
        //checks if head collides with body
        for (int i = snake_length; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        //check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) { //check if running is not true then stop timer
            timer.stop();
        }
    }

    //gameOver function comes in place when our game stops running
    public void gameOver(Graphics g) {
        //Score display
        g.setColor(Color.red); //red color for our font graphics
        g.setFont(new Font("Serif", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont()); //again declaring a fontMetrics names as metrics1
        g.drawString("Score: " + foodEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());

        //Game Over display
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //now overriding the method actionPerformed and taking a parameter e declared through actionEvent class that fetch response as after we press buttons while playing game

        if (running) { //if game is running then
            move(); //first call move function
            checkFood(); // calling checkFood function
            checkCollisions();// calling check Collision function
        }
        repaint(); //this method cannot be overridden that's why we use this to repaint itself. If we have done anything to change the look of the component but not the size then we can call this method.
    }

    public class MyKeyAdapter extends KeyAdapter { //created MyKeyAdapter method which extends KeyAdapter
        @Override  //Overriding KeyPressed method
        public void keyPressed(KeyEvent e) {
            //switching the cases of keys we pressed
            switch (e.getKeyCode()) { //e is the KeyEvent we declared means the event or response that takes place like clicking of button from our keyboard
                case KeyEvent.VK_LEFT: //this is the code for left arrow key from our keyboard
                    if (dir != 'R') { //as if our snake is not moving right then we can easily change it's direction to left
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'U') {
                        dir = 'D';
                    }
                    break;
            }
        }
    }
}