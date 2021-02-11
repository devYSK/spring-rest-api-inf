package com.ys.restapi.events;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API developer")
                .build();

        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        String name = "Event";
        Event event = new Event();
        event.setName(name);


        assertThat(event.getName()).isEqualTo(name);
    }
}