# Trigger
* 데이터의 변경(insert, update, delete)가 발생할때 자동적으로 실행되는 프로시저

## trigger 쿼리
```
delimiter $$
CREATE TRIGGER 트리거이름
BEFORE/AFTER UPDATE
ON 변경이 있을 테이블 FOR EACH ROW
BEGIN 바디
END $$
delimiter ;

OLD 키워드 : BEFORE문에서 사용, 업데이트 되기 전의 튜플
NEW 키워드 : AFTER문에서 사용, 업데이트 된 후의 튜플
```

## 예제1
* 사용자의 닉네임 변경 이력을 저장하는 트리거
```
>> delimiter $$
>> CREATE TRIGGER log_nickname 
BEFORE INSERT/UPDATE/DELETE
ON users FOR EACH ROW
BEGIN
insert into users_log values (OLD.id, OLD.nickname, now());
END $$
>> delimiter ;
```

## 예제2
* 사용자가 마트에서 상품을 구매할 때마다 
* 지금까지 누적된 구매 비용을 구하라
```
>> delimiter $$
>> CREATE TRIGGER sum_buy_prices_trigger
AFTER INSERT
ON buy FOR EACH ROW
BEGIN
DECLARE total INT;
DECLARE user_id INT DEFAULT NEW.user_id;
select sum(price) into total from buy where user_id = user_id;
update user_buy_status set price_sum = total where user_id = user_id;
END $$
>> delimiter ;
```

## 주의 사항
* 가시적이지 않아 개발, 관리, 문제파악이 어려움
* stored procedure는 코드에서 호출이라도 하는데
* 트리거는 아예 알 방법이 없음.
