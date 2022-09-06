# multipartFile 다중파일로 업로드 하기

## 서비스 로직
* 개인적으로 내가 선호하는 파일 생성 전략으로 저장한다. 
* uuid + _ + orgiName = saveFileName
<pre>
@Transactional
public void saveImage(String title, List<MultipartFile> uploadFile) throws IOException {
    for (MultipartFile multipartFile : uploadFile) {
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(saveFileName));
        Image image = Image.builder()
                .title(title)
                .saveFileName(saveFileName)
                .build();
        imageRepository.save(image);
    }
}
</pre>

## 컨트롤러
* 리스트 형식으로 입력받는다
* if문으로 판별후 저장하는데 반드시 식별자를 함께 저장해야한다.(그래야 구분가능함..)
<pre>
@RequestParam List<MultipartFile> uploadFile

if (!uploadFile.isEmpty()) {
    imageService.saveImage(title, uploadFile);
    log.info("Posting Success!!");
    return "redirect:/";
} else {
    log.info("Posting Fail, Because there is no file!!");
    return "/location/wrongPage";
}
</pre>

## 뷰
<pre>
enctype="multipart/form-data" : form태그에 저장한다.
</pre>
<code>
<div class="form-group row">
    <label for="inputFile" class="col-sm-2 col-form-label"><strong>사진 선택(3개)</strong></label>
        <div class="col-sm-10">
            <input class="form-control" type="file" name="uploadFile" multiple="multiple" id="inputFile">
        </div>
    </label>
</div>
</code>
