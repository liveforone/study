# stored procedure
* 사용자가 정의한 프로시저
* RDBMS에 저장되고 사용되는 프로시저
* 구체적인 하나의 task를 수행한다.

## 예제1 - IN, OUT
* 두 정수의 곱셈 결과 리턴
```
>> delimiter $$
>> CREATE PROCEDURE product(IN a int, IN b int, OUT result int) BEGIN SET result = a * b END $$
>> delimiter ;

>> call product(5, 7, @result);
>> select @result;
```

## 예제2 - INOUT
* 두 정수를 맞바꾸는 프로시저
```
>> delimiter $$
>> CREATE PROCEDURE swap(INOUT a int, INOUT b int) BEGIN set @temp = a; set a = b; set b = @temp; END $$
>> delimiter ;

>> set @a = 5, @b = 7;
>> call swap(@a, @b);
>> select @a, @b;
```

## 예제3
* 각 부서별 평균연봉리턴
```
>> delimiter $$
>> CREATE PROCEDURE get_dept_avg_salary() BEGIN select dept_id, avg(salary) from employee group by dept_id; END $$
>> delimiter ;

>> call get_dept_avg_salary()
```

## stored function과의 차이점
* return 키워드로 반환 불가능
* 파라미터로 값 반환
* 값을 꼭 반환하지 않아도 됨
* sql statement에서 호출 불가능
* transcation 사용 가능
* 주된 사용 목적 : business logic
