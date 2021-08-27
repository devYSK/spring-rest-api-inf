package com.ys.restapi.events;

import com.ys.restapi.accounts.Account;
import com.ys.restapi.accounts.AccountAdapter;
import com.ys.restapi.accounts.CurrentUser;
import com.ys.restapi.common.ErrorsResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Slf4j
@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor

public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;

    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto,
                                      Errors errors,
                                        @CurrentUser Account currentUser) {


        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        eventValidator.validate(eventDto, errors);

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        event.setManager(currentUser);
        Event newEvent = this.eventRepository.save(event);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());

        URI createdUri = selfLinkBuilder.toUri();


        Integer eventId = newEvent.getId();

        EntityModel eventResource = EntityModel.of(newEvent);
        eventResource.add(linkTo(EventController.class).slash(eventId).withSelfRel());
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-event"));

//        eventResource.add(linkTo(EventController.class).withRel("query-events"));
//        eventResource.add(selfLinkBuilder.withSelfRel());
//        eventResource.add(selfLinkBuilder.withRel("update-event"));

        return ResponseEntity.created(createdUri).body(eventResource);
    }


    @GetMapping //get으로 조회한다... get으로 작성해놓고 post요청하면 405error발생함
    public ResponseEntity queryEvents(Pageable pageable,
                                      PagedResourcesAssembler<Event> assembler,
                                      @CurrentUser Account account) {

        Page<Event> page = this.eventRepository.findAll(pageable);
        //page와 관련한 정보를 넘겨준다.
        //현재 페이지, 이전 페이지, 다음 페이지 등의 link 정보를 말한다.
        //e -> () 페이지 하나하나의 목록에 link를 생성해서 HATEOAS성질을 부여한다.
        var pagedResources = assembler.toModel(page, EventResource::of);
        pagedResources.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));


        if (account != null) {
            pagedResources.add(linkTo(EventController.class).withRel("create-event"));
        }


        return ResponseEntity.ok(pagedResources);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }


    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id, @CurrentUser Account currentUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<Event> optionalEvent = this.eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event event = optionalEvent.get();

        log.error("아악!!!!!!!!!!!!!");
        log.info(event.toString());
        EntityModel<Event> eventResource = EventResource.of(event);
        eventResource.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        eventResource.add(linkTo(EventController.class).slash(event.getId()).withSelfRel()); // of로 만들어서 self 링크를 추가해줘야한다.

        if (event.getManager().equals(currentUser)) {
            eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
        }
        return ResponseEntity.ok(eventResource);

    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors,
                                      @CurrentUser Account currentUser
    ) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        this.eventValidator.validate(eventDto, errors);

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event existingEvent = optionalEvent.get();

        if (!existingEvent.getManager().equals(currentUser)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        this.modelMapper.map(eventDto, existingEvent);
        Event savedEvent = eventRepository.save(existingEvent); // 서비스 레이어에서 트랙잰셩 계층 안이 아니므로 명시적으로 세이브 해줘야 한다.

        EntityModel<Event> entityModel = EventResource.of(savedEvent);
        entityModel.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
        entityModel.add(linkTo(EventController.class).slash(savedEvent.getId()).withSelfRel()); // of로 만들어서 self 링크를 추가해줘야한다.

        return ResponseEntity.ok(entityModel);

    }


}
