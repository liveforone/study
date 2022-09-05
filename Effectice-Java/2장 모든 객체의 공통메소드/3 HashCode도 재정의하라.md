# equals를 재정의 하려거든 hashCode도 재정의하라

## why?
* equals를 재정의 한 클래스에서는 반드시 hashCode도 재정의 해야한다.
* 그렇지 않는다면 hashCode의 일반규약을 어기게된다.
* 일반규약을 어기게된다면 HashMap이나 HashSet같은 컬렉션 사용시 문제가 발생한다.

## HashCode 일반 규약
* equals 비교에 사용되는 정보가 변경되지 않았다면 hashCode를 몇번씩 호출해도 같은 값을 반환해야한다.
* <u>equals가 두 객체를 같다고 판단하면 두객체의 hashCode는 똑같은 값을 반환해야한다.</u>
* equals가 두객체를 다르다고 판단하더라도, 두객체의 hashCode는 똑같은 값을 반환해야한다.
