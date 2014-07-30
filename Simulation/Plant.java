import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Plant is a class that acts as a food source for the small fish. The plant is at the bottom of the food chain, and
 * is directly proportional to the amount of small fish alive.
 * 
 * @author Douglas Wong
 * @version March 2014
 */
public class Plant extends Actor
{
    /**
     * Creates a Shark facing a random direction.
     * @param direction Checks whether or not to spawn the plant facing right or left
     */
    public Plant(boolean direction) {
        //If direction is true, makes the plant face right
        if (direction){
            this.setImage ("water-plant-md.png");
        }

        //If direction is false, makes the plant face left
        if (direction == false){
            this.setImage ("water-plant-md Left.png");
        }
    } 
}
