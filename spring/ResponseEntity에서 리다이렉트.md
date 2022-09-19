# ResponseEntity에서 리다이렉트 하는 방법

## 기존의 방법
* 기존에 mvc 패턴에서는 return "redirect:링크"; 로 리다이렉트 했다.
* 하지만 rest-api를 사용하는 @RestController는 mvc 패턴이 아니기에 뷰리졸버가 사용되지않아 저 방법을 사용할 수 없다.

## ResponseEntity에서 리다이렉트
```
HttpHeaders httpHeaders = new HttpHeaders();
httpHeaders.setLocation(URI.create("경로"));  //해당 경로로 리다이렉트
return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
```
* 헤더를 이용해서 리다이렉트 할 수 있다.
* 여기서 핵심은 httpstatus를 moved_permanently로 설정한다는 것이다.
