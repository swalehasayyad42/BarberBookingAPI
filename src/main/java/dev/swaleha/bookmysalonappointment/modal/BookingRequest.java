package dev.swaleha.bookmysalonappointment.modal;

import java.time.LocalDateTime;
import java.util.List;

import dev.swaleha.bookmysalonappointment.entity.ServiceItem;

import java.math.BigDecimal;

public class BookingRequest {

    private String id; // booking id
    private String shopId;
    private String shopName; 
    private String barberId;
    private String barberName; 
    public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getBarberName() {
		return barberName;
	}
	public void setBarberName(String barberName) {
		this.barberName = barberName;
	}
	private String customerId; 
    private CustomerTO customer; // contains customer id + name
    private List<ServiceItem> services; // booked services
    private String status; // PENDING, COMPLETED
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal totalAmount;
    private List<String> serviceIds; // list of service IDs for creating booking

 // getter and setter
 public List<String> getServiceIds() { return serviceIds; }
 public void setServiceIds(List<String> serviceIds) { this.serviceIds = serviceIds; }
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }

    public String getBarberId() { return barberId; }
    public void setBarberId(String barberId) { this.barberId = barberId; }

    public CustomerTO getCustomer() { return customer; }
    public void setCustomer(CustomerTO customer) { this.customer = customer; }

    public List<ServiceItem> getServices() { return services; }
    public void setServices(List<ServiceItem> services) { this.services = services; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}