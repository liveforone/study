# Dto와 Entity의 서로로 변환
> 여러번 설명했지만 엔티티를 직접 노출하지 않는 dto는 상당히 많은 장점을 가진다.
>  이 글을 보는 독자들은 dto를 사용하는 이유에 대해 충분히 숙지했을것이라고 생각한다.

## dto -> entity 
* 프론트로부터 dto로 입력을 받는다.
* getter/setter가 있는 dto라면 프론트로부터 requestbody를 통해 값을 dto에 넣을 수 있게된다.
* 이렇게 입력받은 값을 엔티티로 변환하여야 jpa의 save() 메소드를 사용할 수 있다.
* XxMapper 클래스에 dtoToEntity() 란 메소드를 만들고 서비스로직에서 save()를 호출할때마다 dto를 엔티티로 즉각 바꾸어서 사용한다.
* 이전에는 dto 클래스 안에 만들고 dto.toEntity()와 같이 썻지만,
* 변환 메소드는 모두 Mapper 클래스에 두어 통일성을 갖도록 할 뿐만 아니라 dto가 단일 책임만을 갖도록 한다.

### 예제
```
//== dto -> entity ==//
public static Board dtoToEntity(BoardRequest board) {
    return Board.builder()
            .id(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .users(board.getUsers())
            .view(board.getView())
            .good(board.getGood())
            .build();
}
```

## entity -> dto
* 뷰에 값을 넘길때 절대로 entity로 넘겨서는 안된다.
* 연관관계가 있다면 순환참조문제가 발생하고 여러가지 에러들이 발생할 수 있다.
* 또한 불필요한 값이나, 클라이언트에 제공되어서는 안되는 값들이 삽입되어 클라이언트에 전달 될 수 있다.
* 넘기는 자료형태, 즉 List, Page, 객체 하나(detail)에 맞게 함수를 만들어 호출하도록 한다.
* 주의할점은 객체를 dto로 변환하는 dtoBuilder메소드의 경우에는 private으로 전환한다.(해당 클래스 안에서만 쓰이므로)

### 예제
```
//== BoardResponse builder method ==//
private static BoardResponse dtoBuilder(Board board) {
    return BoardResponse.builder()
            .id(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .view(board.getView())
            .good(board.getGood())
            .createdDate(board.getCreatedDate())
            .build();
}

//== entity ->  dto 편의메소드1 - 페이징 ==//
public static Page<BoardResponse> entityToDtoPage(Page<Board> boardList) {
    return boardList.map(BoardMapper::dtoBuilder);
}

//== entity -> dto 편의메소드2 - 엔티티 ==//
public static BoardResponse entityToDtoDetail(Board board) {

    if (board == null) {
        return null;
    }

    return BoardMapper.dtoBuilder(board);
}

//== entity -> dto 편의메소드3 - 리스트 ==//
public static List<BoardResponse> entityToDtoList(List<Board> boardList) {
        return itemList
                .stream()
                .map(BoardMapper::dtoBuilder)
                .collect(Collectors.toList());
 }
```

## 번외 - static
* static으로 선언한 변수와 메소드들은 클래스처럼 힙영역이 아닌 메모리에 올려진다.
* 이는 프로그램이 실행될때 생성되어 프로그램 종료까지 유지된다는 말이다.
