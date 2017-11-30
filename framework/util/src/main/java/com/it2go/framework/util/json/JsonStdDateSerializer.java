package com.it2go.framework.util.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class JsonStdDateSerializer extends JsonSerializer<Date> {

    private static final DateFormat iso8601Format =
            StdDateFormat.getISO8601Format(TimeZone.getDefault(), Locale.getDefault());

    @Override
    public void serialize(
            Date date, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        // clone because DateFormat is not thread-safe
        DateFormat myformat = (DateFormat) iso8601Format.clone();
        String formattedDate = myformat.format(date);
        jgen.writeString(formattedDate);
    }
}
