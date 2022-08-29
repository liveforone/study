# equals는 일반 규약을 지켜 재정의하라

## equals는 회피가 최선이다
* equals는 곳곳에 함정이 도사리고 있다.
* 자칫하면 끔찍한 결과를 초래하는데, 제일 좋은 방법은 재정의하지 않는것이다.

## equals를 재정의하지 않는것이 최선인 상황
1. 각 인스턴스가 본질적으로 고유하다
> 값을 표현하는게 아니라 동작하는 개체를 표현하는 클래스가 해당한다.
> Thread가 좋은 예로 equals는 이러한 클래스에 딱맞게 구현되었다.
2. 인스턴스의 논리적 동치성을 검사할 일이 없다.
3. 상위 클래스에서 재정의한 equals가 하위 클래스에도 딱 들어맞는다.
> Set 구현체는 AbstractSet 구현한 equals를 상속받아쓰고,
> List 구현체는 AbstractList 로부터 Map도 그렇다.
4. 클래스가 private 이거나 package-private이고 equals메소드를 호출할 일이 없다.
> 위험을 피하는 스타일이라면 아래와 같이 구현하자
<pre>
@Override
public boolean equals(Object o) {
    throw new AssertionError();  //아예 equals 호출을 막아버린다.
}
</pre>

## equals를 재정의 해야할때
* 객체의 식별성(객체가 물리적으로 같은가)이 아니라 논리적 동치성을 확인해야할때 이다.
* 주로 값 클래스 들이 이에 해당한다.(Integer, String)
* 값 클래스중 인스턴스 통제클래스(enum 같은)는 재정의 안해도된다.
* 재정의 할때에는 반드시 일반 규약을 따라야한다.

## 재정의 일반 규약
* 반사성 : null이 아닌 참조값 x에대해 x.equals(x) 는 true
* 대칭성 : null이 아닌 참조값 x,y에 대해 x.equals(y)가 true라면 그 반대도 true이다.
* 추이성 : null이 아닌 참조값 x,y,z 에 대해 x.equals(y)가 true이고 y.equals(z)라면 x.equals(z)는 true이다.
* 일관성 : null이 아닌 참조값 x,y에 대해 x.equals(y)를 반복해서 호출하면 <u>항상</u> true나 false를 반환한다.
* null-아님 : null이 아닌 모든 참조값 x에대해 x.equals(null)은 false다.

## NullPointerException 조심하기
* equals로 null과 비교하다가 널포인터에러를 던지는 경우가 발생할 수 있다.
* 이런에러는 좋지않으므로 아래와 같이 널 아님을 해결하자
<pre>
@Override
public boolean equals(Object o) {
    if (!(o instanceOf MyType)) {
        return false;
    }
    MyType mt = (MyType) 0;
}
</pre>

## 최종 equals 구현 방법
1. == 연산자로 입력이 자기 자신의 참조인지 확인한다.
> 자기자신이면 true를 리턴
2. instanceOf 연산자로 입력이 올바른 타입인지 확인한다.
> 그렇지 않다면 false를 리턴한다.
> 올바른 타입은 equals가 정의된 클래스가 보통이지만, 가끔 그 클래스가 구현한 특정 인터페이스가 될 수도 있다.
3. 입력을 올바른 타입으로 형변환한다.
4. 입력객체와 자기 자신의 대응되는 '핵심'필드들이 모두 일치하는지 하나씩 검사한다.

## 주의
* float과 Double을 제외한 기본 타입은 ==로 비교한다.
* 참조타입은 equals로 비교한다.
* float은 Float.compare로 비교한다.
* double은 Double.compare로 비교한다.
* 반드시 equals는 Object 타입으로 받는다. 다른 타입으로 받지 않는다.
