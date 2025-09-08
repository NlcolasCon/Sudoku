
/**
* Author: Nicolas Constantinou
* Written: 4/11/2023
* Last updated: 14/11/2023
*
* Compilation: javac -cp C:/Users/cnico/eclipse-workspace/HMW2/src/stdlib.jar Board.java UserChoice.java Sudoku.java
* Execution: java -cp C:/Users/cnico/eclipse-workspace/HMW2/src/stdlib.jar Sudoku <N> <game-file>
* 
* The Sudoku game initialises through objects a board and through console input and another object it
* initialises the variables used to play and change the board. The program constantly checks whether
* the commands are correct in format, follow the rules of the sudoku game and whether the puzzle
* is solved, valid or to be saved. If the user manages to use the correct format to fill the board with
* the correct set of numbers and fills each box, rule and row with different numbers, then the puzzled is completed!
*
*/

public class Sudoku {
	
	public static void main ( String args[] ) {
				
		if( checks (args) == true ) {					//if args.length and args variables are correct then puzzle gets initialised
			
			Board sudoku = new Board ();
			if ( args.length != 0 ) {
				int N = Integer.parseInt(args[0]);
				String file = args[1];
				sudoku = new Board (N, file);			//Initialising board through an object variable type Board
				sudoku.readBoard();
			}
			if ( sudoku.checkValidity() ) {				//If board follows rules then game starts
				play(sudoku);
			}
			
		}
	}
		
	public static void play ( Board sudoku ) {
		
		boolean stop = false;
		while(!isSolution(sudoku) && !stop) {					//while user doesn't save and doesn't finish the game repeat
			String command = new String(getCommand(sudoku));	//input command
			UserChoice choice = new UserChoice(command);		
			getUserInput(choice);								//set the variables
			stop = playMove(choice,sudoku);						//Insert or clear a value
		}														//stop=true if the input is 0,0=0
		
		if(isSolution(sudoku)) {
			System.out.println("Game completed!!!");			//Winning credits if board is solved
			sudoku.displayBoard();
		}
		
	}
	//Inserts or clears a value from the board
	private static boolean playMove ( UserChoice move, Board sudoku) {
		if (move.getRow()==0 && move.getColumn() == 0 && move.getValue() ==0) {
			endOfGame(sudoku);
			return true;
		}
		else {
			for(int i=0; i<sudoku.getN(); i++) {
				for(int j=0; j<sudoku.getN(); j++) {
					if(move.getRow()-1==i && move.getColumn()-1==j && move.getValue()!=0) {
						sudoku.changeValue( move );
						System.out.println("Value inserted!");
					}
					else if(move.getRow()-1==i && move.getColumn()-1==j && move.getValue() == 0) {
						sudoku.changeValue( move );
						System.out.println("Value cleared!");
					}
				}
			}
		}
		return false;
		
	}
	
	private static String getCommand ( Board sudoku ) {
		sudoku.displayBoard();		//input the command
		commands(sudoku.getN());
		String input = commandCheck(sudoku,StdIn.readLine());
		while(!isValidMove(input,sudoku)) {		//while it has wrong format repeat input
			sudoku.displayBoard();
			commands(sudoku.getN());
			input = new String (commandCheck(sudoku,StdIn.readLine()));
		}
		while(!sudoku.checkValidity(Integer.parseInt(input.substring(0,1)), Integer.parseInt(input.substring(2,3)), Integer.parseInt(input.substring(4)))) {
			sudoku.displayBoard();
			commands(sudoku.getN());											//while the move is not following the rules
			input = new String (commandCheck(sudoku,StdIn.readLine()));			//repeat input
			while(!isValidMove(input,sudoku)) {
				sudoku.displayBoard();											//while it has wrong format repeat input
				commands(sudoku.getN());
				input = new String (commandCheck(sudoku,StdIn.readLine()));
			}
		}
		return input;	//in the end the input has correct format and is a valid move, following the rules of the game
	}
	//Checks whether format and length of command given is correct
	private static String commandCheck ( Board sudoku, String command ) {
		while(command.length()<5) {
			System.out.println("Error: wrong format of command!");
			sudoku.displayBoard();
			commands(sudoku.getN());
			command = new String (StdIn.readLine());
		}
		while(!(is_Digit(command.substring(0,1)) && is_Digit(command.substring(2,3)) && command.charAt(1) == ',' && command.charAt(3) == '=' && is_Digit(command.substring(4)))) {
			System.out.println("Error: wrong format of command!");
			sudoku.displayBoard();
			commands(sudoku.getN());
			command = new String (StdIn.readLine());
			while(command.length()<5) {
				System.out.println("Error: wrong format of command!");
				sudoku.displayBoard();
				commands(sudoku.getN());
				command = new String (StdIn.readLine());
			}
		}
		return command;
	}

	private static void getUserInput ( UserChoice move ) {
		move.setChoice();
	}
	//Checks if the command given is a valid move, meaning it has correct format
	private static boolean isValidMove ( String s, Board sudoku ) {
		
		if(!(is_Digit(s.substring(0,1)) && is_Digit(s.substring(2,3)) && s.charAt(1) == ',' && s.charAt(3) == '=' && is_Digit(s.substring(4)))){
			System.out.println("Error: wrong format of command!");
			return false;
		}
		if(Integer.parseInt(s.substring(0,1)) == 0 && Integer.parseInt(s.substring(2,3)) == 0 && Integer.parseInt(s.substring(4)) == 0){
			return true;
		}
		if(Integer.parseInt(s.substring(0,1)) <= 0 || Integer.parseInt(s.substring(0,1)) > sudoku.getN() ) {
			System.out.println("Error: i,j or val are outside the allowed range [1.." + sudoku.getN() + "]!");
			return false;
		}
		if(Integer.parseInt(s.substring(2,3)) <= 0 || Integer.parseInt(s.substring(2,3)) > sudoku.getN() ) {
			System.out.println("Error: i,j or val are outside the allowed range [1.." + sudoku.getN() + "]!");
			return false;
		}
		if(Integer.parseInt(s.substring(4)) < 0 || Integer.parseInt(s.substring(4)) > sudoku.getN() ) {
			System.out.println("Error: i,j or val are outside the allowed range [1.." + sudoku.getN() + "]!");
			return false;
		}
		if( sudoku.Occupied(s) && Integer.parseInt(s.substring(4)) != 0) {
				System.out.println("Error: cell is already occupied!");
				return false;
		}
		return true;
	}
	//Prints out the command for each turn the user makes plus in the start
	private static void commands ( int N ) {
		System.out.println("Enter your command in the following format:");
		System.out.println("+ i,j=val: for entering val at position (i,j)");
		System.out.println("+ i,j=0 : for clearing cell (i,j)");
		System.out.println("+ 0,0=0 : for saving and ending the game");
		System.out.println("Notice: i,j,val numbering is from [1.." + N + "]");
		System.out.print(">");
	}
	//Checks whether args.length is 2 and if each input is in correct format in order to play 
	public static boolean checks ( String args[] ) {
		if( args.length != 2 ) {
			System.out.println("Please give the dimension N followed by a <game-file> as the only 2 arguments");
			return false;				
		}
		if ( !is_Digit(args[0]) || is_Digit(args[1]) ) {
			System.out.println("Please give the dimension N followed by a <game-file> as the only 2 arguments");
			return false;
		}
		if ( args[0].charAt(0) != '4' && args[0].charAt(0) != '9' ) {
			System.out.println("The allowed value for N is either 4 or 9!\nPlease re run the program with a valid value for N");
			return false;			
		}
		int length = sudokuLength( args[1] );
		if ( length < Integer.parseInt(args[0]) ) {
			System.out.println("Error: Missing values from file!");
			return false;
		}
		else if ( length > Integer.parseInt(args[0]) ) {
			System.out.println("Error: Illegal number in input file!");
			return false;
		}
		return true;
	}
	//Finds length of file for beginning checks
	public static int sudokuLength ( String f ) {
		In file = new In (f);
		int length = 0;
		while ( file.hasNextLine() ) {
			file.readLine();
			length++;
		}
		file.close();
		return length;
	}
	//Checks if String represents a digit
	public static boolean is_Digit ( String st ) {
		for ( int i = 0; i < st.length(); i++ ) {
			if ( !Character.isDigit ( st.charAt(i) ) )
				return false;
		}
		return true;
	}	
	//Checks if board is the solution
	private static boolean isSolution ( Board sudoku ) {
		if( sudoku.checkValidity() && sudoku.boardFull() ) {
			return true;
		}
		return false;
	}
	//Saving of the file from this class
	private static void endOfGame (Board sudoku) {
		System.out.println("Saving game to out-" + sudoku.getFile());
		sudoku.saveFile();
	}

}