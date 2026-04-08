package dev.swaleha.bookmysalonappointment.service;

import dev.swaleha.bookmysalonappointment.modal.ServiceRequest;
import dev.swaleha.bookmysalonappointment.modal.ServiceTO;
import dev.swaleha.bookmysalonappointment.modal.ServiceUpdateRequest;

import java.util.List;

public interface ShopServiceService {
    ServiceTO save(ServiceRequest serviceRequest) throws Exception;

    ServiceTO update(ServiceUpdateRequest serviceUpdateRequest) throws Exception;

    List<ServiceTO> findByShopId(String shopId) throws Exception;

    ServiceTO updateServicePrice(String serviceId, int newPrice) throws Exception;
    
    void deleteService(String id) throws Exception;
}
