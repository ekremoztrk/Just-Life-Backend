package com.justlife.justlifebackend.payload.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeInterval {

    LocalDateTime startTime;

    LocalDateTime endTime;

    public TimeInterval(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
