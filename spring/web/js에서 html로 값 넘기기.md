# js에서 html로 값 전달

## 방법
* 서버로 값을 전달할때는 form객체를 사용한다.
* form태그에 name으로 이름을 선언한다.
* input태그를 hidden으로 설정하고 name으로 이름을 선언한다.
* 해당 input태그에 value=""을 선언한다.
* 스크립트 태그에서 아래와 같은 방법으로 값을 넘긴다.
<pre>
var num = "1";
document.폼이름.input이름.value = num;
</pre>
