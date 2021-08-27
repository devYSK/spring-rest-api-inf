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
 
|basePrice|maxPrice| - |
|--------|------|-------|
|0 |100 선착순 등록|
|0 |0 |무료|
|100 |0 |무제한 경매 (높은 금액 낸 사람이 등록)|
|100 |200 |제한가 선착순 등록,<br>처음 부터 200을 낸 사람은 선 등록.<br>100을 내고 등록할 수 있으나 더 많이낸 사람에 의해 밀려날 수 있음. |



# 섹션 2. 2. 이벤트 생성 API 개발

스프링 부트 슬라이스 테스트
### @WebMvcTest
  * MockMvc 빈을 자동 설정 해준다. 따라서 그냥 가져와서 쓰면 됨.
  * 웹 관련 빈만 등록해 준다. (슬라이스)

### MockMvc
  * 스프링 MVC 테스트 핵심 클래스
  * 웹 서버를 띄우지 않고도 스프링 MVC (DispatcherServlet)가 요청을 처리하는 과정을 확인할 수 있기 때문에 컨트롤러 테스트용으로 자주 쓰임.

### 테스트 할 것
* 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인.
  * Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인.
  * id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인

* 입력값으로 누가 id나 eventStatus, offline, free 이런 데이터까지 같이 주면?
  * Bad_Request로 응답 vs ​받기로 한 값 이외는 무시

* 입력 데이터가 이상한 경우 Bad_Request로 응답
  * 입력값이 이상한 경우 에러
  * 비즈니스 로직으로 검사할 수 있는 에러
  * 에러 응답 메시지에 에러에 대한 정보가 있어야 한다.

* 비즈니스 로직 적용 됐는지 응답 메시지 확인
  * offline과 free 값 확인

* 응답에 HATEOA와 profile 관련 링크가 있는지 확인.
  * self (view)
  * update (만든 사람은 수정할 수 있으니까)
  * events (목록으로 가는 링크)

* API 문서 만들기
  * 요청 문서화
  * 응답 문서화
  * 링크 문서화
  * profile 링크 추가

---

### @RestController
* @ResponseBody를 모든 메소드에 적용한 것과 동일하다.

### ResponseEntity를 사용하는 이유
* 응답 코드, 헤더, 본문 모두 다루기 편한 API

### Location URI 만들기
* HATEOS가 제공하는 linkTo(), methodOn() 사용
  * org.springframework.hateoas.server.mvc.`WebMvcLinkBuilder` 패키지명으로 변경되었다. 


### 객체를 JSON으로 변환
* ObjectMapper 사용

### 테스트 할 것
* 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인.
  * Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인.
  * id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인

## 이벤트 API 테스트 클래스 생성

## 이벤트 Repository

스프링 데이터 JPA
  * JpaRepository 상속 받아 만들기

Enum을 JPA 맵핑시 주의할 것
  * @Enumerated(EnumType.STRING)

@MockBean
  * Mockito를 사용해서 mock 객체를 만들고 빈으로 등록해 줌.
  * (주의) 기존 빈을 테스트용 빈이 대체 한다.

테스트 할 것

* 입력값들을 전달하면 JSON 응답으로 201이 나오는지 확인.
  * Location 헤더에 생성된 이벤트를 조회할 수 있는 URI 담겨 있는지 확인.
  * id는 DB에 들어갈 때 자동생성된 값으로 나오는지 확인


## 입력값 제한하기

#### 입력값 제한
* id (자동생성) 또는 입력 받은 데이터로 계산해야 하는 값들은 입력을 받지 않아야 한다.
  * 입력 받지 말아야 되는 값은 제한해야한다.  
  * ex) id 값이 100이면 안되고 free 값이 true이면 안될 경우의 테스트 
  * ```java
     mockMvc.perform(post("/api/events/")
              ... // 생략
              .andExpect(jsonPath("id").value(Matchers.not(100)))
              .andExpect(jsonPath("free").value(Matchers.not(true)))
    ```

* EventDto 적용하여 컨트롤러의 입력값을 테스트 
  * DTO -> 도메인 객체로 값 복사
    *  ModelMapper 라이브러리 
    * ```xml
      <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>2.3.1</version>
      </dependency>
      ```
    * 공용으로 사용할 수 있기 때문에 Bean으로 등록하여 사용하는 경우가 좋다.


#### 통합 테스트로 전환
* @WebMvcTest 빼고 다음 애노테이션 추가
  * @SpringBootTest
  * @AutoConfigureMockMvc
  * EventControllerTests내의  Repository @MockBean 코드 제거
    
## 입력값 이외에 에러 발생

#### ObjectMapper 커스터마이징
* spring.jackson.deserialization.fail-on-unknown-properties=true
  * ObjectMapper 확장 기능 
  * deserialization할때 unknown-propertie가 있으면 실패하도록 설정 
  * 우리가 지정하지 않은(받을 수 없는) 프로퍼티들을 받으면 (예를들어 dto에 없는) 에러가 발생하여 400 badrequest를 리턴한다 
  * json을 object로 변환하는 과정을 deserialization
  * 객체를 json으로 변환하는 과정을 serialization

#### 테스트 할 것 
* 입력값으로 누가 id나 eventStatus, offline, free 이런 데이터까지 같이 주면?
  * Bad_Request로 응답​ vs 받기로 한 값 이외는 무시

## Bad Request 처리

#### @Valid와 BindingResult (또는 Errors)
* BindingResult는 항상 @Valid 바로 다음 인자로 사용해야 함. (스프링 MVC)
* @NotNull, @NotEmpty, @Min, @Max, ... 사용해서 입력값 바인딩할 때 에러 확인할
수 있음 - 입력값 검증 

* 어노테이션들을 이용하여 검증을 수행한 결과를 컨트롤러의 @Valid 어노테이션을 사용한 객체의 바로 다음 파라미터로 Errors 타입의 객체에다가 에러를 넣어준다. 
  * JS303 valid 관련 사용하려면 `spring-boot-starter-validation` 의존성을 추가해주면 된다. 

```java
// Controller 내 메서드

@PostMapping
public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
    if (errors.hasErrors()) {
          return ResponseEntity.badRequest().build();
    }
    ...

    return ...
  }
```

#### 비즈니스 로직으로 입력


#### 도메인 Validator 만들기
* Validator​ 인터페이스 없이 만들어도 상관없음
테스트 설명 용 애노테이션 만들기
* @Target, @Retention


#### 테스트 할 것
* 입력 데이터가 이상한 경우 Bad_Request로 응답
  * 입력값이 이상한 경우 에러
  * 비즈니스 로직으로 검사할 수 있는 에러
  * 에러 응답 메시지에 에러에 대한 정보가 있어야 한다


## Bad Request 응답

Bad Request 응답 본문 만들기

#### 커스텀 JSON Serializer 만들기
* extends JsonSerializer<T> (Jackson JSON 제공)
* @JsonComponent (스프링 부트 제공)
  * 이 어노테이션을 사용하면 자동으로 빈으로 등록 된다. 
  * 이러면 JsonSerializer를 상속받은 Serializer를 ObjectMapper에 등록이 된다. 
 
#### BindingError
* FieldError 와 GlobalError (ObjectError)가 있음
* objectName
* defaultMessage
* code
* field
* rejectedValue

## 비즈니스 로직 적용
* 비즈니스 로직 적용 됐는지 응답 메시지 확인
  * offline과 free 값 확인


## 매개변수를 이용한 테스트

#### 테스트 코드 리팩토링
* 테스트에서 중복 코드 제거
* 매개변수만 바꿀 수 있으면 좋겠는데?
  * JUnitParams

#### JUnitParams
* https://github.com/Pragmatists/JUnitParams
  * junit4가 제공하는 @Test를 사용해야 한다 
    * junit5가 제공하는 @Test를 사용하면 오류가 난다 

* JUnit5로 사용하는법 
```xml
<dependency>
   <groupId>org.junit.jupiter</groupId>
   <artifactId>junit-jupiter-params</artifactId>
   <version>5.4.2</version>
   <scope>test</scope>
</dependency>
```
```java
@ParameterizedTest
@CsvSource({
        "0, 0, true",
        "0, 100, false",
        "100, 0, false",
})
public void testFree(int basePrice, int maxPrice, boolean isFree) {
    Event event = Event.builder()
            .basePrice(basePrice)
            .maxPrice(maxPrice)
            .build();

    event.update();

    assertThat(event.isFree()).isEqualTo(isFree);
}

@ParameterizedTest
@MethodSource("isOffline")
public void testOffline(String location, boolean isOffline) {
    Event event = Event.builder()
            .location(location)
            .build();

    event.update();

    assertThat(event.isOffline()).isEqualTo(isOffline);
}

private static Stream<Arguments> isOffline() {
    return Stream.of(
            Arguments.of("강남역", true),
            Arguments.of(null, false),
            Arguments.of("", false)
    );
}
```

# 섹션 3. 3. HATEOAS와 Self-Describtive Message 적용

## 스프링 HATEOAS 소개

스프링 HATEOAS
* https://docs.spring.io/spring-hateoas/docs/current/reference/html/
* 링크 만드는 기능
  * 문자열 가지고 만들기
  * 컨트롤러와 메소드로 만들기
* 리소스 만드는 기능
  * `리소스: 데이터(응답본문) + 링크정보`
* 링크 찾아주는 기능
  * Traverson
  * LinkDiscoverers
* 링크
  * href
  * REL(릴레이션, 현재 이 리소스와의 관계)
    * self
    * profile (응답본문에 대한 문서의 링크)
    * update-event
    * query-events

![](img/2021-02-16-18-37-24.png)

* Rest Application 아키텍처 컴포넌트 중의 하나
* 하이퍼미디어를 사용해서 애플리케이션 서버의 정보를 동적으로 클라이언트가 서버와의 정보를 주고 받을 수 방법이다.


## 스프링 HATEOAS 적용

#### EventResource 만들기
* extends ResourceSupport의 문제 
  * @JsonUnwrapped로 해결
  * extends Resource<T>로 해결

* https://docs.spring.io/spring-hateoas/docs/1.0.1.RELEASE/reference/html/
  * 버전이 바뀌면서 확인해야할 것

> * ResourceSupport is now RepresentationModel
> * Resource is now EntityModel
> * Resources is now CollectionModel
> * PagedResources is now PagedModel

* ResourceSupport, Resource 객체가 1.2.1 버전부터 각각   
   RepresentationModel, EntityModel로 변경

#### 테스트 할 것
* 응답에 HATEOA와 profile 관련 링크가 있는지 확인.
  * self (view)
  * update (만든 사람은 수정할 수 있으니까)
  * events (목록으로 가는 링크)


#### 테스트시 한글 깨짐 문제

* https://jehuipark.github.io/spring/boot-2-2-x-mock-mvc-encoding-issue


#### JSON으로 받을때 객체 이름 없이 받는법 

### `@JsonUnwrapped 어노테이션`

@Getter
public class EventResource extends RepresentationModel {

    @JsonUnwrapped
    private Event event;

    public EventResource(Event event) {
        this.event = event;
    }
}

```json
{
  "event" : -{
    "id" : 1,
    "name" : Spring,
    "description" : REST API Development with Spring,
    ...
  },
  "_links" : -{
    "query-events" : -{
      "href" : http://localhost/api/events
      },
    ...
  }
}
```
위와 같은 Json형태에서 객체 이름을 빼준다. (unwrapped)
  * "event"를 뺀다 
```json
  {
    "id" : 1,
    "name" : Spring,
    "description" : REST API Development with Spring,
    ...
  }
  "_links" : -{
    "query-events" : -{
      "href" : http://localhost/api/events
      },
    ...
  }
}
```

EntityModel를 상속받은 Resource 객체는 @JsonUnwrapped를 사용하지 않아도
unwrapped 된다. 

```java
EntityModel eventResource = EntityModel.of(newEvent);
eventResource.add(linkTo(EventController.class).slash(eventwithSelfRel());
eventResource.add(linkTo(EventController.class).withRel("query-events"));
eventResource.add(selfLinkBuilder.withRel("update-event"));
```
## 스프링 REST Docs 소개

https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/

이 라이브러리는 우리가 만든 테스트를 실행하면서 요청과 응답, 헤더 등의 정보를 사용하여 문서 조각을 만들 수 있다. (snippets라고 한다)

문서조각을 만들어서 html로 restapi documentation을 만들 수 있다. 

어떻게 스니펫(snippets)을 생성하는가?


Rest docs랑 연동할 수 있는 것

1. 테스트에서 사용하는 MockMvc도 연동가능
2. 스프링 5부터 지원하는 WebTestClient을 사용하여 연동
3. TestNG, JUnit5도 사용 가능 


```java
this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk())
	.andDo(document("index", links( // 1 
			linkWithRel("alpha").description("Link to the alpha resource"), // 2 
			linkWithRel("bravo").description("Link to the bravo resource")))); // 3
```
1. document라는 메서드를 사용해서 현재 이 테스트를 실행한 결과(snippets)을 어떤 디렉토리 아래("index")에다가 만드는 것


#### 사용법

@AutoConfigureRestDocs 어노테이션을 테스트 클래스에 붙여준다
```java
@AutoConfigureRestDocs
public class EventControllerTests {
}
```


#### REST Docs 코딩
* andDo(document(“doc-name”, snippets))
* snippets
  * links​()
  * requestParameters() + parameterWithName()
  * pathParameters() + parametersWithName()
  * requestParts() + partWithname()
  * requestPartBody()
  * requestPartFields()
  * requestHeaders() + headerWithName()
  * requestFields​() + fieldWithPath()
  * responseHeaders() + headerWithName()
  * responseFields​() + fieldWithPath()
  * ...

* Relaxed*

* Processor
  * preprocessRequest(prettyPrint())
  * preprocessResponse(prettyPrint())
  * ...


#### Constraint
● https://github.com/spring-projects/spring-restdocs/blob/v2.0.2.RELEASE/samples/res
t-notes-spring-hateoas/src/test/java/com/example/notes/ApiDocumentation.java

## 스프링 REST Docs 적용

REST Docs 자동 설정
* @AutoConfigureRestDocs

RestDocMockMvc 커스터마이징
  * RestDocsMockMvcConfigurationCustomizer 구현한 빈 등록
  * @TestConfiguration

테스트 할 것
* API 문서 만들기
  * 요청 본문 문서화
  * 응답 본문 문서화
  * 링크 문서화
    * profile 링크 추가
  * 응답 헤더 문서화



## 스프링 REST Docs 각종 문서 조각 생성하기


#### 이쁘게 문서 출력하기 [JSON 포맷팅]

```java
@TestConfiguration
public class RestDocConfiguration {

    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {

        return new RestDocsMockMvcConfigurationCustomizer() {
            @Override
            public void customize(MockMvcRestDocumentationConfigurer configurer) {
                configurer.operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint());
            }
        };
    }
}

// 테스트 클래스

@Import(RestDocConfiguration.class)
public class EventControllerTests {
...
}
```

* @Import(RestDocConfiguration.class) 어노테이션을 붙여야 한다

요청 필드 문서화

* requestFields() + fieldWithPath()
* responseFields() + fieldWithPath()
* requestHeaders() + headerWithName()
* responseHedaers() + headerWithName()
* links() + linkWithRel()


-> 
```java
.andDo(document("create-event",
        links(
              linkWithRel("self").description("link to self"),
              linkWithRel("query-events").description("link to query events"),
              linkWithRel("update-event").description("link to update an existing event")
              ),
        requestHeaders(
                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                headerWithName(HttpHeaders.CONTENT_TYPE).description("content header")
        ),
        requestFields(
                fieldWithPath("name").description("Name of new event"),
                ...
        ),
        responseHeaders(
                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                ....
        ),
        relaxedResponseFields(
                fieldWithPath("id").description("id of new event"),
                ....
                )
      ))
```

* 모든 필드를 문서화 해야 하는데 일부분 필드들만 문서화 하고 싶다면?
  * relaxed 라는 접두어를 붙인 메소드를 사용하면 된다.
  * 이걸 사용하지 않고 모든 필드를 문서화 하지 않는다면, 오류가 난다.

Relaxed 접두어
* 장점: 문서 일부분만 테스트 할 수 있다.
* 단점: 정확한 문서를 생성하지 못한다

* relaxed 접두어를 사용하거나,  
  fieldWithPath 메소드로 일일이 links를 채워준다  
 


## 스프링 REST Docs 문서 빌드

스프링 REST Docs
* https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/
* pom.xml에 메이븐 플러그인 설정

## 테스트용 DB와 설정 분리하기

테스트 할 때는 계속 H2를 사용해도 좋지만 애플리케이션 서버를 실행할 때 PostgreSQL을
사용하도록 변경하자.
/scripts.md 참고
1. PostgreSQL 드라이버 의존성 추가
```
<dependency>
<groupId>org.postgresql</groupId>
<artifactId>postgresql</artifactId>
</dependency>
```

2. 도커로 PostgreSQL 컨테이너 실행
> docker run --name ndb -p 5432:5432 -e POSTGRES_PASSWORD=pass -d postgres

3. 도커 컨테이너에 들어가보기
> docker exec -i -t ndb bash
su - postgres
psql -d postgres -U postgres
\l
\dt

4. 데이터소스 설정
application.properties



spring.datasource.username=postgres
spring.datasource.password=pass
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.driver-class-name=org.postgresql.Driver

5. 하이버네이트 설정
application.properties

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG


* ## 테스트 클래스에 어노테이션을 붙여야 한다 

* @ActiveProfiles("프로퍼티즈파일명")
  * 이러면 테스트 파일에서만 사용할 프로퍼티즈를 해결 가능 

```java
@SampleConfig
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
@ActiveProfiles("test")
public class EventControllerTests {
  ...
}
```

## API 인덱스 만들기

인덱스 핸들러
* 다른 리소스에 대한 링크 제공
* 문서화

```java
@GetMapping("/api")
public ResourceSupport root() {
  ResourceSupport index = new ResourceSupport();
  index.add(linkTo(EventController.class).withRel("events"));
  return index;
}

// to

@GetMapping("/api")
public RepresentationModel index() {
        var index = new RepresentationModel<>();
        index.add(linkTo(EventController.class).withRel("events"));
        return index;
        }
```


# !!!!!!
* ResourceSupport 클래스가 RepresentationModel클래스로 대체되었다. !!!

테스트 컨트롤러 리팩토링
* 중복 코드 제거 에러 리소스
* 인덱스로 가는 링크 제공

에러 리소스
* 인덱스로 가는 링크 제공

# 섹션 4. 4. 이벤트 조회 및 수정 REST API 개발

## 이벤트 목록 조회 API 구현
페이징, 정렬 어떻게 하지?

  * 스프링 데이터 JPA가 제공하는 Pageable

  * Page<Event>에 안에 들어있는 Event 들은 리소스로 어떻게 변경할까?

    * 하나씩 순회하면서 직접 EventResource로 맵핑을 시킬까..

    * PagedResourceAssembler<T> 사용하기

  * 테스트 할 때 Pageable 파라미터 제공하는 방법
    * page: 0부터 시작
    * size: 기본값 20
    * sort: property,property(,ASC|DESC)


테스트 할 것
* Event 목록 Page 정보와 함께 받기
  * content[0].id 확인
  * pageable 경로 확인
* Sort과 Paging 확인
  * 30개를 만들고, 10개 사이즈로 두번째 페이지 조회하면 이전, 다음 페이지로
가는 링크가 있어야 한다.
  * 이벤트 이름순으로 정렬하기
  * page 관련 링크
* Event를 EventResource로 변환해서 받기
  * 각 이벤트 마다 self
* 링크 확인
  * self
  * profile
  * (create)
* 문서화

## 이벤트 조회 API 구현

## 이벤트 수정 API 구현

## 테스트 코드 리팩토링

# 섹션 5. 5. REST API 보안 적용

## Account 도메인 추가

* OAuth2로 인증을 하려면 일단 Account 부터
  * id

  *  email

  *  password

  *  roels
* AccountRoles
  * ADMIN, USER

* JPA 맵핑

  * @Table(“Users”)

* JPA enumeration collection mapping

  * @ElementCollection(fetch = FetchType.EAGER)
  
  * @Enumerated(EnumType.STRING) private
  
  * Set<AccountRole> roles;

* Event에 owner 추가
  * @ManyToOne

  * Account manager;
## 스프링 시큐리티 적용
* 스프링 시큐리티
  * 웹 시큐리티 (Filter 기반 시큐리티)

  * 메소드 시큐리티

  * 이 둘 다 Security Interceptor를 사용합니다.

  * 리소스에 접근을 허용할 것이냐 말것이냐를 결정하는 로직이 들어있음.

* ![](img/f001bbcd.png)

* 의존성 추가
```manifest
<dependency>
    <groupId>org.springframework.security.oauth.boot</groupId>
    <artifactId>spring-security-oauth2-autoconfigure</artifactId>
    <version>2.1.0.RELEASE</version>
</dependency>
```

## 예외 테스트

## 스프링 시큐리티 기본 설정
* 시큐리티 필터를 적용하기 않음...
  * /docs/index.html

* 로그인 없이 접근 가능
  * GET /api/events

  * GET /api/events/{id}

* 로그인 해야 접근 가능
  * 나머지 다...
  * POST /api/events
  * PUT /api/events/{id{
  * ...

* 스프링 시큐리티 OAuth 2.0
  * AuthorizationServer: OAuth2 토큰 발행(/oauth/token) 및 토큰 인증(/oauth/authorize)
    * Oder 0 (리소스 서버 보다 우선 순위가 높다.)
  * ResourceServer: 리소스 요청 인증 처리 (OAuth 2 토큰 검사)
    * Oder 3 (이 값은 현재 고칠 수 없음)

* 스프링 시큐리티 설정
  * @EnableWebSecurity

  * @EnableGlobalMethodSecurity

  * extends WebSecurityConfigurerAdapter

  * PasswordEncoder: PasswordEncoderFactories.  createDelegatingPassworkEncoder()

  * TokenStore: InMemoryTokenStore

  * AuthenticationManagerBean

  * configure(AuthenticationManagerBuidler auth)
    *  userDetailsService
    *  passwordEncoder● configure(HttpSecurity http)
  
    * /docs/**: permitAll
  * configure(WebSecurty web)
    * ignore ​
      * /docs/**
      * /favicon.ico
  * PathRequest.toStaticResources() 사용하기

```java

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/docs/index.html").anonymous()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).anonymous();
    }

    // 시큐리티 인증이 필요없는 부분을 설정.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/docs/index.html");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적인 리소스 무시

    }
```
* 위 두 코드는 동일하지만, 위와 같이 처리할 경우 성능 상으론 web configure가 더 낫다. 
  * filter chain을 탈 때 두 메서드 기능이 다르기 때문. 
  * 요청 처리 필터 갯수가 다르다. 


## 스프링 시큐리티 폼 인증 설정
```java
@Override protected void configure(HttpSecurity http) throws Exception{
        http
        .anonymous()
        .and()
        .formLogin()
        .and()
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET,"/api/**").authenticated()
        .anyRequest().authenticated();
}
```
* 익명 사용자 사용 활성화

* 폼 인증 방식 활성화
  * 스프링 시큐리티가 기본 로그인 페이지 제공
 
* 요청에 인증 적용
  * /api 이하 모든 GET 요청에 인증이 필요함. (permitAll()을 사용하여 인증이
필요없이 익명으로 접근이 가능케 할 수 있음)
  * 그밖에 모은 요청도 인증이 필요함.

## 스프링 시큐리티 OAuth2 인증 서버 설정
```manifest

<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <version>${spring-security.version}</version>
    <scope>test</scope> 
</dependency>
```

토큰 발행 테스트

* User
* Client
* POST /oauth/token
  * HTTP Basic 인증 헤더 (클라이언트 아이디 + 클라이언트 시크릿)
  * 요청 매개변수 (MultiValuMap<String, String>)
    * grant_type: password

    * username
    * password
  * 응답에 access_token 나오는지 확인

* Grant Type: Password
  * Granty Type: 토큰 받아오는 방법

  * 서비스 오너가 만든 클라이언트에서 사용하는 Grant Type

  * https://developer.okta.com/blog/2018/06/29/what-is-the-oauth2-password-grant
  
* AuthorizationServer 설정
  * @EnableAuthorizationServer
  * extends AuthorizationServerConfigurerAdapter
  * configure(AuthorizationServerSecurityConfigurer security)
    * PassswordEncode 설정
  * configure(ClientDetailsServiceConfigurer clients)
    * 클라이언트 설정
    * grantTypes
      * password
      * refresh_token
  * scopes

  * secret / name

  * accessTokenValiditySeconds

  *  refreshTokenValiditySeconds

* AuthorizationServerEndpointsConfigurer
  * tokenStore

  * authenticationMaanger

  * userDetailsService



## 리소스 서버 설정
* 테스트 수정
  * GET을 제외하고 모두 엑세스 토큰을 가지고 요청 하도록 테스트 수정

* ResourceServer 설정
  * @EnableResourceServer

  * extends ResourceServerConfigurerAdapter

  * configure(ResourceServerSecurityConfigurer resources)
    * 리소스 ID

  * configure(HttpSecurity http)
    * anonymous
    * GET /api/** : permit all
    * POST /api/**: authenticated
    * PUT /api/**: authenticated
    * 에러 처리
    * accessDeniedHandler(OAuth2AccessDeniedHandler())
    

## 문자열을 외부 설정으로 빼내기

* 기본 유저 만들기
  *  ApplicationRunner
  * Admin
  * User


* 외부 설정으로 기본 유저와 클라이언트 정보 빼내기
* @ConfigurationProperties

* 하드코딩을 안하는것. 
  * 하드코딩을 안하고 외부 설정파일에서 문자열을 주입받는다.  
* 다음과 같이 클래스를 만들어 빈으로 설정하고 properties파일에서 바인딩을 받는다. 
* application-properties에서 바인딩할 문자열 지정.

* 다음 의존성을 추가하고, 프로젝트를 한번 빌드 해야 protperties파일에서 인식이 된다. 
```manifest
      <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
        </dependency>

```

```java

@Component
@ConfigurationProperties(prefix = "my-app")
@Getter @Setter
public class AppProperties {

    // 외부에서 바인딩 받을것들임

    @NotEmpty
    private String adminUsername;

    @NotEmpty
    private String adminPassword;

    @NotEmpty
    private String userUsername;

    @NotEmpty
    private String userPassword;

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String clientSecret;
}
```

## 이벤트 API 점검
* 토큰 발급 받기
  * POST /oauth/token
  * BASIC authentication 헤더○ client Id(myApp) + client secret(pass)

  * 요청 본문 폼
    * username: admin@email.com
    * password: admin
    * grant_type: password

* 토큰 갱신하기
  *  POST /oauth/token
  * BASIC authentication 헤더
    * client Id(myApp) + client secret(pass)
  * 요청 본문 폼
    * token: 처음에 발급받았던 refersh 토큰
    * grant_type: refresh_token

* 이벤트 목록 조회 API

  * 로그인 했을 때
  *  이벤트 생성 링크 제공

* 이벤트 조회
  * 로그인 했을 때
    * 이벤트 Manager인 경우에는 이벤트 수정 링크 제공

## 현재 사용자 조회

* 현재 요청한 유저의 사용자 정보를 참조할 수 있다.

* SecurityContext
  * 자바 ThreadLocal 기반 구현으로 인증 정보를 담고 있다.

  *  인증 정보 꺼내는 방법: Authentication authentication =
* SecurityContextHolder.getContext().getAuthentication();

* @AuthenticationPrincipal spring.security.User user
  * 인증 안한 경우에 null

  * 인증 한 경우에는 username과 authorities 참조 가능

* spring.security.User를 상속받는 클래스를 구현하면
  * 도메인 User를 받을 수 있다.

  * @AuthenticationPrincipa me.whiteship.user.UserAdapter

  * Adatepr.getUser().getId()

* SpEL을 사용하면
* @AuthenticationPrincipa(expression=”account”) me.whiteship.user.Account
```java
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "account")
public @interface CurrentUser { } 커스텀
```
* 애노테이션을 만들면
  * @CurrentUser Account account

  *  엇? 근데 인증 안하고 접근하면..?

* expression = "#this == 'anonymousUser' ? null : account"
  * 현재 인증 정보가 anonymousUse 인 경우에는 null을 보내고 아니면 “account”를
꺼내준다.

* 조회 API 개선

  * 현재 조회하는 사용자가 owner인 경우에 update 링크 추가 (HATEOAS)

* 수정 API 개선 현재 사용자가 이벤트 owner가 아닌 경우에
  * 403 에러 발생


* 인증된 현재 사용자의 정보를 조회할려면 ? 
```java
 public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler){

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User principal=authentication.getPrincipal(); // AccountService에서 리턴한 스프링 시큐리티의 User 객체가 나온다
        }
```

* 컨트롤러에서 다음과 같이 주입 받을수도 있따
* 인증된 사용자 정보 
  * @AuthenticationPrincipal User user

```java
@GetMapping //get으로 조회한다... get으로 작성해놓고 post요청하면 405error발생함
public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler,
                                      @AuthenticationPrincipal User user
                                      ){
```

* 스프링 시큐리티의 User 인터페이스가 아닌 우리 프로젝트의 Account 객체를 받고싶다면?
  * UserDetailsService의 구현체인 AccountService의 loadUserByUsername 메서드에서 Account를 리턴해야 한다.

```java

// AccountAdapter 클래스 생성 (spring security User를 상속받는 )
@Getter
public class AccountAdapter extends User {

  private Account account;

  public AccountAdapter(Account account) {
    super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));

  }

  private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
    return roles.stream()
            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
            .collect(Collectors.toList());
  }
}
// 그리고 아래와 같이 적용해서 리턴한다 


@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
  ...

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Account account = accountRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return new User(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
  }
  ...
}

// 위의 기존의 코드를 아래와 같이 바꾼다 

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
  ...

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Account account = accountRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return new AccountAdapter(account); // 이부분  
  }
  ...
}

```

* 이러면 @AuthenticationPrincipal 어노테이션이 붙은 객체에 AccountAdapter를 주입해준다. 

* ## AccountAdapter 말고 Account를 받을라면? 
```java
@GetMapping //get으로 조회한다... get으로 작성해놓고 post요청하면 405error발생함
public ResponseEntity queryEvents(Pageable pageable,
                                  PagedResourcesAssembler<Event> assembler, 
                                  @AuthenticationPrincipal(expression = "account") Account account){
        ...
}

// 다음과 같은 어노테이션을 사용해서 코드를 줄일수도 있따. 


@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "expression = \"#this == 'anonymousUser' ? null : account\"")
//현재 인증 정보가 anonymousUse 인 경우에는 null을 보내고 아니면 “account”를 꺼내준다
public @interface CurrentUser {
}

// 코드량이 줄었따!  
  
@GetMapping //get으로 조회한다... get으로 작성해놓고 post요청하면 405error발생
public ResponseEntity queryEvents(Pageable pageable,
                                    PagedResourcesAssembler<Event> assembler,
                                    @CurrentUser Account account){
        ...
}


```

## 출력값 제한하기
* 생성 API 개선
  * Event owner 설정

  * 응답에서 owner의 id만 보내 줄 것.
```json
{ "
id" : 4, "name" : "test
3PISM1Ju", "description" :
"test event", ... ​"free" : false,
"eventStatus" : "DRAFT", "owner" : { "id" : 3, "email" : "keesun@email.com", "password" :
"{bcrypt}$2a$10$3z/rHmeYsKpoOQR3aUq38OmZjZNsrGfRZxSnmpLfL3lpLxjD5/JZ6",
"roles" : [ "USER", "ADMIN" ] },
```
* 패스워드까지 나와버리는 경우가있음. 

JsonSerializer<User> 구현
● @JsonSerialize(using) 설정

# 섹션 6. 보강

## 깨진 테스트 살펴보기

## 스프링 부트 업그레이드

##  함께 학습하면 좋은 로드맵

## [초급~활용] 마이크로소프트 개발자가 알려주는 자바 스프링(spring) 완전 정복

##  1443 227


Process finished with exit code 0
