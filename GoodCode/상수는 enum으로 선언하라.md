# 상수는 enum으로 선언하라

## 상수
* 나의 스타일 가이드에서는 매직넘버를 없애는 용도로 사용했다.
* 주로 정수를 다루어 왔었다.

## 상수의 단점
* 상수는 자료형만 같지 사용하려는 목적과 이에따른 종류가 모두 다른데
* 종류가 다른 상수끼리 연산해도 경고메세지가 나오지 않는다.
* 즉 타입의 안전성을 보장해주지 않는다.
* 컴파일 후 상수의 값이 바뀌면 다시 컴파일 해주어야 한다.
* 즉 프로그램이 깨지기 쉽다.

## enum으로 바꿔라
* 이 이야기는 Effective-java 라는 책에서도 하고 있다.
* enum으로 바꾸면 인스턴스가 하나씩만 존재함을 보장한다.
* 또한 타입의 안전성을 보장한다.

## 변환 예시
* 두 상수의 종류는 완전히 다르다.
* 아래와 같이 변환이 가능하다.
```
private static final MAX_USER_SIZE = 10;
private static final MAX_ITEM_SIZE = 20;

@Getter
@AllArgsConstructor
enum UserConstants {
    MAX_USER_SIZE(10);

    private int value;
}

@Getter
@AllArgsConstructor
enum ItemConstants {
    MAX_ITEM_SIZE(20);

    private int value;
}
```
