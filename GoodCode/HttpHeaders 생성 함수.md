# HttpHeaders 생성 함수로 중복 없애기

## why?
* 리다이렉트를 할때 ResponseEntity 같은 경우에는 headers()를 걸어주어야한다.
* 원하는 url로 헤더를 걸어주는 코드는 보통 3줄에서 4줄 정도 나오게된다
* 길이는 둘째치고 리다이렉트를 하는 로그인, 무언가 save하는 url, 수정하는 url, 삭제 하는 url 등에서 중복이 발생한다.
* 이때 생각한것이 url만 넣어주면 헤더를 리턴해주는 함수가 있으면 좋겠다고 생갔했다.
* CommonUtils에 makeHeader()라는 함수를 만들어 해결하였다.


## 코드
```
public static HttpHeaders makeHeader(String uri) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(URI.create(uri));

    return httpHeaders;
}

//호출 방법
String url = "/book/" + id;
HttpHeaders httpHeaders = CommonUtils.makeHeader(url);
```

## 기존 코드
```
HttpHeaders httpHeaders = new HttpHeaders();
httpHeaders.setLocation(URI.create("...."))

return ResponseEntity
          .status(...)
          .header(httpHeaders)
          .build;
```
