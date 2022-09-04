// CS3 Spreadsheet TestCheckpoint1 class.  Do not modify this file.
// Version 1.1

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestCheckpoint1 {
    // Tests for checkpoint 1.
    // Pass them all, plus ensure main loop until quit works, for full
    // credit on checkpoint 1.
    // Note these must also pass for all subsequent checkpoints including
    // final project.
    Sheet grid;

    @Before
    public void initializeGrid() {
        grid = new Sheet();
    }

    @Test
    public void testGetRows() {
        assertEquals("getRows", 20, grid.getRows());
    }

    @Test
    public void testGetCols() {
        assertEquals("getCols", 12, grid.getCols());
    }

    @Test
    public void testProcessCommand() {
        String str = grid.processCommand("");
        assertEquals("output from empty command", "", str);
    }

    @Test
    public void testProcessCommandNonliteralEmpty() {
        String input = " ".trim();
        String output = grid.processCommand(input);
        assertEquals("output from empty command", "", output);
    }

    @Test
    public void testEmptyCellAbbreviatedCellText() {
        Cell cell = new Cell();
        assertEquals("Cell's abbreviated text", "          ",
                cell.abbreviatedCellText());
    }

    @Test
    public void testEmptyCellFullCellText() {
        Cell cell = new Cell();
        assertEquals("Cell's full text", "", cell.fullCellText());
    }

}
