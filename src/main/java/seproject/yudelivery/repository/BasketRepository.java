package seproject.yudelivery.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.BasketEntity;
import seproject.yudelivery.entity.BasketFoodEntity;
import seproject.yudelivery.entity.StoreEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void saveNewBasket(BasketEntity basket){ //고객이 회원가입할때 사용
        if(basket.getId() != null)
            em.persist(basket);
    }

    public void addFood(BasketFoodEntity basketFood, Long userId, StoreEntity store){ //같은 가게의 음식이면 추가, 만약 이미 추가된 음식이면 수량 추가
        if(isStoreEmpty(userId)){ // 장바구니가 비어있으면
            BasketEntity basket = findBasket(userId);
            basket.setStore(store); //장바구니에 가게 등록
            em.persist(basketFood); //그 후 음식 등록
        }else { // 장바구니가 비어있지 않으면
            if(isSameStore(userId,store)){ // 장바구니에 등록된 가게와 추가하는 음식이 같으면
                Long inBasketId = isAlreadyInBasket(basketFood.getFood().getId(), userId); // 음식이 이미 장바구니에 추가되었는지 검사
                if(inBasketId > 0) // 이미 있으면
                    updateFoodQuantityToBasket(inBasketId, basketFood.getFood_quantity()); // 수량 추가
                else
                    em.persist(basketFood); // 없으면 그대로 추가
            }
            else
                throw new IllegalStateException("장바구니엔 같은 가게의 음식만 들어갈 수 있습니다!!"); // 다른 가게의 음식 추가했을때
        }

    }

    public Long isAlreadyInBasket(Long foodId, Long userId){
        //이미 장바구니에 들어가있는 음식인지 판별하고 들어가있으면 들어있는 basketFood의 id반환
        //들어있지 않으면 -1 반환
        BasketEntity basket = findBasket(userId);
        List<BasketFoodEntity> basketFood = findBasketFood(basket.getId());
        for (BasketFoodEntity basketFoodEntity : basketFood) {
            if(basketFoodEntity.getFood().getId().equals(foodId))
                return basketFoodEntity.getId();
        }
        return -1L;
    }

    public BasketEntity findBasket(Long userId){ //고객의 장바구니 찾아서 반환
        return em.createQuery("select b from BasketEntity b where b.customer.id =: userId", BasketEntity.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }

    public List<BasketFoodEntity> findBasketFood(Long userId){ //고객의 장바구니에 있는 음식들 반환
        BasketEntity basket = findBasket(userId);
        List<BasketFoodEntity> basketfood = em.createQuery("select f from BasketFoodEntity f join fetch f.food where f.basket.id = :basketId", BasketFoodEntity.class)
                .setParameter("basketId", basket.getId())
                .getResultList();
        return basketfood;
    }

    public void updateFoodQuantityToBasket(Long basketFoodId, int quantity){ //장바구니에 있는 음식들의 수량 업데이트
        BasketFoodEntity basketFood = em.find(BasketFoodEntity.class, basketFoodId);
        basketFood.changeFoodQuantity(quantity);
    }

    public void cancelFood(Long basketFoodId){ //장바구니에서 음식 삭제
        BasketFoodEntity findBasketFood = em.find(BasketFoodEntity.class, basketFoodId);
        Long userId = findUserIdByBasketFoodId(basketFoodId); // 음식을 삭제하는 유저 id가져오기
        em.remove(findBasketFood);
        List<BasketFoodEntity> basketFood = findBasketFood(userId);
        if(basketFood.size() == 0) //삭제 후 장바구니가 비었다면 장바구니에 등록된 스토어 삭제
            findBasket(userId).setStore(null);
    }

    public int getFoodQuantity(Long basketFoodId){ //장바구니에 있는 음식 하나의 수량 반환
        BasketFoodEntity basketFood = em.find(BasketFoodEntity.class, basketFoodId);
        return basketFood.getFood_quantity();
    }

    public boolean isStoreEmpty(Long userId){ //지금 장바구니에 등록된 가게가 비어있는지 확인
        BasketEntity basket = findBasket(userId);
        if(basket.getStore() == null)
            return true;
        else
            return false;
    }

    public boolean isSameStore(Long userId, StoreEntity store){
        String storeName = store.getStore_name();
        String basketStoreName = findBasket(userId).getStore().getStore_name();
        if(storeName.equals(basketStoreName))
            return true;
        else
            return false;
    }

    public Long findUserIdByBasketFoodId(Long basketFoodId){
        Long basketId = em.createQuery("select s.id from BasketFoodEntity b join b.basket s where b.id =:id", Long.class)
                .setParameter("id", basketFoodId)
                .getSingleResult();
        Long userId = em.createQuery("select c.id from BasketEntity b join b.customer c where b.id =:id", Long.class)
                .setParameter("id", basketId)
                .getSingleResult();
        return userId;
    }

    public void clear(Long userId) {
        BasketEntity basket = findBasket(userId);
        BasketEntity basket1 = em.find(BasketEntity.class, basket.getId());
        basket1.clearStore();
        em.createQuery("delete from BasketFoodEntity b where  b.basket.id =:basket")
                .setParameter("basket",basket.getId())
                .executeUpdate();
    }
}
