// CS3 Spreadsheet RealCell class.  Fill in the details.

public class RealCell extends Cell {
	private String fullText; // Stores full text of this cell
	
	public RealCell(String str) {
		this.fullText = str;
	}

	public double getDoubleValue() {
		return Double.parseDouble(fullText); // TODO: change this to return this cell's value
	}
	
	@Override
	public String abbreviatedCellText() {
		String text = "" + getDoubleValue();
		String ret = "";
		if (text.length() > 10)
		{
			for (int i = 0; i < 10; i++)
			{
				ret = ret + text.substring(i, i+1);
			}
		}
		else if (text.length() < 10)
		{
			ret = text;
			for (int i = 0; i < 10 - text.length(); i++)
			{
				ret = ret + " ";
			}
		}
		return ret; // TODO: change this to call getDoubleValue and format the result
	}

	@Override
	public String fullCellText() {

		return fullText;
	}
}
