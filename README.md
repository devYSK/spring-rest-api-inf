# Spring-REST-API-Study
인프런 Spring-REST-API 강좌를(백기선님) 학습하고 정리한 내용입니다.

# [섹션 1. 1. REST API 및 프로젝트 소개](#섹션-1.-1.-REST-API-및-프로젝트-소개)
* [REST API](#REST-API)
* [Event REST API](#Event-REST-API)
* [Postman & Restlet](#Postman-&-Restlet)
* [Project 만들기](#Project-만들기)
* [이벤트 도메인 구현](#이벤트-도메인-구현)
* [이벤트 비즈니스 로직](#이벤트-비즈니스-로직)

# [섹션 2. 2. 이벤트 생성 API 개발](#섹션-2.-2.-이벤트-생성-API-개발)
* [이벤트 API 테스트 클래스 생성](#이벤트-API-테스트-클래스-생성)
* [이벤트 Repository](#이벤트-Repository)
* [입력값 제한하기](#입력값-제한하기)
* [입력값 이외에 에러 발생](#입력값-이외에-에러-발생)
* [Bad Request 처리](#Bad-Request-처리)
* [Bad Request 응답](#Bad-Request-응답)
* [비즈니스 로직 적용](#비즈니스-로직-적용)
* [매개변수를 이용한 테스트](#매개변수를-이용한-테스트)

# [섹션 3. 3. HATEOAS와 Self-Describtive Message 적용](#섹션-3.-3.-HATEOAS와-Self-Describtive-Message-적용)
* [스프링 HATEOAS 소개](#스프링-HATEOAS-소개)
* [스프링 HATEOAS 적용](#스프링-HATEOAS-적용)
* [스프링 REST Docs 소개](#스프링-REST-Docs-소개)
* [스프링 REST Docs 적용](#스프링-REST-Docs-적용)
* [스프링 REST Docs 각종 문서 조각 생성하기](#스프링-REST-Docs-각종-문서-조각-생성하기)
* [스프링 REST Docs 문서 빌드](#스프링-REST-Docs-문서-빌드)
* [테스트용 DB와 설정 분리하기](#테스트용-DB와-설정-분리하기)
* [API 인덱스 만들기](#API-인덱스-만들기)

# [섹션 4. 4. 이벤트 조회 및 수정 REST API 개발](#섹션-4.-4.-이벤트-조회-및-수정-REST-API-개발)
* [이벤트 목록 조회 API 구현](#이벤트-목록-조회-API-구현)
* [이벤트 조회 API 구현](#이벤트-조회-API-구현)
* [이벤트 수정 API 구현](#이벤트-수정-API-구현)
* [테스트 코드 리팩토링](#테스트-코드-리팩토링)

# [섹션 5. 5. REST API 보안 적용](#섹션-5.-5.-REST-API-보안-적용)
* [Account 도메인 추가](#Account-도메인-추가)
* [스프링 시큐리티 적용](#스프링-시큐리티-적용)
* [예외 테스트](#예외-테스트)
* [스프링 시큐리티 기본 설정](#스프링-시큐리티-기본-설정)
* [스프링 시큐리티 폼 인증 설정](#스프링-시큐리티-폼-인증-설정)
* [스프링 시큐리티 OAuth2 인증 서버 설정](#스프링-시큐리티-OAuth2-인증-서버-설정)
* [리소스 서버 설정](#리소스-서버-설정)
* [문자열을 외부 설정으로 빼내기](#문자열을-외부-설정으로-빼내기)
* [이벤트 API 점검](#이벤트-API-점검)
* [현재 사용자 조회](#현재-사용자-조회)
* [출력값 제한하기](#출력값-제한하기)

# [섹션 6. 보강](#섹션-6.-보강)
* [깨진 테스트 살펴보기](#깨진-테스트-살펴보기)
* [스프링 부트 업그레이드](#스프링-부트-업그레이드)
* [ 함께 학습하면 좋은 로드맵](#-함께-학습하면-좋은-로드맵)
* [[초급~활용] 마이크로소프트 개발자가 알려주는 자바 스프링(spring) 완전 정복](#[초급~활용]-마이크로소프트-개발자가-알려주는-자바-스프링(spring)-완전-정복)
* [ 1443 227](#-1443-227)


# 섹션 1. 1. REST API 및 프로젝트 소개

## REST API

#### API : A​pplication​ P​rogramming ​I​nterface의 약자

### REST
* RE​presentational​S​tate​T​ransfer
* 인터넷상의 시스템간의 상호운용성(interoperability)을 제공하는 방법 중 하나
* 시스템 제각각의 ​`독립적인 ​​진화 `​를보장하기 위한 방법
* REST API: REST 아키텍처 스타일을 따르는 API

* [발표 영상](#https://www.youtube.com/watch?v=RP_f5dMoHFc)

### REST아키텍처스타일 (​발표 영상의 ​11분부터 )
* Client-Server
* Stateless
* Cache
* Uniform Interface
* Layered System
* Code-On-Demand (optional)

### Uniform Interface (발표영상11분40초)
* Identification of resources
* manipulation of resources through represenations
* [`self-descrptive messages`](#Self-descriptive-message)
* [hypermedia as the engine of appliaction state (HATEOAS)](#HATEOAS)

* #### 두문제를좀더자세히살펴보자. (발표영상37분50초)
    * ##### Self-descriptive message
      * 메시지 스스로 메시지에 대한 설명이 가능해야한다.
        * 데이터(응답메시지)만 봐서 응답 메시지가 뭘 뜻하는지(해석) 알 수 없으면 안된다. 
        * 그러므로 해석할 수 있게 API 문서의 본문을(링크, 프로필) 메시지에 담아줘야한다
         
      * 서버가 변해서 메시지가 변해도 클라이언트는 그 메시지를 보고 해석이  가능하다.
        * 메시지를 보고 해석할 수 있는 정보가 메시지 안에 담겨 있으니까.  
      * 확장가능한 커뮤니케이션
     
    * ##### HATEOAS
      * 응답에 애플리케이션 상태 변화가 가능한 하이퍼미디어 링크가 들어  있어야 한다.  
      * 하이퍼미디어(링크)를 통해 애플리케이션 상태변화가 가능해야한다.
      * 링크정보를 동적으로 바꿀 수 있다.​(Versioning할필요없이!)
      * [HATEOAS란 1.](#https://wallees.wordpress.com/2018/04/19/rest-api-hateoas/)
      * [HATEOAS란 2.](#https://pjh3749.tistory.com/260)
  

* Self-descriptive message해결방법
 
  * 방법1: 미디어타입을 정의하고 IANA에 등록하고 그 미디어타입을 리소스리턴할때 Content-Type으로사용한다.
     
  * 방법2: profile링크헤더를 추가한다. (발표영상41분50초)
    * 브라우저들이아직스팩지원을잘안해
    * 대안으로​ [HAL](#http://stateless.co/hal_specification.html)​의 링크데이터에​ [profile링크](#http://stateless.co/hal_specification.html) ​추가

* HATEOAS해결방법
* 방법1: 데이터에링크제공○ 링크를 어떻게 정의할 것인가? HAL
* 방법2: 링크 헤더나 Location을 제공

* [바람직한 Restapi 예제 - 깃허브 공식 문서](#https://docs.github.com/en/free-pro-team@latest/rest/reference/issues) 

---

## Event REST API

이벤트 등록, 조회 및 수정 API
* GET /api/events
  * 이벤트 목록 조회 REST API (로그인 안 한 상태)
    * 응답에 보여줘야 할 데이터
      * 이벤트 목록
      * 링크
        * self
        * profile: 이벤트 목록 조회 API ​문서​로 링크
        * get-an-event: 이벤트 하나 조회하는 API 링크
        * next: 다음 페이지 (optional)
        * prev: 이전 페이지 (optional)
    * 문서?
      * 스프링 REST Docs로 만들 예정

* 이벤트 목록 조회 REST API (로그인 한 상태)
    * 응답에 보여줘야 할 데이터
      * 이벤트 목록
      * 링크
        * self
        * profile: 이벤트 목록 조회 API ​문서​로 링크
        * get-an-event: 이벤트 하나 조회하는 API 링크
        * create-new-event: 이벤트를 생성할 수 있는 API 링크
        * next: 다음 페이지 (optional)
        * prev: 이전 페이지 (optional)
    
    * 로그인 한 상태???? (stateless라며..)
      * 아니, 사실은 Bearer 헤더에 유효한 AccessToken이 들어있는 경우!

* POST /api/events
  * 이벤트 생성

* GET /api/events/{id}
  * 이벤트 하나 조회

* PUT /api/events/{id}
  * 이벤트 수정

## Events API 사용 예제
1. (토큰 없이) 이벤트 목록 조회
    a. create 안 보임
2. access token 발급 받기 (A 사용자 로그인)
3. (유효한 A 토큰 가지고) 이벤트 목록 조회
    a. create event 보임
4. (유효한 A 토큰 가지고) 이벤트 만들기
5. (토큰 없이) 이벤트 조회
    a. update 링크 안 보임
6. (유효한 A 토큰 가지고) 이벤트 조회
    a. update 링크 보임
7. access token 발급 받기 (B 사용자 로그인)
8. (유효한 B 토큰 가지고) 이벤트 조회
    a. update 안 보임

* REST API 테스트 클라이언트 애플리케이션
- 크롬 플러그인 - Restlet
- 애플리케이션 - PostMan


## Postman & Restlet

## Project 만들기
추가할 의존성
* Web
* JPA
* HATEOAS
* REST Docs
* H2
* PostgreSQL
* Lombok

자바 버전 11로 시작
* 자바는 여전히 무료다

## 이벤트 도메인 구현

* 왜 @EqualsAndHasCode에서 of를 사용하는가
  * EqualsAndHasCode를 구현할 때 모든 필드를 사용하는데, 객체간의 연관관계가 있을 때, 상호 참조하는 관계가 되버리면 서로 계속 참조하여 스택 오버플로우가 발생할 수 있기 때문에 보통 id + 몇몇필드로만 비교한다. 
   
* 왜 @Builder를 사용할 때 @AllArgsConstructor가 필요한가

* 애노테이션 줄일 수 없나
  * 롬복 애노테이션은 커스텀해서 다 넣어 사용할 수 없다. -> 방법이 없다.

* @Data를 쓰지 않는 이유
  * EqualsAndHasCode도 같이 구현 해주는데, 모든 필드를 사용한다.   
    그래서 상호참조 문제 때문에 엔티티에는 사용하지 않는다.


## 이벤트 비즈니스 로직

Event 생성 API
* 다음의 입력 값을 받는다.
  * name
  * description
  * beginEnrollmentDateTime
  * closeEnrollmentDateTime
  * beginEventDateTime
  * endEventDateTime
  * location (optional) 장소. 이게 없으면 온라인 모임
  * basePrice (optional) // 참가비
  * maxPrice (optional) //  
  * limitOfEnrollment // 몇명까지 등록가능한지 제한 


* basePrice와 maxPrice의 경우의 수와 각각 로직 
|basePrice|maxPrice| |
|--------|------|----|
|0 |100 선착순 등록|
|0 |0 |무료|
|100 |0 |무제한 경매 (높은 금액 낸 사람이 등록)|
|100 |200 |제한가 선착순 등록  ,<br>처음 부터 200을 낸 사람은 선 등록.<br>100을 내고 등록할 수 있으나 더 많이낸 사람에 의해 밀려날 수 있음.|


# 섹션 2. 2. 이벤트 생성 API 개발

## 이벤트 API 테스트 클래스 생성

## 이벤트 Repository

## 입력값 제한하기

## 입력값 이외에 에러 발생

## Bad Request 처리

## Bad Request 응답

## 비즈니스 로직 적용

## 매개변수를 이용한 테스트

# 섹션 3. 3. HATEOAS와 Self-Describtive Message 적용

## 스프링 HATEOAS 소개

## 스프링 HATEOAS 적용

## 스프링 REST Docs 소개

## 스프링 REST Docs 적용

## 스프링 REST Docs 각종 문서 조각 생성하기

## 스프링 REST Docs 문서 빌드

## 테스트용 DB와 설정 분리하기

## API 인덱스 만들기

# 섹션 4. 4. 이벤트 조회 및 수정 REST API 개발

## 이벤트 목록 조회 API 구현

## 이벤트 조회 API 구현

## 이벤트 수정 API 구현

## 테스트 코드 리팩토링

# 섹션 5. 5. REST API 보안 적용

## Account 도메인 추가

## 스프링 시큐리티 적용

## 예외 테스트

## 스프링 시큐리티 기본 설정

## 스프링 시큐리티 폼 인증 설정

## 스프링 시큐리티 OAuth2 인증 서버 설정

## 리소스 서버 설정

## 문자열을 외부 설정으로 빼내기

## 이벤트 API 점검

## 현재 사용자 조회

## 출력값 제한하기

# 섹션 6. 보강

## 깨진 테스트 살펴보기

## 스프링 부트 업그레이드

##  함께 학습하면 좋은 로드맵

## [초급~활용] 마이크로소프트 개발자가 알려주는 자바 스프링(spring) 완전 정복

##  1443 227


Process finished with exit code 0
