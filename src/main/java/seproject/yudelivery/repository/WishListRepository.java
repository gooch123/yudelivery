package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.WishListEntity;

public interface WishListRepository extends JpaRepository<WishListEntity,Long> {

}
