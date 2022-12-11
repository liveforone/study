# Dto 직접 조회를 애용하자

## Dto 직접 조회란?
* [Dto 직접 조회](https://github.com/liveforone/study/blob/main/spring/DataBase/jpql.md)
* jpql의 프로젝션 중 하나이다.
* 스칼라 타입 프로젝션 중 하나이다.

## 장점은?
* 엔티티를 조회하여 MapperUtils 클래스로 dto를 변환하는 작업이 없어 성능 우위와 코드를 줄일 수 있다.
* 조인이 들어가는 엔티티의 경우 조인필드를 뷰에 리턴하지 않는 dto라면 더욱 큰 성능상 우위를 가질 수 있다.

## 필요한 어노테이션
* 이러한 dto 직접 조회를 하려면 두가지 어노테이션이 필수적이다.
* @AllArgsConstructor와 @NoArgsConstructor가 있다.
### @AllArgsConstructor
* 모든 필드를 파라미터로 하는 생성자를 만드는 어노테이션이다.
* 이 어노테이션이 필요한 이유로는 dto 직접조회를 하려면 생성자가 꼭 필요하기 때문이다.
### @NoArgsConstructor
* 해당 어노테이션은 파라미터가 없는 기본생성자를 만들어준다.
* 이 어노테이션이 없다면 jackson이 object로 직렬화와 역직렬화를 할때 실패하기에 꼭넣도록한다.
* 이는 뷰에 리턴하는 ResponseDto 뿐만 아니라 RequestDto도 마찬가지 이다.

## 번외
* RequestDto의 경우에는 @NoArgsConstructor의 레벨을 private으로 설정하자.
* 접근 제한자는 가능한 좁히는 것이 좋기 때문에 그렇다.
* @RequestBody를 통해 클라이언트로 입력을 받을때에는 Jackson2HttpMessageConverter를 사용하는데
* 이 친구는 리플랙션을 활용한다.
* 따라서 private이라도 관계없이 동작한다.
