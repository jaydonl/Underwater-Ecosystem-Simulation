import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Fish is an abstract Greenfoot Actor that is the parent class of three types of fishes: SmallFish, MedFish and Shark.
 * This class contains all the similar actions that different fish have, such as swimming, sleeping and getting hungry.
 * Fish also contains similar attributes of different fish, such as speed, level, and the fish they evolve into.
 * <p>
 * Fish are an essential part to the eco-system, as they keep the plant population in check, and are fished by the FisherMan. 
 * The three different subclasses of Fish are meant to keep one another's population in check. 
 * 
 * @author Jaydon Lau, Douglas Wong  
 * @version March 2014
 */
public abstract class Fish extends Actor
{
    // integer for hunger levels
    protected int hunger;
    // Specific fish for fishes to evolve to 
    protected Fish evolvedFish;
    // speed of the fish
    protected int initialSpeed;
    protected int mySpeed;
    // checks which direction fish is facing
    protected boolean facingRight;
    // level represents the current size of the fish 
    protected int level; 
    // target the fish wants to eat
    protected Actor target;
    // boolean to check if fish is living
    protected boolean alive;
    // integer for degrees the fish can turn
    protected int turnRandom;
    // string for picture name, depending on fish level
    protected String fishName;
    // string for direction of fish
    protected String directionName;
    // boolean for awake
    protected boolean awake; 
    /**
     * Sets Fish rotation and moves the Fish based on speed.
     */
    protected void swimRandom() {
        if (Greenfoot.getRandomNumber (100) == 50)
        {
            // turns at a random angle between 0 and 44
            turn (Greenfoot.getRandomNumber(25));           
        }
        else
        {
            // moves fish at designated speed
            move (mySpeed);
        }
    }

    /**
     * Evolves Fish into bigger Fish subclass after reaching a certain level.
     */
    protected void evolve() {
        getWorld().addObject (evolvedFish, getX(), getY());
        getWorld().removeObject(this);
    }

    /**
     * Sets facingRight as false and changes speed to negative if the Fish hits the edge of the world.
     */
    protected void hitEdgeOfWorld()
    {
        if (this.getX() >= 950)
        {
            mySpeed = -mySpeed;
            facingRight = false;
        }
        if (this.getX() <= 0)
        {
            mySpeed = -mySpeed;
            facingRight = true;
        }
    }

    /**
     * Checks to see if the image and the direction the fish is facing match.
     */
    public abstract void checkImage ();

    /**
     * Eats a designated target upon contact.
     */
    public abstract void eatTarget ();

    /**
     * Flips the Fish image upside down, and causes the Fish to sink to the bottom.
     */
    protected void sink () {
        setRotation (180);
        if (getY() < 630) {
            setLocation (getX(), getY() + 2);
        }
    }

    /**
     * Mutator to set the fish dead.
     */
    public void setFishDead () {
        alive = false;
    }

    /**
     * If it is night time in the world, sets the fish to sleep, causing it to sink to the bottom of the world.
     */
    protected void checkSleep () {
        // if night time is false
        if (((EcoWorld)getWorld()).getNightTime() == false) {
            awake = true;
            // reset to original speed
            if (facingRight) {
                mySpeed = initialSpeed;
            } else {
                // sets to negative speed if fish is facing left
                mySpeed = -initialSpeed;
            }   
        } 
        else {
            awake = false;
            // sets speed to 0
            mySpeed = 0;
        }
    }

    /**
     * Increases hunger every 10 acts in the world, and checks if Fish has died from hunger.
     */
    protected void getAndCheckHungry () {
        // gets hungry every 10 acts
        if ((((EcoWorld)getWorld()).getTimer() % 10) == 0) {
            hunger += 1;
        }
        // shark is dead when hunger reaches 1000
        if (hunger >= 1000) {
            alive = false;
        }
    }
}
