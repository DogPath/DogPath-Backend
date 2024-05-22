package dogpath.server.dogpath.global.exception;

import dogpath.server.dogpath.global.dto.BaseExceptionResponseStatus;
import lombok.Getter;

@Getter
public class DatabaseException extends RuntimeException {
    private final BaseExceptionResponseStatus baseExceptionResponseStatus;

    public DatabaseException(BaseExceptionResponseStatus baseExceptionResponseStatus) {
        super(baseExceptionResponseStatus.getMessage());
        this.baseExceptionResponseStatus = baseExceptionResponseStatus;
    }
}
