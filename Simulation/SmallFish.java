import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * SmallFish is a subclass of the Fish parent class, and is the first evolution for fishes.
 * SmallFish can only eat Plants, and are eaten by MedFish. SmallFish can also be hooked by FishingBoats.
 * SmallFish can also increase their level, up to three times. Afterwards, they will evolve to MedFish. 
 * 
 * @author Jaydon Lau
 * @version March 2014
 */
public class SmallFish extends Fish
{
    // boolean to check if Small Fish has been hooked yet.
    private boolean hooked;
    /**
     * Creates a living level 1 Small Fish facing a random direction, setting speed to 3 and hunger to 50.
     */
    public SmallFish (boolean direction) {
        initialSpeed = 3;
        mySpeed = initialSpeed;
        hunger = 50;
        facingRight = direction;
        level = 1;
        evolvedFish = new MedFish (facingRight);
        alive = true;
        hooked = false;
    }

    /**
     * If SmallFish is awake and alive, then SmallFish will swim around the world and eat Plants upon contact.
     * When hunger reaches 0, SmallFish will level up and evolve.
     */
    public void act() 
    {
        turnRandom = Greenfoot.getRandomNumber (80);
        checkSleep();
        if (detectHook() != null) {
            hooked = true;
            setLocation (detectHook().getX(), detectHook().getY());
        }
        else {
            getAndCheckHungry();
            if (awake == false) {
                sink();
            } 
            else {
                // if hunger reaches zero, grow the fish
                if (hunger == 0) {
                    if (level < 3) {
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
        fishName = "small fish " + directionName + level + ".png";
        setImage (fishName);
    }

    /**
     * Eats Plants upon contact, removing the Plant from the world. 
     */
    public void eatTarget () {
        if (isTouching (Plant.class)) {
            hunger -= 50;
            removeTouching (Plant.class);
        }
    }

    /**
     * Detects if Fish is touching a Hook, and returns the Hook object
     * @return Actor Hook actor, if touching. Otherwise return null. 
     */
    private Actor detectHook () {
        Hook hookTarget = (Hook)getOneIntersectingObject(Hook.class);
        if (hookTarget != null) {
            return hookTarget;
        }
        else {
            return null;
        }
    }

    /**
     * Accessor to check if the SmallFish has been hooked.
     */
    public boolean getHooked () {
        return hooked;
    }

}
