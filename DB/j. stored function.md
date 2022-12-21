# stored function
* 사용자가 정의한 함수
* sql의 select, insert, update, delete 에서 사용할 수 있다.

## 1. 예제1
* 직원의 id를 열자리 정수로 랜덤하게 발급한다.
* 맨 처음 id는 1로 고정이다.
```
>> delimiter $$
>> CREATE FUNCTION id_generator() RETURNS int NO SQL BEGIN RETURN (10000000000 + floor(rand() * 10000000000)); END $$
>> delimiter ;

[설명]
sql의 기본 delimiter는 세미콜론이다.
따라서 delimiter를 지정해주지 않으면 세미콜론을 만나면 꺼져버린다.
함수는 ()안에 매개변수를 받을 수 있다.
또한 NO SQL은 mysql에서만 쓰이는 문법이다.
```

## 2. 예제2
* 부서id를 파라미터로 받아 해당 부서의 평균 연봉을 리턴하는 함수
```
>> delimiter $$
>> CREATE FUNCTION dept_ave_salary(d_id int) 
RETURNS int READS SQL DATA 
BEGIN 
DECLARE avg_sal int;
select avg(salary) into avg_sal from employee where dept_id = d_id;
RETURNS avg_sal;
END $$
>> delimiter ;

[설명]
READS SQL DATA 는 mysql 문법임.
declare로 변수를 선언할 수 있음.
select into로 찾은 값을 into 뒤에 오는 변수에 넣을 수 있음.
변수 선언이 싫으면 into 뒤에 @변수이름 으로 넣어도 됨.

[사용]
>> SELECT *, dept_avg_salary(id) FROM department;
```

## 3. 예제3
* 토익 800점 이상을 학생들이 충복했는지 알려주는 함수
```
>> delimiter $$
>> CREATE FUNCTION toeic_check(toeic_score int) RETURNS char(4) NO SQL
BEGIN DECLARE re char(4);
IF toeic_score is null THEN SET re = 'fail;
ELSEIF toeic_score < 800 THEN SET re = 'fail;
ELSE SET re = 'pass;
END IF;
RETURN re;
END $$
>> delimiter ;
```

## 4. 함수 삭제하기
```
DROP FUNCTION 함수이름;
```

## 5. 등록된 함수 보기
```
[등록된 모든 함수 보기]
SHOW FUNCTION STATUS where DB = 'db(스키마)이름';

[등록된 함수 중 한개를 볼때]
SHOW CREATE FUNCTION 이름;
```

## 6. 함수 생성시 db지정
* 함수를 생성하면 지금 사용하고 있는 db에 저장된다.
* 이것이 싫다면 use db이름 해서 현재 사용하는 db를 바꾸거나
* CREATE FUNCTION db이름.지정할 함수 이름  으로 생성해주면된다.
