public class Ticket {
    private int row;
    private int seat;
    private int price;
    private Person newCustomer;

    public Ticket(int row, int seat, int price, Person newCustomer) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.newCustomer = newCustomer;
    }

    public int getRow() {
        return row;
    }
    public int getSeat() {
        return seat;
    }
    public int getPrice() {
        return price;
    }
    public Person getNewCustomer() {
        return newCustomer;
    }
    public void print() {
        System.out.println("Name: " + newCustomer.getName() + ", " + "Surname: " + newCustomer.getSurname());
        System.out.println("Email: " + newCustomer.getEmail());
        System.out.println("Row: " + this.getRow() + ", " + "Seat: " + this.getSeat());
        System.out.println("Price: £" + this.getPrice());
    }


}

