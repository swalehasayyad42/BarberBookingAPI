package dev.swaleha.bookmysalonappointment.repository;

import dev.swaleha.bookmysalonappointment.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByContact(String contact);

    List<User> findByShopId(String shopId);
    
    Optional<User> findById(int id);
}
