# manna
## 아무나 만나는 서비스

## Spec
- gradle multi module (이상하게 나눠놓음,,)
- Springboot 2.7.X
- JPA, QueryDsl
- Spring Rest Docs (협업력 상승,,)
  - 문서경로: /docs/index.html
- mariadb (docker 로 설치)
- Elastic Beanstalk 으로 배포할거임,,

## 사용방법
- 서버를 구동하시고,,
- 포스트맨 폴더에서 [MANNA] API Service.vX.X.X.json 파일 받으시고,,
- 포스트맨 깔아서 import 하시고,,
- rest docs 문서도 보는 척 해 보시고,,
- API 호출해 보시고,,
- 앱 만들어서 연동해 보시고,,
- 그 담은 알아서 하시고,,

## Installation mariadb (feat. docker)
- 도커는 알아서 설치 하시고,,
- mac 쓰시면 brew install cask docker

### Download mariadb image from docker hub
```
# docker pull mariadb:10.8.3
```

### Run container from mariadb image
#### 옵션 설명
- --name: 컨테이너명
- -p, --port: 포트맵핑 (ex. -p 3306:3306 -> 로컬머신포트:컨테이너포트)
- -e, --env: 환경변수 (docker image document 에서 꼭 찾아봐야 함)
- -d, --detach: 데몬모드, 백그라운드모드
- 옵션이 무수히 많아서 생략,, (volume, network, link 등 요런것도 자주 씀 찾아보셈)
```
# docker run --name mariadb -p 3306:3306 -e MARIADB_ROOT_PASSWORD='root!@34' -d mariadb:10.8.3
```

### Exec container access
- 컨테이너 내부로 접근
```
# docker exec -it mariadb /bin/sh
```
- mariadb cli 로 db 접속
```
# 컨테이너 내부
# mysql -u root -p
# input password
```

### 테이블 권한 (feat. datagrip)
사실 아까 위에 cli 에서 해도 됨
- datagrip 에서 db 접속 후 create schema
- schema 이름은 manna
- 위에서 만든 schema 의 사용자를 아래와 같이 생성해 줌
```
GRANT ALL PRIVILEGES ON `manna`.* TO 'manna'@'%' IDENTIFIED BY 'manna!@34';
REVOKE GRANT OPTION, LOCK TABLES ON `manna`.* FROM 'manna'@'%';
SHOW GRANTS FOR 'manna'@'%';
```
- 권한옵션이 많아서 GRANT ALL PRIVILEGES 주고 REVOKE 로 옵션 2개 뺌,,
