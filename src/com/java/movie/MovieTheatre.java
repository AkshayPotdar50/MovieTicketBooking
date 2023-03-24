package com.java.movie;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieTheatre {
    static final double PLATINUM_COST = 320;
    static final double GOLD_COST = 280;
    static final double SILVER_COST = 240;
    static final double SERVICE_TAX_RATE = 0.14;
    static final double SWACHH_BHARAT_CESS_RATE = 0.005;
    static final double KRISHI_KALYAN_CESS_RATE = 0.005;
//as these all values are going to be same throughout the program
    static final String[][] AUDI_1_SEATS = {
            {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9"},
            {"B1", "B2", "B3", "B4", "B5", "B6"},
            {"C2", "C3", "C4", "C5", "C6", "C7"}  
            //declare the matrix in string bec string is 
            //immutable in java
    };

    static final String[][] AUDI_2_SEATS = {
            {"A1", "A2", "A3", "A4", "A5", "A6", "A7"},
            {"B2", "B3", "B4", "B5", "B6"},
            {"C1", "C2", "C3", "C4", "C5", "C6", "C7"}
    };

    static final String[][] AUDI_3_SEATS = {
            {"A1", "A2", "A3", "A4", "A5", "A6", "A7"},
            {"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8"},
            {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9"}
    };
//all the following variables are also going to be same throughout the program
    static int showNo;
    static ArrayList<String> bookedSeats = new ArrayList<>();//to strore the booked seat numbers
    static double revenue = 0;
  //all the following variables are also going to be same throughout the program
    static double serviceTax = 0;
    static double swachhBharatCess = 0;
    static double krishiKalyanCess = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continueBooking = "Yes";
        while (continueBooking.equalsIgnoreCase("Yes")) {
        	//Compares this String to another String, ignoring caseconsiderations. 
            System.out.print("Enter Show no: ");
            showNo = scanner.nextInt();
            System.out.println("All available seats:");
            //callind=g the method for available seats
            showAvailableSeats(showNo);
            String seats;//here we declare string
            do {
                System.out.print("Enter seats (comma separated): ");
                seats = scanner.next();
            } while (!isSeatSelectionValid(seats.split(",")));//if seat selection is valid the go for printing 
            double totalCost = bookSeats(seats.split(","));
            printTicketDetails(totalCost);
            System.out.print("Would you like to continue (Yes/No): ");
            continueBooking = scanner.next();
        }
        //final method for printing the billing details
        printTotalRevenueDetails();
    }
//first method for showing available seats
    static void showAvailableSeats(int showNo) {
        String[][] seats;
        switch (showNo) {
            case 1:
                seats = AUDI_1_SEATS;
                break;
            case 2:
                seats = AUDI_2_SEATS;
                break;
            case 3:
                seats = AUDI_3_SEATS;
                break;
            default:
                return;
        }
        System.out.println("Platinum: " + String.join(", ", seats[0]) + " (Rs./seat " + PLATINUM_COST + ")");
        System.out.println("Gold: " + String.join(", ", seats[1]) + " (Rs./seat " + GOLD_COST + ")");
        System.out.println("Silver: " + String.join(", ", seats[2]) + " (Rs./seat " + SILVER_COST + ")");
    }
    //the following method will check the selection is valid or not

    static boolean isSeatSelectionValid(String[] selectedSeats) {
        for (String seat : selectedSeats) {
            if (!isSeatAvailable(seat)) {
                System.out.println(seat + " not available, Please select different seats");
                return false;
            }
        }
        return true;
    }
//the following method will check the seat is available or not
    static boolean isSeatAvailable(String seat) {
        String[][] seats;
        switch (showNo) {
            case 1:
                seats = AUDI_1_SEATS;
                break;
            case 2:
                seats = AUDI_2_SEATS;
                break;
            case 3:
                seats = AUDI_3_SEATS;
                break;
            default:
                return false;
        }//this will traverse through the matrix for checking availablity of seats
        for (String[] category : seats) {
            for (String availableSeat : category) {
                if (availableSeat.equalsIgnoreCase(seat) && !bookedSeats.contains(seat)) {
                    return true;
                }
            }
        }
        return false;
    }
//this method will book the seats
    static double bookSeats(String[] selectedSeats) {
        double totalCost = 0;
        String[][] seats;
        switch (showNo) {
            case 1:
                seats = AUDI_1_SEATS;
                break;
            case 2:
                seats = AUDI_2_SEATS;
                break;
            case 3:
                seats = AUDI_3_SEATS;
                break;
            default:
                return 0;
        }
        for (String seat : selectedSeats) {
            for (int i = 0; i < seats.length; i++) {
                for (int j = 0; j < seats[i].length; j++) {
                    if (seats[i][j].equalsIgnoreCase(seat)) {
                        double cost;
                        switch (i) {
                            case 0:
                                cost = PLATINUM_COST;
                                break;
                            case 1:
                                cost = GOLD_COST;
                                break;
                            default:
                                cost = SILVER_COST;
                                break;
                        }
                        totalCost += cost;
                        bookedSeats.add(seat);
                    }
                }
            }
        }
        updateRevenueDetails(totalCost);
        return totalCost;
    }
//this method will finalize the total shopping details
    static void updateRevenueDetails(double ticketCost) {
        double totalCostWithTaxes = ticketCost +
                ticketCost * SERVICE_TAX_RATE +
                ticketCost * SWACHH_BHARAT_CESS_RATE +
                ticketCost * KRISHI_KALYAN_CESS_RATE;
        revenue += ticketCost;
        serviceTax += ticketCost * SERVICE_TAX_RATE;
        swachhBharatCess += ticketCost * SWACHH_BHARAT_CESS_RATE;
        krishiKalyanCess += ticketCost * KRISHI_KALYAN_CESS_RATE;
    }
//this method will print the total ticket details
    static void printTicketDetails(double totalCost) {
        System.out.println("Successfully Booked - Show " + showNo);
        System.out.println("Subtotal: Rs. " + totalCost);
        System.out.println("Service Tax @14%: Rs. " + serviceTax);
        System.out.println("Swachh Bharat Cess @0.5%: Rs. " + swachhBharatCess);
        System.out.println("Krishi Kalyan Cess @0.5%: Rs. " + krishiKalyanCess);
        System.out.println("Total: Rs. " + (totalCost + serviceTax + swachhBharatCess + krishiKalyanCess));
    }
//this method will print the total revenue details
    static void printTotalRevenueDetails() {
        System.out.println("Revenue: Rs. " + revenue);
        System.out.println("Service Tax: Rs. " + serviceTax);
        System.out.println("Swachh Bharat Cess: Rs. " + swachhBharatCess);
        System.out.println("Krishi Kalyan Cess: Rs. " + krishiKalyanCess);
    }
}
