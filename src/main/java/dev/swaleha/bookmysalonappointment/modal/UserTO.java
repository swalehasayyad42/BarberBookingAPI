package dev.swaleha.bookmysalonappointment.modal;


import dev.swaleha.bookmysalonappointment.entity.Shop;

import java.util.List;

public record UserTO(String id,
                     String name,
                     String contact,
                     String userName,
                     String password,
                     String role,
                     List<String> expertise,
                     Shop shop
) {
    public UserTO {
    }
}
