import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A diver that looks for treasure and gets eaten by sharks.
 * 
 * @author Terrence Hung
 * @version March 23, 2014
 */
public class Diver extends Person
{
    //declare variables
    private boolean alive;
    private boolean carryingTreasure;
    private static int xCoordinate;
    private static int yCoordinate;
    private int exitDirection;
    private boolean isSinking;
    private boolean moveLeft;
    private Blood blood;
    /**
     * Constructs a diver that will be added into the world.
     */
    public Diver()
    {
        speed = 1; //set speed to 1
        alive = false; //set alive to false
        carryingTreasure = false; //set carryingTreasure to false
        exitDirection = 5; //give it any number besides 0 or 1 because direction is decided if it is 0 or 1
    }

    /**
     * Makes the diver turn towards the treasure and move towards it, and swim off the screen if it gets the treasure.
     */
    public void act() 
    {    
        //if diver isn't carrying treasure, then turn towards treasure
        if (!(carryingTreasure))
        {
            turnTowards(Treasure.getXCoordinate(), Treasure.getYCoordinate()); //turn towards treasure chest            
        }
        //if diver has picked up treasure and doesn't have a direction set
        else if (carryingTreasure && exitDirection != 0 && exitDirection != 1)
        {
            exitDirection = Greenfoot.getRandomNumber(2); //assign either 0 or 1
            //go left if 0
            if (exitDirection == 0)
                turnTowards(0, 205);
            //go right if 1
            else if (exitDirection == 1)
                turnTowards(960, 205);
        }
        //if diver is carrying treasure and already has a direction set
        else if (carryingTreasure)
        {
            //go left if 0
            if (exitDirection == 0)
            {
                turnTowards(0, 405); //turn towards left side of screen
                setImage("diver left.png"); //set image to diver left
                moveLeft = true;
                Treasure.setDirection(true); //move treasure chest to the left of the diver
            }
            //go right if 1
            else if (exitDirection == 1)
            {
                turnTowards(960, 405); //turn towards right side of screen
                setImage("diver right.png"); //set image to diver right
                moveLeft = false;
                Treasure.setDirection(false); //move treasure chest to the right of the diver
            }
        }
        move(speed); //move diver
        checkGrabTreasure(); //check if diver touches treasure
        //update x and y coordinates
        xCoordinate = this.getX(); 
        yCoordinate = this.getY();
        if (isSinking) //if isSinking is true then make diver sink
            sinking();
        checkAndRemove(); //check if diver is at edge of world and remove if it is
    }    

    /**
     * Changes the value of boolean alive.
     * 
     * @param trueOrFalse Value that boolean alive will be changed to.
     */
    public void setAlive(boolean trueOrFalse)
    {
        alive = trueOrFalse;
    }

    /**
     * Gets the value of boolean alive.
     * 
     * @return boolean Return true if diver is alive, false if not.
     */
    public boolean getAlive()
    {
        return alive;
    }

    /**
     * Checks if the diver is touching treasure, and makes the treasure follow the diver.
     */
    private void checkGrabTreasure()
    {
        //check if diver touches treasure
        Treasure treasure = (Treasure)getOneIntersectingObject(Treasure.class);
        if (treasure != null && alive) //only grab treasure if diver is alive
        {
            treasure.setFollow(true); //set followingDiver in treasure to true
            carryingTreasure = true; //change carryingTreasure to true
        }        
    }

    /**
     * Gets the x-coordinate of the diver.
     * 
     * @return int The current x-coordinate of the diver.
     */
    public static int getXCoordinate()
    {
        return xCoordinate;
    }

    /**
     * Gets the y-coordinate of the diver.
     * 
     * @return int The current y-coordinate of the diver.
     */
    public static int getYCoordinate()
    {
        return yCoordinate;
    }

    /**
     * Checks if diver is at the edge of the world. If it is, remove the diver, set alive to false, change treasure's followingDiver boolean to false, change carryingTreasure to false, and reset exit direction.
     */
    protected void checkAndRemove()
    {
        //if diver touches edges of the world
        if (getX() <= 0 || getX() >= 959)
        {
            getWorld().removeObject(this); //remove object from world
            alive = false; //set alive to false
            Treasure.setFollow(false); //make treasure stop following diver
            Treasure.setInWorld(false); //set inWorld for treasure to false
            carryingTreasure = false; //change carrying treasure to false
            exitDirection = 5; //reset exit direction 
        }
        //if diver reaches the bottom of the world from sinking
        else if (getY() >= 630)
        {
            getWorld().removeObject(this); //remove object from world
            alive = false; //set alive to false
            Treasure.setFollow(false); //make treasure stop following diver
            carryingTreasure = false; //change carrying treasure to false
            exitDirection = 5; //reset exit direction
            isSinking = false; //change isSinking to false
            speed = 1; //change speed back to normal
        }
    }   

    /**
     * Changes the value of isSinking boolean to true.
     */
    public void makeDiverSink()
    {
        blood = new Blood(); //construct blood
        isSinking = true; //change isSinking to true
        Treasure.setFollow(false); //make treasure stop following diver
        alive = false; //set alive to false
        getWorld().addObject(blood, getX(), getY()); //add blood where the diver was
        scream.play(); //play scream sound effect
    }

    /**
     * Turns the diver upside down, moves it downwards, and removes it from the world when it reaches the bottom.
     */
    private void sinking()
    {
        speed = 0; //set speed to 0
        if (!(moveLeft)) //if diver is swimming right
        {
            setRotation(0); //reset rotation
            setImage("diver left.png"); //turn diver upside down
        }
        else if (moveLeft) //if diver is swimming left
        {
            setRotation(0); //reset rotation
            setImage("diver left sinking.png"); //change picture to facing left upside down
        }
        if (getY() < 630) //move downwards if not at bottom of screen yet
            setLocation(getX(), getY() + 2);
    }

    /**
     * Sets the direction for the diver to swim.
     * 
     * @param trueOrFalse True if diver is swimming left, false if diver is swimming right.
     */
    public void swimLeft(boolean trueOrFalse)
    {
        moveLeft = trueOrFalse;
    }
    
    /**
     * Gets the value of isSinking boolean.
     * 
     * @return boolean Return true if diver is currently sinking.
     */
    public boolean getSinking()
    {
        return isSinking;
    }
}