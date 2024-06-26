package dogpath.server.dogpath.global.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseExceptionResponseStatus {

    /**
     * 1000: 요청 성공 (OK)
     */
    SUCCESS(1000, HttpStatus.OK.value(), "요청에 성공하였습니다."),

    /**
     * 2000: 클라이언트 Request 오류 (BAD_REQUEST)
     */
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 요청입니다."),
    NOT_FOUND_MEMBER(2001, HttpStatus.NOT_FOUND.value(), "찾을 수 없는 사용자입니다."),
    METHOD_NOT_ALLOWED(2002, HttpStatus.METHOD_NOT_ALLOWED.value(), "해당 URL에서는 지원하지 않는 HTTP Method 입니다."),
    NOT_FOUND_DIARY(2003, HttpStatus.NOT_FOUND.value(), "찾을 수 없는 기록입니다."),

    /**
     * 3000: Server, Database 오류 (INTERNAL_SERVER_ERROR)
     */
    SERVER_ERROR(3000, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에서 오류가 발생하였습니다."),
    DATABASE_ERROR(3001, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스에서 오류가 발생하였습니다."),
    BAD_SQL_GRAMMAR(3002, HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL에 오류가 있습니다."),
    WALK_EVALUATION_NOT_FOUND(3003, HttpStatus.INTERNAL_SERVER_ERROR.value(), "산책 기록의 평가 항목을 찾을 수 없습니다."),
    NOT_DEFINED_ERROR(3999, HttpStatus.INTERNAL_SERVER_ERROR.value(),"정의되지 않은 오류입니다.");

    private final int code;
    private final int status;
    private final String message;
}
