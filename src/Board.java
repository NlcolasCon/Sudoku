
public class Board {

	private int N;
	private String file;
	private int tableau[][] = new int[100][100];
	
	//Constructor
	public Board (int a0, String a1){
		this.N = a0;
		this.file = a1;
		this.tableau = new int [this.N][this.N];
	}
	//Default Constructor
	public Board () {
		this.N = 9;               
		this.tableau = new int [this.N][this.N];
	}
	//Getter function for N
	public int getN () { 
		return this.N;
	}
	//Getter function for file
	public String getFile () {
		return this.file;
	}
	//check if cell has a number other than 0, so if it is occupied and can't be changed without getting deleted first
	public boolean Occupied ( String s ) {
		if(tableau[(Integer.parseInt(s.substring(0,1)))-1][(Integer.parseInt(s.substring(2,3)))-1] != 0) {
			return true;
		}
		return false;
	}
	//assigns a new value on the sudoku board
	public int changeValue ( UserChoice move ) {
		tableau[move.getRow()-1][move.getColumn()-1] = move.getValue();
		return tableau[move.getRow()-1][move.getColumn()-1];
	}
	//Setter function to read the board
	public void readBoard () {
		In ifile = new In (this.file);
		for ( int i = 0; i < this.N; i++ ) {
			for ( int j = 0; j < this.N; j++) {
				String s = new String (ifile.readString());
				this.tableau[i][j] = Integer.parseInt(s);
			}
		}
		ifile.close();
	}
	//to String method to save a file with correct format
	public String toString () {
		String st = "";
		for(int i = 0; i < this.N; i++ ) {
			for(int j = 0; j < this.N; j++) {
				st+=this.tableau[i][j]+" ";
			}
			if(i!=this.N-1) {
				st+="\n";
			}	
		}
		return st;
	}
	//Checks if sudoku is valid rule-wise, at the start of every game
	public boolean checkValidity () {
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				int trial = this.tableau[i][j];
				int count = 0;
				for (int k = 0; k < this.N; k++) {
					if(trial == this.tableau[i][k] && trial != 0) {
						count++;
					}
				}
				if(count>1) {
					System.out.println("Error: This is not a valid sudoku! Same row rule not met!");
					return false;
				}
			}	
		}
		
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				int trial = this.tableau[j][i];
				int count = 0;
				for (int k = 0; k < this.N; k++) {
					if(trial == this.tableau[k][i] && trial != 0) {
						count++;
					}
				}
				if( count > 1 ) {
					System.out.println("Error: This is not a valid sudoku! Same column rule not met!");
					return false;
				}
			}	
		}
		
		for ( int boxRow = 0; boxRow < this.N; boxRow += Math.sqrt(this.N) ) {
			for ( int boxCol = 0; boxCol < this.N; boxCol += Math.sqrt(this.N) ) {
				for ( int i = boxRow; i < Math.sqrt(this.N)+boxRow; i++ ) {
					for ( int j = boxCol; j < Math.sqrt(this.N)+boxCol; j++ ) {
						int testing = this.tableau[i][j];
						int count = 0;
						for ( int row = boxRow; row < Math.sqrt(this.N)+boxRow; row++ ) {
							for ( int col = boxCol; col < Math.sqrt(this.N)+boxCol; col++ ) {
								if ( testing == this.tableau[row][col] && testing != 0) {
									count++;
								}
							}
						}
						if( count > 1 ) {
							System.out.println("Error: This is not a valid sudoku! Same box rule not met!");
							return false;
						}
					}
				}
			}
		
		}
		return true;
	}			
	//Checks if sudoku is valid rule-wise, before every move is made
	public boolean checkValidity (int row, int col, int val) {
		
		if( row == 0 && col == 0 && val == 0) {
			return true;
		}
		
		for (int i = 0; i < this.N; i++) {
			if(val == Math.abs(this.tableau[i][col-1]) && val!=0) {
				System.out.println("Error: This is not a valid sudoku! Same column rule not met!");
				return false;
			}
		}
		
		for (int i = 0; i < this.N; i++) {
			if(val == Math.abs(this.tableau[row-1][i]) && val!=0) {
				System.out.println("Error: This is not a valid sudoku! Same row rule not met!");
				return false;
			}
		}
				
		for ( int boxRow = 0; boxRow < this.N; boxRow += Math.sqrt(this.N) ) {
			for ( int boxCol = 0; boxCol < this.N; boxCol += Math.sqrt(this.N) ) {
				for ( int i = boxRow; i < Math.sqrt(this.N)+boxRow; i++ ) {
					for ( int j = boxCol; j < Math.sqrt(this.N)+boxCol; j++ ) {
						if( row-1 == i && col-1 == j ) {
							for ( int r = boxRow; r < Math.sqrt(this.N)+boxRow; r++ ) {
								for ( int c = boxCol; c < Math.sqrt(this.N)+boxCol; c++ ) {
									if ( val == Math.abs(this.tableau[r][c]) && val!=0 ) {
										System.out.println("Error: This is not a valid sudoku! Same box rule not met!");
										return false;
									}
								}
							}
							return true;
						}
					}
				}
			}
		}
		
		return true;
	}			
	//Saves file using Out Class of StdLib
	public void saveFile () {
		Out fout = new Out (this.file);
		fout.print(this);
		fout.close();
	}
	//Checks if board is full
	public boolean boardFull () {
		for(int i=0; i<this.N; i++) {
			for(int j =0; j<this.N; j++) {
				if(this.tableau[i][j]==0) {
					return false;
				}
			}
		}
		return true;
	}
	//Getter function to display in text-based form, the board using 6 static and non-static methods seen below
	public void displayBoard () {
		if ( this.N == 4 ) {
			printSudoku4();
		}	
		else if ( this.N == 9 ) {
			printSudoku9();
		}
	}

	private void printSudoku4 () {
		int row = 0;
		int col = 0;
		printLine4();
		this.printSudoku4pieces(row,col);
		row += 2;
		col = 0;
		printLine4();
		this.printSudoku4pieces(row,col);
		printLine4();
	}
	
	private static void printLine4 () {
		for( int i = 0; i <= 14; i++) {
			if( i % 7 == 0 ) {
				System.out.print("+");
			}
			else
				System.out.print("-");
		}
		System.out.println();
	}
	
	private void printSudoku4pieces ( int row, int col ) {
		for( int i = 0; i <= 1; i++ ) {
			for( int j = 0; j <= 14; j++ ) {	
				if( j % 7 == 0 ) {
					System.out.print("|");
				}
				else if ( col < 4 ) {
					if ( tableau[row][col] == 0 ) {
						System.out.print(" . ");
						col++;
						j+=2;
					}
					else if ( tableau[row][col] >= 1 && tableau[row][col] <= 4 ) {
						System.out.print(" " + tableau[row][col] + " ");
						col++;
						j+=2;
					}
					else if ( tableau[row][col] <= -1 && tableau[row][col] >= -4 ) {
						System.out.print("(" + Math.abs(tableau[row][col]) + ")");
						col++;
						j+=2;
					}
				}
			}
			row++;
			col=0;
			System.out.println();
		}
	}

	private void printSudoku9 () {
		int row = 0;
		int col = 0;
		printLine9();
		this.printSudoku9pieces(row,col);
		row = 3;
		col = 0;
		printLine9();
		this.printSudoku9pieces(row,col);
		row = 6;
		col = 0;
		printLine9();
		this.printSudoku9pieces(row,col);
		printLine9();
	}

	private static void printLine9 () {
		for( int i = 0; i <= 30; i++) {
			if( i % 10 == 0 ) {
				System.out.print("+");
			}
			else
				System.out.print("-");
		}
		System.out.println();
	}

	private void printSudoku9pieces ( int row, int col ) {
		for(int i = 0; i <= 2; i++) {
			for(int j = 0; j<= 30; j++) {
				if(j%10==0) {
					System.out.print("|");
				}
				else if ( col < 9 ) {
					if ( tableau[row][col] == 0 ) {
						System.out.print(" . ");
						col++;
						j+=2;
					}
					else if ( tableau[row][col] >= 1 && tableau[row][col] <= 9 ) {
						System.out.print( " " + tableau[row][col] + " " );
						col++;
						j+=2;
					}
					else if ( tableau[row][col] <= -1 && tableau[row][col] >= -9 ) {
						System.out.print( "(" + Math.abs(tableau[row][col]) + ")" );
						col++;
						j+=2;
					}
				}	
			}
		row++;
		col=0;
		System.out.println();
		}
	}
		
}
