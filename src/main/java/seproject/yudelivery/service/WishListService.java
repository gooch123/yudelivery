package seproject.yudelivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.WishListDTO;
import seproject.yudelivery.repository.WishListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;

    public List<WishListDTO> getWishList(Long userId){
        return null;
    }

}
