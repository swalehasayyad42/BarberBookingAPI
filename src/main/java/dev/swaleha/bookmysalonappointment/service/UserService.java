package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.entity.User;
import dev.swaleha.bookmysalonappointment.modal.RegisterRequest;
import dev.swaleha.bookmysalonappointment.modal.UserTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserTO> findAll() throws Exception;
    UserTO login(String userName, String password) throws Exception;
    UserTO save(RegisterRequest registerRequest) throws Exception;
    List<UserTO> findByShopId(String shopId) throws Exception;
}
