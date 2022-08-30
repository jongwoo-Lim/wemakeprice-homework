# 위메프 과제 전형


## 요구사항

<details>
<summary>요구 사항</summary>
<div markdown="1">

✅ 모든 문자 입력 가능

✅ 영어, 숫자만 출력

✅ 오름차순
```text
* 영어
    - AaBb....YyZz

* 숫자
    - 0,1,2....
```

✅ 교차 출력
```text
* 영어 숫자 .... 영어 숫자
```

✅ 출력 묶음단위를 통해 몫과 나머지를 계산한다

</div>
</details>

## 개발환경(Dependency)
- Java 11
- Spring Boot 2.7.3
- Spring Web MVC
- Spring Validation
- Thymeleaf
- Gradle

## 프로젝트 패키지 구조
패키지 구조는 전체적으로 쉽게 파악하기 위해 같은 layer 끼리 묶어 계층형 구조로 구성하였습니다.
```bash
📦java
 ┗ 📂com
 ┃ ┗ 📂wemakeprice
 ┃ ┃ ┗ 📂homework
 ┃ ┃ ┃ ┃ ┗ 📂api
 ┃ ┃ ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┣ 📂handler
 ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂item
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂processor
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂reader
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┗ 📂web 
 ┃ ┃ ┃ ┗ 📜App.java
```
## 프로젝트 실행

<details>
<summary>애플리케이션</summary>
<div markdown="1">

```
git clone https://github.com/jongwoo-Lim/wemakeprice-homework.git
cd wemakeprice-homework

./gradlew clean build 또는 ./gradlew clean build -x test (테스트 스킵)

cd ./build/libs/

java -jar homework-0.0.1-SNAPSHOT.jar

주소: http://localhost:8080/
```

</div>
</details>


