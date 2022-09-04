// CS3 Spreadsheet Spreadsheet class.  Fill in the details.

import java.util.Scanner;

public class Spreadsheet {
	public static Scanner console = new Scanner(System.in);

	public static void main(String[] args) {
		Sheet sheet = new Sheet(); // Keep this as the first statement in main
		Scanner scanner = new Scanner(System.in);
		// TODO in checkpoint 1: finish implementing by adding your own code here
		// with a loop to read lines of user input until the user enters quit,
		// calling sheet.processCommand with each line and printing the result to the console.
		System.out.println(sheet);
		System.out.print("Enter command (/h for help, enter quit to quit): ");
	    String command = scanner.nextLine();
		String output = sheet.processCommand(command);
		while (output != null)
		{
			if (command.equals("/h"))
			{
				System.out.println("quit: quits program");
			}
			else
			{
				if (output.equals(""))
				{
					System.out.print(output);
				}
				else
				{
					System.out.println(output);
				}
			}
			System.out.print("Enter command (/h for help): ");
			command = scanner.nextLine();
			output = sheet.processCommand(command);

		}

	}
}
