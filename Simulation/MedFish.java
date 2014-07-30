import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * MedFish is a subclass of the Fish parent class, and is the second evolution for fishes. 
 * MedFish can only eat SmallFish, and are eaten by Sharks. 
 * MedFish can increase their level up to two times, afterwards it will evolve into a Shark.
 * 
 * @author Jaydon Lau
 * @version March 2014
 */
public class MedFish extends Fish
{
    /**
     * Act - do whatever the MedFish wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public MedFish (boolean direction) {
        initialSpeed = 4;
        mySpeed = initialSpeed;
        hunger = 50;
        level = 1;
        facingRight = direction;
        evolvedFish = new Shark (facingRight);
        alive = true;
    }

    /**
     * If MedFish is alive and awake, MedFish will swim around the world, eating SmallFish upon contact.
     * Once hunger reaches 0, MedFish will level up and evolve.
     */
    public void act() 
    {
        turnRandom = Greenfoot.getRandomNumber (80);
        checkSleep();
        if (awake == false) {
            sink();
        } 
        else {
            getAndCheckHungry();
            // if hunger reaches zero, grow the fish
            if (hunger == 0) {
                if (level < 2) {
                    level ++;
                }
                else {
                    evolve();
                }
            }
            else {
                if (alive) {
                    eatTarget();
                    // if shark is in water, swim
                    if ((getY() > 200) && (getY() < 620)) {
                        swimRandom();
                    }
                    // if shark is above water, rotate
                    else if (getY() <= 200) {
                        if (facingRight) {
                            setRotation (turnRandom);
                            move (mySpeed);
                        }
                        else {
                            setRotation (turnRandom + 270);
                            move (mySpeed);
                        }
                    }
                    // if shark is going to touch bottom, rotate
                    else if (getY() >= 620) {
                        if (facingRight) {
                            setRotation (turnRandom + 270);
                            move (mySpeed);
                        }
                        else {
                            setRotation (turnRandom);
                            move (mySpeed);
                        }
                    }
                    hitEdgeOfWorld();
                    checkImage();
                }
                else {
                    sink();
                    if (getY() >= 630) {
                        getWorld().removeObject (this);
                    }
                }

            }    
        }
    }

    /**
     * Checks to see if the image and the direction the fish is facing match. Also checks for the current level of the fish and increases size accordingly. 
     */
    public void checkImage () {
        if (facingRight) {
            directionName = "right ";
        }
        else {
            directionName = "left ";
        }
        fishName = "med fish " + directionName + level + ".png";
        setImage (fishName);
    }

    /**
     * Eats SmallFish upon contact, removing the SmallFish from the world. 
     */
    public void eatTarget () {
        SmallFish sf;
        if (isTouching (SmallFish.class)) {
            sf = (SmallFish)getOneIntersectingObject (SmallFish.class);
            if (sf.getHooked() == false) {
                hunger -= 50;
                removeTouching (SmallFish.class);
            }
        }
    }
}
