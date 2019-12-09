package com.game.battleship;

import java.util.HashSet;

public class Player
{
    // These are the lengths of all of the ships.
    private static final int[] SHIP_LENGTHS = {2, 3, 3};
    private static final int NUM_OF_SHIPS = 3;
    
    public HashSet<String> ship1;
    public HashSet<String> ship2;
    public HashSet<String> ship3;
    public PlayerType type;
    public Ship[] ships;
    public Grid playerGrid;
    public Grid oppGrid;
    
    public Player()
    {
    	type = PlayerType.HUMAN; //By default Player Type is Human.
        if (NUM_OF_SHIPS != 3) // Num of ships must be 3
        {
            throw new IllegalArgumentException("ERROR! Num of ships must be 5");
        }
        
        ships = new Ship[NUM_OF_SHIPS];
        for (int i = 0; i < NUM_OF_SHIPS; i++)
        {
            Ship tempShip = new Ship(SHIP_LENGTHS[i]);
            ships[i] = tempShip;
        }
        
        playerGrid = new Grid();
        oppGrid = new Grid();
    }
    
    public void addShips(){
    	System.out.println("Adding all ships: ");
    	System.out.println(ships);
        for (Ship s: ships)
        {
            playerGrid.addShip(s);
        }
    }
    
    public int numOfShipsLeft()
    {
        int counter = 3;
        for (Ship s: ships)
        {
            if (s.isLocationSet() && s.isDirectionSet())
                counter--;
        }
        
        return counter;
        
    }
    
    public void chooseShipLocation(Ship s, int row, int col, int direction){
        s.setLocation(row, col);
        s.setDirection(direction);
        playerGrid.addShip(s);
    }
}