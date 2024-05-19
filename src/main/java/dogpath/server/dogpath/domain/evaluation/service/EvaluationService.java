package dogpath.server.dogpath.domain.evaluation.service;

import dogpath.server.dogpath.domain.evaluation.domain.Evaluation;
import dogpath.server.dogpath.domain.evaluation.domain.WalkEvaluation;
import dogpath.server.dogpath.domain.evaluation.dto.EvaluateRouteReq;
import dogpath.server.dogpath.domain.evaluation.repository.EvaluationRepository;
import dogpath.server.dogpath.domain.evaluation.repository.WalkEvaluationRepository;
import dogpath.server.dogpath.global.s3.S3Service;
import dogpath.server.dogpath.domain.user.domain.Preference;
import dogpath.server.dogpath.domain.user.domain.User;
import dogpath.server.dogpath.domain.user.repository.PreferenceRepository;
import dogpath.server.dogpath.domain.user.repository.UserRepository;
import dogpath.server.dogpath.domain.walk.domain.Walk;
import dogpath.server.dogpath.domain.walk.repository.WalkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final WalkEvaluationRepository walkEvaluationRepository;
    private final WalkRepository walkRepository;
    private final UserRepository userRepository;
    private final PreferenceRepository preferenceRepository;
    private final S3Service s3Service;

    public HttpStatus evaluateRoute(EvaluateRouteReq evaluateRouteReq, MultipartFile routeImage) throws IOException {
        //유저 찾기
        Long userId = 1L;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());

        //사진 저장
        String imageURL = s3Service.s3UpLoad(routeImage);

        //산책 테이블 저장
        Walk walk = Walk.builder()
                .distance(BigDecimal.valueOf(evaluateRouteReq.getDistance()))
                .imageURL(imageURL)
                .time(LocalTime.parse(evaluateRouteReq.getTime()))
                .user(user)
                .build();

        walkRepository.save(walk);

        //산책령가 조인 테이블 저장
        List<Evaluation> evaluations = makeEvaluations(evaluateRouteReq.getEvaluations());
        List<WalkEvaluation> walkEvaluations = evaluations.stream()
                .map(e -> new WalkEvaluation(e, walk))
                .collect(Collectors.toList());
        walkEvaluationRepository.saveAll(walkEvaluations);

        //호감도 조절
        Preference preference = user.getPreference();
        evaluations.stream()
                .forEach(preference::updateEvaluation);
        preferenceRepository.save(preference);

        return HttpStatus.CREATED;
    }


    private List<Evaluation> makeEvaluations(String evaluations) {
        String[] evaluationArray = evaluations.split(",");
        List<Evaluation> resultList = new ArrayList<>();
        for (int i = 0; i < evaluationArray.length; i++) {
            String evaluation = evaluationArray[i];
            List<Evaluation> evaluationList= evaluationRepository.findByTitle(evaluation);
            resultList.addAll(evaluationList);
        }
        return resultList;
    }

}
