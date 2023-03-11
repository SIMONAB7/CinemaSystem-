import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static int[][] rows =
            {new int[12], new int[16], new int[20]};//initialising 2D array holding 3 rows that hold different numbers of seats
    //row 1 -> 12seats, row 2 -> 16seats, row 3 -> 20seats
    static ArrayList<Ticket> tickets = new ArrayList<>(); //arraylist of Ticket objects that stores ticket information

    public static void main(String[] args) {

        boolean quit = false;
        while (!quit) { //loops through the options until the user quits the program
            System.out.println("\nWelcome to the New Theatre!");
            System.out.println("-------------------------------");
            System.out.println("Please select an option: ");
            System.out.println("""
                    1) Buy ticket\s
                    2) Print seating area\s
                    3) Cancel ticket\s
                    4) List available seats\s
                    5) Save to file\s
                    6) Load from file\s
                    7) Print ticket information and total price\s
                    8) Sort tickets by price\s
                    0) Quit""");
            System.out.println("-------------------------------\nEnter option: ");

            Scanner input = new Scanner(System.in);
            String user_option;

            user_option = input.nextLine(); //takes user input

            switch (user_option) { //executes depending on the choice made by the user
                case "0" -> {
                    quit = true;
                    System.out.println("See you soon, Goodbye!");
                }
                case "1" -> buy_ticket(false);
                case "2" -> print_seating_area(rows);
                case "3" -> cancel_ticket();
                case "4" -> show_available();
                case "5" -> { //saves ticket info to specified file
                    try {
                        save();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "6" -> {//loads info that has been saved to the specified file
                    try {
                        load();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "7" -> show_tickets_info();
                case "8" -> sort_tickets(tickets);
                default -> System.out.println("Invalid input!");
            }
        }
    }

    public static void buy_ticket(boolean cancelTicket) {

        Scanner input = new Scanner(System.in);//gets user input
        System.out.println("Input row number: "); //ask for user to input the row number using scanner
        int row_number = 0;

        try {
            row_number = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter number!");
            buy_ticket(false);
        }

        if (row_number > 0 && row_number <= 3) {
        } else {                                   //if the row number is not more than 0 and less or
            System.out.println("Invalid input!"); // equals to 3 the program will show that the input is invalid
            buy_ticket(false);
        }

        //ask for user to input the seat number using scanner
        System.out.println("Input seat number: ");
        int seat_number = 0;
        try {
            seat_number = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter number!");
            buy_ticket(false);
        }

        if (cancelTicket) { //if cancelTicket is true then this code will execute and remove the selected seat
            for (int i = 0; i < rows.length; i++) { //loops through each row
                for (int j = 0; j < seat_number; j++) { //loops through each seat
                    if (i == row_number - 1 && j == seat_number - 1) { //this will check if the selected seat and row are occupied
                        rows[i][j] = 0; // if seat is occupied it will change it back to zero to indicate the cancellation requested
                        System.out.println("Ticket cancelled!");
                    }
                }
            }
            //loops through each ticket in the arraylist and finds and removes the ticket that has matching row and seat number
            for (Ticket ticket : tickets) { //loops through all the tickets
                if (ticket.getRow() == row_number || ticket.getSeat() == seat_number) {//checks if the row and seat number from the ArrayList is equal to the row and seat from the original arrays
                    tickets.remove(ticket);//if everything matches then the ticket will be removed
                    break;
                }
            }
        } else {
            for (int i = 0; i < rows.length; i++) {
                //checking if the given row number is the same as the current row being checked and seat number is valid
                if (row_number == i + 1 && seat_number > 0 && seat_number <= rows[i].length) {
                    if (rows[i][seat_number - 1] == 1) {
                        System.out.println("Seat occupied");
                    } else {
                        rows[i][seat_number - 1] = 1;//if seat is available, the seat will be set to 1 indicating that its sold
                        System.out.println("Ticket successfully purchased");
                        //gets customer information to create a new Ticket object
                        System.out.println("Input your name: ");
                        String name = input.next();
                        System.out.println("Input your surname: ");
                        String surname = input.next();
                        System.out.println("Input your email: ");
                        String email = input.next();
                        System.out.println("Input price: ");
                        int price = input.nextInt();
                        Person newCustomer = new Person(name, surname, email);
                        Ticket newTicket = new Ticket(row_number, seat_number, price, newCustomer);
                        tickets.add(newTicket);//adds a new Ticket object to the ArrayList of tickets
                    }
                    return;
                }
            }
            System.out.println("Seat doesn't exist");
        }

    }

    public static void print_seating_area(int[][] rows) {
        //prints the stage plan with simple sout operation
        System.out.print("     ***********\n");
        System.out.print("     *  STAGE  *\n");
        System.out.print("     ***********\n");

        String spaceHolder = " "; //this is a variable which holds space character, used to format the spacing of the stage
        for (int i = 0; i < 3; i++) { //loops through the three rows of the seating plan
            String spaceLeft = spaceHolder.repeat((20 - rows[i].length) / 2);
            //^^^calculates how many spaces are needed on the left side of each row
            //^^^to generate the stage plan (i.e., row 1 needs 4 spaces, row 2 -> 2 spaces, row 3 no space)
            //^^^as row 1 has the least seats it needs most spaces

            System.out.print(spaceLeft); //prints left padding spaces for styling the stage plan
            for (int j = 0; j < rows[i].length; j++) {
                //adds a space character in order to separate the rows in the middle
                if (j == rows[i].length / 2) {
                    System.out.print(" ");
                }
                //prints O if seat is available and X if occupied
                if (rows[i][j] == 0) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }

    }

    public static void cancel_ticket() {
        buy_ticket(true); //if true it will activate the switch cases in buy_ticket and remove the seat selected
    }

    public static void show_available() {
        //print function for available seats for all rows
        for (int i = 0; i < rows.length; i++) { //looping through the rows and prints the seats that are available for the specific row (1, 2, 3)
            System.out.print("Available seats on row " + (i + 1) + ": ");
            for (int j = 0; j < rows[i].length; j++) { //loops through each row to check for availability
                if (rows[i][j] == 0) { //if seat is available then the number of that seat will be printed
                    System.out.print(j + 1 + " ");
                }
            }
            System.out.println(); //prints a line after each of the rows for readability
        }
        System.out.println();
    }

    public static void save() throws Exception {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter("saveFile.txt", false));
        //loops through the rows array and writes the three rows into the assigned file
        for (int[] row : rows) {
            for (int j = 0; j < row.length; j++) {
                fileWriter.write((Integer.toString(row[j]))); //writes the element in the row as a string into the file
            }
            if (row.length == 12 || row.length == 16 || row.length == 20) { //checks the length of the array and
                fileWriter.newLine();                                  // if they are 12, 16, 20 the program will print it as a new line in the text file
            }
        }
        //flushes the buffer and closes the file in order to save the changes made
        fileWriter.flush();
        fileWriter.close();
        System.out.println("File successfully saved!");
    }

    public static void load() throws FileNotFoundException {
        try {
            Scanner fileLoader = new Scanner(new File("saveFile.txt")); //creates the path from which the scanner will read/load the file
            for (int i = 0; i < rows.length; i++) {
                String rowCount = fileLoader.nextLine(); //reads the next line(i.e. the rows) in the file
                for (int j = 0; j < rows[i].length; j++) {
                    String seatCount = String.valueOf(rowCount.charAt(j)); //converted to string
                    rows[i][j] = Integer.parseInt(seatCount); //looping through every character in the rows and converts it to integer after it was converted to a string
                }
            }
            System.out.println("File successfully loaded!");
            fileLoader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace(); //used to handle exceptions and errors in the code when file not found
        }
    }

    public static void show_tickets_info() {
        if (tickets.isEmpty()) { //checks if there are any tickets saved in the arrayList
            System.out.println("There are no tickets purchased"); //if there are no tickets it will print a message
        } else {
            int totalPrice = 0; //if there are tickets the program will initialise a variable that will be used later to calculate the total price
            System.out.println("Loading Tickets information...\n");//this will print a message that the information about the tickets is loading
            for (Ticket ticket : tickets) { //here the program loops through each ticket in the "ticket" arrayList and prints information for each ticket
                ticket.print();
                System.out.println("-------------------------------");
                totalPrice += ticket.getPrice(); // as it loops through each ticket it adds the prices together into the initialised variable at the beginning
            }
            System.out.println("Total price of tickets: £" + totalPrice);
            //after printing the information for the tickets, then it will print the total price of all the tickets bought
        }
    }

    public static void sort_tickets(ArrayList<Ticket> tickets) {
        ArrayList<Ticket> orderedTickets = new ArrayList<>(tickets.size());

        for (Ticket ticket : tickets) {
            orderedTickets.add(ticket);
        }
        //bubble sort algorithm
        for (int i = 0; i < orderedTickets.size() - 1; i++) { //iterates through the ArrayList from the first to the second to last ticket which ones the loop
            //-> is finished the last ticket from the ArrayList will be sorted in the right position
            for (int j = i + 1; j < orderedTickets.size(); j++) { //this loop starts to iterate from the next ticket after the current one in the previous loop,
                // and it compares the left tickets to the remaining tickets in the ArrayList, so they can be sorted
                if (orderedTickets.get(i).getPrice() > orderedTickets.get(j).getPrice()) {
                    //swaps the positions of two tickets if ticket_1 at position "i" is with higher price compared to ticket_2 at position "j"
                    Ticket temp = orderedTickets.get(i); //temp = temporary; assigning ticket for index "i" in the temp variable
                    orderedTickets.set(i, orderedTickets.get(j)); //this sets the ticket which is at index "i" to be the same with the ticket at index "j"
                    orderedTickets.set(j, temp); //this sets the index "j" ticket to be like the ticket which was stored before in the variable temp
                }
            }
        } // essentially what this algorithm does is it swaps the indexes "i" and "j" around, until they are in the right order
        // The process will go until all the tickets are in the correct order by price
        for (Ticket ticket : orderedTickets) {
            ticket.print(); //prints all information about the tickets
            System.out.println("-------------------------------");
        }
    }
}