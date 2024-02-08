insert into USER_ENTITY(USER_ID,USERNAME,PASSWORD,NICKNAME,PHONE,EMAIL,DTYPE,CUSTOMER_ADDRESS,ROLE)
values ('test1','김고객','test','test1','01011111111','test1@naver.com','Customer','대구 영남대','CUSTOMER' ),
       ( 'test2','박고객','test','test2','01022222222','test2@naver.com','Customer','대구 경북대','CUSTOMER' ),
       ( 'test3','김점주','test','test3','01033333333','test3@naver.com','Customer','대구 계명대','STORE' ),
       ( 'test4','김점조','test','test4','01044444444','test4@naver.com','Customer','대구 대구대','STORE' );

insert into STORE_ENTITY(store_name,SALES,STORE_INFO,DELIVER_TIME,USER_ID,ADDRESS1,ADDRESS2,ADDRESS3,OPEN_TIME,CLOSE_TIME,PHONE)
values ( 'YU 치킨과 피자',10000000,'치킨과 피자를 판매합니다.',10,3,'경북 경산시 대학로 280','1-1','(대동)','06:30:00','20:30:00','010-5555-5555')
     ,('YU 족발과 보쌈 그리고 덮밥',20000000,'족발, 보쌈, 덮밥을 팝니다',20,4,'경북 경산시 대학로 280','1-2','(대동)','06:30:00','20:30:00','010-6666-6666');

insert into BASKET_ENTITY(customer_id,STORE_ID)
values ( 1,2 ), (2,1);

insert into FOOD_ENTITY(food_price, food_name, FOOD_SIZE, STORE_ID)
values ( 20000, '치킨',10,1),(30000,'피자',10,1),(50000,'족발',10,2),(20000,'보쌈',10,2),(9000,'덮밥',3,2);

insert into BASKET_FOOD_ENTITY(bakset_id, food_id, food_quantity)
values ( 1,3,3 ),(1,4,2),(1,5,5)
     ,( 2,1,2 ),(2,2,4);

insert into WISH_LIST_ENTITY(customer_id, store_id) values ( 1,1 ),(1,2),(2,1);

insert into ORDER_ENTITY(customer_id, store_id, STATUS, TOTAL_PRICE,ORDER_TIME)
values ( 1,1,'COMPLETE',50000,'2023-11-20' ),(2,1,'COMPLETE',100000,'2023-11-22'),
       (1,2,'COMPLETE',50000,'2023-11-25'),(1,2,'COMPLETE',120000,'2023-11-27'),(1,1,'COMPLETE',30000,'2023-11-27');

insert into ORDER_FOOD_ENTITY(food_id, order_id, QUANTITY)
VALUES ( 1,1,3 ),(2,1,5),(3,3,1),(4,4,5),(5,4,4),(1,5,1);
