package com.infinite.Hospitalparking;

public enum VehicleCategory {

	TWOWHEELER(10),
	FOURWHEELER(20),
	AMBULANCE(15);
	
	
	private final int cost;

    VehicleCategory(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}

