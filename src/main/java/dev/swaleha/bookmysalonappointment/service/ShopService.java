package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.entity.Shop;
import dev.swaleha.bookmysalonappointment.modal.RegisterRequest;
import dev.swaleha.bookmysalonappointment.modal.ShopTO;

import java.util.List;

public interface ShopService {
    ShopTO save(RegisterRequest registerRequest) throws Exception;
    List<ShopTO> findByShopName(String shopName) throws Exception;
}
