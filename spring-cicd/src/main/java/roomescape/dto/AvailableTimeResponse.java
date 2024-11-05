package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record AvailableTimeResponse(long id, LocalTime startAt, boolean isBooked) {
    public static AvailableTimeResponse of(ReservationTime reservationTime, boolean isBooked) {
        return new AvailableTimeResponse(reservationTime.getId(), reservationTime.getStartAt(), isBooked);
    }
}
