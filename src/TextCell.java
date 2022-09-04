import javax.print.attribute.TextSyntax;

// CS3 Spreadsheet TextCell class.

public class TextCell extends Cell {

	// TODO: declare a private attribute of type String
	private String fulltext;

	// TODO: add a constructor
	public TextCell(String str)
	{
		this.fulltext = str;
	}

	/** Does not include enclosing quotes */
	@Override
	public String abbreviatedCellText() {
		String ret = "";
		if (fulltext.length() > 10)
		{
			for (int i = 0; i < 10; i++)
			{
				ret = ret + fulltext.substring(i, i+1);
			}
		}

		else
		{
			ret = fulltext;
			for (int i = 0; i < 10 - fulltext.length(); i++)
			{
				ret = ret + " ";
			}

		}

		return ret; // TODO: Change this
	}

	/** Includes enclosing quotes */
	@Override
	public String fullCellText() {
		return "\"" + fulltext + "\""; // TODO: Change this
	}
}
