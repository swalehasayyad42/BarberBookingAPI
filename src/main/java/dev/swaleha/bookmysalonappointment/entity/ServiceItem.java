package dev.swaleha.bookmysalonappointment.entity;

import org.springframework.data.annotation.Id;

public class ServiceItem {

	@Id
    private String serviceId;
    public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int i) {
		this.rate = i;
	}

	private String name;
    private int duration; // in minutes
    private int rate;

    public ServiceItem() {}

    public ServiceItem(String serviceId, String name, int duration, int rate) {
        this.serviceId = serviceId;
        this.name = name;
        this.duration = duration;
        this.rate = rate;
    }
}
