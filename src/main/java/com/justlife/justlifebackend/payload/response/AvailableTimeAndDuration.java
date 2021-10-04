package com.justlife.justlifebackend.payload.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AvailableTimeAndDuration {

    private LocalDateTime startTime;

    private int duration;

    public AvailableTimeAndDuration(LocalDateTime startTime, int duration) {
        this.startTime = startTime;
        this.duration = duration;
    }
}
