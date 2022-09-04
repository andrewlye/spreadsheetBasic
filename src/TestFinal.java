// CS3 Spreadsheet TestFinal class.  Do not modify this file.
// Version 1.1

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestFinal {
    // Additional tests for final project.
    Sheet grid;

    @Before
    public void initializeGrid() {
        grid = new Sheet();
    }

    @Test
    public void testSumSingle() {
        grid.processCommand("A15 = 37.05");
        grid.processCommand("A16 = ( SuM A15-A15 )");
        Cell cell = grid.getCell(new Location(15, 0));
        assertEquals("sum single cell", TestHelper.format("37.05"),
                cell.abbreviatedCellText());
    }

    @Test
    public void testAvgSingle() {
        grid.processCommand("A1 = -9");
        grid.processCommand("A2 = ( 3 * A1 )");
        grid.processCommand("B1 = ( avg A2-A2 )");
        Cell cell = grid.getCell(new Location(0, 1));
        assertEquals("avg single cell", TestHelper.format("-27.0"),
                cell.abbreviatedCellText());
    }

    @Test
    public void testVertical() {
        grid.processCommand("C3 = 1");
        grid.processCommand("C4 = ( C3 * 2 )"); // 2
        grid.processCommand("C5 = ( C4 - C3 )"); // 1
        grid.processCommand("C6 = ( 32 - C4 )"); // 30
        grid.processCommand("K20 = ( SUM c3-c6 )"); // 34
        grid.processCommand("L20 = ( avg C3-C6 )"); // 8.5
        Cell cellSum = grid.getCell(new Location(19, 10));
        Cell cellAvg = grid.getCell(new Location(19, 11));
        assertEquals("sum vertical", TestHelper.format("34.0"),
                cellSum.abbreviatedCellText());
        assertEquals("avg vertical", TestHelper.format("8.5"),
                cellAvg.abbreviatedCellText());
    }

    @Test
    public void testHorizontal() {
        grid.processCommand("F8 = 3");
        grid.processCommand("G8 = ( 5 )");
        grid.processCommand("H8 = ( -1 * F8 + G8 )"); // 2
        grid.processCommand("I8 = ( sum F8-H8 )"); // 10
        grid.processCommand("J8 = ( AVG F8-I8 )"); // 5
        Cell cellSum = grid.getCell(new Location(7, 8));
        Cell cellAvg = grid.getCell(new Location(7, 9));
        assertEquals("sum horizontal", TestHelper.format("10.0"),
                cellSum.abbreviatedCellText());
        assertEquals("avg horizontal", TestHelper.format("5.0"),
                cellAvg.abbreviatedCellText());
    }

    @Test
    public void testRectangular() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {
                String cellId = "" + (char) ('A' + j) + (i + 1);
                grid.processCommand(cellId + " = " + (i * j));
            }
        grid.processCommand("G8 = ( sum A1-E4 )");
        grid.processCommand("G9 = ( avg A1-E4 )");
        Cell cellSum = grid.getCell(new Location(7, 6));
        Cell cellAvg = grid.getCell(new Location(8, 6));
        assertEquals("sum rectangular", TestHelper.format("60.0"),
                cellSum.abbreviatedCellText());
        assertEquals("avg rectangular", TestHelper.format("3.0"),
                cellAvg.abbreviatedCellText());
    }

    @Test
    public void testProcessCommand() {
        TestHelper helper = new TestHelper();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {
                String cellId = "" + (char) ('A' + j) + (i + 1);
                grid.processCommand(cellId + " = " + (i * j));
                helper.setItem(i, j, "" + (i * j) + ".0");
            }
        String first = grid.processCommand("G8 = ( sum A1-E4 )");
        helper.setItem(7, 6, "60.0");
        assertEquals("grid with sum", helper.getText(), first);
        String second = grid.processCommand("G9 = ( avg A1-E4 )");
        helper.setItem(8, 6, "3.0");
        assertEquals("grid with sum and avg", helper.getText(), second);
        String updated = grid.processCommand("E4 = ( sum A4-D4 )");
        helper.setItem(3, 4, "18.0");
        helper.setItem(7, 6, "66.0");
        helper.setItem(8, 6, "3.3");
        assertEquals("updated grid", helper.getText(), updated);
    }

    @Test
    public void testSumSingleNegative() {
        grid.processCommand("A15 = -37.05");
        grid.processCommand("A16 = ( SuM A15-A15 )");
        Cell cell = grid.getCell(new Location(15, 0));
        assertEquals("sum single cell", TestHelper.format("-37.05"),
                cell.abbreviatedCellText());
    }

    @Test
    public void testAvgSingleNontrivial() {
        grid.processCommand("A1 = -9");
        grid.processCommand("A2 = ( 14 - 7 + -4 - 3 + 3 * A1 )");
        grid.processCommand("b1 = ( avG A2-a2 )");
        Cell cell = grid.getCell(new Location(0, 1));
        assertEquals("avg single cell", TestHelper.format("-27.0"),
                cell.abbreviatedCellText());
    }

    @Test
    public void testVerticalNontrivial() {
        grid.processCommand("C13 = 1.0");
        grid.processCommand("C14 = ( 7 + 2 - 3 + -6 + C13 * 2 )"); // 2
        grid.processCommand("C15 = ( C14 - C13 )"); // 1
        grid.processCommand("C16 = ( 32 - C14 )"); // 30
        grid.processCommand("K20 = ( SuM c13-C16 )"); // 34
        grid.processCommand("L20 = ( Avg c13-C16 )"); // 8.5
        Cell cellSum = grid.getCell(new Location(19, 10));
        Cell cellAvg = grid.getCell(new Location(19, 11));
        assertEquals("sum vertical", TestHelper.format("34.0"),
                cellSum.abbreviatedCellText());
        assertEquals("avg vertical", TestHelper.format("8.5"),
                cellAvg.abbreviatedCellText());
    }

    @Test
    public void testHorizontalNontrivial() {
        grid.processCommand("F8 = 3");
        grid.processCommand("G8 = ( 5 )");
        grid.processCommand("H8 = ( 2 * -3 + 4 - -2 + -1 * F8 + G8 )"); // 2
        grid.processCommand("I8 = ( sum F8-H8 )"); // 10
        grid.processCommand("J8 = ( AVG F8-I8 )"); // 5
        Cell cellSum = grid.getCell(new Location(7, 8));
        Cell cellAvg = grid.getCell(new Location(7, 9));
        assertEquals("sum horizontal", TestHelper.format("10.0"),
                cellSum.abbreviatedCellText());
        assertEquals("avg horizontal", TestHelper.format("5.0"),
                cellAvg.abbreviatedCellText());
    }

    @Test
    public void testRectangularNontrivial() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {
                String cellId = "" + (char) ('A' + j) + (i + 1);
                grid.processCommand(cellId + " = ( 3 * 2 - 4 + -2 + " + i
                        + " * " + j + " )");
            }
        grid.processCommand("G8 = ( sum A1-E4 )");
        grid.processCommand("G9 = ( avg A1-E4 )");
        Cell cellSum = grid.getCell(new Location(7, 6));
        Cell cellAvg = grid.getCell(new Location(8, 6));
        assertEquals("sum rectangular", TestHelper.format("60.0"),
                cellSum.abbreviatedCellText());
        assertEquals("avg rectangular", TestHelper.format("3.0"),
                cellAvg.abbreviatedCellText());
    }

    @Test
    public void testMultipleNesting() {
        grid.processCommand("A1 = ( 1 + 2 + 3 + 4 )"); // 10, then 9
        grid.processCommand("A2 = ( 1 * 2 * 3 * 4 )"); // 24
        grid.processCommand("B1 = ( Sum a1-a2 )"); // 34, then 33
        grid.processCommand("B2 = ( avG a1-A2 )"); // 17, then 16.5
        grid.processCommand("C1 = ( sum A1-B2 )"); // 85, then 82.5
        grid.processCommand("C2 = ( avg a1-b2 )"); // 21.25, then 20.625
        grid.processCommand("d1 = ( c1 / 5.0 )"); // 17, then 16.5
        grid.processCommand("d2 = ( c2 + 1.75 + a1 )"); // 33, then 31.375
        grid.processCommand("e2 = 18");
        grid.processCommand("d3 = 29");
        grid.processCommand("A20 = ( SUM A1-D2 )"); // 241.25, then 233.5
        grid.processCommand("B20 = ( AVG A1-D2 )"); // 30.15625, then
                                                    // 29.1875
        Cell cellSum = grid.getCell(new Location(19, 0));
        Cell cellAvg = grid.getCell(new Location(19, 1));
        double resultSum = Double
                .parseDouble(cellSum.abbreviatedCellText());
        double resultAvg = Double
                .parseDouble(cellAvg.abbreviatedCellText());
        assertEquals("sum nested", 241.25, resultSum, TestHelper.epsilon);
        assertEquals("avg nested", 30.15625, resultAvg, TestHelper.epsilon);
        grid.processCommand("a1 = 9");
        cellSum = grid.getCell(new Location(19, 0));
        cellAvg = grid.getCell(new Location(19, 1));
        resultSum = Double.parseDouble(cellSum.abbreviatedCellText());
        resultAvg = Double.parseDouble(cellAvg.abbreviatedCellText());
        assertEquals("updated sum nested", 233.5, resultSum, TestHelper.epsilon);
        assertEquals("updated avg nested", 29.1875, resultAvg,
                TestHelper.epsilon);
    }
}
