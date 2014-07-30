import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Fishes with a fishing rod and rides a boat 
 * 
 * @author Jason Fok
 * @version March 2014
 */
public class Fisherman extends Person
{    
    private int currSpeed;
    private int lineHeight;
    private int initialHookX;
    private int initialHookY;
    private int spearX;
    private int spearY;
    private int stoppingPoint;
    private boolean caughtFish;
    private boolean castedLine;
    private boolean finishedReelingOut;
    private boolean finishedReelingIn;
    private boolean spawnedOnce;
    private boolean spearInWorld;
    private String spearDirection;
    private Hook hook;
    private FishingBoat boat;
    private Hunter hunter;
    private Spear spear;
    private FishingLine line = new FishingLine();

    /**
     * Constructor with no hunter
     * 
     * @param stoppingPoint Where the fisherman will start fishing
     * @param speed Speed of the boat
     * @param fishingBoat The fishing boat
     */

    public Fisherman (int stoppingPoint, int speed, FishingBoat fishingBoat)
    {
        this.stoppingPoint = stoppingPoint;
        currSpeed = speed;
        this.speed = speed;
        lineHeight = 0;
        castedLine = false;
        spearInWorld = false;

        hook = new Hook();
        boat = fishingBoat; 
        //getWorld().addObject (boat, fishingBoatSpawnX, fishingBoatSpawnY);
    }

    /**
     * Constructor with hunter
     * 
     * @param stoppingPoint Where the fisherman will start fishing
     * @param speed Speed of the boat
     * @param fishingBoat The fishing boat the fisherman is using
     * @param hunter The hunter on the same boat
     */

    public Fisherman (int stoppingPoint, int speed, FishingBoat fishingBoat, Hunter hunter)
    {
        this.stoppingPoint = stoppingPoint;
        currSpeed = speed;
        this.speed = speed;
        lineHeight = 0;
        castedLine = false;

        hook = new Hook();
        boat = fishingBoat; 
        this.hunter = hunter;
        //getWorld().addObject (boat, fishingBoatSpawnX, fishingBoatSpawnY);
    }

    /**
     * Controls the fisherman's, boat's, fishingline's, hook's, spear's and the hunter's movements
     * 
     */
    public void act() 
    {
        //Move the boat by the current speed
        move (currSpeed);
        //Reel the FishingLine into the water if it has reached it's random stopping point 
        if (checkFishingPoint() && !finishedReelingOut)
        {
            currSpeed = 0;
            boat.setSpeed(0);
            reelOut();
        }
        //Check to spawn and remove spears
        if(checkFishingPoint())
        {
            if (hunter != null)
            {
                //Check to see if the spear spawned at least once
                if (!spawnedOnce)
                {
                    spear = new Spear(this);
                    getWorld().addObject(spear, spearX, spearY);
                    if (spearDirection == "right")
                    {
                        spear.setRotation(Greenfoot.getRandomNumber(30) + 20);
                    }
                    else if (spearDirection == "left")
                    {
                        spear.setRotation(Greenfoot.getRandomNumber(30)+ 100);
                    }
                    spear.setLocation(getX() - 100, getY());
                    hunter.setSpeed(0);
                    spawnedOnce = true;
                    spearInWorld = true;
                }
                //Spawn the spear and set it's rotation and the Hunter's speed to 0
                //when the spear throwned beforehand is no longer in the world
                if (!spearInWorld)
                {
                    spear = new Spear(this);
                    getWorld().addObject(spear, spearX, spearY);
                    if (spearDirection == "right")
                    {
                        spear.setRotation(Greenfoot.getRandomNumber(30) + 20);
                    }
                    else if (spearDirection == "left")
                    {
                        spear.setRotation(Greenfoot.getRandomNumber(30)+ 100);
                    }
                    spear.setLocation(getX() - 100, getY());
                    hunter.setSpeed(0);
                    spearInWorld = true;
                }
            }
        }
        //Check if the FishingLine has been casted
        if (castedLine)
        {
            //Checks if a SmallFish has been hooked yet
            if (hook.checkFish())
            {
                caughtFish = true;
            }
            //Reel in the FishingLine if a SmallFish has been caught
            if (caughtFish && lineHeight > 6 && finishedReelingOut )
            {
                reelIn();
            }
            //If the hook is back to it's initial y-coordinate and the Fisherman has finsihed reeling the line out then make 
            //the Fisherman leave the world 
            if (finishedReelingIn && finishedReelingOut)
            {
                currSpeed = speed;
                boat.setSpeed(speed);
                hunter.setSpeed(speed);
                hook.setLocation (getX()+15, getY() + lineHeight-10);
                line.setLocation (getX()+15, getY()-18);
            }
        }
        checkAndRemove();
    }    

    /**
     * Reel in the fishing line up
     */
    public void reelIn()
    {
        if (lineHeight - 6 > 0)
        {
            lineHeight-=6;
        }
        line.updateIn(lineHeight);
        hook.setLocation (getX()+15, getY() + lineHeight-15);//10
        if (hook.getY() < initialHookY) {
            finishedReelingIn = true;
        }
    }

    /**
     * Reel out the fishing line down
     */   
    public void reelOut()
    {
        if (lineHeight <= 300)
        {
            lineHeight += 6;
        }
        if (lineHeight >= 300)
        {
            finishedReelingOut = true;
        }
        //getWorld().addObject(line, getX()+15, getY()-23);

        //getWorld().getBackground().drawLine(getX()+15, getY() - 23, getX()+15, getY() + lineHeight);
        if (!(castedLine))
        {
            getWorld().addObject(line, getX()+15, getY()-23);//18
            getWorld().addObject(hook, getX()+15,getY()+ lineHeight);
            initialHookX = getX()+15;
            initialHookY = getY()+ lineHeight;
            castedLine = true;
        }
        line.updateOut(lineHeight);
        hook.setLocation (getX()+15, getY() + lineHeight-10);
    }

    /**
     *Check if the Fisherman is at the stopping point
     *@return boolean True if the Fisherman is at the stopping point
     */
    private boolean checkFishingPoint()
    {
        if (getX() >= stoppingPoint)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Set the direction of the spear
     * @param String Direction of the spear
     */
    public void setSpearDirection (String spearDirection)
    {
        this.spearDirection = spearDirection;
    }

    /**
     * Remove the line, boat, fish, hook, hunter and the fisherman when it reaches the
     * edge of the world of remove everything else except the boat if the boat was sinked
     */
    public void checkAndRemove()
    {
        if (getX() < -100 || getX() >= 958)
        {
            ((EcoWorld)getWorld()).changeFishCaught(1); //add 1 to fishCaught in world   
            getWorld().removeObject(line);
            getWorld().removeObject(boat);
            getWorld().removeObject(hook.returnFish());
            getWorld().removeObject(hook);
            getWorld().removeObject(hunter);
            getWorld().removeObject(this);
        }
        //Do not remove the boat if it was sinked but remove everything else
        if (boat.checkIfBoatSank())
        {
            getWorld().removeObject(line);
            getWorld().removeObject(hook);
            getWorld().removeObject(hunter);
            getWorld().removeObject(this);
            scream.play(); //play scream
        }
    }   

    /**
     * Sets the boolean spearInworld to false
     */
    public void spearIsRemoved()
    {
        spearInWorld = false;
    }
}