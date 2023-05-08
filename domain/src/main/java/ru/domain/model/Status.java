package ru.domain.model;

import javax.persistence.Entity;

public enum Status {
    ACCEPTED("принят"), CANCELED("отменён"), WAIT_PAY("ожидает оплату"),
    PAYED("оплачен"), COOKING("готовится"),
    READY_FOR_DELIVERY("готов к выдаче"), DELIVERING("доставляется"),
    DELIVERED("доставлен"), NOT_FOUND("не найден");

    private final String str;

    Status(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}