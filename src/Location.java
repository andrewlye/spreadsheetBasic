// CS3 Spreadsheet Location class.  Do not modify this file.

public class Location {
	private int row;
	private int col;
	public Location(int r, int c) {
		row = r;
		col = c;
	}
	public int getRow() { return row; } // gets row of this location (starts at 0, so 5 for B6)
	public int getCol() { return col; } // gets column of this location (starts at 0, so 1 for B6)
}
