package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional
    public StoreEntity createStore(StoreDTO storeDTO) {
        StoreEntity store = storeDTO.createStore();
        storeRepository.saveNewStore(store);
        return store;
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
