import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Shark is a subclass of the Fish parent class, and is the final evolution for fishes. 
 * Sharks can eat MedFish, Divers and can attack Fishing Boats. Also, Sharks can be killed by spears.  
 * 
 * @author Jaydon Lau and Douglas Wong
 * @version March 2014
 */
public class Shark extends Fish
{
    // fishing boat target that the shark will attack
    private FishingBoat fishingBoatTarget;
    // distance from shark to fishing boat
    private double distanceToTarget;
    // boolean to check if shark is in the water
    private boolean inWater = true;
    // boolean to check if all requirements are meant to attack
    private boolean attackReady = false;
    // list of potential fishing boat targets
    private ArrayList <FishingBoat> targetList;

    /**
     * Creates a living Shark facing a random direction, setting speed to 5 and hunger to 50. 
     * @param direction Checks direction Shark is facing 
     */
    public Shark (boolean direction) {
        initialSpeed = 5;
        mySpeed = initialSpeed;
        hunger = 50;
        facingRight = direction;
        alive = true;
    }

    /**
     * If Shark is alive and awake, then Shark will swim around the world, attacking boats and eating targets if it is able to.
     */
    public void act() 
    {
        // checks if shark needs to sleep
        checkSleep();
        // checks if shark is in the water
        checkInWater();
        turnRandom = Greenfoot.getRandomNumber (80);
        // searches for closest target
        closestTarget ();
        // if target is found
        if (targetList.size() > 0 && fishingBoatTarget != null) {
            // checks if requirements are satisfied in order to jump
            checkAttackReady(fishingBoatTarget.getX());
        }
        // if night time, the sink to the bottom
        if (awake == false) {
            sink();
        }
        else {
            getAndCheckHungry();
            // only acts if shark is alive
            if (alive) {
                // eats medium fish and diver upon contact
                eatTarget();
                if (attackReady) {
                    // jumps to coordinates
                    if (targetList.size() > 0 && fishingBoatTarget != null) {
                        jump (fishingBoatTarget.getX(), fishingBoatTarget.getY());
                    }
                }
                else {
                    // if shark is in water, swim
                    if ((getY() > 200) && (getY() < 620)) {
                        swimRandom();
                    }
                    // if shark is above water, rotate
                    else if (getY() <= 200) {
                        if (facingRight) {
                            setRotation (turnRandom);
                            swimRandom();
                        }
                        else {
                            setRotation (turnRandom + 270);
                            swimRandom();
                        }
                    }
                    // if shark is going to touch bottom, rotate
                    else if (getY() >= 620) {
                        if (facingRight) {
                            setRotation (turnRandom + 270);
                            swimRandom();
                        }
                        else {
                            setRotation (turnRandom);
                            swimRandom();
                        }
                    }
                }
                // checks if shark hit the edge of the world
                hitEdgeOfWorld();
                // checks if image needs to be reset
                checkImage();
            }
            else {
                // shark sinks if it is not alive, removes itself if it hits the bottom of the world
                sink();
                if (getY() >= 630) {
                    getWorld().removeObject (this);
                }
            }
        }
    }

    /**
     * Checks to see if the image and the direction the fish is facing match.
     */
    public void checkImage () {
        if (facingRight) {
            setImage ("Shark Right.png");
        }
        else {
            setImage ("Shark Left.png");
        }
    }

    /**
     * Jumps out of the water to attack a FishingBoat, sinking the boat in the process. Will fall back into the water after the boat has been sunk.
     * @param fishingBoatX X coordinate of the FishingBoat target.
     * @param fishingBoatY Y coordinate of the FishingBoatTarget
     */
    private void jump (int fishingBoatX, int fishingBoatY) {
        // if shark is in water
        if (inWater) {
            // turns to different points depending if shark is facing right or left
            if (facingRight) {
                turnTowards (fishingBoatX, fishingBoatY);
            }
            else {
                turnTowards (fishingBoatX, fishingBoatY);
                // if facing left, flips image after turning
                setRotation (getRotation() + 180);
            }
        }
        else {
            // if shark comes into contact with the boat
            fishingBoatTarget = (FishingBoat) getOneIntersectingObject (FishingBoat.class);
            if (fishingBoatTarget != null && (fishingBoatTarget.checkIfBoatSank() == false)) {
                // sinks the boat and reduces hunger of the shark
                fishingBoatTarget.sinkBoat();
                hunger -= 600;
                // sets shark rotation so that the shark goes back into the water
                if (facingRight) {
                    setRotation (45);
                } 
                else {
                    setRotation (315);
                }                   
            }
        }
        move(mySpeed);
    }

    /**
     * Checks if the shark is currently in the water, or above the water.
     */
    private void checkInWater() {
        if (getY() > 0 && getY() < 640){
            if (getY() > 160){
                inWater = true;
            }
            if (getY() <= 160){
                inWater = false;
            }
        }
    }

    /**
     * Eats Medium Fish or Divers upon contact, removing Med Fish from the world and causing Divers to sink.
     */
    public void eatTarget () {
        MedFish mf;
        Diver diver;
        if (isTouching (MedFish.class)) {
            mf = (MedFish)getOneIntersectingObject (MedFish.class);
            hunger -= 50;
            removeTouching (MedFish.class);
        }
        if (isTouching (Diver.class)) {
            diver = (Diver) getOneIntersectingObject (Diver.class);
            hunger -= 50;
            if (diver.getAlive()) {
                diver.makeDiverSink();
            }
        }            
    }

    /**
     * Constantly checks for the closest FishingBoat target, credit to Mr. Cohen for the code.
     */
    private void closestTarget () {
        double closestTargetDistance = 0;
        double distanceToActor;
        targetList = (ArrayList) getWorld().getObjects (FishingBoat.class);
        if (targetList.size() > 20) {
            targetList = (ArrayList) getWorld().getObjects (FishingBoat.class);
        }
        if (targetList.size() > 0) {
            //set first one as target
            fishingBoatTarget = targetList.get(0);
            // use method to get distance to target, which will be used to check if any other targets are closer
            closestTargetDistance = EcoWorld.getDistance (this, fishingBoatTarget);
            // loop through objects in ArrayList to find the closest target
            for (FishingBoat o: targetList) {
                // measure distance
                distanceToActor = EcoWorld.getDistance (this, o);
                // if another actor is closer, set that as closest target
                if (distanceToActor < closestTargetDistance) {
                    fishingBoatTarget = o;
                    closestTargetDistance = distanceToActor;
                }
            }
        }
    }

    /**
     * Checks to see if proper requirements are met before jumping to attack the Fishing Boat. 
     * Ex. checking of the boat has sank yet, or if the shark is in proper position.
     * @param boatX X coordinate of the FishingBoat target.
     */
    private void checkAttackReady(int boatX) {
        // if distance between shark and boat is less than 50
        if (EcoWorld.getDistance (this, fishingBoatTarget) < 150)
        {
            // if fishing boat has not sunk yet
            if (fishingBoatTarget.checkIfBoatSank() == false) {
                // if shark is facing right and is behind the boat
                if (facingRight && (getX() <= boatX)) {
                    attackReady = true;
                }
                // if shark is facing left and is in front of the boat
                else if ((facingRight == false) && (getX() >= boatX)) {
                    attackReady = true;
                }
                else {
                    attackReady = false;
                }
            }
            else {
                attackReady = false;
            }
        }
        else {
            attackReady = false;
        }
    }
}
