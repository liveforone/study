# 서브쿼리

## 1. 서브쿼리
* ()안에 한 번더 작성되는 쿼리를 의미함
* select, insert, update, delete에서 모두 사용 가능.
### select subquery 예제
```
[id가 14인 직원의 생일보다 빠른 직원의 attribute를 얻고 싶다]
>> SELECT id, name, birth_date FROM employee WHERE birth_date < (
    SELECT birth_date FROM employee WHERE id = 14
);

[id가 1인 직원과 같은 부서 같은 성별인 직원들을 조회]
>> SELECT id, name, position FROM employee WHERE (dept_id, sex) = (
    SELECT dept_id, sex FROM employee WHERE id = 1
); 
```

## 2. in query
* IN안에는 조건이 여러개 들어가도 상관없다.
* IN 안에 들어가는 값은 set 혹은 multiset이다.
* NOT IN 이란것도 있으며 IN의 반대의미다.
### in쿼리 예제
```
[id가 5인 직원과 같은 프로젝트에 참여한 직원들을 조회]
>> SELECT id, name FROM employee WHERE id IN (
    SELECT DISTINCT empl_id FROM works_on WHERE empl_id != 5 AND proj_id IN (
    SELECT proj_id FROM works_on WHERE empl_id = 5
    )
); 

프로젝트가 여러개일 수 있으니 distinct로 중복을 없애고
자기자신(id=5)는 빼주고, id=5인 직원과 같은 프로젝트에 있는(in)직원id를 가져온다. 이 id와 employee에 있는 id값을 매칭시켜 직원 정보를 가져온다.
```

## 3. exists
* 서브쿼리의 결과가 최소 하나의 튜플이라도 있다면 true
* NOT EXISTS도 있으며 반대의 의미를 가진다.
### exists 예제
```
[id=7 or id=12인 직원이 참여한 프로젝트를 조회]
SELECT p.id, p.name FROM project p WHERE EXISTS (
    SELECT * FROM works_on w WHERE w.proj_id = p.id AND w.empl_id IN (7, 12)
);
```

## 4. any
* 서브쿼리가 반환한 결과등 중 하나라도 비교연산이 true라면 true이다.
* some 도 any와 같은 역할을 한다.
### any 예제
```
[리더보다 높은 연봉을 받는 사원을 가진 리더를 조회]
SELECT e.id, e.name, e.salary FROM department d, employee e WHERE d.leader_id = e.id AND e.salary < ANY (
    SELECT salary FROM employee WHERE id <> d.leader_id AND dept_id = e.dept_id
)
```

## 5. ALL
* 서브쿼리가 반환한 결과들과 비교연산이 모두 true라면 true 리턴.
### all 예제
```
[id=13인 직원과 한 번도 같은 프로젝트에 참여하지 못한 직원들을 조회]
SELECT DISTINCT e.id, e.name, e.position FROM employee e, works_on w WHERE e.id = w.empl_id AND w.proj_id <> ALL (
    SELECT proj_id FROM works_on WHERE empl_id = 13
);
```

## 6. IN vs EXISTS 성능비교
* RDBMS에 따라 다 다르다.
* 최근 많은 개선이 이루어져 거의 차이가 없다.
