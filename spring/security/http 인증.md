## Http 인증 순서
1. 사용자가 서버로 인증되지 않은 요청을 보냄
2. 서버는 사용자에게 www-authenticate 헤더와 401코드를 보냄
3. 사용자는 Authorizatoin 헤더에 인증(자격)정보를 포함해 재요청함
4. 서버에서 인증정보를 체크, 맞다면 200코드를, 틀리다면 403 코드를 리턴

## 여기서 Authorization 헤더란?
* 인증(자격)정보를 담는 헤더이다.
* '타입 인증(자격)정보' 를 담아서 보낸다.

## Bearer 타입은 무엇일까?
* jwt의 경우 Bearer 타입을 쓴다
* 이것은 자격정보에 accessToken을 넣어서 내보내는 형태이다.
* 일반적으로 포스트맨 사용시 Bearer[accessToken]의 형태로 보낸다.
