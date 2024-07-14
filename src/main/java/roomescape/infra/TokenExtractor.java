package roomescape.infra;

import jakarta.servlet.http.Cookie;
import roomescape.exception.RoomescapeException;

import java.util.Arrays;

import static roomescape.exception.ExceptionType.INVALID_TOKEN;

public class TokenExtractor {
    public static String extractFrom(Cookie[] cookies) {
        if (cookies == null) {
            throw new RoomescapeException(INVALID_TOKEN);
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .limit(1)
                .findAny()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RoomescapeException(INVALID_TOKEN));
    }
}
