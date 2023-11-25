package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.WishListEntity;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishListEntity,Long> {

    public List<WishListEntity> findByCustomer_Id(Long userId);

    public boolean existsByCustomer_IdAndStoreId(Long userId,Long storeId);

}
