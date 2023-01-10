# multipartFile 다중파일로 업로드 하기

## 1. 파일 생성 전략
* 파일명 : uuid + _ + orgiName = saveFileName
* 다중 파일 업로드를 할때 갯수를 제한하는 것보다 
* for-each문으로 모두 돌리고, 크기만 지정하는게 낫다.

## 2. 서비스 로직
```
@Transactional
public void saveFile(List<MultipartFile> uploadFile, Board board) throws IOException {
    for (MultipartFile file : uploadFile) {
        String saveFileName = makeSaveFileName(file);
        file.transferTo(new File(saveFileName));

        UploadFileRequest dto = UploadFileRequest.builder()
                .saveFileName(saveFileName)
                .board(board)
                .build();
        uploadFileRepository.save(UploadFileMapper.dtoToEntity(dto));
    }
}

private String makeSaveFileName(MultipartFile file) {
    UUID uuid = UUID.randomUUID();
    return uuid + "_" + file.getOriginalFilename();
}

@Transactional
public void editFile(List<MultipartFile> uploadFile, Board board) throws IOException {
    deleteFile(board);
    saveFile(uploadFile, board);
}

@Transactional
public void deleteFile(Board board) {
    List<UploadFile> files = uploadFileRepository.findFilesByRoomId(board.getId());

    for (UploadFile uploadFile : files) {
        String saveFileName = uploadFile.getSaveFileName();
        File file = new File("C:\\Temp\\upload\\" + saveFileName);
        if (file.delete()) {
            log.info("file : " + saveFileName + " 삭제 완료");
        }
    }
    uploadFileRepository.deleteBulkFileByRoomId(board);
}
```


## 3. 클라이언트
* enctype="multipart/form-data" : form태그에 저장한다.

## 포스트맨 설정 참조 링크
[링크](https://emoney96.tistory.com/258)
