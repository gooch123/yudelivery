package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.StoreEntity;

@Service
public class StoreService {

    @Transactional
    public StoreEntity createStore(StoreDTO storeDTO) {

    }
}
