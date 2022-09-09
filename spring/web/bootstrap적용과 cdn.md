# bootstrap 적용과 cdn

## bootstrap cdn으로 적용하기
* 다운로드 받지 않고도 부트스트랩을 사용하는것이 가능하다.
* 바로 cdn을 사용하는 것이다.
* 부트스트랩 quick start에 cdn링크가 포함된 기본 템플릿이 있다.
* 복사해서 나의 입맛에 맞게 사용하면된다.

## 예시코드
```
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
  </head>
  <body>
    <h1>Hello, world!</h1>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
  </body>
</html>
```

## CDN이란?
* cdn : content deivery network 
* 컨텐츠를 효율적으로 전달하기위해서 여러 노드를 가진 네트워크에 데이터를 저장하여 제공하는 시스템이다.
* 많은 회사들이 부트스트랩을 cdn방식으로 사용한다.
* 간편하기 때문이다.
