import java.util.*;

class RoomBookingSystem {
    public void bookRoom(String type) { System.out.println("Room booked: " + type); }
    public void checkAvailability() { System.out.println("Checking room availability..."); }
    public void cancelBooking() { System.out.println("Room booking canceled."); }
}

class RestaurantSystem {
    public void reserveTable(int peopleCount) { System.out.println("Table reserved for " + peopleCount + " persons."); }
    public void orderFood(String dish) { System.out.println("Food ordered: " + dish); }
    public void callTaxi() { System.out.println("Taxi called to the restaurant."); }
}

class EventManagementSystem {
    public void bookConferenceHall() { System.out.println("Conference hall booked."); }
    public void orderEquipment(String equipment) { System.out.println("Equipment ordered: " + equipment); }
}

class CleaningService {
    public void scheduleCleaning(String time) { System.out.println("Cleaning scheduled at: " + time); }
    public void performImmediateCleaning() { System.out.println("Sending cleaning staff immediately."); }
}

class HotelFacade {
    private RoomBookingSystem rooms;
    private RestaurantSystem restaurant;
    private EventManagementSystem events;
    private CleaningService cleaning;

    public HotelFacade() {
        this.rooms = new RoomBookingSystem();
        this.restaurant = new RestaurantSystem();
        this.events = new EventManagementSystem();
        this.cleaning = new CleaningService();
    }

    public void bookFullStay(String roomType, String dish, String cleaningTime) {
        System.out.println("\n--- Processing Full Stay Booking ---");
        rooms.checkAvailability();
        rooms.bookRoom(roomType);
        restaurant.orderFood(dish);
        cleaning.scheduleCleaning(cleaningTime);
    }

    public void organizeEvent(String equipment, int guestRoomsCount) {
        System.out.println("\n--- Organizing Event ---");
        events.bookConferenceHall();
        events.orderEquipment(equipment);
        for (int i = 1; i <= guestRoomsCount; i++) {
            rooms.bookRoom("Standard Room for Guest " + i);
        }
    }

    public void bookDinnerWithTaxi(int people) {
        System.out.println("\n--- Booking Dinner with Taxi ---");
        restaurant.reserveTable(people);
        restaurant.callTaxi();
    }

    public void requestUrgentCleaning() {
        System.out.println("\n--- Requesting Urgent Cleaning ---");
        cleaning.performImmediateCleaning();
    }

    public void cancelAll() {
        System.out.println("\n--- Canceling Services ---");
        rooms.cancelBooking();
    }
}

public class Main {
    public static void main(String[] args) {
        HotelFacade hotel = new HotelFacade();

        hotel.bookFullStay("Deluxe", "Steak", "11:00 AM");
        hotel.organizeEvent("Projector & Audio", 2);
        hotel.bookDinnerWithTaxi(4);
        hotel.requestUrgentCleaning();
        hotel.cancelAll();
    }
}
