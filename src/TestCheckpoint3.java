// CS3 Spreadsheet TestCheckpoint3 class.  Do not modify this file.
// Version 1.1

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestCheckpoint3 {
    // Tests for checkpoint 3.
    // Note these must also pass for all subsequent checkpoints including
    // final project.
    Sheet grid;

    @Before
    public void initializeGrid() {
        grid = new Sheet();
    }

    @Test
    public void testDateCell() {
        String date = "11/25/1964";
        grid.processCommand("A1 = " + date);
        Cell dateCell = grid.getCell(new Location(0, 0));
        assertEquals("date cell text", TestHelper.format(date),
                dateCell.abbreviatedCellText());
        assertEquals("date inspection text", date, dateCell.fullCellText());
    }

    @Test
    public void testBasicRealCell() {
        String real = "3.14";
        grid.processCommand("D18 = " + real);
        Cell realCell = grid.getCell(new Location(17, 3));
        assertEquals("real cell text", TestHelper.format(real),
                realCell.abbreviatedCellText());
        assertEquals("real inspection text", real, realCell.fullCellText());
    }

    @Test
    public void testMoreRealCells() {
        String zero = "0.0";
        grid.processCommand("A1 = " + zero);
        Cell zeroCell = grid.getCell(new Location(0, 0));
        assertEquals("real cell 0", TestHelper.format(zero),
                zeroCell.abbreviatedCellText());
        assertEquals("real inspection 0", zero, zeroCell.fullCellText());
        String negativeTwo = "-2.0";
        grid.processCommand("B1 = " + negativeTwo);
        Cell negativeTwoCell = grid.getCell(new Location(0, 1));
        assertEquals("real cell -2", TestHelper.format(negativeTwo),
                negativeTwoCell.abbreviatedCellText());
        assertEquals("real inspection -2", negativeTwo,
                negativeTwoCell.fullCellText());
    }

    @Test
    public void testDifferentCellTypes() {
        grid.processCommand("H4 = 12/28/1998");
        grid.processCommand("G3 = \"5\"");
        grid.processCommand("F2 = -123.456");
        Cell dateCell = grid.getCell(new Location(3, 7));
        Cell stringCell = grid.getCell(new Location(2, 6));
        Cell realCell = grid.getCell(new Location(1, 5));
        Cell emptyCell = grid.getCell(new Location(0, 4));
        Cell[] differentCells = { dateCell, stringCell, realCell, emptyCell };
        for (int i = 0; i < differentCells.length - 1; i++)
            for (int j = i + 1; j < differentCells.length; j++) {
                assertTrue(
                        "date, string, real, empty cells must all have different class types",
                        !differentCells[i].getClass().equals(
                                differentCells[j].getClass()));
            }
    }

    @Test
    public void testFormulaAssignment() {
        String formula1 = "( 4 * 5.5 / 2 + 1 - -11.5 )";
        grid.processCommand("K9 = " + formula1);
        Cell cell1 = grid.getCell(new Location(8, 10));
        assertEquals("cell length 1", 10, cell1.abbreviatedCellText()
                .length());
        assertEquals("inspection 1", formula1, cell1.fullCellText());
    }

    @Test
    public void testProcessCommand() {
        TestHelper helper = new TestHelper();
        String first = grid.processCommand("A1 = 01/02/1822");
        helper.setItem(0, 0, "01/02/1822");
        assertEquals("grid with date", helper.getText(), first);
        String second = grid.processCommand("B2 = -5.0");
        helper.setItem(1, 1, "-5.0");
        assertEquals("grid with date and number", helper.getText(), second);
        String third = grid.processCommand("C3 = 2.718");
        helper.setItem(2, 2, "2.718");
        assertEquals("grid with date and two numbers", helper.getText(),
                third);
        String fourth = grid.processCommand("D4 = 0.0");
        helper.setItem(3, 3, "0.0");
        assertEquals("grid with date and three numbers", helper.getText(),
                fourth);
    }

    @Test
    public void testShortDateCell() {
        // NOTE spec not totally consistent on inspection format, allow
        // either as entered or mm/dd/yyyy
        String[] datesEntered = { "1/2/1934", "7/15/2004", "11/6/2011" };
        String[] datesFormatted = { "01/02/1934", "07/15/2004",
                "11/06/2011" };
        TestHelper helper = new TestHelper();
        for (int col = 0; col < datesEntered.length; col++) {
            for (int row = 6; row < 20; row += 10) {
                String cellName = Character.toString((char) ('a' + col))
                        + (row + 1);
                String sheet = grid.processCommand(cellName + " = "
                        + datesEntered[col]);
                helper.setItem(row, col, datesFormatted[col]);
                assertEquals("sheet after setting cell " + cellName,
                        helper.getText(), sheet);
                String inspected = grid.getCell(new Location(row, col))
                        .fullCellText();
                assertTrue(
                        "inspected " + cellName
                                + " either as entered or formatted",
                        inspected.equals(datesEntered[col])
                                || inspected.equals(datesFormatted[col]));
            }
        }
        assertEquals("final sheet", helper.getText(), grid.toString());
    }

    @Test
    public void testRealCellFormat() {
        // NOTE spec not totally consistent on inspection format, allow
        // anything that parses to within epsilon of as entered
        String[] realsEntered = { "3.00", "-74.05000", "400.0" };
        String[] realsFormatted = { "3.0       ", "-74.05    ",
                "400.0     " };
        TestHelper helper = new TestHelper();
        for (int col = 0; col < realsEntered.length; col++) {
            for (int row = 6; row < 20; row += 10) {
                String cellName = Character.toString((char) ('A' + col))
                        + (row + 1);
                String sheet = grid.processCommand(cellName + " = "
                        + realsEntered[col]);
                helper.setItem(row, col, realsFormatted[col]);
                assertEquals("sheet after setting cell " + cellName,
                        helper.getText(), sheet);
                String inspected = grid.getCell(new Location(row, col))
                        .fullCellText();
                double expected = Double.parseDouble(realsEntered[col]);
                double actual = Double.parseDouble(inspected);
                assertEquals("inspected real value", actual, expected,
                        TestHelper.epsilon);
            }
        }
        assertEquals("final sheet", helper.getText(), grid.toString());
    }

    @Test
    public void testRealCellTruncation() {
        String big = "-9876543212345";
        grid.processCommand("A1 = " + big);
        Cell bigCell = grid.getCell(new Location(0, 0));
        assertEquals("real big cell length", 10, bigCell
                .abbreviatedCellText().length());
        assertEquals("real big inspection ", Double.parseDouble(big),
                Double.parseDouble(bigCell.fullCellText()), TestHelper.epsilon);
        String precise = "3.14159265358979";
        grid.processCommand("A2 = " + precise);
        Cell preciseCell = grid.getCell(new Location(1, 0));
        assertEquals("real precise cell length", 10, preciseCell
                .abbreviatedCellText().length());
        assertEquals("real precise cell", Double.parseDouble(precise),
                Double.parseDouble(preciseCell.abbreviatedCellText()),
                TestHelper.epsilon);
        assertEquals("real precise inspection ",
                Double.parseDouble(precise),
                Double.parseDouble(preciseCell.fullCellText()),
                TestHelper.epsilon);
        String moderate = "123456.0";
        grid.processCommand("A3 = " + moderate);
        Cell moderateCell = grid.getCell(new Location(2, 0));
        assertEquals("real moderate cell length", 10, moderateCell
                .abbreviatedCellText().length());
        assertEquals("real moderate cell", moderate, moderateCell
                .abbreviatedCellText().trim());
        assertEquals("real moderate inspection", moderate,
                moderateCell.fullCellText());
    }

}
