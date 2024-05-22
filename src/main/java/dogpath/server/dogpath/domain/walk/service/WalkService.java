package dogpath.server.dogpath.domain.walk.service;

import dogpath.server.dogpath.domain.evaluation.domain.Evaluation;
import dogpath.server.dogpath.domain.evaluation.domain.WalkEvaluation;
import dogpath.server.dogpath.domain.evaluation.domain.WalkEvaluationId;
import dogpath.server.dogpath.domain.evaluation.repository.EvaluationRepository;
import dogpath.server.dogpath.domain.evaluation.repository.WalkEvaluationRepository;
import dogpath.server.dogpath.domain.walk.dto.GetPathRecordRequest;
import dogpath.server.dogpath.domain.walk.dto.GetPathRecordResponse;
import dogpath.server.dogpath.domain.walk.dto.GetPathRecordsResponse;
import dogpath.server.dogpath.domain.walk.dto.PathRecordDTO;
import dogpath.server.dogpath.domain.walk.repository.WalkRepository;
import dogpath.server.dogpath.global.exception.notfound.WalkEvaluationsNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalkService {
    private final WalkRepository walkRepository;
    private final WalkEvaluationRepository walkEvaluationRepository;
    private final EvaluationRepository evaluationRepository;

    public GetPathRecordsResponse getPathRecordsByUserId(Long userId) {
        log.debug("[WalkService.getPathRecordsByUserId]");
        return new GetPathRecordsResponse(
                walkRepository.findPathRecordsById(userId)
                        .stream()
                        .map(PathRecordDTO::new)
                        .collect(Collectors.toList()));
    }


    public GetPathRecordResponse getPathRecord(GetPathRecordRequest request) {
        log.debug("[WalkService.getPathRecord]");
        PathRecordDTO pathRecord = walkRepository.findPathRecordById(request.getWalkId(), request.getUserId())
                .orElseThrow(IllegalArgumentException::new);
        List<WalkEvaluation> walkEvaluations = walkEvaluationRepository.findByWalkId(pathRecord.getWalkId());
        String evaluationStr = String.join(",", getEvaluationList(walkEvaluations));
        pathRecord.setEvaluations(evaluationStr);

        return new GetPathRecordResponse(pathRecord);
    }

    private ArrayList<String> getEvaluationList(List<WalkEvaluation> walkEvaluations) {
        ArrayList<String> eval = new ArrayList<>();

        for (WalkEvaluation walkEvaluation : walkEvaluations) {
            Evaluation evaluation = evaluationRepository.findById(walkEvaluation.getEvaluation().getId())
                    .orElseThrow(IllegalArgumentException::new);
            String title = evaluation.getTitle();
            eval.add(title);
        }
        return eval;
    }


    @Transactional
    public HttpStatus deletePathRecordById(Long walkId) {

        // WalkEvaluation 가져오기
        List<WalkEvaluation> walkEvaluations = walkEvaluationRepository.findByWalkId(walkId);

        if (walkEvaluations.isEmpty()) {
            throw new WalkEvaluationsNotFoundException();
        }

        for (WalkEvaluation walkEvaluation : walkEvaluations) {
            Long evalId = walkEvaluation.getEvaluation().getId();

            // evaluation, walkEvaluation 삭제
            evaluationRepository.deleteById(evalId);
            walkEvaluationRepository.deleteById(new WalkEvaluationId(walkId, evalId));

        }
        walkRepository.deleteById(walkId);

        return HttpStatus.OK;
    }

}
