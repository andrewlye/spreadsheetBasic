// CS3 Spreadsheet SheetLocation class.  Fill in the details.

public class SheetLocation extends Location {
	
	public SheetLocation(int rowIn, int colIn) {
		super(rowIn, colIn);
	}

	// TODO: Add another constructor that takes a string like "B3" for row 2 column 1
	public SheetLocation(String str)
	{
		super(Integer.parseInt(str.substring(1)) - 1, "ABCDEFGHIJKL".indexOf(str.substring(0, 1).toUpperCase()));
		
		
	}
	
}
