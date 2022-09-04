// CS3 Spreadsheet FormulaCell class.  Fill in the details.
import java.util.*;

public class FormulaCell extends RealCell {
	private Sheet sheet; // needed to find values of cells in the formula
	
	public FormulaCell(String str, Sheet sheetIn) {
		super(str);
		this.sheet = sheetIn;
	}

	// TODO: add private methods as needed
	private double getSum(String in)
	{
		String[] sumTokens = in.split("-");
		Location loc1 = new SheetLocation(sumTokens[0]);
		Location loc2 = new SheetLocation(sumTokens[1]);
		double sum = 0.0;

		for (int row = loc1.getRow(); row <= loc2.getRow(); row++)
		{
			for (int col = loc1.getCol(); col <= loc2.getCol(); col++)
			{
				Location tempLoc = new SheetLocation(row, col);
				RealCell tempCell = (RealCell) sheet.getCell(tempLoc);
				double tempVal = tempCell.getDoubleValue();
				sum = sum + tempVal;
				
			}
		}

		return sum;
	}

	private double getAverage(String in)
	{
		String[] avgTokens = in.split("-");
		Location loc1 = new SheetLocation(avgTokens[0]);
		Location loc2 = new SheetLocation(avgTokens[1]);
		double sum = 0.0;
		int count = 0;

		for (int row = loc1.getRow(); row <= loc2.getRow(); row++)
		{
			for (int col = loc1.getCol(); col <= loc2.getCol(); col++)
			{
				Location tempLoc = new SheetLocation(row, col);
				RealCell tempCell = (RealCell) sheet.getCell(tempLoc);
				double tempVal = tempCell.getDoubleValue();
				sum = sum + tempVal;
				count++;
				
			}
		}

		return sum/count;
	}

	private double parseFormula(ArrayList<String> in)
	{
		
		//remove outer parens, transfer into arraylist
		ArrayList<String> formula = new ArrayList<String>();

		for(int i = 1; i < in.size() - 1; i++)
		{
			formula.add(in.get(i));
		}

		while(formula.size() > 1)
		{
			//* and / precedence
			int xCount = 0;
			for(int i = 0; i < formula.size(); i++)
			{
				if(formula.get(i).equals("*") || formula.get(i).equals("/"))
				{
					xCount++;
				}
			}
			if (xCount > 0)
			{
				for(int i = 0; i < formula.size(); i++)
				{
					if(formula.get(i).equals("*"))
					{
						double tempFormVal1 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i-1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i-1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal1 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal1 = Double.parseDouble(formula.get(i-1));
						}
						double tempFormVal2 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i+1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i+1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal2 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal2 = Double.parseDouble(formula.get(i+1));
						}
						double tempFormValSum = tempFormVal1 * tempFormVal2;
						formula.set(i-1, tempFormValSum + "");
						formula.remove(i);
						formula.remove(i);
						i--;
					}
					else if (formula.get(i).equals("/"))
					{
						double tempFormVal1 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i-1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i-1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal1 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal1 = Double.parseDouble(formula.get(i-1));
						}
						double tempFormVal2 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i+1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i+1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal2 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal2 = Double.parseDouble(formula.get(i+1));
						}
						double tempFormValSum = tempFormVal1 / tempFormVal2;
						formula.set(i-1, tempFormValSum + "");
						formula.remove(i);
						formula.remove(i);
						i--;
					}
				}
			}

			//addition and subtraction

			for(int i = 0; i < formula.size(); i++)
			{
				if(formula.get(i).equals("+"))
				{
					double tempFormVal1 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i-1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i-1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal1 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal1 = Double.parseDouble(formula.get(i-1));
						}
					double tempFormVal2 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i+1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i+1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal2 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal2 = Double.parseDouble(formula.get(i+1));
						}
					double tempFormValSum = tempFormVal1 + tempFormVal2;
					formula.set(i-1, tempFormValSum + "");
					formula.remove(i);
					formula.remove(i);
					i--;
				}
				else if(formula.get(i).equals("-"))
				{
					double tempFormVal1 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i-1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i-1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal1 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal1 = Double.parseDouble(formula.get(i-1));
						}
					double tempFormVal2 = 0.0;
						if ("ABCDEFGHIJKL".indexOf(formula.get(i+1).substring(0, 1).toUpperCase()) != -1)
						{
							Location loc = new SheetLocation(formula.get(i+1));
							RealCell realCell = (RealCell) sheet.getCell(loc);
							tempFormVal2 = realCell.getDoubleValue();
						}
						else
						{
							tempFormVal2 = Double.parseDouble(formula.get(i+1));
						}
					double tempFormValSum = tempFormVal1 - tempFormVal2;
					formula.set(i-1, tempFormValSum + "");
					formula.remove(i);
					formula.remove(i);
					i--;
				}
			}
		}

		return Double.parseDouble(formula.get(0));
	}

	private double parseFormulaWithParen(String[] in)
	{
		ArrayList<String> formFinal = new ArrayList<String>();
		for (int i = 0; i < in.length; i++)
		{
			formFinal.add(in[i]);
		}

		while(formFinal.size() > 1)
		{
			for(int i = 0; i < formFinal.size(); i++)
			{
				if (formFinal.get(i).equals(")"))
				{
					for(int j = i; j >= 0; j--)
					{
						if (formFinal.get(j).equals("("))
						{
							ArrayList<String> pass = new ArrayList<String>();
							double parse = 0.0;
							for (int k = j; k <= i; k++)
							{
								pass.add(formFinal.get(k));
							}
							parse = parseFormula(pass);
							formFinal.set(j, parse + "");
							int rem = j + 1;
							while (formFinal.get(rem).equals(")") == false)
							{
								formFinal.remove(rem);
							}
							formFinal.remove(rem);
							break;
						}
					}
				}
			}
		}

		return Double.parseDouble(formFinal.get(0));
	}

	// TODO: override the getDoubleValue() method
	
	public double getDoubleValue() {
		String[] formulaTokens = this.fullCellText().split(" ");
		if (formulaTokens[1].toUpperCase().equals("SUM"))
		{
			return getSum(formulaTokens[2]);
		}
		else if (formulaTokens[1].toUpperCase().equals("AVG"))
		{
			return getAverage(formulaTokens[2]);
		}
		else
		{

			return parseFormulaWithParen(formulaTokens); 

		} // TODO: change this to return this cell's value
	}
}
