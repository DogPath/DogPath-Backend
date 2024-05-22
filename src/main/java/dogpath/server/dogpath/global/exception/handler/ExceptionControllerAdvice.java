package dogpath.server.dogpath.global.exception.handler;

import dogpath.server.dogpath.global.dto.BaseErrorResponse;
import dogpath.server.dogpath.global.dto.BaseResponse;
import dogpath.server.dogpath.global.dto.BaseExceptionResponseStatus;
import dogpath.server.dogpath.global.exception.DogPathException;
import dogpath.server.dogpath.global.exception.notfound.WalkEvaluationsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static dogpath.server.dogpath.global.dto.BaseExceptionResponseStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DogPathException.class)
    public ResponseEntity<BaseResponse> baseErrorResponse(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(NOT_DEFINED_ERROR.getStatus())
                .body(new BaseResponse(NOT_DEFINED_ERROR, "정의되지 않은 서버 에러입니다."));
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WalkEvaluationsNotFoundException.class)
    public BaseErrorResponse handleWalkEvaluationsNotFoundException(WalkEvaluationsNotFoundException e) {
        log.error("[exceptionHandle] WalkEvaluationsNotFoundException]", e);
        return new BaseErrorResponse(WALK_EVALUATION_NOT_FOUND);
    }

}
