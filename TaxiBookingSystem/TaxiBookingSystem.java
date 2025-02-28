import java.util.*;
public class TaxiBookingSystem {
    static List<Taxi> taxis = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int customerCounter = 1;

    public static void main(String[] args) {
        System.out.print("Enter number of taxis : ");
        int numOfTaxi = sc.nextInt();
        initializeTaxis(numOfTaxi);
        while (true) {
            System.out.println("\n1.Book taxi\n2.Display Taxi Details\n3.Exit");
            System.out.print("Enter your Choice :  ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bookTaxi();
                    break;
                case 2:
                    displayTaxiDetails();
                    break;
                case 3:
                    System.out.println("Exiting....");
                    return;
                default:
                    System.out.println("Invalid Choice...Try again.");
            }
        }
    }
    public static void initializeTaxis(int n) {
//        Initializing the taxi number
        for (int i=1;i<=n;i++) {
            taxis.add(new Taxi(i));
        }
    }
    public static void bookTaxi() {
        int customerId = customerCounter++;
        System.out.print("Enter PickUp Point (A-F) : ");
        char pickUp = sc.next().toUpperCase().charAt(0);
        System.out.print("Enter Drop Point (A-F) : ");
        char drop = sc.next().toUpperCase().charAt(0);
        System.out.print("Enter PickUp Time (in Hours) : ");
        int pickUpTime = sc.nextInt();

        Taxi selectedTaxi = null;
        int minDistance = Integer.MAX_VALUE;
        for (Taxi taxi : taxis) {
//            Check taxi available at request pickUp Time
            if (taxi.isAvailable(pickUpTime)) {
//                Distance bet current location and pickup point
                int distance = Math.abs(taxi.currentPoint - pickUp);
//                select taxi with min distance (or) Low earnings if the distance are equal
                if (distance < minDistance ||
                        (distance == minDistance && taxi.totalEarnings < selectedTaxi.totalEarnings)) {
                    selectedTaxi = taxi;
                    minDistance = distance;
                }
            }
        }
        if (selectedTaxi == null) {
            System.out.println("Booking Rejected. No taxi available.");
            return;
        }
        int dropTime = pickUpTime + Math.abs(drop - pickUp);
        int amount = selectedTaxi.calculateEarnings(pickUp,drop);
        int bookingId = selectedTaxi.bookings.size() + 1;

        Booking booking = new Booking(bookingId,customerId,pickUpTime,dropTime,amount,pickUp,drop);
//        Add the new booking to the selected taxi
        selectedTaxi.addBooking(booking);
        System.out.println("Taxi-" + selectedTaxi.id + " is allocated.");
    }
    public static void displayTaxiDetails() {
        for (Taxi taxi : taxis) {
            System.out.println("Taxi-" + taxi.id + " Total Earnings: Rs." + taxi.totalEarnings);
            System.out.printf("%-10s %-10s %-5s %-5s %-12s %-9s %-6s%n",
                    "BookingID", "CustomerID", "From", "To", "PickupTime", "DropTime", "Amount");
            for (Booking booking : taxi.bookings) {
                System.out.printf("%-10d %-10d %-5c %-5c %-12d %-9d %-6d%n",
                        booking.bookingId, booking.customerId, booking.from, booking.to,
                        booking.pickUpTime, booking.dropTime, booking.amount);
            }
        }
    }
}
