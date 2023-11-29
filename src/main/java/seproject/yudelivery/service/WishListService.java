package seproject.yudelivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.reader.StreamReader;
import seproject.yudelivery.dto.WishListDTO;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.entity.WishListEntity;
import seproject.yudelivery.repository.StoreRepository;
import seproject.yudelivery.repository.UserRepository;
import seproject.yudelivery.repository.WishListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public List<WishListDTO> getWishList(Long userId){
        List<WishListEntity> wishLists = wishListRepository.findByCustomer_Id(userId);
        List<WishListDTO> wishListDTOList = new ArrayList<>();
        for (WishListEntity wishList : wishLists) {
            wishListDTOList.add(new WishListDTO(
                    wishList.getId(),
                    wishList.getStore().getStore_name(),
                    wishList.getStore().getId(),
                    wishList.getStore().getStore_info()));
        }

        return wishListDTOList;
    }

    public void saveWishList(Long userId, Long storeId){
        if(wishListRepository.existsByCustomer_IdAndStoreId(userId,storeId))
            throw new IllegalStateException("이미 찜한 가게입니다.");
        else{
            StoreEntity store = storeRepository.findStoreById(storeId);
            UserEntity userEntity = userRepository.findById(userId).get();
            WishListEntity wishList = new WishListEntity(userEntity, store); //추후 customer로 바꿔야 함
            wishListRepository.save(wishList);
        }
    }

    public void deleteWishList(Long wishListId) {
        wishListRepository.deleteById(wishListId);
    }
}
