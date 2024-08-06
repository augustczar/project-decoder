package com.ead.authuser.configs;

import java.io.IOException;

import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PageImplSerializer extends StdSerializer<PageImpl<?>> {

    public PageImplSerializer() {
        super(PageImpl.class, false);
    }
    
    @Override
    public void serialize(PageImpl<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("content", page.getContent());
        gen.writeNumberField("number", page.getNumber());
        gen.writeNumberField("size", page.getSize());
        gen.writeNumberField("totalElements", page.getTotalElements());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeBooleanField("last", page.isLast());
        gen.writeBooleanField("first", page.isFirst());
        gen.writeBooleanField("empty", page.isEmpty());
        gen.writeNumberField("numberOfElements", page.getNumberOfElements());
        gen.writeObjectField("pageable", page.getPageable());
        gen.writeObjectField("sort", page.getSort());
        gen.writeEndObject();
    }
}
