package dogpath.server.dogpath.global.exception.notfound;

import dogpath.server.dogpath.global.dto.BaseExceptionResponseStatus;
import dogpath.server.dogpath.global.exception.DatabaseException;

public class WalkEvaluationsNotFoundException extends DatabaseException {

    public WalkEvaluationsNotFoundException() {
        super(BaseExceptionResponseStatus.WALK_EVALUATION_NOT_FOUND);
    }
}