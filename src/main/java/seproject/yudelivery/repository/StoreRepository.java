package seproject.yudelivery.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.StoreEntity;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class StoreRepository {
    private final EntityManager em;

    public StoreEntity findStoreById(Long id){
        return em.find(StoreEntity.class, id);
    }
    public List<StoreEntity> findAllStore(){
        return em.createQuery("select s from StoreEntity s", StoreEntity.class)
                .getResultList();
    }
    public List<StoreEntity> findStoreByCategory(String category){
        return em.createQuery("select s from StoreEntity s where s.category = :category", StoreEntity.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<StoreEntity> findStoreByKeyword(String keyword){
        return em.createQuery("select s from StoreEntity s where s.store_name like :keyword", StoreEntity.class)
                .setParameter("keyword", "%"+keyword+"%")
                .getResultList();
    }

    public StoreEntity findMyStore(Long user_id){
        List<StoreEntity> store = em.createQuery("select s from StoreEntity s where s.user.id = :user_id", StoreEntity.class)
                .setParameter("user_id", user_id)
                .getResultList();
        if(store.isEmpty())
            return null;
        else
            return store.get(0);
    }

    public void saveNewStore(StoreEntity store){
        if(store.getId() == null)
            em.persist(store);
    }

    public void deleteMyStore(Long user_id){
        StoreEntity store = findMyStore(user_id);
        em.remove(store);
    }

    public void deleteStore(Long store_id){
        StoreEntity store = em.find(StoreEntity.class, store_id);
        em.remove(store);
    }

    public StoreEntity updateStore(StoreEntity store){
        store = em.merge(store);
        return store;
    }
}
