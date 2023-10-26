package com.youro.web.entity;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
    COMPLETED(1),
    SCHEDULED(2),
    CANCELED(3),
    UNATTENDED(4);

    private final int value;

    AppointmentStatus(int value) {
        this.value = value;
    }

}
