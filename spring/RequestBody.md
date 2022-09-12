# @ResquestBody

## ResquestBody 란?
* 클라이언트가 보낸 http요청(json & xml) 을 자바 오브젝트로 변환한다.
* 자동으로 dto 매개변수에 값을 대입해준다.(커맨드 객체처럼)

## 메커니즘
* ObjectMapper를 통해서 json값을 자바 오브젝트로 역직렬화한다.
* 역직렬화하는 과정에서 기본생성자는 필수 이므로 기본생성자가 없을시에러가뜬다.
* 이러한 대입과정에서 getter와 setter로 대입을 하기때문에 getter와 setter메서드가 없으면 예외가 발생해 대입 실패한다.
