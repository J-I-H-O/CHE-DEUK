package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationStatus;

import java.time.LocalDate;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time,
        ThemeResponse theme,
        ReservationStatus status
) {
    public static ReservationResponse from(Reservation reservation) {
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(reservation.getReservationTime());
        ThemeResponse themeResponse = ThemeResponse.from(reservation.getTheme());

        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTimeResponse,
                themeResponse,
                reservation.getStatus()
        );
    }
}
