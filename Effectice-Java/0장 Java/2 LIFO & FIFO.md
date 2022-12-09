# LIFO & FIFO 컬렉션
> 자바의 스택(stack) 큐(queue)를 지원하는 클래스(인터페이스)

## LIFO : Last In First Out
* 말 그대로 나중에 넣은 객체가 먼저 나오는 자료구조이다.

## FIFO : First In First Out
* 말 그대로 먼저 넣은 객체가 먼저 나오는 자료구조이다.

## Stack 클래스
* stack 클래스는 LIFO 자료구조를 구현한 클래스이다.

### Stack 메소드
* push() : 주어진 객체를 스택에 넣는다.
* peek() : 스택의 맨 위 객체를 가져온다. 스택에서 <u>제거하진 않는다</u>.
* pop() : 스택의 맨 위 객체를 가져온다. 스택에서 <u>제거한다</u>.

#### stack 예제
```
import java.util.Stack;

class Main {
  public static void main(String[] args) {
    Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");
        String last = stack.peek();
        System.out.println(last);
  }
}
>>> third 가 출력된다.
```

## Queue 인터페이스
* FIFO 자료구조를 나타낸 클래스이다.
* 큐 인터페이스를 구현한 클래스로는 LinkedList가 있다.
* (LinkedList는 List 인터페이스를 구현했기 때문에 List 컬렉션이기도 하다)

### Queue 메소드
* offer() : 객체 넣기
* peek() : 객체하나를 가져온다. 큐에서 <u>지우지 않는다</u>.
* poll() : 객체하나를 가져온다. 큐에서 <u>제거한다</u>.

#### Queue 예제
```
Queue<Type> queue = new LinkedList<>();
```
