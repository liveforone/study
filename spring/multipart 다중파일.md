# multipartFile 다중파일로 업로드 하기

## 서비스 로직
* 개인적으로 내가 선호하는 파일 생성 전략으로 저장한다. 
* uuid + _ + orgiName = saveFileName
```
@Transactional
public void saveImage(List<MultipartFile> uploadFile, ImageFile imageFile, Long id) throws IOException {
    Item item = itemRepository.findById(id);
    
    for (MultipartFile multipartFile : uploadFile) {
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(saveFileName));
        imageFile.setSaveFileName(saveFileName);
        imageFile.setItem(item);
        imageFileRepository.save(imageFile);
    }
}
```

## 뷰
* mvc 프로젝트인 경우에는 enctype="multipart/form-data" : form태그에 저장한다.
