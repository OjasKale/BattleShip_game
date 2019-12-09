package com.game.battleship;
import java.util.Scanner;

public class Battleship
{
    public static Scanner reader = new Scanner(System.in);
      
    public static void main(String[] args) throws Exception
    {
        System.out.println("JAVA BATTLESHIP - ** Ojas Kale **");  
        
        System.out.println("\nSelect the Game Mode");
        System.out.println("1. Human vs Human");
        System.out.println("2. Human vs Computer");
        System.out.println("Select your Choice: ");
        int modeOption = reader.nextInt();
        System.out.println("Mode Option: " + modeOption);
        Player player1 = new Player();
        Player player2 = new Player();
        switch (modeOption) {
		case 1:
			System.out.println("\nPlayer1 SETUP:");
	        player1.type = PlayerType.HUMAN;
	        setup(player1);
	        
	        System.out.println("\nPlayer2 SETUP:");
	        player2.type = PlayerType.HUMAN;
	        setup(player2);
	        
	        
			break;
		case 2:
			System.out.println("\nPlayer1 SETUP:");
	        player1.type = PlayerType.HUMAN;
	        setup(player1);
	        
			System.out.println("Computer SETUP...DONE...PRESS ENTER TO CONTINUE...");
	        reader.nextLine();
	        reader.nextLine();
	        player2.type = PlayerType.COMPUTER;
	        setupComputer(player2);
	        System.out.println("\nCOMPUTER GRID (FOR DEBUG)...");
	        player2.playerGrid.printShips();
			
			break;
		default:
			break;
		}
        
        //System.out.println("Ship1 for player 1");
        //System.out.println(player1.ship1);
        
        String result = "";
        
        	if(modeOption == 2){
        		while(true)
                {
	        		System.out.println(result);
	                System.out.println("\nUSER MAKE GUESS:");
	                result = askForGuess(player1, player2);
	                
	                if (player1.playerGrid.hasLost())
	                {
	                    System.out.println("COMP HIT!...USER LOSES");
	                    break;
	                }
	                else if (player2.playerGrid.hasLost())
	                {
	                    System.out.println("HIT!...COMPUTER LOSES");
	                    break;
	                }
	                
	                System.out.println("\nCOMPUTER IS MAKING GUESS...");
	                  
	                  
	                compMakeGuess(player2, player1);
                }
        	} else {
        		while(true){
        			System.out.println("\n USER1 Making Guess");
        			result = askForGuess(player1, player2);
        			System.out.println(result);
        			if (player1.playerGrid.hasLost())
	                {
	                    System.out.println("HIT!...USER1 LOSES");
	                    break;
	                }
	                else if (player2.playerGrid.hasLost())
	                {
	                    System.out.println("HIT!...USER2 LOSES");
	                    break;
	                }
        			System.out.println("\n USER2 Making Guess");
        			result = askForGuess(player2, player1);
        			System.out.println(result);
        			if (player1.playerGrid.hasLost())
	                {
	                    System.out.println("COMP HIT!...USER LOSES");
	                    break;
	                }
	                else if (player2.playerGrid.hasLost())
	                {
	                    System.out.println("HIT!...COMPUTER LOSES");
	                    break;
	                }
        		}
        	}
            
        
    }
    
    private static void compMakeGuess(Player comp, Player user)
    {
        Randomizer rand = new Randomizer();
        int row = Randomizer.nextInt(0, 5);
        int col = Randomizer.nextInt(0, 5);
        
        // While computer already guessed this posiiton, make a new random guess
        while (comp.oppGrid.alreadyGuessed(row, col))
        {
            row = Randomizer.nextInt(0, 5);
            col = Randomizer.nextInt(0, 5);
        }
        
        if (user.playerGrid.hasShip(row, col))
        {
        	if(comp.ship1.contains(""+row+col)){
        		comp.ship1.remove(""+row+col);
            	if(comp.ship1.size() == 0){
            		System.out.println("\n** COMP SANK A SHIP **");
            	}
            }else if(comp.ship2.contains(""+row+col)){
            	comp.ship2.remove(""+row+col);
            	if(comp.ship2.size() == 0){
            		System.out.println("\n** COMP SANK A SHIP **");
            	}
            }else if(comp.ship3.contains(""+row+col)){
            	comp.ship3.remove(""+row+col);
            	if(comp.ship3.size() == 0){
            		System.out.println("\n** COMP SANK A SHIP **");
            	}
            }
            comp.oppGrid.markHit(row, col);
            user.playerGrid.markHit(row, col);
            System.out.println("COMP HIT AT " + convertIntToLetter(row) + convertCompColToRegular(col));
        }
        else
        {
            comp.oppGrid.markMiss(row, col);
            user.playerGrid.markMiss(row, col);
            System.out.println("COMP MISS AT " + convertIntToLetter(row) + convertCompColToRegular(col));
        }
        
        
        System.out.println("\nYOUR BOARD...PRESS ENTER TO CONTINUE...");
        reader.nextLine();
        user.playerGrid.printCombined();
        System.out.println("PRESS ENTER TO CONTINUE...");
        reader.nextLine();
    }

    private static String askForGuess(Player p, Player opp)
    {
        System.out.println("Viewing My Guesses:");
        p.oppGrid.printStatus();
        
        int row = -1;
        int col = -1;
        
        String oldRow = "Z";
        int oldCol = -1;
        
        while(true)
        {
            System.out.print("Type in row (A-F): ");
            String userInputRow = reader.next();
            userInputRow = userInputRow.toUpperCase();
            oldRow = userInputRow;
            row = convertLetterToInt(userInputRow);
                    
            System.out.print("Type in column (1-6): ");
            col = reader.nextInt();
            oldCol = col;
            col = convertUserColToProCol(col);

                    
            if (col >= 0 && col <= 5 && row != -1)
                break;
                    
            System.out.println("Invalid location!");
        }
        
        if(opp.playerGrid.previouslyHit(row, col)){
        	return "** USER  HAS ALREADY HIT " + oldRow + oldCol + " **";
        }else if (opp.playerGrid.hasShip(row, col)){
        	
            p.oppGrid.markHit(row, col);
            opp.playerGrid.markHit(row, col);
            if(opp.ship1.contains(""+row+col)){
            	opp.ship1.remove(""+row+col);
            	if(opp.ship1.size() == 0){
            		return "** USER  HIT AT " + oldRow + oldCol + " **" + "\n** USER SANK A SHIP **";
            	}
            }else if(opp.ship2.contains(""+row+col)){
            	opp.ship2.remove(""+row+col);
            	if(opp.ship2.size() == 0){
            		return "** USER  HIT AT " + oldRow + oldCol + " **" + "\n** USER SANK A SHIP **";
            	}
            }else if(opp.ship3.contains(""+row+col)){
            	opp.ship3.remove(""+row+col);
            	if(opp.ship3.size() == 0){
            		return "** USER  HIT AT " + oldRow + oldCol + " **" + "\n** USER SANK A SHIP **";
            	}
            }
            
            return "** USER  HIT AT " + oldRow + oldCol + " **";
        }else
        {
            p.oppGrid.markMiss(row, col);
            opp.playerGrid.markMiss(row, col);
            return "** USER MISS AT " + oldRow + oldCol + " **";
        }
    }
    
    private static void setup(Player p) throws Exception{
        p.playerGrid.printShips();
        System.out.println();
        int counter = 1;
        int normCounter = 0;
        while (p.numOfShipsLeft() > 0)
        {
            for (Ship s: p.ships)
            {
                System.out.println("\nShip #" + counter + ": Length-" + s.getLength());
                int row = -1;
                int col = -1;
                int dir = -1;
                while(true)
                {
                    System.out.print("Type in row (A-F): ");
                    String userInputRow = reader.next();
                    userInputRow = userInputRow.toUpperCase();
                    row = convertLetterToInt(userInputRow);
                    
                    System.out.print("Type in column (1-6): ");
                    col = reader.nextInt();
                    col = convertUserColToProCol(col);
                    
                    System.out.print("Type in direction (0-H, 1-V): ");
                    dir = reader.nextInt();
                    
                    //System.out.println("DEBUG: " + row + col + dir);
                    
                    if (col >= 0 && col <= 6 && row != -1 && dir != -1) // Check valid input
                    {
                        if (!hasErrors(row, col, dir, p, normCounter)) // Check if errors will produce (out of bounds)
                        {
                            break;
                        }
                    }
    
                    System.out.println("Invalid location!");
                }
            
                //System.out.println("FURTHER DEBUG: row = " + row + "; col = " + col);
                System.out.println("Norm Counter!!");
                System.out.println(normCounter);
                p.ships[normCounter].setLocation(row, col);
                p.ships[normCounter].setDirection(dir);
                if(normCounter == 0){
                	p.ship1 = p.playerGrid.addShip(p.ships[normCounter]);
                }else if(normCounter == 1){
                	p.ship2 = p.playerGrid.addShip(p.ships[normCounter]);
                }else{
                	p.ship3 = p.playerGrid.addShip(p.ships[normCounter]);
                }
                
                p.playerGrid.printShips();
                System.out.println();
                System.out.println("You have " + p.numOfShipsLeft() + " remaining ships to place.");
                
                normCounter++;
                counter++;
            }
        }
    }

    private static void setupComputer(Player p)
    {
        System.out.println();
        int normCounter = 0;
        
        //Randomizer rand = new Randomizer();
        
        while (p.numOfShipsLeft() > 0){
            for (Ship s: p.ships){
            	System.out.println("Computer ship number!!");
            	System.out.println(normCounter);
                int row = Randomizer.nextInt(0, 5);
                int col = Randomizer.nextInt(0, 5);
                int dir = Randomizer.nextInt(0, 1);
                
                System.out.println("DEBUG IN Setup!: row-" + row + "; col-" + col + "; dir-" + dir);
                
                while (hasErrorsComp(row, col, dir, p, normCounter)) // while the random nums make error, start again
                {
                    row = Randomizer.nextInt(0, 5);
                    col = Randomizer.nextInt(0, 5);
                    dir = Randomizer.nextInt(0, 1);
                    //System.out.println("AGAIN-DEBUG: row-" + row + "; col-" + col + "; dir-" + dir);
                }
                
                //System.out.println("FURTHER DEBUG: row = " + row + "; col = " + col);
                p.ships[normCounter].setLocation(row, col);
                p.ships[normCounter].setDirection(dir);
                //p.playerGrid.addShip(p.ships[normCounter]);
                if(normCounter == 0){
                	p.ship1 = p.playerGrid.addShip(p.ships[normCounter]);
                }else if(normCounter == 1){
                	p.ship2 = p.playerGrid.addShip(p.ships[normCounter]);
                }else{
                	p.ship3 = p.playerGrid.addShip(p.ships[normCounter]);
                }
                
                normCounter++;
            }
        }
    }
    
    private static boolean hasErrors(int row, int col, int dir, Player p, int count)
    {
        //System.out.println("DEBUG: count arg is " + count);
        
        int length = p.ships[count].getLength();
        
        // Check if off grid - Horizontal
        if (dir == 0)
        {
            int checker = length + col;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 6)
            {
                System.out.println("SHIP DOES NOT FIT");
                return true;
            }
        }
        
        // Check if off grid - Vertical
        if (dir == 1) // VERTICAL
        {
            int checker = length + row;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 6)
            {
                System.out.println("SHIP DOES NOT FIT");
                return true;
            }
        }
            
        // Check if overlapping with another ship
        if (dir == 0) // Hortizontal
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = col; i < col+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(row, i))
                {
                    System.out.println("THERE IS ALREADY A SHIP AT THAT LOCATION");
                    return true;
                }
            }
        }
        else if (dir == 1) // Vertical
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = row; i < row+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(i, col))
                {
                    System.out.println("THERE IS ALREADY A SHIP AT THAT LOCATION");
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static boolean hasErrorsComp(int row, int col, int dir, Player p, int count)
    {
        //System.out.println("DEBUG: count arg is " + count);
        
        int length = p.ships[count].getLength();
        
        // Check if off grid - Horizontal
        if (dir == 0)
        {
            int checker = length + col;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 6)
            {
                return true;
            }
        }
        
        // Check if off grid - Vertical
        if (dir == 1) // VERTICAL
        {
            int checker = length + row;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 6)
            {
                return true;
            }
        }
            
        // Check if overlapping with another ship
        if (dir == 0) // Hortizontal
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = col; i < col+length; i++)
            {
                System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(row, i))
                {
                    return true;
                }
            }
        }
        else if (dir == 1) // Vertical
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = row; i < row+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(i, col))
                {
                    return true;
                }
            }
        }
        
        return false;
    }


    /*HELPER METHODS*/
    private static int convertLetterToInt(String val)
    {
        int toReturn = -1;
        switch (val)
        {
            case "A":   toReturn = 0;
                        break;
            case "B":   toReturn = 1;
                        break;
            case "C":   toReturn = 2;
                        break;
            case "D":   toReturn = 3;
                        break;
            case "E":   toReturn = 4;
                        break;
            case "F":   toReturn = 5;
                        break;
            case "G":   toReturn = 6;
                        break;
            case "H":   toReturn = 7;
                        break;
            case "I":   toReturn = 8;
                        break;
            case "J":   toReturn = 9;
                        break;
            default:    toReturn = -1;
                        break;
        }
        
        return toReturn;
    }
    
    private static String convertIntToLetter(int val)
    {
        String toReturn = "Z";
        switch (val)
        {
            case 0:   toReturn = "A";
                        break;
            case 1:   toReturn = "B";
                        break;
            case 2:   toReturn = "C";
                        break;
            case 3:   toReturn = "D";
                        break;
            case 4:   toReturn = "E";
                        break;
            case 5:   toReturn = "F";
                        break;
            case 6:   toReturn = "G";
                        break;
            case 7:   toReturn = "H";
                        break;
            case 8:   toReturn = "I";
                        break;
            case 9:   toReturn = "J";
                        break;
            default:    toReturn = "Z";
                        break;
        }
        
        return toReturn;
    }
    
    private static int convertUserColToProCol(int val)
    {
        int toReturn = -1;
        switch (val)
        {
            case 1:   toReturn = 0;
                        break;
            case 2:   toReturn = 1;
                        break;
            case 3:   toReturn = 2;
                        break;
            case 4:   toReturn = 3;
                        break;
            case 5:   toReturn = 4;
                        break;
            case 6:   toReturn = 5;
                        break;
            case 7:   toReturn = 6;
                        break;
            case 8:   toReturn = 7;
                        break;
            case 9:   toReturn = 8;
                        break;
            case 10:   toReturn = 9;
                        break;
            default:    toReturn = -1;
                        break;
        }
        
        return toReturn;
    }
    
    private static int convertCompColToRegular(int val)
    {
        int toReturn = -1;
        switch (val)
        {
            case 0:   toReturn = 1;
                        break;
            case 1:   toReturn = 2;
                        break;
            case 2:   toReturn = 3;
                        break;
            case 3:   toReturn = 4;
                        break;
            case 4:   toReturn = 5;
                        break;
            case 5:   toReturn = 6;
                        break;
            case 6:   toReturn = 7;
                        break;
            case 7:   toReturn = 8;
                        break;
            case 8:   toReturn = 9;
                        break;
            case 9:   toReturn = 10;
                        break;
            default:    toReturn = -1;
                        break;
        }
        
        return toReturn;
    }
}