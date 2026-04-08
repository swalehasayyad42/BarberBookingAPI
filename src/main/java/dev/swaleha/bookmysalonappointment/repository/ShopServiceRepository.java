package dev.swaleha.bookmysalonappointment.repository;

import dev.swaleha.bookmysalonappointment.entity.ShopService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopServiceRepository extends MongoRepository<ShopService, String> {
    List<ShopService> findByShopId(String shopId);
    Optional<ShopService> findById(String id);
    void deleteById(String id);
    @Query("{ '_id': { $in: ?0 } }")
    List<ShopService> findByIds(List<String> ids);
}
