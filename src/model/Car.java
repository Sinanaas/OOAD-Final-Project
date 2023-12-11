package model;

public class Car {
	private String carID;
	private String carName;
	private Integer carPrice;
	
	public Car(String carID, String carName, Integer carPrice) {
		super();
		this.carID = carID;
		this.carName = carName;
		this.carPrice = carPrice;
	}

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public Integer getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(Integer carPrice) {
		this.carPrice = carPrice;
	}
	
}
