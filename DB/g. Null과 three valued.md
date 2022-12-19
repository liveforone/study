# Null과 three-valued logic

## 1. NULL
* unknown, unavailable, not applicable의 의미를 가짐
* 즉 알려지지 않았거나, 이용가능하지 않거나, 해당사항이 없는 경우이다.

## 2. three-valued logic
* NULL과 비교연산을 하면 결과는 unknown 이다.
* 이는 Null과 Null을 비교할때도 마찬가지이다.
* unknown은 true일수도 false 일수도 있다라는 의미이다.
* three-valued logic은 sql에서 비교/논리 연산의 결과로,
* true, false, unknown을 가진다.
* 결론 Null과 연산하면 unknown을 갖는다.

## 3. WHERE 절에서 조건
* where 절에서는 조건이 true인 경우만 선택된다.
* false나 unknown이나 모두 선택되지 않는다.

## 4. 결론
* NOT IN 과 같은 연산등에 Null값이 들어가면
* unknown으로 처리되며 결과 값이 안나오는 상황들이 발생할 수 있으니 Null에 항상 주의 하자.
