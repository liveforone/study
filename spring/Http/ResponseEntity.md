# ResponseEntity

## What is ResponseEntity?
* HttpHeader & HttpBody를 포함하고 있는 HttpEntity 클래스를 상속하고있는 클래스이다.
* 클라이언테 넘길때에는 응답 메세지, statuscode, body 를 넣어서 넘겨준다.

## 리턴값
* 넘겨줄때 값을 제네릭 안에 명시해주는것이 일반적이지만, 잘 모를경우 ?로 넘겨주면 알아서 값을 채워 넘겨준다.
* 리스트, map 등등 다양한 값을 넘겨줄 수 있다.

## ResponseEntity로 여러개 보내기
* 보통은 map을 많이 쓴다. 
* Map<String, Object> map = new HashMap<>(); 형태로 많이 보낸다.
* List도 같이 보낼 수 있으며, list는 인덱스로 이름을 가지고 그 안에 object 형식으로 보내진다.
