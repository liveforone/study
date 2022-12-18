# 데이터 조회

## 1. 데이터 조회하기
* SELECT attribute(혹은 *) FROM 테이블명 WHERE 조건;
* 여기서 attribute는 내가 가져오고 싶은 attribute만 가져오므로 projection attribute라 함.
* 조건은 selection condition 이라함.
### select 예제
```
[2002 프로젝트를 lead하는 직원의 id와 이름, 직군을 select]
>> SELECT emplyoee.id, emplyoee.name, emplyoee.position FROM project, employee WHERE project.id = 2002 and project.leader_id = employee.id;

여기서 project.id = 2002는 selection condition이고
project.leader_id = employee.id는 join condition이다.
```

## 2. as - 별칭(alias)
* AS로 별칭을 붙여줄 수 있다.
* 별칭을 통해 조금더 가독성있고 짧은 쿼리를 작성할 수 있게 된다.
* 별칭은 AS를 생략 가능하다.
### 별칭 예제
```
>> SELECT e.id, e.name, e.position FROM project AS p, employee AS e WHERE p.id = 2002 and p.leader_id = e.id;

[출력시에 id -> leader_id, name -> leader_name, positon -> leader_positon으로 출력하고 싶을 때]
>> SELECT e.id AS leader_id e.name AS leader_name, e.position AS leader_position FROM project AS p, employee AS e WHERE p.id = 2002 and p.leader_id = e.id;
```

## 3. distinct
* 중복되는 튜플을 제외함.
### distinct 예제
```
>> SELECT DISTINCT p.id, p.name FROM employee e, works_on w, project p WHERE e.position = 'DSGN' and e.id = w.empl_id and w.proj_id = p.id;
```

## 4. Like 
* 검색 쿼리 라고도 함.
* %를 사용해서 문자열을 검색 할 수 있음.
* 문자% -> 문자로 시작하는
* %문자 -> 문자로 끝나는
* %문자% -> 문자가 들어가는
* %는 0개 이상의 문자열을 의미함.
* _는 한개의 문자를 의미함.
### Like 예제
```
[이름이 n으로 시작하거나 n으로 끝나는 직원의 이름을 알고싶다]
>> SELECT name FROM employee WHERE name LIKE 'n%' or name LIKE '%n';

[이름에 ng가 들어가는 직원 찾기]
>> SELECT name FROM employee WHERE name LIKE '%ng%';

[이름이 j로 시작하는 총 4글자의 이름을 가지는 직원 조회]
>> SELECT name FROM employee WHERE name LIKE 'j___';

[이스케이프 문자를 사용할때]
역슬래시를 사용하면된다.
\% \_
ex) : '\%%', '%\%', '\_%', '%\_'
```

## 5. *(asterisk) 
* 모두(all)의 의미를 가진다.
* 선택된 튜플의 모든 attribute를 가져온다 라는 의미이다.
### *(asterisk) 예제
```
[id가 9인 직원의 모든 attribute를 조회]
>> SELECT * FROM employee WHERE id = 9;
```
