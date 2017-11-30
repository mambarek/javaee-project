package com.it2go.framework.util.json.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it2go.framework.util.json.JsonStdDateSerializer;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonStdDateSerializerTest {

    public class Event {
        public String name;

        @JsonSerialize(using = JsonStdDateSerializer.class)
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

        String toParse = "2014-12-20T02:30:00.000+0100";
        LocalDateTime dateTime = LocalDateTime.of(2014,12,20,2,30,0);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());//df.parse(toParse);
        Event event = new Event("party", date);

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(event);
        assertThat(result, containsString(toParse));
    }
}
