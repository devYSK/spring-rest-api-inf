package com.ys.restapi.events;

import java.time.LocalDateTime;

public class Event {

    private Integer id;

    private String name;

    private String description;

    private LocalDateTime beginEnrollmentDateTime;

    private LocalDateTime closeEnrollmentDateTime;

    private LocalDateTime beginEventDateTime;

    private LocalDateTime endEventDateTime;

    private String location;  // (optional) 이게 없으면 온라인 모임

    private int basePrice;  // (optional)

    private int maxPrice;  // (optional)

    private int limitOfEnrollment;

    private boolean offline; // 오프라인인지 온라인인지
    private boolean free; // 유료인지 무료인지
    private EventStatus eventStatus = EventStatus.DRAFT; // 이벤트 상태

}
