insert into USER_ENTITY(id,USER_ID,USERNAME,PASSWORD,NICKNAME,PHONE,EMAIL,DTYPE)
values ( 1,'gooch123','임구철','goo6485','goochul','01041451370','gooch123@naver.com','CUSTOMER' ),
       ( 2,'guyoon119','임구윤','goo1234','guyoon','01045256267','guyoon119@naver.com','CUSTOMER' ),
       ( 3,'soon2115','김정순','882992','soon','01011111111','soon2115@naver.com','CUSTOMER' );

insert into STORE_ENTITY(store_name,SALES,STORE_INFO,DELIVER_TIME)
values ( 'YU 치킨과 피자',10,'치킨과 피자를 판매합니다.',10)
        ,('YU 족발과 보쌈 그리고 덮밥',20,'족발, 보쌈, 덮밥을 팝니다',20);

insert into BASKET_ENTITY(id,STORE_ID)
values ( 1,1 ), (2,1), (3,null);

insert into FOOD_ENTITY(food_price, food_name, FOOD_SIZE, STORE_ID)
values ( 20000, '치킨',10,1),(30000,'피자',10,1),(50000,'족발',10,2),(20000,'보쌈',10,2),(9000,'덮밥',3,2);

-- insert into BASKET_FOOD_ENTITY(bakset_food_id, bakset_id, food_id, food_quantity)
-- values ( 1,1,1,3 ),(2,1,2,2),(3,1,3,5)
-- ,( 4,2,1,2 ),(5,2,2,4);

insert into BASKET_FOOD_ENTITY(bakset_id, food_id, food_quantity)
values ( 1,3,3 ),(1,4,2),(1,5,5)
     ,( 2,1,2 ),(2,2,4);

insert into WISH_LIST_ENTITY(customer_id, store_id) values ( 1,1 ),(1,2),(2,1);


