package dev.swaleha.bookmysalonappointment.repository;

import dev.swaleha.bookmysalonappointment.entity.ShopService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ShopServiceRepository extends MongoRepository<ShopService, String> {
    List<ShopService> findByShopId(String shopId);
    Optional<ShopService> findById(String id);

}
