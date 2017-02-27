package io.craftsman;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.dozer.CustomConverter;

import io.craftsman.creator.CreatorFactory;

public class Jdk8CompatibilityConverter implements CustomConverter {

    private CreatorFactory creatorFactory = new CreatorFactory();

    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {

        if (destinationClass == null || sourceClass == null) {
            return destination;
        }

        if (source == null) {
            destination = null;
        } else if (destinationClass.isAssignableFrom(LocalDate.class) && sourceClass.isAssignableFrom(LocalDate.class)) {
            destination = creatorFactory.createLocalDateCreator().create(source);
        } else if (destinationClass.isAssignableFrom(LocalTime.class) && sourceClass.isAssignableFrom(LocalTime.class)) {
            destination = creatorFactory.createLocalTimeCreator().create(source);
        } else if (destinationClass.isAssignableFrom(LocalDateTime.class) && sourceClass.isAssignableFrom(LocalDateTime.class)) {
            destination = creatorFactory.createLocalDateTimeCreator().create(source);
        // use instance of source because otherwise it seems to be not possible to handle the package protected ZoneRegion class
        } else if (destinationClass.isAssignableFrom(ZoneId.class) && (source instanceof ZoneId)) {
            destination = creatorFactory.createZoneIdCreator().create(source);
        } else if (destinationClass.isAssignableFrom(Duration.class) && sourceClass.isAssignableFrom(Duration.class)) {
            destination = creatorFactory.createDurationCreator().create(source);
        } else if (destinationClass.isAssignableFrom(Period.class) && sourceClass.isAssignableFrom(Period.class)) {
        	destination = creatorFactory.createPeriodCreator().create(source);
        } else if (destinationClass.isAssignableFrom(ZonedDateTime.class) && sourceClass.isAssignableFrom(ZonedDateTime.class)) {
            destination = creatorFactory.createZonedDateTimeCreator().create(source);
        }

        return destination;
    }
}
