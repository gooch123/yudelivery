package seproject.yudelivery.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.entity.BasketEntity;
import seproject.yudelivery.entity.BasketFoodEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void saveNewBasket(BasketEntity basket){
        if(basket.getId() != null)
            em.persist(basket);
    }

    public Long findBasketId(Long userId){
        return em.createQuery("select b.id from BasketEntity b where b.customer.id =: userId", Long.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }

    public List<BasketFoodEntity> findBasketFood(Long basketId){
        List<BasketFoodEntity> basketfood = em.createQuery("select f from BasketFoodEntity f join fetch f.food where f.basket.id = :basketId", BasketFoodEntity.class)
                .setParameter("basketId", basketId)
                .getResultList();
        return basketfood;
    }

    public void updateBasket(Long basketFoodId, int quantity){
        BasketFoodEntity basketFood = em.find(BasketFoodEntity.class, basketFoodId);
        basketFood.changeFoodQuantity(quantity);
    }

    public void cancelFood(Long basketFoodId){
        em.remove(basketFoodId);
    }

    public int getFoodQuantity(Long basketFoodId){
        BasketFoodEntity basketFood = em.find(BasketFoodEntity.class, basketFoodId);
        return basketFood.getFood_quantity();
    }

}
