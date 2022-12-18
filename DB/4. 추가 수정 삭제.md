# 데이터 추가/수정/삭제

## 1. 데이터 추가
* INSERT INTO 테이블명 VALUES (생성순의 attribute);
* INSERT INTO 테이블명 (attribute) VALUES (attribute);
* 주의할 점은 constraints이다.
* 제약조건을 지키지 않으면 에러메세지가 뜨기 때문에 주의하자.
* 직접 지정하지 않은 경우에는 NULL 
* 혹은 DEFAULT 값이 지정된 경우에는 DEFAULT 값이 들어간다. 
### 생성 예제
```
>> INSERT INTO employee VALUES (1, 'messi', '2003-01-01', 'M', 'COMPUTER', 70000000, null);

>> INSERT INTO employee VALUES (2, 'jane', '1996-05-05', 'F', 'DSGN', 90000000, null);

>> INSERT INTO employee (name, birth_date, sex, position, id) VALUES ('jenny', '2000-10-12', 'F', 'DEV', 3);
```
### 다중 생성
* 한 테이블에 한개씩 insert를 날리는 것이 아니라
* 여러개의 insert를 한 번에 날릴 수도 있다.
* INSERT INTO 테이블 이름 VALUES (값들), (값들), (값들);

## 2. 데이터 수정하기
* UPDATE 테이블명 SET 원하는 컬럼 WHERE 조건;
* foreign key는 생성시 걸어놓은 조건대로 이루어진다.
### 수정 예제
```
[직원1의 dept_id를 1003 으로 수정]
>> UPDATE employee SET dept_id = 1003 WHERE id = 1;

[dept_id가 1003인 튜플에 한해 연봉 2배인상]
>> UPDATE employee SET salary = salary * 2 WHERE dept_id = 1003;

[프로젝트 id가 2003인 직원들의 연봉을 2배 인상]
>> UPDATE employee, works_on SET salary = salary * 2 WHERE employee.id = works_on.empl_id and works_on.proj_id = 2003;

[모든 구성원의 연봉을 두배 올려라]
>> UPDATE employee SET salary = salary * 2;
```

## 3. 데이터 삭제
* DELETE FROM 테이블명 WHERE 조건;
* foreign key는 생성시 걸어놓은 조건대로 이루어진다.
* <> 는 != 과 동일한 의미를 지닌다.
* 즉 제외하고 라는 뜻을 가진다.
### 삭제 예제
```
[john이 퇴사를 한다. john의 id는 8이고 현재 2001번 프로젝트에 참여하고 있다.]
>> DELETE FROM employee WHERE id = 8;

[프로젝트를 2개를 참여하는 id가 5인 직원의 프로젝트 중 id 2002만 삭제하라]
>> DELETE FROM works_on WHERE empl_id = 5 and proj_id = 2002;

[프로젝트를 5개 참여하는 id가 5인 직원의 프로젝트 중 id가 2003을 제외하고 모두 삭제하라]
>> DELETE FROM works_on WHERE empl_id = 5 and proj_id <> 2003;
```
