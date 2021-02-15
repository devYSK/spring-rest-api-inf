package com.ys.restapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {

        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {

            errors.rejectValue("basePrice", "wrongValue", "basePrice is wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "macPrice is wrong.");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();

        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())
                || endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())
                || endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong.");
        }
        //TODO 다른 데이터들도 검증 해야함 .
        //TODO beginEventDateTime
        //TODO closeEventDateTime

        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();

    }
}
