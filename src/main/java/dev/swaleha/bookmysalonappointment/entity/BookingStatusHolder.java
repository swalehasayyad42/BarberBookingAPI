package dev.swaleha.bookmysalonappointment.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class BookingStatusHolder {

    @Field("status")
    private String status = Status.PENDING;  // default value

    // Optional: define constants
    public static class Status {
        public static final String PENDING = "PENDING";
        public static final String COMPLETED = "COMPLETED";
        public static final String CANCELLED = "CANCELLED";
    }

    // Getter and setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}