// CS3 Spreadsheet TestCheckpoint2 class.  Do not modify this file.
// Version 1.1

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestCheckpoint2 {
	// Tests for checkpoint 2.
		// Note these must also pass for all subsequent checkpoints including
		// final project.
		Sheet grid;

		@Before
		public void initializeGrid() {
			grid = new Sheet();
		}

		@Test
		public void testEmptyGridCells() {
			assertTrue(grid.getRows() > 0 && grid.getCols() > 0);
			for (int i = 0; i < grid.getRows(); i++)
				for (int j = 0; j < grid.getCols(); j++) {
					Cell cell = grid.getCell(new Location(i, j));
					assertEquals("empty cell text", TestHelper.format(""),
							cell.abbreviatedCellText());
					assertEquals("empty inspection text", "",
							cell.fullCellText());
				}
		}

		@Test
		public void testEmptyGridText() {
			TestHelper helper = new TestHelper();
			assertEquals("empty grid", helper.getText(), grid.toString());
		}

		@Test
		public void testShortStringCell() {
			String hello = "Hello";
			grid.processCommand("A1 = \"" + hello + "\"");
			Cell helloCell = grid.getCell(new Location(0, 0));
			assertEquals("hello cell text", TestHelper.format(hello),
					helloCell.abbreviatedCellText());
			assertEquals("hello inspection text", "\"" + hello + "\"",
					helloCell.fullCellText());
		}

		@Test
		public void testLongShortStringCell() {
			String greeting = "Hello, world!";
			grid.processCommand("L20 = \"" + greeting + "\"");
			Cell greetingCell = grid.getCell(new Location(19, 11));
			assertEquals("greeting cell text", TestHelper.format(greeting),
					greetingCell.abbreviatedCellText());
			assertEquals("greeting inspection text", "\"" + greeting + "\"",
					greetingCell.fullCellText());
		}

		@Test
		public void testEmptyStringCell() {
			grid.processCommand("B2 = \"\"");
			Cell emptyStringCell = grid.getCell(new Location(1, 1));
			assertEquals("empty string cell text", TestHelper.format(""),
					emptyStringCell.abbreviatedCellText());
			assertEquals("empty string inspection text", "\"\"",
					emptyStringCell.fullCellText());
		}

		@Test
		public void testDifferentCellTypes() {
			grid.processCommand("C11 = \"hi\"");
			Cell stringCell = grid.getCell(new Location(10, 2));
			Cell emptyCell = grid.getCell(new Location(0, 0));
			assertTrue(
					"string cell implementation class must be different from empty cell",
					!emptyCell.getClass().equals(stringCell.getClass()));
		}

		@Test
		public void testClear() {
			grid.processCommand("A1 = \"first\"");
			grid.processCommand("D8 = \"second\"");
			grid.processCommand("clear");
			Cell cellFirst = grid.getCell(new Location(0, 0));
			Cell cellSecond = grid.getCell(new Location(7, 3));
			assertEquals("cellFirst inspection text after clear", "",
					cellFirst.fullCellText());
			assertEquals("cellSecond inspection text after clear", "",
					cellSecond.fullCellText());
		}

		@Test
		public void testClearLocation() {
			grid.processCommand("A1 = \"first\"");
			grid.processCommand("D8 = \"second\"");
			grid.processCommand("clear A1");
			Cell cellFirst = grid.getCell(new Location(0, 0));
			Cell cellSecond = grid.getCell(new Location(7, 3));
			assertEquals("cellFirst inspection text after clear", "",
					cellFirst.fullCellText());
			assertEquals("cellSecond inspection text after clear",
					"\"second\"", cellSecond.fullCellText());
		}

		@Test
		public void testProcessCommandInspection() {
			String empty = grid.processCommand("A1");
			assertEquals("inspection of empty cell", "", empty);
			grid.processCommand("A1 = \"first\"");
			String first = grid.processCommand("A1");
			assertEquals("inspection of string cell", "\"first\"", first);
		}

		@Test
		public void testProcessCommand() {
			TestHelper helper = new TestHelper();
			String gridOne = grid.processCommand("A1 = \"oNe\"");
			helper.setItem(0, 0, "oNe");
			assertEquals("grid with one string cell", helper.getText(), gridOne);
			String accessorOne = grid.toString();
			assertEquals("grid from accessor with one string cell",
					helper.getText(), accessorOne);
			String gridTwo = grid.processCommand("L20 = \"TWo\"");
			helper.setItem(19, 11, "TWo");
			assertEquals("grid from accessor with two string cells",
					helper.getText(), gridTwo);
			String gridOnlyTwo = grid.processCommand("clear A1");
			helper.setItem(0, 0, "");
			assertEquals("grid with only the second string cell",
					helper.getText(), gridOnlyTwo);
			String gridEmpty = grid.processCommand("clear");
			helper.setItem(19, 11, "");
			assertEquals("empty grid", helper.getText(), gridEmpty);
		}

		@Test
		public void testProcessCommandSpecialStrings() {
			String stringSpecial1 = "A1 = ( avg A2-A3 )";
			String stringSpecial2 = "A1 = ( 1 * 2 / 1 + 3 - 5 )";
			TestHelper helper = new TestHelper();
			String grid1 = grid.processCommand("B7 = \"" + stringSpecial1
					+ "\"");
			helper.setItem(6, 1, stringSpecial1);
			assertEquals("grid with one special string", helper.getText(),
					grid1);
			String grid2 = grid.processCommand("F13 = \"" + stringSpecial2
					+ "\"");
			helper.setItem(12, 5, stringSpecial2);
			assertEquals("grid with two special strings", helper.getText(),
					grid2);
			String inspectedSpecial1 = grid.getCell(new Location(6, 1))
					.fullCellText();
			assertEquals("inspected first special string", "\""
					+ stringSpecial1 + "\"", inspectedSpecial1);
			String inspectedSpecial2 = grid.getCell(new Location(12, 5))
					.fullCellText();
			assertEquals("inspected second special string", "\""
					+ stringSpecial2 + "\"", inspectedSpecial2);
		}

		@Test
		public void testLongStringCellNoSpaces() {
			String greeting = "ThisIsALongString";
			grid.processCommand("L2 = \"" + greeting + "\"");
			Cell greetingCell = grid.getCell(new Location(1, 11));
			assertEquals("greeting cell text", TestHelper.format(greeting),
					greetingCell.abbreviatedCellText());
			assertEquals("greeting inspection text", "\"" + greeting + "\"",
					greetingCell.fullCellText());
		}

		@Test
		public void testLowerCaseCellAssignment() {
			String text = "Cell";
			grid.processCommand("b5 = \"" + text + "\"");
			Cell cell = grid.getCell(new Location(4, 1));
			assertEquals("cell text", TestHelper.format(text),
					cell.abbreviatedCellText());
			assertEquals("inspection text", "\"" + text + "\"",
					cell.fullCellText());
			String processText = grid.processCommand("b5");
			assertEquals("processed inspection text", "\"" + text + "\"",
					processText);
			String processText2 = grid.processCommand("B5");
			assertEquals("processed inspection text 2", "\"" + text + "\"",
					processText2);
		}

		@Test
		public void testLowerCaseCellProcessInspection() {
			grid.processCommand("B2 = \"\"");
			String processText = grid.processCommand("b2");
			assertEquals("processed inspection text", "\"\"", processText);
			grid.processCommand("c18 = \"3.1410\"");
			String processText2 = grid.processCommand("c18");
			assertEquals("processed inspection text 2", "\"3.1410\"",
					processText2);
		}

		@Test
		public void testMixedCaseClear() {
			grid.processCommand("A1 = \"first\"");
			grid.processCommand("D8 = \"second\"");
			grid.processCommand("CleaR");
			Cell cellFirst = grid.getCell(new Location(0, 0));
			Cell cellSecond = grid.getCell(new Location(7, 3));
			assertEquals("cellFirst inspection text after clear", "",
					cellFirst.fullCellText());
			assertEquals("cellSecond inspection text after clear", "",
					cellSecond.fullCellText());
		}

		@Test
		public void textNonliteralClear() {
			String clear = " clear ".trim();
			grid.processCommand("A1 = \"first\"");
			grid.processCommand("D8 = \"second\"");
			grid.processCommand(clear);
			Cell cellFirst = grid.getCell(new Location(0, 0));
			Cell cellSecond = grid.getCell(new Location(7, 3));
			assertEquals("cellFirst inspection text after clear", "",
					cellFirst.fullCellText());
			assertEquals("cellSecond inspection text after clear", "",
					cellSecond.fullCellText());
			String finalGrid = grid.toString();
			TestHelper th = new TestHelper();
			String emptyGrid = th.getText();
			assertEquals("empty grid", emptyGrid, finalGrid);
		}

		@Test
		public void testMixedCaseClearLocation() {
			grid.processCommand("A18 = \"first\"");
			grid.processCommand("D8 = \"second\"");
			grid.processCommand("clEAr a18");
			Cell cellFirst = grid.getCell(new Location(17, 0));
			Cell cellSecond = grid.getCell(new Location(7, 3));
			assertEquals("cellFirst inspection text after clear", "",
					cellFirst.fullCellText());
			assertEquals("cellSecond inspection text after clear",
					"\"second\"", cellSecond.fullCellText());
			String processedCleared = grid.processCommand("A18");
			assertEquals("processed inspection after clear", "",
					processedCleared);
		}

		@Test
		public void testProcessCommandMoreSpecialStrings() {
			String[] specialStrings = new String[] { "clear", "(", " = ", "5",
					"4.3", "12/28/1998", "A1 = ( 1 / 1 )", "A20 = 1/1/2000",
					"A9 = 4.3", "abcdefgh", "abcdefghi", "abcdefghijk" };

			TestHelper helper = new TestHelper();
			for (int col = 0; col < specialStrings.length; col++) {
				for (int row = 5; row < 20; row += 10) {
					String cellName = Character.toString((char) ('A' + col))
							+ (row + 1);
					helper.setItem(row, col, specialStrings[col]);
					String sheet = grid.processCommand(cellName + " = \""
							+ specialStrings[col] + "\"");
					assertEquals("grid after setting cell " + cellName,
							helper.getText(), sheet);
					String inspected = grid.getCell(new Location(row, col))
							.fullCellText();
					assertEquals("inspected cell " + cellName, "\""
							+ specialStrings[col] + "\"", inspected);
				}
			}
			assertEquals("final sheet", helper.getText(), grid.toString());
		}    
}
