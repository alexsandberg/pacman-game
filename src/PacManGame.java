/****
 * This program is a Pac-Man-style game where the user moves around a grid
 "eating" cookies.
 * Assignment: Mini Project 1
 * Author: Alex Sandberg-Bernard
 ****/


// import Scanner
import java.util.Scanner;

public class PacManGame
{

    /****
     * main() serves as the central method through which the flow of the program
     is directed and all other methods are integrated. This method accepts the
     initial user input for the dimensions of the PacMan grid, which is then
     passed to generateGrid() to generate the grid. main() also processes user
     input from the command menu and utilizes a switch to call the appropriate
     method, while using a do-while loop to ensure the validity of user input.
     *
     * Pre-conditions: java.util.Scanner has been imported
     *
     * Post-conditions: main() will accept user input for grid size and direct
     those inputs to the proper methods for creating a correctly sized game
     grid. main() will evaluate user input in the command menu and call the
     correct methods for executing the corresponding commands. main() will
     calculate the number of cookies for a given grid (12% of grid size). main()
     will print statistics at the end of the game.
     ****/


    public static void main( String [] args )
    {
        // initiate new scanner for input
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println( "Pac-Man game" );
        System.out.println();

        // prompt user to specify grid size X and Y
        System.out.println( "What size grid would you like to display " +
                "(X x Y)? First enter X (one number): " );
        int numX = input.nextInt();
        System.out.println( "Now enter Y (one number): " );
        int numY = input.nextInt();

        // display command menu
        displayCommandMenu();

        // generate starting game array
        String[] gameArray =  generateGrid( numX, numY );

        // calculate number of cookies for grid
        int cookiesNum = (int) ( gameArray.length * 0.12 );

        // cookies remaining after each move
        double cookiesRemaining = 0;

        // display game grid
        gameDisplay( numX, numY, gameArray );

        // variable for user command
        int commandChoice = -1;

        // variable for storing user moves
        int moveCounter = 0;

        // use do while loop to validate user command choice
        do
        {
            // prompt user to enter command
            System.out.println( "\n" + "Enter number of desired command:" );
            commandChoice = input.nextInt();

            System.out.println();

            // use switch to direct program based on user input
            switch ( commandChoice )
            {
                case 1: // display command menu, game grid, move counter
                {
                    displayCommandMenu();
                    gameDisplay( numX, numY, gameArray );
                    System.out.println( "\n" + "Moves: " + moveCounter );
                    break;
                }

                case 2: // call method for turning left, then redisplay game
                {
                    turnLeft( gameArray );
                    displayCommandMenu();
                    gameDisplay( numX, numY, gameArray );
                    cookiesRemaining = cookiesRemaining( gameArray, cookiesNum,
                            cookiesRemaining ); // displays cookies remaining
                    System.out.println( "Moves: " + moveCounter );
                    break;
                }

                case 3: // call method for turning right, then redisplay game
                {
                    turnRight( gameArray );
                    displayCommandMenu();
                    gameDisplay( numX, numY, gameArray );
                    cookiesRemaining = cookiesRemaining( gameArray, cookiesNum,
                            cookiesRemaining );
                    System.out.println( "Moves: " + moveCounter );
                    break;
                }

                case 4: // call method for moving character, then redisplay game
                {
                    move( gameArray, numY, numX );
                    moveCounter++; // add 1 to moveCounter
                    displayCommandMenu();
                    gameDisplay( numX, numY, gameArray );

                    // calls method cookiesRemaining and stores value
                    cookiesRemaining = cookiesRemaining( gameArray, cookiesNum,
                            cookiesRemaining );
                    System.out.println( "Moves: " + moveCounter );
                    break;
                }

                case 5: // command for exiting game
                {
                    break;
                }

                default: // invalid entry
                {
                    System.out.println( "\n" + "Invalid entry." + "\n" );
                }
            }

            // if user gets all the cookies, game ends
            if ( cookiesRemaining == 0 )
            {
                commandChoice = 5;
            }

            // execute loop until user enters 4 to exit game
        } while ( !( commandChoice == 5 ) );

        // display statistics at end of game
        System.out.println( "\n" + "Game over! Results: " + "\n");
        System.out.println( "Total moves: " + moveCounter );

        // call statistics method to display average moves per cookie eaten
        System.out.println( "Average moves per cookie eaten: " +
                statistics( moveCounter, cookiesNum,
                        cookiesRemaining ) );

        System.out.println();
    }


    /****
     * The method cookiesRemaining() calculates the number of cookies remaining
     in the game grid at any given time. It accomplishes this by indexing
     gameArray[] and counting every match with the cookie symbol "O". The method
     also registers if a cookie has been eaten by comparing the amount of
     cookies in the array after the most recent move to the amount after the
     prior move.
     *
     * Pre-Conditions: gameArray[] has been correctly populated with cookies
     ("O"). int cookiesNum has been correctly calculated and reflects the
     initial amount of cookies at start of game. double cookiesRemaining
     accurately contains the amount of cookies remaining after prior move.
     *
     * Post-Conditions: cookiesRemaining() will return the correct amount of
     cookies remaining in gameArray after the method has been called. The method
     will also correctly recognize when a cookie has been eaten and will display
     cookie eaten message.
     ****/


    public static double cookiesRemaining( String[] gameArray, int cookiesNum,
                                           double cookiesRemaining)
    {
        // variable for counting cookies remaining
        double cookiesCounter = 0;

        // calculate cookies remaining after each move
        for ( int index = 0; index < gameArray.length; index++ )
        {
            // store each index position in tempString
            String tempString = gameArray[index];

            // if the position contains a cookie, add 1 to cookiesCounter
            if ( tempString.equals( "O" ) )
            {
                cookiesCounter++;
            }
        }

        /*
         cookiesRemaining is equal to the amount of cookies left after the
         previous move, so if it's less than cookiesCounter that means that one
         has been eaten, and it will display the "Cookie eaten!" message
          */
        if ( ( cookiesRemaining > cookiesCounter ) )
        {
            System.out.println( "\n" + "Cookie eaten!" );
        }

        // displays number of cookies eaten
        System.out.println( "\n" + "Cookies eaten: " +
                (int)( cookiesNum - cookiesCounter ) );

        // returns number of cookies remaining
        return cookiesCounter;
    }


    /****
     * The method statistics() calculates the average moves per cookie eaten
     for use in the statistics display at the end of the game. It calculates
     this average by first calculating the number of cookies eaten and then
     dividing the moveCounter by cookiesEaten.
     *
     * Pre-Conditions: int moveCounter has correctly counted the number of moves
     that the user has made. int cookiesNum accurately reflects the amount of
     cookies present at the beginning of the game. double cookiesRemaining has
     been correctly calculated to reflect the amount of cookiesRemaining in the
     game, in order to calculate cookiesEaten.
     *
     * Post-Conditions: statistics() will correctly calculate the average number
     of moves per cookie eaten and will return the average as a double value.
     ****/


    public static double statistics( int moveCounter, int cookiesNum,
                                     double cookiesRemaining )
    {
        // variable for storing average moves per cookie
        double movesAverage = 0;

        // calculate number of cookies eaten
        double cookiesEaten = ( cookiesNum - cookiesRemaining );

        // prevents divide by 0 error
        if ( 0 < ( cookiesNum - cookiesRemaining ) )
        {
            // calculates average number of moves per cookie eaten
            movesAverage = ( moveCounter / cookiesEaten );
        }

        // return average
        return movesAverage;
    }


    /****
     * The turnLeft() method turns the Pac-Man symbol to the left. It does this
     by using a for loop to index the game grid (gameArray), searching for a
     match with any of the Pac-Man symbols (<, >, V, ^), only one of which will
     be present at any given time. When it finds a match, it replaces the
     present symbol with whichever symbol represents a 90° turn left, then
     returns the updated gameArray.
     *
     * Pre-Conditions: gameArray has been built correctly and contains exactly
     one of the four possible Pac-Man symbols.
     *
     * Post-Conditions: turnLeft() will correctly find and replace the present
     Pac-Man symbol with the symbol that represents a 90° left turn, then return
     the updated gameArray.
     ****/


    public static String[] turnLeft( String[] gameArray )
    {
        // use for loop to index entire array for PacMan symbols
        for ( int index = 0; index < gameArray.length; index++ )
        {
            // turn symbol left by replacing ">" with "^"
            if ( gameArray[index].equals( ">" ) )
            {
                gameArray[index] = gameArray[index].replace(">", "^");
                break;
            }

            // turn symbol left by replacing "^" with "<"
            if ( gameArray[index].equals( "^" ) )
            {
                gameArray[index] = gameArray[index].replace("^", "<");
                break;
            }

            // turn symbol left by replacing "<" with "V"
            if ( gameArray[index].equals( "<" ) )
            {
                gameArray[index] = gameArray[index].replace("<", "V");
                break;
            }

            // turn symbol left by replacing "V" with ">"
            if ( gameArray[index].equals( "V" ) )
            {
                gameArray[index] = gameArray[index].replace("V", ">");
                break;
            }
        }
        // return updated array
        return gameArray;
    }


    /****
     * The turnRight() method turns the Pac-Man symbol to the right. It does
     this by using a for loop to index the game grid (gameArray), searching for
     a match with any of the Pac-Man symbols (<, >, V, ^), only one of which
     will be present at any given time. When it finds a match, it replaces the
     present symbol with whichever symbol represents a 90° turn right, then
     returns the updated gameArray.
     *
     * Pre-Conditions: gameArray has been built correctly and contains exactly
     one of the four possible Pac-Man symbols.
     *
     * Post-Conditions: turnRight() will correctly find and replace the present
     Pac-Man symbol with the symbol that represents a 90° right turn, then
     return the updated gameArray.
     ****/


    public static String[] turnRight( String[] gameArray )
    {
        // use for loop to index entire array for PacMan symbols
        for ( int index = 0; index < gameArray.length; index++ )
        {
            // turn symbol right by replacing ">" with "V"
            if ( gameArray[index].equals( ">" ) )
            {
                gameArray[index] = gameArray[index].replace(">", "V");
                break;
            }

            // turn symbol right by replacing "V" with "<"
            if ( gameArray[index].equals( "V" ) )
            {
                gameArray[index] = gameArray[index].replace("V", "<");
                break;
            }

            // turn symbol right by replacing "<" with "^"
            if ( gameArray[index].equals( "<" ) )
            {
                gameArray[index] = gameArray[index].replace("<", "^");
                break;
            }

            // turn symbol right by replacing "^" with ">"
            if ( gameArray[index].equals( "^" ) )
            {
                gameArray[index] = gameArray[index].replace("^", ">");
                break;
            }
        }
        // return updated array
        return gameArray;
    }


    /****
     * The move() method moves the Pac-Man symbol by one place in whichever
     direction the symbol is facing (i.e., "<" moves to the right). The method
     accomplishes this by first indexing gameArray for a match with any of the
     four Pac-Man symbols (<, >, V, ^), only one of which will be present at any
     given time, then it replaces the current cell with an empty symbol (" "),
     then calculates the index value of the next cell over in the given
     direction, then replaces that cell with the same Pac-Man symbol (Pac-Man
     never turns). Finally, the updated gameArray is returned. Additionally, if
     the Pac-Man symbol is in a cell in the top or bottom row, or the far left
     or far right column, and the Pac-Man attempts to move in the direction of
     the game grid "wall", the Pac-Man will remain in place and will not "wrap
     around" or jump to any other place in the grid.
     *
     * Pre-Conditions: The game grid (gameArray) has been correctly formulated
     and contains exactly one of the four possible Pac-Man symbols. int numY and
     int numX contain the user input values corresponding to the dimensions of
     the game grid.
     *
     * Post-Conditions: move() will correctly move the Pac-Man symbol by one
     position in whichever direction the Pac-Man symbol is facing, by first
     finding the present symbol and then correctly calculating the position that
     represents a move of one in the correct direction. If the Pac-Man is up
     against one of the edges of the grid and attempts to move towards the edge,
     it will remain in place and will not jump to any other position in the
     grid. The correctly updated gameArray will be returned at the completion of
     the execution of the method.
     ****/


    public static String[] move( String[] gameArray, int numY, int numX )
    {
        // use for loop to search for PacMan symbol
        // movement actions determined by which symbol present and location
        for ( int index = 0; index < gameArray.length; index++ )
        {
            // only executes when Pac-Man symbol present is ">"
            if ( gameArray[index].equals( ">" ) )
            {
                // indexInt gives row number (1,2,3, etc.)
                int indexInt = ( (index / numX) + 1 );

                // if symbol is in far left column, Pac-Man doesn't move
                if ( index == ( ( numX * indexInt ) - numX ) )
                {
                }
                else // runs if symbol is anywhere except far left column
                {
                    gameArray[index] = " ";
                    index--;
                    gameArray[index] = ">";
                }
            }

            // only executes when Pac-Man symbol present is "V"
            if ( gameArray[index].equals( "V" ) )
            {
                // if symbol is in top row, Pac-Man doesn't move
                if ( index < numX)
                {
                }
                else // runs if symbol is anywhere aside from top row
                {
                    gameArray[index] = " ";
                    index -= numX;
                    gameArray[index] = "V";
                }
            }

            // only executes when Pac-Man symbol present is "<"
            if ( gameArray[index].equals( "<" ) )
            {
                // indexInt gives row number starting at 1 (1,2,3, etc.)
                int indexInt = ( (index / numX) + 1 );

                // if symbol is in far right column, Pac-Man doesn't move
                if ( index == ( ( numX * indexInt ) - 1 ) )
                {
                }
                else // runs if symbol is anywhere aside from far right column
                {
                    gameArray[index] = " ";
                    index++;
                    gameArray[index] = "<";
                }
            }

            // only executes when Pac-Man symbol present is "^"
            if ( gameArray[index].equals( "^" ) )
            {

                // if symbol is in bottom row, Pac-Man doesn't move
                if ( index >= ( ( numX * numY ) - numX ) )
                {
                }
                else // runs if symbol is anywhere but bottom row
                {
                    gameArray[index] = " ";
                    index += numX;
                    gameArray[index] = "^";
                }
            }
        }
        // return updated game array
        return gameArray;
    }


    /****
     * The gameDisplay() method prints the single dimensional array gameArray
     as a two-dimensional grid with the dimensions specified by user input. This
     is accomplished by using a set of for loops that iterate until the
     dimensions as specified by user input in numX and numY have been reached.
     *
     * Pre-Conditions: int numX and int numY correctly reflect the dimensions of
     the game grid as specified by user input. (numX * numY) is equal to the
     length of gameArray to ensure that every value in the array is printed.
     gameArray has been populated with gameplay symbols and has a length that
     has been correctly calculated to reflect user input.
     *
     * Post-Conditions: gameDisplay() will print each value of gameArray in the
     correct order and display the values in a grid with dimensions that match
     the dimensions specified by the user and stored in numX and numY.
     ****/


    public static void gameDisplay( int numX, int numY, String[] gameArray )
    {
        // variable for indexing value of gameArray with each loop iteration
        int counter = 0;

        // for loops print game array with dimensions based on user input
        for ( int indexY = 0; indexY<numY; indexY++ )
        {
            for ( int indexX = 0; indexX<numX;indexX++ )
            {
                // print each successive value of gameArray, then a space
                System.out.print(gameArray[counter] + " ");
                counter++;
            }
            System.out.println();
        }
    }


    /****
     * The displayCommandMenu() method displays the list of command options that
     the user can enter, with a short description of the command function and
     the corresponding number to be entered in order to execute that function.
     *
     * Pre-Conditions: displayCommandMenu() is called elsewhere and it is
     understood that the functionality of these commands in not controlled by
     this method. This method merely prints the menu options.
     *
     * Post-Conditions: The list of commands is printed each time the method is
     called, with each command option printed on a separate line and a blank
     line printed both prior to and following the list of commands.
     ****/


    public static void displayCommandMenu()
    {
        // menu of commands is displayed, one command per line
        System.out.println();
        System.out.println( "Commands:" );
        System.out.println( "Display menu of commands (1): " );
        System.out.println( "Turn left (2): " );
        System.out.println( "Turn right (3): " );
        System.out.println( "Move (4): " );
        System.out.println( "Exit game (5): " );
        System.out.println();
    }


    /****
     * The generateGrid() method generates the initial gameplay grid following
     user input of size specifications. The size of the grid is calculated based
     on the user input dimensions (numX and numY), then 12% of the grid is
     randomly filled with cookie symbols ("O"), then the remaining grid cells
     are filled with ".", and finally a left-facing Pac-Man symbol is added in
     index position 0. This method is called at the beginning of the game and
     establishes the starting grid which will successively be updated throughout
     the game using other methods. This method ensures that 12% of the array
     cells are filled with cookies by adding an extra cookie at a new random
     index location any time a random number is called more than once.
     *
     * Pre-Conditions: int numX and int numY reflect the desired dimensions of
     the game grid as specified by user input.
     *
     * Post-Conditions: generateGrid() will generate and return an array with
     length as determined by user input values. The array will be filled with
     12% cookies (rounded down), a left-facing (">") Pac-Man symbol in the first
     array position (index 0), and all other cells filled with ".".
     ****/


    public static String[] generateGrid( int numX, int numY )
    {
        // calculate total grid size
        int gridSize = numX * numY;

        // create array with size based on user input
        String[] pacmanGrid = new String[gridSize];

        // calculate number of cookies for grid
        int cookiesNum = (int) ( gridSize * 0.12 );

        // fill string array with ""
        for ( int index = 0; index <gridSize; index ++ )
        {
            pacmanGrid[index] = "";
        }

        // randomly determine position of indexes for 'O' and fill array
        // execute loop until number of cookies has been reached
        for ( int index = 0; index < cookiesNum; index++ )
        {
            // generate random number between 1 and gridSize-1 (index value)
            int randomInt = (int)(Math.random() * (gridSize-1) + 1);

            // add extra cookie in case of repeat random number
            if ( pacmanGrid[randomInt].equals( "O" ) )
            {
                cookiesNum++;
            }

            // fill gridSizeArray at random positions with cookie symbol
            pacmanGrid[randomInt] = "O";
        }

        // fill all other positions with '.'
        for ( int index = 0; index < gridSize; index++ )
        {
            if ( ( pacmanGrid[index].equals("") ) )
            {
                pacmanGrid[index] = ".";
            }
        }

        // fill position 0 with pacman symbol
        pacmanGrid[0] = ">";

        // return game array
        return (pacmanGrid);
    }
}
