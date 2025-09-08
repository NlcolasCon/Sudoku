
public class UserChoice {

	private int i;
	private int j;
	private int val;
	private String command;
	//Initialises the variables with 0 and initialises the command variable with he given command from the input
	public UserChoice ( String s ){
		this.i = 0;
		this.j = 0;
		this.val = 0;
		this.command = new String(s);
	}
	//sets the coordinates and the variable to-be-set for the board
	public void setChoice () {
		this.i = Integer.parseInt(command.substring(0,1));
		this.j = Integer.parseInt(command.substring(2,3));
		this.val = Integer.parseInt(command.substring(4));
	}
	//Getter function for Row
	public int getRow () {
		return this.i;
	}
	//Getter function for Column
	public int getColumn () {
		return this.j;
	}
	//Getter function for Value
	public int getValue () {
		return this.val;
	}
	
}
