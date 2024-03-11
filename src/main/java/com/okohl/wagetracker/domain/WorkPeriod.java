package com.okohl.wagetracker.domain;

import java.time.Instant;

public record WorkPeriod(Instant start, Instant end) {
}
