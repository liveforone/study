# DB 생성

## 1. SQL 기본
* relation -> table
* attribute -> column
* tuple -> row(행)
* domain -> domain
* 또한 sql에서는 튜플이 중복이 허용된다(multiset)
* RDBMS 마다 sql 스펙이 조금씩 다르다.

## 2. attribute 속성 지정
### 숫자
```
[정수]
INT(4byte), BIGINT(8byte)

[부동 소수점(실수 저장)]
FLOAT(4byte), DOUBLE(8byte)

[고정 소수점(실수를 정확히 저장)]
DECIMAL / NUMERIC
표현시에는 (자릿수, 소수점)으로 표현한다.
(ex : DECIMAL(5, 2) -> -999.99 ~ 999.99)
```
### 문자열
```
[고정 크기]
CHAR(n)
최대 몇개의 '문자'를 가지는 문자열을 저장할지 지정
지정된 문자열보다 크기가 작으면 스페이스로 채워서 저장
전화번호 같이 크기가 지정되고 변하지 않는 경우에는 char로 선언하는 것이 성능상에 이점이 있음.

[가변 크기]
VARCHAR(n)
최대 몇개의 '문자'를 가지는 문자열을 저장할지 지정
저장된 문자열의 길이만큼만 저장
```
### 날짜와 시간
```
[날짜]
DATE
YYYY-MM-DD  (년, 월, 일)

[시간]
TIME
hh:mm:ss or hhh:mm:ss

[날짜와 시간]
DATETIME
YYYY-MM-DD hh:mm:ss

TIMESTAMP
YYYY-MM-DD hh:mm:ss '타임존'
DB의 지역 time-zone이 반영됨
```
### Others
```
[byte-string]
BINARY / VARBINARY / BLOB type

[위치]
GEOMETRY

[Json]
JSON
```

## 3. 제약 & 속성
### primary key
```
[primary key가 하나로 구성]
(1) : id INT PRIMARY KEY 

(2) : id INT
PRIMARY KEY (id)

[하나 이상으로 구성]
id INT,
team_id INT,
PRIMARY KEY (id, team_id)
```
### UNIQUE
* PRIMARY KEY와 동일하다.
### NOT NULL
* 각 컬럼마다 적어준다.
### DEFAULT
* default 값을 지정할 때 사용
* 새로운 튜플을 저장할 때 해당 값이 없다면 default 값이 들어감.
### CHECK
* 원하는 조건을 넣어서 체크함
```
[하나일때]
age INT CHECK(age >= 20)

[하나 이상일때]
start_date DATE,
end_date DATE,
CHECK(start_date < end_date)

만약 이 함수를 누락시키면 함수가 누락됬다고 에러가 뜬다.
그런데 이 에러만 보고는 어떤 체크문인지 알 수 없다.
이 때는 아래와 같이 쿼리문을 날려서 알 수 있다.
SHOW CREATE TABLE 테이블명;
```
### FOREIGN KEY
```
[CASCADE]
참조값의 삭제/변경을 반영

[SET NULL]
참조 값이 삭제/변경되면 NULL로 변경

[RESTRICT]
참조값 삭제/변경 금지

[NO ACTION]
RESTRICT와 유사

[SET DEFAULT]
참조값이 삭제/변경시 default로 변경
```
### 제약 명시
* 제약에 이름을 명시하여 에러 등을 쉽게 파악할 수 있다.
* 제약을 삭제 할때에도 이름으로 삭제가 가능하다.
```
age INT CONSTRAINT age_over_20 CHECK (age >= 20)
```


## 4. 생성 예제
* mysql을 기준으로한다.
* 조심할 점은 mysql에서는 schema와 database의 의미가 같으나 다른 DB에서는 그렇지 않다.
### DB 생성
```
[company라는 데이터 베이스를 만들고 싶다.]
>> CREATE DATABASE company;

[생성한 DB를 사용한다.]
>> USE company;
```
### table 요구사항
```
[DEPARTMENT]
(id, name, leader_id)

[EMPLOYEE]
(id, name, birth_date, sex, position, salary, dept_id)

[PROJECT]
(id, name, leader_id, start_date, end_date)

[WORKS_ON]
(empl_id, proj_id)
```
### table 생성
```
create table DEPARTMENT (
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    leader_id INT
);

create table EMPLOYEE (
    id INT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    birth_date DATE,
    sex CHAR(1) CHECK (sex in ('M', 'F')),
    position VARCHAR(10),
    salary INT DEFAULT 50000000,
    dept_id INT,
    FOREIGN KEY (dept_id) references DEPARTMENT(id) on delete SET NULL on update CASCADE,
    CHECK (salary >= 50000000)
);

create table PROJECT (
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    leader_id INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (leader_id) references EMPLOYEE(id) on delete SET NULL on update CASCADE,
    CHECK (start_date < end_date)
);

create table WORKS_ON (
    empl_id INT,
    proj_id INT,
    PRIMARY KEY (empl_id, proj_id),
    FOREIGN KEY (empl_id) references EMPLOYEE(id) on delete CASCADE on update CASCADE,
    FOREIGN KEY (proj_id) references PROJECT(id) on delete CASCADE on update CASCADE
);
```
### 테이블 변경
```
DEPARTMENT는 employee가 없어서 leader_id에 FK를 걸지 못했다.

생성을 다 마치고 나서 걸어주면 된다.

이미 서비스 중인 테이블의 경우에는 alter쿼리를 함부로 날리지 말자.

ALTER TABLE department ADD FOREIGN KEY (leader_id) REFERENCES employee(id) on update CASCADE on delete SET NULL;
```
### 번외 - 삭제
```
>> DROP DATABASE company;
```
