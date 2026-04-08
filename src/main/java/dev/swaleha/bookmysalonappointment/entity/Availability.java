package dev.swaleha.bookmysalonappointment.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "availability")
public class Availability {

    @Id
    private String id;

    private String barberId;
    private String shopId;

    private String fromTime; // "09:00"
    private String toTime;   // "18:00"

    public Availability() {}

    public Availability(String barberId, String shopId, String fromTime, String toTime) {
        this.barberId = barberId;
        this.shopId = shopId;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBarberId() {
		return barberId;
	}

	public void setBarberId(String barberId) {
		this.barberId = barberId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

    // getters & setters
}