package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.entity.Shop;
import dev.swaleha.bookmysalonappointment.entity.User;
import dev.swaleha.bookmysalonappointment.modal.RegisterRequest;
import dev.swaleha.bookmysalonappointment.modal.ShopTO;
import dev.swaleha.bookmysalonappointment.modal.UserTO;
import dev.swaleha.bookmysalonappointment.repository.ShopRepository;
import dev.swaleha.bookmysalonappointment.repository.ShopServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopServiceRepository shopServiceRepository;

    @Override
    public ShopTO save(RegisterRequest registerRequest) throws Exception {

        if (Objects.isNull(registerRequest)) {
            throw new Exception("Shop request cannot be null");
        }
        Optional<Shop> shopExist = shopRepository.findByGstNo(registerRequest.getGstNo());

        if (!shopExist.isEmpty()) {
            throw new Exception("GST number already registered with another shop");
        }

        Shop shop = new Shop();
        shop.setShopName(registerRequest.getShopName());
        shop.setGstNo(registerRequest.getGstNo());

        Shop shop1 = shopRepository.save(shop);

        if (Objects.isNull(shop1)) {
            throw new Exception("Shop details not found");
        }

        ShopTO shopTO = new ShopTO(shop1.getId(), shop1.getShopName(), shop1.getGstNo());

        return shopTO;
    }

    @Override
    public List<ShopTO> findByShopName(String shopName) throws Exception {
        List<Shop> shopList = shopRepository.findByShopNameLikeIgnoreCase(shopName);

        if (shopList.isEmpty()) {
            return Collections.emptyList();
        }

        return shopList.stream()
                .map(shop -> {
                    return new ShopTO(shop.getId(),shop.getShopName(),shop.getGstNo());
                })
                .collect(Collectors.toList());
    }

}
