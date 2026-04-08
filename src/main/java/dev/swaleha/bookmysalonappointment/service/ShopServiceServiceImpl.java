package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.entity.Shop;
import dev.swaleha.bookmysalonappointment.entity.ShopService;
import dev.swaleha.bookmysalonappointment.modal.ServiceRequest;
import dev.swaleha.bookmysalonappointment.modal.ServiceTO;
import dev.swaleha.bookmysalonappointment.modal.ServiceUpdateRequest;
import dev.swaleha.bookmysalonappointment.repository.ShopServiceRepository;
import dev.swaleha.bookmysalonappointment.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceServiceImpl implements ShopServiceService {
    @Autowired
    ShopServiceRepository serviceRepository;
    @Autowired
    ShopRepository shopRepository;

    @Override
    public ServiceTO save(ServiceRequest serviceRequest) throws Exception {
        // Check if the serviceRequest is null
        if (Objects.isNull(serviceRequest)) {
            throw new Exception("Service request cannot be null");
        }

        // Check if the shopId in the request is valid
        Optional<Shop> shopExist = shopRepository.findById(serviceRequest.getShopId());

        if (!shopExist.isPresent()) {
            throw new Exception("Shop not found with the provided shopId");
        }

        // Create a new Service object and set the details
        Shop shop = shopExist.get(); // Get the shop from the repository
        ShopService newService = new ShopService();
        newService.setName(serviceRequest.getName());
        newService.setRate(serviceRequest.getRate());
        newService.setShop(shop); // Associate the service with the shop
        newService.setDuration(serviceRequest.getDuration());

        // Save the new service to the database
        ShopService savedService = serviceRepository.save(newService);

        if (Objects.isNull(savedService)) {
            throw new Exception("Failed to save the service");
        }

        // Return the saved service details as a ServiceTO object
        return new ServiceTO(savedService.getId(), savedService.getName(), savedService.getRate(), savedService.getShop().getId(),savedService.getDuration());
    }


    @Override
    public ServiceTO update(ServiceUpdateRequest serviceUpdateRequest) throws Exception {
        return null;
    }

    @Override
    public List<ServiceTO> findByShopId(String shopId) throws Exception {
        // Fetch all services for the given shopId
        List<ShopService> serviceList = serviceRepository.findByShopId(shopId);

        // Check if the service list is empty
        if (serviceList.isEmpty()) {
            return Collections.emptyList();
        }

        return serviceList.stream()
                .map(service -> {
                    return new ServiceTO(service.getId(),
                            service.getName(),
                            service.getRate(),
                            service.getShop().getId(),
                    		service.getDuration());
                })
                .collect(Collectors.toList());
    }


	@Override
	public ServiceTO updateServicePrice(String serviceId, int newPrice) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//    @Override
//    public ServiceTO updateServicePrice(String serviceId, int newPrice) throws Exception {
//        Optional<ShopService> serviceOptional = serviceRepository.findById(serviceId);
//
//        if (serviceOptional.isEmpty()) {
//            throw new Exception("Service not found with id: " + serviceId);
//        }
//
//        ShopService service = serviceOptional.get();
//        service.setRate(newPrice);
//
//        // Save the updated service
//        ShopService updatedService = serviceRepository.save(service);
//
//        // Returning ServiceTO with updated details
//        return new ServiceTO(updatedService.getId(), updatedService.getName(), updatedService.getRate(), updatedService.getShop());
//    }
	
	@Override
    public void deleteService(String serviceId) throws Exception {
        Optional<dev.swaleha.bookmysalonappointment.entity.ShopService> service = serviceRepository.findById(serviceId);

        if (service.isEmpty()) {
            throw new Exception("Service not found");
        }

        serviceRepository.deleteById(serviceId);
    }

}
