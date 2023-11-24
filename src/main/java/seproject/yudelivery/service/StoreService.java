package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.StoreRepository;

import java.util.Optional;

@Service
public class StoreService {
    private StoreRepository storeRepository;

    @Transactional
    public StoreEntity createStore(StoreDTO storeDTO) {
        StoreEntity storeEntity = storeDTO.toEntity();
        storeRepository.saveNewStore(storeEntity);
        return storeEntity;
    }
    public StoreEntity updateStore(StoreDTO storeDTO) {
        return null;
    }
    public StoreEntity deleteStore(StoreDTO storeDTO) {
        return null;
    }
    public StoreEntity getMyStore(Long user_id) {
        return storeRepository.findMyStore(user_id);
    }
    public StoreEntity getStore(Long store_id) {
        return storeRepository.findStore(store_id);
    }
}
