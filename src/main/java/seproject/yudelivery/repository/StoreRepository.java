package seproject.yudelivery.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.StoreEntity;


@Repository
@RequiredArgsConstructor
public class StoreRepository {
    private final EntityManager em;

    public StoreEntity findStore(Long id){
        return em.find(StoreEntity.class, id);
    }

}
