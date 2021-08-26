package com.ys.restapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class EventResource extends EntityModel<Event> {
    public EventResource(Event event, Link... links) {
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    public EventResource(Event event) {
        super(event);
    }

}
