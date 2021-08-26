package com.ys.restapi.events;

import com.ys.restapi.accounts.Account;
import com.ys.restapi.accounts.AccountRepository;
import com.ys.restapi.accounts.AccountRole;
import com.ys.restapi.accounts.AccountService;
import com.ys.restapi.common.BasicControllerTests;
import com.ys.restapi.common.TestDescription;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class EventControllerTests extends BasicControllerTests {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() {
        this.eventRepository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    @TestDescription("정상적으로 이벤트를 생성하는 테스트 ")
    public void createEvent() throws Exception {

        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 2, 12, 22, 24))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 2, 13, 22, 24))
                .beginEventDateTime(LocalDateTime.of(2021, 2, 14, 22, 24))

                .endEventDateTime(LocalDateTime.of(2021, 2, 15, 22, 24))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();


        mockMvc.perform(post("/api/events/")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())  // 201 응답
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
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
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of begin of new event"),
                                fieldWithPath("basePrice").description("base price of begin of new event"),
                                fieldWithPath("maxPrice").description("max price of begin of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrollment of begin of new event")

                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        relaxedResponseFields( // 이렇게도 사용 가능
//                        responseFields(
                                fieldWithPath("id").description("id of new event"),
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of begin of new event"),
                                fieldWithPath("basePrice").description("base price of begin of new event"),
                                fieldWithPath("maxPrice").description("max price of begin of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrollment of begin of new event"),
                                fieldWithPath("free").description("it tells if this event is free or not "),
                                fieldWithPath("offline").description("it tells if this event is offline or not "),
                                fieldWithPath("eventStatus").description("event status"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.query-events.href").description("link to query event list"),
                                fieldWithPath("_links.update-event.href").description("link to update existing event")


                        )
                ))

        ;


    }

    private String getBearerToken() throws Exception {
        return "Bearer " + getAccessToken();
    }

    private String getAccessToken() throws Exception {
        String username = "youngsoo2@naver.com";
        String password = "youngsoo";

        Account youngsoo = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(youngsoo);

        String clientId = "myApp";
        String clientSecret = "pass";

        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"));

        String responseBody = perform.andReturn().getResponse().getContentAsString();

        Jackson2JsonParser parser = new Jackson2JsonParser();

        return parser.parseMap(responseBody).get("access_token").toString();

    }

    @Test
    @TestDescription("입력 받을 수 없는 값을 사용한 경우 에러가 발생하는 테스트  ")
    public void createEvent_Bad_Request() throws Exception {
        Event event = Event.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 2, 12, 22, 24))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 2, 13, 22, 24))
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 2, 14, 22, 24))
                .endEventDateTime(LocalDateTime.of(2021, 2, 15, 22, 24))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();


        mockMvc.perform(post("/api/events/")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())  // 201 응답

        ;
    }

    @Test
    @TestDescription("입력 값이 비어있는 경우 에러가 발생하는 테스트   ")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @TestDescription("입력 값이 잘못된 경우 에러가 발생하는 테스트  ")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {

        //ex 이벤트 끝나는날짜가 시작하는 날짜보다 빠른 경우 - 데이터 이상

        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 2, 14, 22, 24))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 2, 13, 22, 24))
                .beginEventDateTime(LocalDateTime.of(2021, 2, 12, 22, 24))
                .endEventDateTime(LocalDateTime.of(2021, 2, 10, 22, 24))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        this.mockMvc.perform(post("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("_links.index").exists())

        ;
    }

    @Test
    @DisplayName("30개의 이벤트를 10개씩, 두번째 페이지 조회하기")
    public void queryEvents() throws Exception {
        //Given
        //이벤트 30개..
        IntStream.range(0, 30).forEach(this::generateEvent);

        //When
        //10개로 2번째 페이지 조회(get)
        this.mockMvc.perform(get("/api/events")
                        .param("page", "1") //2번째 페이지
                        .param("size", "10") //10개 묶음
                        .param("sort", "name,DESC") //이름 역순으로 요청
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                //eventList [0]첫번째 요소에서 self link가 있는가?
                //하나의 세부 item 요소에 연결해주는 link가 생성된것이다.
//                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists()) //profile link 있는가? 확인
                .andDo(document("query-events"))
        //TODO link, page들에 대한 설명을담은 문서 추가로 생성해야함
        ;
    }

    private Event generateEvent(int index) {
        Event event = Event.builder()
                .name("Spring" + index)
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 2, 12, 22, 24))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 2, 13, 22, 24))
                .beginEventDateTime(LocalDateTime.of(2021, 2, 14, 22, 24))

                .endEventDateTime(LocalDateTime.of(2021, 2, 15, 22, 24))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();

        return this.eventRepository.save(event);
    }

    @Test
    @TestDescription("기존의 이벤트를 하나 조회하기 ")
    public void getEvent() throws Exception {
        Event event = this.generateEvent(100);


        this.mockMvc.perform(get("/api/events/{id}", event.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(print())
                .andDo(document("get-an-event"));

    }

    @Test
    @TestDescription("없는 이벤트 조회했을때 404 응답")
    public void getEvent404() throws Exception {
        Event event = this.generateEvent(100);


        this.mockMvc.perform(get("/api/events/234135325")
                )
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @TestDescription("이벤트를 정상적으로 수정하기")
    public void updateEvent() throws Exception {

        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        String eventName = "Updated Event";
        eventDto.setName(eventName);

        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andExpect(jsonPath("_links.self").exists());

    }

    @Test
    @TestDescription("입력값 잘못된 경우 이벤트 수정 실패 ")
    public void updateEvent400Wrong() throws Exception {

        Event event = this.generateEvent(200);

        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }


    @Test
    @TestDescription("존재하지 않는 경우 이벤트 수정 실패 ")
    public void updateEvent404() throws Exception {

        Event event = this.generateEvent(200);

        EventDto eventDto = this.modelMapper.map(event, EventDto.class);
        eventDto.setBasePrice(20000);
        eventDto.setMaxPrice(1000);

        this.mockMvc.perform(put("/api/events/23412534")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    @TestDescription("입력값 비어있는 경우 이벤트 수정 실패 ")
    public void updateEvent400Empty() throws Exception {

        Event event = this.generateEvent(200);

        EventDto eventDto = new EventDto();

        // When & Then
        this.mockMvc.perform(put("/api/events/{id}", event.getId())
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    //TODO: 에러 발생시 처리 테스트
    @Test
    public void next() throws Exception {
        //given

        //when

        //then

    }

}
