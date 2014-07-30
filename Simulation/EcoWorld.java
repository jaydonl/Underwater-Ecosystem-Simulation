import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * EcoWorld is a Greenfoot World that acts as a controller for all the objects in the world, spawning all actor subclasses.
 * EcoWorld changes from night time and day time, adding a night object over the world. 
 * Once it is night time, the functionality of certain actors change, as some will begin to sleep.
 * The ScoreBoard is also updated in EcoWorld.
 * 
 * @author Terrence Hung, Jason Fok, Douglas Wong, Jaydon Lau
 * @version March 2014
 */
public class EcoWorld extends World
{
    private Water w;
    private int fishermanSpawnRate = 500;
    private int fishermanSpawnX = 70;
    private int fishermanSpawnY = 152;
    private int fishingBoatSpawnX = fishermanSpawnX - 50;
    private int fishingBoatSpawnY = fishermanSpawnY - 2;
    private int hunterSpawnX = fishermanSpawnX -100;
    private int hunterSpawnY = fishermanSpawnY + 7;
    private int speedOfFishermanAndBoat = 2;

    // spawn rates for each type of fish
    private int smallFishSpawnRate = 50;
    private int medFishSpawnRate = 200;
    private int sharkSpawnRate = 2500;
    
    // spawn rate for plants
    private int plantSpawnRate = 10;
    
    //X-coordinate of stopping point
    private int stoppingPoint;
    private FishingBoat fishingBoat;
    private Hunter hunter;
    private Fisherman fisherman;

    private Treasure treasure;
    private Diver diver;
    private int diverTreasureSpawnRate = 500;
    private int diverSpawner; //spawns diver on either left side or right side
    private Night night;
    private int timer = 0;
    private boolean nightTime = false;
    private boolean changingDay;
    private ScoreBar stats;
    private int treasuresCaught = 0;
    private int fishCaught = 0;
    private int fadeTimer = 0;

    private GreenfootSound dayTimeMusic = new GreenfootSound("dayTimeMusic.mp3"); 
    private GreenfootSound nightTimeMusic = new GreenfootSound("nightTimeMusic.mp3"); 
    /**
     * Creates a 960x640 pixel world, along with Water, a ScoreBoard and Buttons to control spawn rates of the Plants.
     * 
     */
    public EcoWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        w = new Water ();
        addObject (w, 480, 410);

        treasure = new Treasure();
        diver = new Diver();
        night = new Night();
        addObject(night, 480, 320);
        stats = new ScoreBar(960);
        addObject(stats, 480, 15);
        stats.update("Fish Caught:" + stats.zeroAdder(fishCaught, 3) + "   Treasures Caught:" + stats.zeroAdder(treasuresCaught, 3) + "                  " + stats.zeroAdder (plantSpawnRate, 2));
        addObject (new PlusButton(), 920, 15);
        addObject (new MinusButton(), 820, 15);
        setPaintOrder(PlusButton.class, MinusButton.class, ScoreBar.class, Night.class, Fisherman.class, FishingBoat.class, Water.class, Diver.class, Treasure.class, Plant.class);
    }

    /**
     * Spawns all actor subclasses randomly, depending on spawn rates.
     * Changes between night time and day time gradually.
     * Updates the ScoreBoard with new information.
     */
    public void act()
    {
        // Spawn fishermen on boats randomly and only during the daytime
        if (Greenfoot.getRandomNumber(fishermanSpawnRate) == 1 && !nightTime)
        {
            //Have the boat stop at least 50 pixels into the world
            stoppingPoint = Greenfoot.getRandomNumber(600)+ 150;
            if (Greenfoot.getRandomNumber(1) == 0)
            {
                fishingBoat = new FishingBoat(speedOfFishermanAndBoat);
                hunter = new Hunter(speedOfFishermanAndBoat);
                addObject (hunter, hunterSpawnX, hunterSpawnY);
                fisherman = new Fisherman(stoppingPoint,speedOfFishermanAndBoat, fishingBoat, hunter);
                addObject (fisherman,fishermanSpawnX, fishermanSpawnY);
                if (stoppingPoint <= 480)
                {
                    hunter.setImage("Hunter right.png"); 
                    fisherman.setSpearDirection("right");
                }
                else if (stoppingPoint > 480)
                {
                    hunter.setImage("Hunter left.png");   
                    fisherman.setSpearDirection("left");
                }
                addObject (fishingBoat, fishingBoatSpawnX, fishingBoatSpawnY);    
            }
            else 
            {
                fishingBoat = new FishingBoat(speedOfFishermanAndBoat);
                addObject (new Fisherman(stoppingPoint,speedOfFishermanAndBoat, fishingBoat),fishermanSpawnX, fishermanSpawnY);
                addObject (fishingBoat, fishingBoatSpawnX, fishingBoatSpawnY);    
            }
        }
        // spawns a shark facing either right or left 
        if (Greenfoot.getRandomNumber (2) == 0) {
            if (Greenfoot.getRandomNumber (sharkSpawnRate) == 2 && !nightTime) 
            {
                addObject (new Shark (true), 50, (Greenfoot.getRandomNumber (498) + 152));
            }
        }
        else {
            if (Greenfoot.getRandomNumber (sharkSpawnRate) == 2 && !nightTime) 
            {
                addObject (new Shark (false), 910, (Greenfoot.getRandomNumber (498) + 152));
            }
        }

        // spawns a plant facing either right or left 
        if (Greenfoot.getRandomNumber (2) == 0) {
            if (Greenfoot.getRandomNumber (plantSpawnRate) == 0) 
            {
                addObject (new Plant (true), (Greenfoot.getRandomNumber (960)), (Greenfoot.getRandomNumber (50) + 570));
            }
        }
        else {
            if (Greenfoot.getRandomNumber (plantSpawnRate) == 0) 
            {
                addObject (new Plant (false), (Greenfoot.getRandomNumber (960)), (Greenfoot.getRandomNumber (50) + 570));
            }
        }
        // spawns a small fish facing either right or left
        if (Greenfoot.getRandomNumber (2) == 0) {
            if (Greenfoot.getRandomNumber (smallFishSpawnRate) == 2 && !nightTime) 
            {
                addObject (new SmallFish (true), 20, (Greenfoot.getRandomNumber (498) + 152));
            }
        }
        else {
            if (Greenfoot.getRandomNumber (smallFishSpawnRate) == 2 && !nightTime) 
            {
                addObject (new SmallFish (false), 940, (Greenfoot.getRandomNumber (498) + 152));
            }
        }

        // spawns a med fish facing either right or left
        if (Greenfoot.getRandomNumber (2) == 0) {
            if (Greenfoot.getRandomNumber (medFishSpawnRate) == 2 && !nightTime) 
            {
                addObject (new MedFish (true), 30, (Greenfoot.getRandomNumber (498) + 152));
            }
        }
        else {
            if (Greenfoot.getRandomNumber (medFishSpawnRate) == 2 && !nightTime)
            {
                addObject (new MedFish (false), 930, (Greenfoot.getRandomNumber (498) + 152));
            }
        }
        //randomly spawn treasure chest if there isn't already a treasure chest in the world and if there is no diver in world
        if (Greenfoot.getRandomNumber(diverTreasureSpawnRate) == 20 && !(treasure.getInWorld()) && !(diver.getAlive()))
        {
            treasure.setCoordinates(Greenfoot.getRandomNumber(361) + 200, 616); //x-coordinate b/w 200 and 560, y-coordinate at bottom
            addObject(treasure, treasure.getXCoordinate(), treasure.getYCoordinate()); //add treasure chest to world
            treasure.setInWorld(true); //set inWorld for treasure to true
        }

        //randomly spawn diver if there is a treasure chest in the world and if there are no other divers
        if (Greenfoot.getRandomNumber(diverTreasureSpawnRate) == 50 && treasure.getInWorld() && !(diver.getAlive()))
        {
            diverSpawner = Greenfoot.getRandomNumber(2); //get a random number either 0 or 1            
            if (diverSpawner == 0) //spawn diver on left side
            {
                diver.swimLeft(false); //set moveLeft in diver to false
                diver.setImage("diver right.png"); //set correct diver image
                addObject(diver, 1, Greenfoot.getRandomNumber(231) + 300); //add diver on the left side of the screen, somewhere in the water
            }
            else if (diverSpawner == 1) //spawn diver on right side
            {
                diver.swimLeft(true); //set moveRight in diver to true
                diver.setImage("diver left.png"); //set correct diver image
                addObject(diver, 957, Greenfoot.getRandomNumber(231) + 300); //add diver on the right side of the screen, somewhere in the water
            }
            diver.setAlive(true); //set alive for diver to true
        }

        //increase timer if it is currently not changing to night or day
        if (!(changingDay))
            timer += 1;  

        //set changingDay to true if the timer reaches a certain amount
        if (timer % 4000 == 0 && !(nightTime))
            changingDay = true;
        //make night time shorter than day time
        else if (timer % 2000 == 0 && nightTime)
            changingDay = true;

        //change to night time if changingDay is true and nightTime is false
        if (changingDay && !(nightTime))
        {
            if(fadeTimer % 10 == 0) //change transparency every 10 acts
                night.changeTransparency(1); //increase transparency by 1
            fadeTimer += 1; //add to fadeTimer
            //when transparency reaches 175, stop changing transparency
            if (night.getTransparency() == 175)
            {
                nightTime = true; //set nightTime to true
                changingDay = false; //set changingDay to false
                fadeTimer = 0; //set fadeTimer to 0
            }
        }
        //change to day time if changingDay is true and nightTime is true
        else if (changingDay && nightTime)
        {
            if(fadeTimer % 10 == 0) //change transparency every 10 acts
                night.changeTransparency(-1); //decrease transparency by 1
            fadeTimer += 1; //add to fadeTimer
            //when transparency reaches 0, stop changing transparency
            if (night.getTransparency() == 0)
            {
                nightTime = false; //set nightTime to false
                changingDay = false; //set changingDay to false
                fadeTimer = 0; //set fadeTimer to 0
            }
        }

        //Play different background music for daytime or nightime
        if (!nightTime)
        {
            if (nightTimeMusic.isPlaying())
            {
                nightTimeMusic.stop();
            }
            dayTimeMusic.play();
        }
        else if (nightTime)
        {
            if (dayTimeMusic.isPlaying())
            {
                dayTimeMusic.stop();
            }
            nightTimeMusic.play();
        }

        stats.update("Fish Caught:" + stats.zeroAdder(fishCaught, 3) + "   Treasures Caught:" + stats.zeroAdder(treasuresCaught, 3) + "                  " + stats.zeroAdder (plantSpawnRate, 2));
    }

    /**
     * Static method that gets the distance between the x,y coordinates of two Actors
     * using Pythagorean Theorum. Credit to Mr. Cohen
     * 
     * @param a     First Actor
     * @param b     Second Actor
     * @return float
     * 
     * 
     */
    public static float getDistance (Actor a, Actor b)
    {
        double distance;
        double xLength = a.getX() - b.getX();
        double yLength = a.getY() - b.getY();
        distance = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
        return (float)distance;
    }

    /**
     * Adds the value of the parameter to int treasuresCaught.
     * 
     * @param change How much the variable will be changed by.
     */
    public void changeTreasuresCaught(int change)
    {
        treasuresCaught += change;
    }

    /**
     * Adds the value of the parameter to int plantSpawnRate.
     * 
     * @param change How much the variable will be changed by.
     */
    public void changePlantSpawnRate (int change){
        plantSpawnRate += change;
    }

    /**
     * Checks the value of the int plantSpawnRate.
     * 
     * 
     */
    public int checkPlantSpawnRate (){
        return plantSpawnRate;
    }

    /**
     * Adds the value of the parameter to int fishCaught.
     * 
     * @param change How much the variable will be changed by.
     */
    public void changeFishCaught(int change)
    {
        fishCaught += change;
    }

    /**
     * Accessor to access the timer of the world.
     */
    public int getTimer () {
        return timer;
    }

    /**
     * Accessor to check if it is night time or day time in the world.
     */
    public boolean getNightTime () {
        return nightTime;
    }
}
