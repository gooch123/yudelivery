package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.StoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    public StoreEntity createStore(StoreDTO storeDTO) {
        StoreEntity store = storeDTO.createStore();
        storeRepository.saveNewStore(store);
        return store;
    }
    public StoreEntity updateStore(StoreDTO storeDTO) {
        StoreEntity store = storeDTO.toEntity();
        return storeRepository.updateStore(store);
    }
    public void updateStore(StoreEntity store) {
        storeRepository.updateStore(store);
    }
    public void deleteMyStore(Long user_id) {
        storeRepository.deleteMyStore(user_id);
    }
    public StoreEntity getMyStore(Long user_id) {
        return storeRepository.findMyStore(user_id);
    }
    public StoreEntity getStoreById(Long store_id) {
        return storeRepository.findStoreById(store_id);
    }

    public StoreEntity getStoreDetail(Long store_id){
        return storeRepository.findStoreDetail(store_id);
    }

    public List<StoreEntity> searchStores(String Keyword) {
        return storeRepository.findStoreByKeyword(Keyword);
    }

}
