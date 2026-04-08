package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.entity.Shop;
import dev.swaleha.bookmysalonappointment.entity.User;
import dev.swaleha.bookmysalonappointment.modal.RegisterRequest;
import dev.swaleha.bookmysalonappointment.modal.UserTO;
import dev.swaleha.bookmysalonappointment.repository.ShopRepository;
import dev.swaleha.bookmysalonappointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;

    @Override
    public List<UserTO> findAll() throws Exception {
        List<User> userList = userRepository.findAll();

        if (CollectionUtils.isEmpty(userList)) {
            throw new Exception("userList details not found");
        }

        List<UserTO> userTOS = userList.stream().map(user -> {
            UserTO userTO = new UserTO(
                    user.getId(),
                    user.getName(),
                    user.getContact(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getRole(),
                    user.getExpertise(),
                    user.getShop()
            );
            return userTO;
        }).collect(Collectors.toList());
        return userTOS;
    }

    @Override
    public UserTO login(String userName, String password) throws Exception {
        // Fetch the user from the repository
        Optional<User> userOptional = userRepository.findByUserName(userName);

        System.out.println(userName);

        // If the user doesn't exist, throw an exception
        if (userOptional.isEmpty()) {
            throw new Exception("User not found with username: " + userName);
        }

        User user = userOptional.get();
        System.out.println(user);
        if (!password.equals(user.getPassword())) {
            throw new Exception("Invalid password for user: " + userName);
        }

        UserTO userTO = new UserTO(
                user.getId(),
                user.getName(),
                user.getContact(),
                user.getUserName(),
                user.getPassword(),
                user.getRole(),
                user.getExpertise(),
                user.getShop()
        );

        System.out.println(userTO);

        return userTO;
    }

    @Override
    public UserTO save(RegisterRequest registerRequest) throws Exception {

        if (Objects.isNull(registerRequest)) {
            throw new Exception("User request cannot be null");
        }

        // Check if contact number already exists
        if (userRepository.findByContact(registerRequest.getContact()).isPresent()) {
            throw new Exception("Contact number already registered");
        }

        // Check if username is either null or already exists
        if (registerRequest.getUserName() != null && userRepository.findByUserName(registerRequest.getUserName()).isPresent()) {
            throw new Exception("User Name is either null or already exists");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setUserName(registerRequest.getUserName());
        user.setPassword(registerRequest.getPassword());
        user.setContact(registerRequest.getContact());
        user.setRole(registerRequest.getRole());
        user.setExperties(registerRequest.getExpertise());

        // If role is 'customer', we don't need to check or create a shop
        if (!"customer".equalsIgnoreCase(registerRequest.getRole())) {
            System.out.println("barber");
            // Handle shop logic if role is not 'customer'
            Optional<Shop> existingShop = shopRepository.findByGstNo(registerRequest.getGstNo());
            System.out.println(existingShop);
            Shop shop;

            if (existingShop.isPresent()) {
                shop = existingShop.get();
            } else {
                shop = new Shop(registerRequest.getShopName(), registerRequest.getGstNo());
                Shop savedShop = shopRepository.save(shop);

                if (Objects.isNull(savedShop)) {
                    throw new Exception("Shop details not saved successfully");
                }
            }
            // Assign shop to the user
            user.setShop(shop);
        }else {
            user.setShop(null);
        }

        // Save the user to the repository
        User savedUser = userRepository.save(user);

        if (Objects.isNull(savedUser)) {
            throw new Exception("User details not saved successfully");
        }

        // Create and return the UserTO object
        UserTO userTO = new UserTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getContact(),
                savedUser.getUserName(),
                savedUser.getPassword(),
                savedUser.getRole(),
                savedUser.getExpertise(),
                savedUser.getShop()
        );

        return userTO;
    }


    @Override
    public List<UserTO> findByShopId(String shopId) throws Exception {
        List<User> userList = userRepository.findByShopId(shopId);

        if (userList.isEmpty()) {
            return Collections.emptyList();
        }

        return userList.stream()
                .map(user -> {
                    return new UserTO(user.getId(),
                            user.getName(),
                            user.getContact(),
                            null,
                            null,
                            user.getRole(),
                            user.getExpertise(),
                            user.getShop());
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteBarber(String id) throws Exception {
        Optional<User> barber = userRepository.findById(id);

        if (barber.isEmpty()) {
            throw new Exception("Barber not found");
        }

        userRepository.deleteById(id);
    }


}
