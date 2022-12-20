# 그룹으로 조회
> group by, aggregate function, order by

## 1. order by
* 특정 attribute(여러개 가능) 기준으로 정렬하여 가져오고 싶을때 사용.
* default는 오름차순(ASC) / 내림차순은(DESC)
### order by 예제
```
[직원들을 연봉순으로 조회 - 오름차순]
>> SELECT * FROM employee ORDER BY salary;

[직원들을 연봉순으로 조회 - 내림차순]
>> SELECT * FROM employee ORDER BY salary DESC;
```

## 2. aggregate function
* 여러 튜플들의 정보를 요약해서 하나의 값으로 추출하는 함수
* count, sum, max, min, avg 등이 있다.
* 관심있는 attribute에 사용된다.
* null값을 제외하고 요약값을 추출한다.
### aggregate function 예제
```
[직원의 수를 알고 싶다]
>> SELECT COUNT(*) FROM employee;

[프로젝트 2002에 참여한 직원수와 최대연봉, 최소연봉, 평균연봉을 조회]
>> SELECT COUNT(*), MAX(salary), MIN(salary), AVG(salary) FROM works_on w JOIN employee e ON w.empl_id = e.id WHERE w.proj_id = 2002;
```

## 3. group by
* 관심있는 attributes를 기준으로 그룹을 나누어 그룹별로 aggregate function을 사용하고 싶을때 사용
* null값이 있을때에는 null값을 가지는 튜플끼리 묶는다.
* aggregate function을 선언하는 부분에 grouping할 조건도 같이 명시해야한다.
### group by 예제
```
[각 프로젝트에 참여한 직원 수와 최대,최소, 평균 연봉 조회]
>> SELECT w.proj_id, COUNT(*), MAX(salary), MIN(salary), AVG(salary) FROM works_on w JOIN employee e ON w.empl_id = e.id GROUP BY w.proj_id;
```

## 4. Having
* group by와 함께 사용한다.
* aggregate function의 결과값을 바탕으로 그룹을 필터링 하고 싶을때 사용.
* HAVING 절의 조건에 만족하는 그룹만 리턴한다.
### Having 예제
```
[프로젝트 참여 인원이 7명 이상인 프로젝트들에 대해 각 프로젝트에 참여한 직원수와 최대,최소,평균 연봉 조회]
>> SELECT w.proj_id, COUNT(*), MAX(salary), MIN(salary), AVG(salary) FROM works_on w JOIN employee e ON w.empl_id = e.id GROUP BY w.proj_id Having COUNT(*) >= 7;
```

## 5. 전체 예제1
```
[각 부서별 인원수를 인원수가 많은 순서대로 정렬조회]
>> SELECT dept_id, COUNT(*) AS empl_count FROM employee GROUP BY dept_id ORDER BY empl_count DESC;

[각 부서별 성별 인원수를 인원수가 많은 순서대로 정렬조회]
>> SELECT dept_id, sex COUNT(*) AS empl_count FROM employee GROUP BY dept_id, sex ORDER BY empl_count DESC;
```

## 6. 전체 예제2
```
[전체 평균 연봉보다 평균 연봉이 적은 부서들의 평균연봉 조회]
>> SELECT dept_id, AVG(salary) FROM employee GROUP BY dept_id HAVING AVG(salary) < (
    SELECT AVG(salary) FROM employee
);
```

## 7. 전체 예제3
* round()는 반올림하는 함수임.
```
[각 프로젝트별로 프로젝트에 참여한 90년생의 수와 평균연봉 조회]
>> SELECT proj_id, COUNT(*), ROUND(AVG(salary), 0) FROM works_on w JOIN employee e ON w.empl_id = e.id WHERE e.birth_date BETWEEN '1990-01-01' AND '1999-12-31' GROUP BY w.proj_id;

[프로젝트 인원이 7명 이상인 프로젝트에 한정해서 프로젝트 별로 참여한 90년대 생의 수와 평균연봉 조회]
>> SELECT proj_id, COUNT(*), ROUND(AVG(salary), 0) FROM works_on w JOIN employee e ON w.empl_id = e.id WHERE e.birth_date BETWEEN '1990-01-01' AND '1999-12-31' AND w.proj_id IN (
    SELECT proj_id FROM works_on GROUP BY proj_id HAVING COUNT(*) >= 7
) GROUP BY w.proj_id;
```

## 8. select 문 순서
* 6. SELECT attributes or aggregate functions
* 1. FROM tables
* 2. WHERE conditions
* 3. GROUP BY  : 그룹핑하고
* 4. HAVING : 그룹을 필터링하고
* 5. ORDER BY : 정렬함
