// CS3 Spreadsheet TestCheckpoint4 class.  Do not modify this file.
// Version 1.1

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestCheckpoint4 {
    // Tests for checkpoint 4.
    // Note these must also pass for all subsequent checkpoints including
    // final project.
    Sheet grid;

    @Before
    public void initializeGrid() {
        grid = new Sheet();
    }

    @Test
    public void testConstant() {
        grid.processCommand("A1 = ( -43.5 )");
        Cell cell = grid.getCell(new Location(0, 0));
        assertEquals("constant formula value", TestHelper.format("-43.5"),
                cell.abbreviatedCellText());
        assertEquals("constant formula inspection", "( -43.5 )",
                cell.fullCellText());
    }

    @Test
    public void testMultiplicative() {
        String formula = "( 2 * 3 * 4 * 5 / 2 / 3 / 2 )";
        grid.processCommand("A1 = " + formula);
        Cell cell = grid.getCell(new Location(0, 0));
        assertEquals("multiplicative formula value", TestHelper.format("10.0"),
                cell.abbreviatedCellText());
        assertEquals("multiplicative formula inspection", formula,
                cell.fullCellText());
    }

    @Test
    public void testAdditive() {
        String formula = "( 1 + 3 + 5 - 2 - 4 - 6 )";
        grid.processCommand("L20 = " + formula);
        Cell cell = grid.getCell(new Location(19, 11));
        assertEquals("additive formula value", TestHelper.format("-3.0"),
                cell.abbreviatedCellText());
        assertEquals("additive formula inspection", formula,
                cell.fullCellText());
    }

    @Test
    public void testMixed() {
        String formula = "( 5.4 * 3.5 / -1.4 + 27.4 - 11.182 )";
        grid.processCommand("E1 = " + formula);
        Cell cell = grid.getCell(new Location(0, 4));
        assertEquals("mixed formula value length", 10, cell
                .abbreviatedCellText().length());
        assertEquals("mixed formula value", 2.718,
                Double.parseDouble(cell.abbreviatedCellText()),
                TestHelper.epsilon);
        assertEquals("mixed formula inspection", formula,
                cell.fullCellText());
    }

    @Test
    public void testReferences() {
        String formula = "( A1 * A2 / A3 + A4 - A5 )";
        grid.processCommand("A1 = 5.4");
        grid.processCommand("A2 = 3.5");
        grid.processCommand("A3 = -1.4");
        grid.processCommand("A4 = 27.4");
        grid.processCommand("A5 = 11.182");
        grid.processCommand("L18 = " + formula);
        Cell cell = grid.getCell(new Location(17, 11));
        assertEquals("reference formula value length", 10, cell
                .abbreviatedCellText().length());
        assertEquals("reference formula value", 2.718,
                Double.parseDouble(cell.abbreviatedCellText()),
                TestHelper.epsilon);
        assertEquals("reference formula inspection", formula,
                cell.fullCellText());
        grid.processCommand("A4 = 25.4");
        assertEquals("updated value length", 10, cell.abbreviatedCellText()
                .length());
        assertEquals("updated value", 0.718,
                Double.parseDouble(cell.abbreviatedCellText()),
                TestHelper.epsilon);
        assertEquals("updated inspection", formula, cell.fullCellText());
    }

    @Test
    public void testTransitiveReferences() {
        grid.processCommand("F1 = 1");
        grid.processCommand("F2 = ( 1 )");
        grid.processCommand("F3 = ( F2 + F1 )");
        grid.processCommand("F4 = ( F2 + F3 )");
        grid.processCommand("F5 = ( F3 + F4 )");
        Cell cell = grid.getCell(new Location(4, 5));
        assertEquals("Fib(5)", TestHelper.format("5.0"),
                cell.abbreviatedCellText());
        assertEquals("inspection", "( F3 + F4 )", cell.fullCellText());
    }

    @Test
    public void testProcessCommand() {
        TestHelper helper = new TestHelper();
        helper.setItem(0, 5, "1.0");
        helper.setItem(1, 5, "1.0");
        helper.setItem(2, 5, "2.0");
        helper.setItem(3, 5, "3.0");
        helper.setItem(4, 5, "5.0");
        grid.processCommand("F1 = 1.0");
        grid.processCommand("F2 = ( 1.0 )");
        grid.processCommand("F3 = ( F2 + F1 )");
        grid.processCommand("F4 = ( F2 + F3 )");
        String actual = grid.processCommand("F5 = ( F3 + F4 )");
        assertEquals("grid", helper.getText(), actual);
        String inspected = grid.processCommand("F4");
        assertEquals("inspected", "( F2 + F3 )", inspected);
        String updated = grid.processCommand("F3 = 11.5");
        helper.setItem(2, 5, "11.5");
        helper.setItem(3, 5, "12.5");
        helper.setItem(4, 5, "24.0");
        assertEquals("updated grid", helper.getText(), updated);
        String updatedInspected = grid.processCommand("F4");
        assertEquals("updated inspected", "( F2 + F3 )", updatedInspected);
    }

    @Test
    public void testMultiplicativeWithNegative() {
        String formula = "( -2 * -3.0 * 4 * 5 / -2 / 3 / 2 )";
        grid.processCommand("A1 = " + formula);
        Cell cell = grid.getCell(new Location(0, 0));
        assertEquals("multiplicative formula value",
                TestHelper.format("-10.0"), cell.abbreviatedCellText());
        assertEquals("multiplicative formula inspection", formula,
                cell.fullCellText());
    }

    @Test
    public void testAdditiveWithNegatives() {
        String formula = "( -1 + 3 + 5.0 - -2 - 4 - 6 + -2 )";
        grid.processCommand("L20 = " + formula);
        Cell cell = grid.getCell(new Location(19, 11));
        assertEquals("additive formula value", TestHelper.format("-3.0"),
                cell.abbreviatedCellText());
        assertEquals("additive formula inspection", formula,
                cell.fullCellText());
    }

    @Test
    public void testSimpleMixedWithOrWithoutPrecedence() {
        // Accept either the left-to-right basic result 13 or the precedence
        // extra credit result 11
        String formula = "( 1 + 2 * 3 + 4 )";
        grid.processCommand("A20 = " + formula);
        Cell cell = grid.getCell(new Location(19, 0));
        String text = cell.abbreviatedCellText();
        assertEquals("length", 10, text.length());
        String result = text.trim();
        assertTrue(
                "result "
                        + result
                        + " should be either 13.0 (left-to-right) or 11.0 (with precedence)",
                result.equals("13.0") || result.equals("11.0"));
    }

    @Test
    public void testMixedWithOrWithoutPrecedence() {
        // Accept either the left-to-right basic result 24
        // or the precedence extra credit result 20
        String formula = "( 1 + 2 * 3 + 4.5 - 5 * 6 - 3.0 / 2 )";
        grid.processCommand("E1 = " + formula);
        Cell cell = grid.getCell(new Location(0, 4));
        assertEquals("mixed formula value length", 10, cell
                .abbreviatedCellText().length());
        double result = Double.parseDouble(cell.abbreviatedCellText());
        if (result > 0) {
            assertEquals("mixed formula value (without precedence)", 24,
                    result, TestHelper.epsilon);
        } else {
            assertEquals("mixed formula value (with precedence)", -20,
                    result, TestHelper.epsilon);
        }
        assertEquals("mixed formula inspection", formula,
                cell.fullCellText());
    }

    @Test
    public void testMixedReferencesAndConstantsWithOrWithoutPrecedence() {
        // Initially with precedence 39.264, without precedence 9.564
        // Then with precedence 37.264, without precedence 13.564
        String formula = "( 3.0 + A1 * A2 / -1.4 + A4 - A5 * -2.0 )";
        grid.processCommand("A1 = 5.4");
        grid.processCommand("A2 = 3.5");
        grid.processCommand("A4 = 27.4");
        grid.processCommand("A5 = 11.182");
        grid.processCommand("L18 = " + formula);
        Cell cell = grid.getCell(new Location(17, 11));
        assertEquals("reference formula value length", 10, cell
                .abbreviatedCellText().length());
        double resultInitial = Double.parseDouble(cell
                .abbreviatedCellText());
        if (resultInitial > 10) {
            assertEquals("initial value", 39.264, resultInitial,
                    TestHelper.epsilon);
        } else {
            assertEquals("initial value", 9.564, resultInitial,
                    TestHelper.epsilon);
        }
        assertEquals("initial formula inspection", formula,
                cell.fullCellText());
        grid.processCommand("A4 = 25.4");
        assertEquals("updated value length", 10, cell.abbreviatedCellText()
                .length());
        double resultUpdated = Double.parseDouble(cell
                .abbreviatedCellText());
        if (resultUpdated > 15) {
            assertEquals("updated value", 37.264, resultUpdated,
                    TestHelper.epsilon);
        } else {
            assertEquals("updated value", 13.564, resultUpdated,
                    TestHelper.epsilon);
        }
        assertEquals("updated inspection", formula, cell.fullCellText());
    }

    @Test
    public void testTransitiveNontrivialReferences() {
        grid.processCommand("F1 = 1");
        grid.processCommand("F2 = ( 1 )");
        grid.processCommand("F3 = ( 1 + 3 + F2 + F1 - 3 - 1 )");
        grid.processCommand("F4 = ( 1.0 * F2 + F3 - 0.0 )");
        String outerFormula = "( 1.0 - 1 + F3 + F4 * 1.0 )";
        grid.processCommand("F5 = " + outerFormula);
        Cell cell = grid.getCell(new Location(4, 5));
        assertEquals("Fib(5)", TestHelper.format("5.0"),
                cell.abbreviatedCellText());
        assertEquals("inspection", outerFormula, cell.fullCellText());
    }
}
