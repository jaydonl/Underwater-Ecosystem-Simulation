import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * MinusButton is a subclass of Button, and is responsible for decreasing the spawn rate of plants, which will 
 * result in the plants spawning more frequently.
 * 
 * @author Douglas Wong
 * @version March 2014
 */
public class MinusButton extends Button
{
    /**
     * Creates a plus button that will take away from the value of the Spawn Rate, making plants spawn more frequently
     */
    public MinusButton() {
        //Sets the rate of which the button will add
        differenceSpawnRate = -1;
    }

    /**
     *If the user clicks the button, Plant spawn rate is updated.
     */
    public void act() 
    {
        //Checks to see if the Spawn Rate is at minumum value. If it isn't, adds to the spawn rate.
        if (((EcoWorld)getWorld()).checkPlantSpawnRate() > 1) {
            updatePlantSpawnRate();
        }
    }    
}
