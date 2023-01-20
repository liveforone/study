# index
* 풀 스캔 하지 않고도 빠르게 찾을 수 있는 인덱스에 대한 정리이다.
* 일반적인 DBMS에는 primary key에 인덱스가 자동 생성된다. 

# DB Table
```
[PLAYER]
id, name, team_id, backnumber
```

# 1. 이미 만들어진 테이블에 인덱스를 걸기
### a. 중복 가능한 하나의 튜플에 인덱스 걸기
* CREATE INDEX 이름 ON 테이블(컬럼...);
```
//사용하려는 sql (조건1개)
SELECT * FROM player WHERE name = 'Sonny';

//테이블에 인덱스 걸기
CREATE INDEX player_name_idx ON player (name);

```
### b. 중복 불가능한 두개의 튜플에 인덱스 걸기
* CREATE UNIQUE INDEX 이름 ON 테이블(컬럼...);
```
//사용하려는 sql (조건2개)
SELECT * FROM player WHERE team_id = 105 and backnumber = 7;

//테이블에 인덱스 걸기
CREATE UNIQUE INDEX team_id_backnumber_idx ON player (team_id, backnumber);
```

# 2. 테이블 생성시 인덱스 걸기
* 테이블 생성시 인덱스를 걸때에는 인덱스 이름을 쓰지 않아도 된다.
* INDEX (컬럼...)
* UNIQUE INDEX (컬럼...)
```
CREATE TABLE player (
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    team_id INT,
    backnumber INT,
    INDEX player_name_idx (name),
    UNIQUE INDEX team_id_backnumber_idx (team_id, backnumber)
);
```

# 3. 만들어진 인덱스 보기
* SHOW INDEX FROM 테이블;
```
SHOW INDEX FROM player;
```

# 4. 인덱스를 거는 조건
* 찾으려는 조건이 하나이면 인덱스를 하나만 걸면된다.
* 그런데 찾으려는 조건이 두개인데, 인덱스를 하나만 걸면 어떻게 될까?
* 인덱스가 걸린 컬럼은 빠르게 찾고, 남은 컬럼들은 풀스캔해서 찾는다..
* 따라서 검색 조건에 모두 인덱스를 거는것이 인덱스의 성능을 풀로 사용하기 좋다.

# 5. 인덱스 컬럼 순서
* 다중 컬럼 인덱스로 만들게 되면 인덱스의 컬럼 순서에 주의해야한다.
* 일반적인 쿼리에서도 그렇지만 a and b 라는 조건을 건다면 a를 먼저찾는다.
* 인덱스도 마찬가지이다. (컬럼1, 컬럼2) 로 인덱스를 걸면,
* 컬럼1을 먼저찾고 컬럼2를 찾는다.
* 일반적인 쿼리의 조건 순서와 동일 하기 때문에 
* 순서에 따라 성능이 달라진다. 인덱스도 일반쿼리의 조건과 마찬가지로 순서를 생각하며 작성하자.
* 또한 하나의 컬럼을 기준으로 조회를 할때 다중컬럼 인덱스를 사용한다면
* 가장 첫번째 조건에 오는 컬럼의 경우 사용해도 무관하다.
* 그러나 첫번째가 아닌 조건에 오는 컬럼의 경우에는 성능이 전혀 향상되지 않으니 조심하라.
* 아래는 예시이다.
```
//인덱스 성능 발휘
index (a, b);
where a;

//인덱스 무용지물, 인덱스가 돌지 않고 풀 스캔해버린다.
index (a, b);
where b;
```

# 6. 인덱스 직접 명시해 사용
* 인덱스의 실행은 DB의 Optimizer가 알아서 선택한다.
* 또한 풀스캔을 쓸지 인덱스를 쓸지도 Optimize가 선택하니 걱정말라.
* 따라서 인덱스를 직접 명시하는 경우는 드물다. 
* 그러나 직접 명시해 사용하려면 아래 처럼 사용한다.
* SELECT xx FROM XX USE INDEX(인덱스 이름) WHERE 컬럼;
* 아래는 must가 아니라 "~이걸 써주세요"의 의미가 강하다.
* 따라서 위의 인덱스를 쓰지 못하면 풀스캔을 돌아버린다.
* 따라서 인덱스를 직접 명시해 사용하는 건 지양하자.

# 7. 인덱스는 막 만들어도 될까?
* 인덱스는 쓰기작업(insert, update)을 할때마다 변경이 일어난다.
* 인덱스는 추가적인 공간이 생기게된다.
* 따라서 불필요하게 만드는 것은 지양하는것이 좋다.
