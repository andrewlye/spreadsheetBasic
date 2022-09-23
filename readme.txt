#Spreadsheet

*A text-based spreadsheet program.*

##Overview

The spreadsheet consists of a two-dimensional series of cells indexed by column letters and row numbers. The spreadsheet is 12 columns (A-L) by 20 rows. 

The program is run by accessing src/Spreadsheet.java

A letter followed by a number identifies cells (e.g. "A13" or "D7"). The program repeatedly accepts commands (for the user to change values, perform operations, etc.) until the user types 'quit', at which the program terminates. 

Some commands specify not just cells, but cell ranges. A cell range is a group of cells in a rectangular region. A1-B7, A1-A7, A1-C8 are all valid cell ranges. Cell ranges always specify two opposite corners of the rectangle of cells in the range, and the upper-left corner always comes first as in the preceding examples.

4 types of cells are stored: empty, text, real, and date. All cells begin as empty cells. Text cells represent strings that are inputted with double quotes but displayed without them. Real cells contain decimals and can have formulas in them that follow standard arithmetic precedence rules as well as allow for parenthetical prioritization. Date cells contain a date (MM/DD/YYYY). 

##Commands

**<cell>**
*Example: B3*

**<cell> = <value or formula>**
*Example: F7 = "hello world", B2 = ( avg B5-D6 ), E9 = 5, C2 = 4.5, D1 = ( 2 - 7 / 3), D2 = ( 1 + A1 + 3 ), F5 = 10/15/2004, A1 = ( ( 27 + 93 ) / ( ( 4 - 3 ) / 5 ) )*

Sets the contents of the specified cell to provided input and determines the type of cell from that input. A space must be entered after the cell and after '='. 

If a cell if a formula, the operation must start with a parenthesis followed by a space. All operators, parentheses, and operands within are separated with a space. Finally, the formula ends with a space and then a right parenthesis.

As shown in the examples above, cells may be assigned to formulas. Input is read as a formula when it is surrounded by parentheses. There are two kinds of formula, arithmetic formulas like ( 1 + B5 + 3 ) and method formulas like ( AVG A2-A5 ) and ( SUM A2-A5 ).

Arithmetic formulas are expressions involving constants, cell references, and the operators +,-,*, and /. Operator precedence prioritizes * and / operators, however these can be overridden by surrounding operations in parentheses, as seen in the last example (which computes as 600.0). 

A method formula can only contain one method, either SUM or AVG followed by a range of cell references. SUM calculates the sum of the values in the specified cell range, while AVG calculates the average.

If a formula contains a cell inside it, that is a reference to the cell. When a cell that is referenced by a formula is changed, the change will also affect the result displayed in the formula's cell.  In the above example of D2 = ( 1 + A1 + 3 ), if A1 has the value 0.0, then D2 would be displayed as 4.0. But if A1 is later changed to 1.5, then D2 would automatically change to display 5.5. Circular references are not supported. Any cells referenced by a formula must be real cells (either numbers or other formulas). 

**clear**
*Example: clear*

Sets all cells to empty cells in the spreadsheet.

**clear <cell>**
*Example: clear E15*

Sets the specified cell to an empty cell.

**quit**
*Example: quit*

Quits the program.

