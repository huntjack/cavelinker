package com.cavelinker.cavelinkerserver.converters;

import jakarta.persistence.AttributeConverter;

import java.time.ZoneId;

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
