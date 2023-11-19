package seproject.yudelivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.entity.BasketEntity;
import seproject.yudelivery.repository.BasketRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;

    public List<BasketDTO> getBasket(Long id){
        Long basketId = basketRepository.find(id);


//        BasketDTO basketDTO = new BasketDTO(StoreName,foodName,foodQuantity,foodPrice);
        return null;
    }

}
