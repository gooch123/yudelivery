package seproject.yudelivery.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.BasketEntity;
import seproject.yudelivery.entity.BasketFoodEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void save(BasketEntity basket){
        if(basket.getId() != null)
            em.persist(basket);
        else
            em.merge(basket);
    }

    public Long find(Long id){
        BasketEntity result = em.createQuery("select o from BasketEntity o where o.customer =:cusotmer", BasketEntity.class)
                .setParameter("customer",id)
                .getSingleResult();
        return result.getId();
    }

}
