// CS3 Spreadsheet TestHelper class.  Do not modify this file.
// Must only be used in test classes, which use it carefully.

public class TestHelper {
    public static final double epsilon = 1.0e-6; // small number to help compare doubles
    private String[][] items;

    public TestHelper() {
        items = new String[20][12];
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 12; j++)
                items[i][j] = format("");
    }

    public static String format(String s) {
        return String.format(String.format("%%-%d.%ds", 10, 10), s);
    }

    public void setItem(int row, int col, String text) {
        items[row][col] = format(text);
    }

    public String getText() {
        String ret = "   |";
        for (int j = 0; j < 12; j++)
            ret = ret + format(Character.toString((char) ('A' + j))) + "|";
        ret = ret + "\n";
        for (int i = 0; i < 20; i++) {
            ret += String.format("%-3d|", i + 1);
            for (int j = 0; j < 12; j++) {
                ret += items[i][j] + "|";
            }
            ret += "\n";
        }
        return ret;
    }
}
