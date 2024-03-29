package com.ys.restapi.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ys.restapi.accounts.Account;
import com.ys.restapi.accounts.AccountSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Event {

    @Id @GeneratedValue
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


    @ManyToOne
    @JsonSerialize(using = AccountSerializer.class)
    private Account manager;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT; // 이벤트 상태

    public void update() {
        this.free = this.basePrice == 0 && this.maxPrice == 0;

        this.offline = this.location != null && !this.location.isBlank();
    }


}
