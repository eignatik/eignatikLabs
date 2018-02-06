package com.library.domain;

import static java.util.Arrays.stream;

public enum BookType {
    DOP(0,0),
    USUAL(60, 10),
    RARE(21, 30),
    UNIQUE(7, 50)
    ;

    private final Integer daysBeforeFine;

    private final Integer finePerDay;

    BookType(Integer daysBeforeFine, Integer finePerDay) {
        this.daysBeforeFine = daysBeforeFine;
        this.finePerDay = finePerDay;
    }

    public Integer getDaysBeforeFine() {
        return daysBeforeFine;
    }

    public Integer getFinePerDay() {
        return finePerDay;
    }

    public static BookType getByName(String value) {
        return stream(values()).filter(type -> type.name().equalsIgnoreCase(value))
                .findAny().get();
    }
}
