public class Booking {
    int bookingId,customerId,pickUpTime,dropTime,amount;
    char from,to;

    public Booking(int bookingId, int customerId, int pickUpTime, int dropTime, int amount, char from, char to) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.pickUpTime = pickUpTime;
        this.dropTime = dropTime;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }
}
