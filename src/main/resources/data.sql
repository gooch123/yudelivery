insert into user_entity(user_id,username, password,nickname,phone,email,dtype) values ('admin', 'admin','admin','admin', '010','admin','store');
insert into store_entity(user_id,store_name,address1,address2,address3,category,phone,open_time,close_time,deliver_time,store_info,sales) values ('1','admin','admin','admin','admin','admin','010-0000-0000','20:00','20:00','30','admin','50');
insert into food_entity(store_id,food_name,food_price,food_info,food_size) values ('1','beak ban','30000','beak ban jeon sik','2');
insert into admin_entity(review_id, content) values ('1','bad comment111');
insert into admin_entity(review_id, content) values ('2','bad comment222');