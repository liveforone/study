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

## ResponseEntity 리다이렉트
### 기존의 방법
* 기존에 mvc 패턴에서는 return "redirect:링크"; 로 리다이렉트 했다.
* 하지만 rest-api를 사용하는 @RestController는 mvc 패턴이 아니기에 뷰리졸버가 사용되지않아 저 방법을 사용할 수 없다.

### ResponseEntity에서 리다이렉트
```
HttpHeaders httpHeaders = new HttpHeaders();
httpHeaders.setLocation(URI.create("경로"));  //해당 경로로 리다이렉트
return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
```
* 헤더를 이용해서 리다이렉트 할 수 있다.
* 여기서 핵심은 httpstatus를 moved_permanently로 설정한다는 것이다.
