# 입출력 관련 API
* 콘솔로부터 데이터를 받을때 : System.in
* 콘솔에 데이터 출력 : System.out
* 에러를 출력 : System.err

## System.in
* InputStream 타입의 필드이다.
* BufferedReader 보조 스트림에 InputStreamReader를 연결하면 readLine() 메소드로 입력된 라인을 읽을 수 있게된다.

## System.in 예제
<pre>
import java.io.BufferedReader; //디펜던시1
import java.io.InputStreamReader; //디펜던시2

BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String str = br.readLine()
</pre>
