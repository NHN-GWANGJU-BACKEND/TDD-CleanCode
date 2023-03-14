package com.nhnacademy.gw1.parking;

public class ParkingSystem {
    private static ParkingSystem parkingSystem = new ParkingSystem();
    private ParkingLot parkingLot;

    public ParkingSystem() {
        parkingLot = ParkingLot.getParkingLot();
    }

    public static ParkingSystem getParkingSystem() {
        return parkingSystem;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

}
