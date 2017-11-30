package com.it2go.framework.util.json.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it2go.framework.util.json.CustomDateSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomDateSerializerTest {

    public class Event {
        public String name;

        @JsonSerialize(using = CustomDateSerializer.class)
        public Date eventDate;

        public Event(String name, Date eventDate) {
            this.name = name;
            this.eventDate = eventDate;
        }
    }

    @Test
    public void whenUsingCustomDateSerializer_thenCorrect()
            throws JsonProcessingException, ParseException {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String toParse = "20-12-2014 02:30:00";
        Date date = df.parse(toParse);
        Event event = new Event("party", date);

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(event);
        assertThat(result, containsString(toParse));
    }
}
