# Git Command(명령어)

## 처음 프로젝트 시작
1. git init
2. git add 파일이름(혹은 .)
3. git status : 상태 확인 및 브랜치이름 확인
4. git commit -m "메세지"
5. git remote -v : 원격 연결 확인
6. git remote add origin url.git
7. git ls-remote origin : 원격 저장소 브랜치 이름 확인
8. git branch -M main
9. git push origin main

## 깃 클론
1. git clone url.git : 클론시에는 .git 파일이 클론한 폴더안에 생성됨. 따라서 2번명령어 실행
2. cd 해서 해당 프로젝트 폴더로 이동(폴더 자체를 clone 하기 때문)
3. git add .(또는 파일이름)
4. git commit -m "메세지"
5. git status : 브랜치 이름 보기
6. git push origin 브랜치 이름

<hr>
====! 이때까지는 master 브랜치를 이용해서 push했다 !====
<hr>

## 깃 pull request
1. 레포를 fork한다.
2. 깃 클론, 클론하면 fork한 레포의 리모트가 등록된다.
3. remote add(fork저장소가 아닌 원본저장소를 add한다. 이름은 origin이 아닌 father과 같이 하는것이 좋다.)
4. 브랜치를 만든다.(master이외의)
5. git status : 브랜치 이름보기
6. git pull origin 브랜치이름
7. 후에 나의 리포지토리(origin)에서 pull request를 한다.
8. 원본 레포의 주인은 pull request를 merge하거나 reject한다.
9. git pull 원본remote이름(father) : 코드 동기화 해준다.
10. git branch -d 삭제할 내가 만든 브랜치
11. [링크](https://inpa.tistory.com/entry/GIT-%E2%9A%A1%EF%B8%8F-%EA%B9%83%ED%97%99-PRPull-Request-%EB%B3%B4%EB%82%B4%EB%8A%94-%EB%B0%A9%EB%B2%95-folk-issue)

## work flow to merge
* git init
* git remote add origin url.git
* git checkout -b 개발전용브랜치 : 브랜치 생성 및 변경
* git add file
* git commit -m "message"
* git ls-remote origin : 원격 저장소 브랜치 이름 확인
* git checkout master/main : 확인한 이름을 사용
* git merge 개발전용브랜치
* git push origin 원격저장소 메인 브랜치
* git branch -d 개발전용 브랜치 : 브랜치 삭제
