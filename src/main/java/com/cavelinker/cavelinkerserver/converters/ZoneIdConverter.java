package com.cavelinker.cavelinkerserver.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.ZoneId;

@Converter
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {
    @Override
    public String convertToDatabaseColumn(ZoneId zoneId) {
        return zoneId.toString();
    }
    @Override
    public ZoneId convertToEntityAttribute(String zoneIdString) {
        return ZoneId.of(zoneIdString);
    }
}
