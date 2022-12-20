# 조인
> 두개 이상의 테이블을 한 번에 조회하는것

## 1. implicit join
* from 절에 테이블만 나열하고 where절에 join condition을 명시하는 것을 의미함
* 아주 기본적인 조인의 형태이며 가독성이 나쁘다.
### implicit join  예제
```
[id=1인 직원의 부서를 가져옴]
>> SELECT d.name FROM employee e, department d WHERE e.id = 1 and e.dept_id = d.id;
```

## 2. explicit join
* from 절에 join키워드와 함께 조인 테이블을 명시함.
* on뒤에 join condition이 오는 방식임.
### explicit join 예제
```
[id=1인 직원의 부서를 가져옴]
>> SELECT d.name FROM employee e JOIN department d on e.dept_id = d.id WHERE e.id = 1;
```

## 3. inner join
* 일반적인 조인의 형태로 inner 키워드는 생략가능함
* join condition을 만족하는 튜플만 리턴함
* null값을 가지는 튜플은 리턴하지 않는다.
* 왜 그런지는 three-valued logic을 통해 배웠음.
* null과 연산하면 unknown이 나오고 unkown은 true가 아니라서 리턴하지 않는다.
* 비교연산자들 사용가능

## 4. outer join
* join condition을 만족하지 않는 튜플도 리턴함
* LEFT JOIN, 또는 RIGHT JOIN, 또는 FULL JOIN을 명시하면 된다.
* left는 오른쪽 테이블에 왼쪽 테이블과 매핑되는 값이 있건 없건 모두 나오고
* right는 오른쪽 테이블의 전체값이 결과로 나오고, 왼쪽 테이블은 있으면 나오고 없으면 안나온다.
* 비교연산자들 사용가능
### outer join 예시
```
>> SELECT * FROM employee e LEFT OUTER JOIN department d ON e.dept_id = d.id;
```

## 5. equi join
* join condition에서 = 연산자를 사용하는 조인

## 6. using
* 조인을 할때 attribute의 이름이 같다면 USING으로 간단하게 작성할 수 있다.
* JOIN table2 USING (attribute)

## 7. natural join
* 같은 이름을 가지는 모든 attribute pair에 대해 equi join을 수행
* join condition을 따로 명시하지 않는다.
* NATURAL JOIN table2
* 모든 attribute를 equi join 하기 때문에 아무것도 반환 하지 않는 그런 결과가 나올 수도 있다.

## 8. cross join
* 테이블의 튜플 pair를 만들 수있는 모든 조합(카르테시안 곱)을 리턴한다.
* join condition이 없다.
* implicit :  FROM table1, table2;
* explicit ; FROM table1 CROSS JOIN table2;

## 9. 조인 최종 예제
```
[id=1003인 부서에 속하는 직원중 리더를 제외한 직원 조회]
>> SELECT * FROM employee e JOIN department d ON e.dept_id = d.id WHERE e.dept_id = 1003 AND e.id != d.leader_id;

[id=2001인 프로젝트에 참여한 직원 + 그 직원의 부서 이름을 알고싶다]
>> SELECT e.name, e.position, d.name AS department_name FROM works_on w JOIN employee e ON w.empl_id = e.id LEFT JOIN department d ON e.dept_id = d.id WHERE w.proj_id = 2001;
```
