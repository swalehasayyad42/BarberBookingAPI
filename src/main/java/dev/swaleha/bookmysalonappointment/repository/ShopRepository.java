package dev.swaleha.bookmysalonappointment.repository;

import dev.swaleha.bookmysalonappointment.entity.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends MongoRepository<Shop, String> {
    Optional<Shop> findByGstNo(String gstNo);
    Optional<Shop> findById(String id);
    List<Shop> findByShopNameLikeIgnoreCase(String shopName);
}
