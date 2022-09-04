// CS3 Spreadsheet Sheet class. Update this file with your own code.

public class Sheet {
	// TODO in checkpoint 1: Declare class constants for number of rows and columns
	private int cols = 12;
	private int rows = 20;

	// TODO in checkpoint 2: declare a field as a 2-dimensional array of Cell
	private Cell[][] sheet;
	
	public Sheet() {
		// TODO Implement this in checkpoint 2, to initialize the field to the proper
		// size 2-dimensional array of Cell, and set all elements to new Cell instances.
		sheet = new Cell[rows][cols];

		for(int row = 0; row < sheet.length; row++)
		{
			for(int col = 0; col < sheet[0].length; col++)
			{
				sheet[row][col] = new Cell();
			}
		}
	}

	// TODO: Add private methods for commands.  They should return strings.
	

	public String processCommand(String command) {
		// TODO Implement this in checkpoints 1, 2, and 3, creating and calling private methods as needed.
		// For checkpoint 1, if command is an empty string (length zero), then return an empty string result.
		// For checkpoint 2, handle these commands, see project specification for details, note you
		// also need to create and implement the SpreadsheetLocation and TextCell classes and use them here:
		//     cell inspection, example: A1
		//     assignment of string values, example: A1 = "Hello"
		//     clearing the entire sheet, example: clear
		//     clearing a single cell, example: clear A1
		// For checkpoint 3, handle these additional variations of the assignment command, see project specification for
		// details, note you also need to create/implement/use the DateCell, RealCell, and FormulaCell classes
		//     assignment of date value, example: A1 = 1/1/2014
		//     assignment of real values, example A1 = 5.2
		//     assignment of real formulas, examples A1 = ( A2 * 4 + A3 ) or A1 = ( sum A1-d4 )
		// Note this method should be complete in checkpoint 3.
		// For checkpoints 4 and the final project, all changes should be in the FormulaCell class, except
		// for any fixes to bugs in previous checkpoints and possibly extra credit features.

		
		String[] tokens = command.split(" ", 3);
		if (command.equals(""))
		{
			return "";
		}
		else if (tokens.length == 1)
		{
			if (tokens[0].toUpperCase().equals("QUIT"))
			{
				return null;
			}
			else if (tokens[0].toUpperCase().equals("CLEAR"))
			{
				for(int row = 0; row < sheet.length; row++)
				{
					for(int col = 0; col < sheet[0].length; col++)
					{
						sheet[row][col] = new Cell();
					}
				}

				return toString();
			}
			else if ("ABCEDFGHIJKL".indexOf(tokens[0].substring(0,1)) != -1 || "abcdefghijkl".indexOf(tokens[0].substring(0, 1)) != -1)
			{
				String rowString = tokens[0].substring(1);
				int row = Integer.parseInt(rowString) - 1;	
				int col = "ABCDEFGHIJKL".indexOf(tokens[0].substring(0, 1).toUpperCase());	
				return sheet[row][col].fullCellText();	
			}
		}
		else if (tokens.length == 2)
		{
			if (tokens[0].toUpperCase().equals("CLEAR"))
			{
				String rowString = tokens[1].substring(1);
				int row = Integer.parseInt(rowString) - 1;	
				int col = "ABCDEFGHIJKL".indexOf(tokens[1].substring(0, 1).toUpperCase());	
				sheet[row][col] = new Cell();
				return toString();
			}
		}
		else if (tokens.length == 3)
		{
			String rowString = tokens[0].substring(1);
			int row = Integer.parseInt(rowString) - 1;	
			int col = "ABCDEFGHIJKL".indexOf(tokens[0].substring(0, 1).toUpperCase());	

			if (tokens[2].substring(0, 1).equals("\""))
			{
				String str = tokens[2].replace("\"", "");
				sheet[row][col] = new TextCell(str);

			}
			else if (tokens[2].substring(0, 1).equals("("))
			{
				sheet[row][col] = new FormulaCell(tokens[2], this);
			}
			else if (tokens[2].contains("/"))
			{
				sheet[row][col] = new DateCell(tokens[2]);


			}
			else
			{
				sheet[row][col] = new RealCell(tokens[2]);
			}

			return toString();
		}
		

		return "";
	}

	public int getRows() {
		// TODO Implement this in checkpoint 1
		return rows;
	}

	public int getCols() {
		// TODO Implement this in checkpoint 1
		return cols;
	}

	public Cell getCell(Location loc) {
		// TODO Implement this in checkpoint 2
		return sheet[loc.getRow()][loc.getCol()];
	}

	// TODO: override the toString method to return a friendly representation of the sheet
	public String toString()
	{
		String print = "   ";
		String letters = "ABCDEFGHIJKL";

		for(int i = 0; i < sheet[0].length; i++)
		{
			print = print + "|" + letters.substring(i, i+1) + "         ";
		}
		print = print + "|" + "\n";



		for(int row = 0; row < sheet.length; row++)
		{
			if (row < 9)
				print = print + (row + 1) + "  ";
			else
				print = print + (row + 1) + " ";
			for(int col = 0; col < sheet[0].length; col++)
			{
				print = print + "|" + sheet[row][col].abbreviatedCellText();
			}
			print = print + "|" + "\n";
		}

		return print;
	}
	
}
