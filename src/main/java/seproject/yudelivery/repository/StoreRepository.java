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

    public StoreEntity findStoreExisting(Long user_id){
        return em.createQuery("select s from StoreEntity s where s.user.id  = :user_id", StoreEntity.class)
                .setParameter("user_id", user_id)
                .getSingleResult();
    }

    public void saveNewStore(StoreEntity store){
        if(store.getId() != null)
            em.persist(store);
        else
            em.merge(store);
    }

    public void deleteStore(Long id){
        StoreEntity store = em.find(StoreEntity.class, id);
        em.remove(store);
    }

    public void updateStore(StoreEntity store){
        em.merge(store);
    }




}
