
// CS3 Spreadsheet DateCell class.  Fill in the details.

public class DateCell extends Cell {
	// TODO: add a private attribute
	private String fulltext;

	// TODO: add a constructor that initializes the attribute
	public DateCell(String str)
	{
		fulltext = str;
	}

	// TODO: override the abbreviatedCellText method
	public String abbreviatedCellText() {
		String[] dateTokens = fulltext.split("/");
		String ret = "";
		for (int i = 0; i < dateTokens.length; i++)
		{
			if (dateTokens[i].length() == 1 && i != 2)
			{
				dateTokens[i] = "0" + dateTokens[i];

			}
			else if (dateTokens[i].length() < 4 && i == 2)
			{
				String format = "";
				for(int j = 0; j < 4 - dateTokens[i].length(); j++)
				{
					format = format + "0";
				}
				dateTokens[i] = format + dateTokens[i];
			}
		}
		ret = dateTokens[0] + "/" + dateTokens[1] + "/" + dateTokens[2];
		if (ret.length() > 10)
		{
			ret = ret.substring(0, 10);
		}
		return ret; 
	}

	// TODO: override the fullCellText method

	public String fullCellText() {
		return fulltext;
	}

	
}
