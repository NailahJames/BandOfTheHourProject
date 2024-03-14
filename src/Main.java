import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Band of the Hour\n-------------------------------");

        //----Prompting the user to enter the number of rows. Checks if between 1 and 10
        int numRows;
        do {
            System.out.print("Please enter number of rows: ");
            numRows = scanner.nextInt();
            if (!(1 <= numRows && numRows <= 10)) {
                System.out.println("ERROR: Out of range, try again");
            }
        } while (!(1 <= numRows && numRows <= 10));

        //---- This asks for the number of positions in rows A through C. Important for calculating average weight in row
        int[] numPositions = new int[numRows];
        int index;
        for (index = 0; index < numRows; index++) {
            char rowLabel = (char) ('A' + index);
            do {
                System.out.print("Please enter number of positions in row " + rowLabel + " (1 to 10): ");
                numPositions[index] = scanner.nextInt();
                if (!(1 <= numPositions[index] && numPositions[index] <= 10)) {
                    System.out.println("ERROR: Out of range, try again");
                }
            } while (!(1 <= numPositions[index] && numPositions[index] <= 10));
        }

        //----This makes the two-dimensional array for the printing
        double[][] rows = new double[numRows][];
        int jindex;
        for (jindex = 0; jindex < numRows; jindex++) {
            rows[jindex] = new double[numPositions[jindex]];
        }

        //----This prompts the user to choose between adding, removing, printing and exiting
        while (true) {
            System.out.print("\n(A)dd, (R)emove, (P)rint, e(X)it: ");
            String choice = scanner.next().toUpperCase();

            //----A switch statement is used to decide which part of the code to run. If anything else entered, it's invalid
            switch (choice) {
                case "A":
                    addMusician(rows, scanner);
                    break;
                case "R":
                    removeMusician(rows, scanner);
                    break;
                case "P":
                    printStadium(rows);
                    break;
                case "X":
                    System.exit(0);
                default:
                    System.out.println("ERROR: Invalid option, try again");
            }
        }
    }//end of main method

    //----Method for printing the two-dimensional array that the user can see
    private static void printStadium(double[][] rows) {
        int index;
        for (index = 0; index < rows.length; index++) {
            char rowLabel = (char) ('A' + index);
            System.out.print(rowLabel + ":");
            for (double weight : rows[index]) {
                System.out.printf("%7.1f", weight);
            }
            double totalWeight = calculateTotalWeightInRow(rows[index]);
            double averageWeight = calculateAverageWeight(rows[index]);
            System.out.printf("    [ %7.1f, %7.1f]%n", totalWeight, averageWeight);
        }
    }//end of printStadium method

    //----Method for calculating the total weight for each row
    private static double calculateTotalWeightInRow(double[] row) {
        double totalWeight = 0;
        int index;

        for (index = 0; index < row.length; index++) {
            totalWeight += row[index];
        }
        return totalWeight;
    }//end of calculateTotalWeightInRow method

    //----Method for calculating the AVERAGE weight of the row
    private static double calculateAverageWeight(double[] row) {
        double totalWeight = calculateTotalWeightInRow(row);
        int numPositions = row.length;
        if (numPositions > 0) {
            return totalWeight / numPositions;
        } else {
            return 0; // To avoid division by zero
        }
    }//end of calculateAverageWeight method

    //----Method for adding the musician as long as it does not exceed weight limit
    private static void addMusician(double[][] rows, Scanner scanner) {
        int rowIndex;
        do {
            System.out.print("Please enter row letter (A to " + (char) ('A' + rows.length - 1) + "): ");
            String rowLetter = scanner.next().toUpperCase();
            rowIndex = (int) rowLetter.charAt(0) - 65;
        } while (!(0 <= rowIndex && rowIndex < rows.length));

        int position;
        do {
            System.out.print("Please enter position number (1 to " + rows[rowIndex].length + "): ");
            position = scanner.nextInt() - 1;
        } while (!(0 <= position && position < rows[rowIndex].length));

        double weight;
        do {
            System.out.print("Please enter weight (45.0 to 200.0): ");
            weight = scanner.nextDouble();
        } while (!(45.0 <= weight && weight <= 200.0));

        double totalWeight = calculateTotalWeightInRow(rows[rowIndex]);
        if (totalWeight + weight > 100.0 * rows[rowIndex].length) {
            System.out.println("ERROR: Adding this musician would exceed the weight limit for this row.");
            return;
        }

        if (rows[rowIndex][position] == 0) {
            rows[rowIndex][position] = weight;
            System.out.println("****** Musician added.");
        } else {
            System.out.println("ERROR: There is already a musician there.");
        }
    }//end of the addMusician method

    //----Method for removing the musician, depending on if the position is vacant
    private static void removeMusician(double[][] rows, Scanner scanner) {
        int rowIndex;
        do {
            System.out.print("Please enter row letter (A to " + (char) ('A' + rows.length - 1) + "): ");
            String rowLetter = scanner.next().toUpperCase();
            rowIndex = (int) rowLetter.charAt(0) - 65;
        } while (!(0 <= rowIndex && rowIndex < rows.length));

        int position;
        do {
            System.out.print("Please enter position number (1 to " + rows[rowIndex].length + "): ");
            position = scanner.nextInt() - 1;
        } while (!(0 <= position && position < rows[rowIndex].length));

        if (rows[rowIndex][position] != 0) {
            rows[rowIndex][position] = 0;
            System.out.println("****** Musician removed.");
        } else {
            System.out.println("ERROR: That position is vacant.");
        }
    }//end of removeMusician method
//----------------------------------------------------------------------------------------------------------------------
//======================================================================================================================
}//end of BandOfTheHour class
//======================================================================================================================

/*
Design

Prompting the User for Input
1.1 Welcoming the user with proper formatting
1.2 do
	1.2.1 Prompting the user for the number of rows
      while
	1.2.2 The number of rows is 1 or over and 10 and under
1.3 do
	1.3.1 Prompting the user for number of positions
      while
	1.3.2 number of positions is 1 or over and 10 and under
1.4 Make the two dimensional array that will be later outputted
1.5 Prompt user to choose between (A)dd, (R) emove, (P)rint and E(X)iting
	1.5.1 Case “A” execute adding musicians method
	1.5.2 Case “R” executes removing musicians method
1.5.3 Case “P” executes printing the stadium method
1.5.4 Case “X” executes exiting and stops the program
Printing the Stadium and Weights
2.1 Printing the Stadium assignment
2.2 Printing the total weight on a row
	2.2.1 Correct outputting format
2.3 Printing the average weight on a row (depending on number of positions in row)
	2.2.1 Correct outputting format
Calculating total weight in each row
3.1 adding all the weights in row together
Calculating Average weights in row
4.1 use total weight
4.2 Calculate total weight / number of positions in a row
Adding musicians
5.1 do
	5.1.1 Prompt the user to enter the row letter to add to
      while
	5.1.2 the index of the row is greater than 0 and less than the row size entered
5.2 do
	5.2.1 Prompt the user to enter weight between 45 and 200 kg
      while
	5.2.2 The entered weight is between 45 and 200 inclusive
5.3 if
	5.3.1Adding the musician exceeds the weight limit, output and Error message
5.4 if
	5.4.1 The weight is 0, sp there is no one there
      else
	5.4.2 Add the musician to that spot

Removing musicians
6.1 do
	6.1.1 Prompting the user what row letter they want to remove a musician from
      while
6.1.2 The index of the row is greater than 0 and less than the row size entered
6.2 do
	6.2.1 Prompting the user for the position they want to change
      while
		6.2.2 The position is in between 1 and the number of positions entered inclusive
6.3 if
	6.3.1 row and position is not 0, make it 0. Output that the musician was removed
      else
	6.3.2 there is no musician there so the spot is already vacant
*/
