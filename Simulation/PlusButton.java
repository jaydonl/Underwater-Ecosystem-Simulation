import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/** 
 * Plus Button is a subclass of Button, and is responsible for increasing the spawn rate of plants, which will 
 * result in the plants spawning less frequently.
 * 
 * @author Douglas Wong
 * @version March 2014
 */
public class PlusButton extends Button
{
    /**
     * Creates a plus button that will add to the value of the Spawn Rate, making plants spawn less frequently
     */
    public PlusButton() {
        //Sets the rate of which the button will add
        differenceSpawnRate = 1;   
    }

    /**
     * If the user clicks the button, Plant spawn rate is updated.
     */
    public void act() 
    {
        //Checks to see if the Spawn Rate is at maximum value. If it isn't, adds to the spawn rate.
        if (((EcoWorld)getWorld()).checkPlantSpawnRate() < 25) {
            updatePlantSpawnRate();
        }
    }    
}
