package dogpath.server.dogpath.domain.path.algorithm;


import com.squareup.okhttp.Response;
import dogpath.server.dogpath.global.tmap.TMapService;
import dogpath.server.dogpath.global.util.JSONUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchAlgorithm {

    private final TMapService tMapService;

    public RouteInfo findRouteByHeuristic(Node startNode, Board calculatedBoard) throws IOException, ParseException {
        /*
         * step 1 : 상위 30% value 노드 들 중 3개의 경유지 선정
         * step 2 : 경유지 순서 정하기.(value 높은순? or else)
         * step 3 : 출발지, 경유지 설정 및 구간 탐색
         * step 4 : step3 반복해서 경유지 == 출발지 일때까지 4개의 구간 생성
         * step 5 : 4개의 구간 -> 1개의 경로 변환 및 tmapService 통해서 거리 및 시간 정보 확인
         * step 6 : 결과 리턴
         */

        //step 1
        List<Node> nodes = calculatedBoard.getAllNodes();
        nodes.sort((node1, node2) -> {
            if (node1.getScore() > node2.getScore()) return -1;
            else return 1;
        });
//        log.info("전체 노드 정보");
//        log.info(nodes.toString());

        List<Node> top30ratioNodes = nodes.subList(0, (int) (nodes.size() * 0.3));
        //경유지 목록 생성
        List<Node> passingNodes = selectPassingNodes(top30ratioNodes);

        //step3, step4 : 3개의 경유지, 1개의 출발지겸 목적지 총 5개의 노드로 전체 경로 생성하여 routeinfo내 저장
        RouteInfo routeInfo = makeRoute(calculatedBoard, startNode, passingNodes);

        //step5 :routeinfo에 있는 List<Node> 기반으로 tmapService 사용하여 결과값 가져와서 response 객체 저장
        setRouteInfoByTmap(routeInfo);

        //step 6: 결과값 리턴
        return routeInfo;
    }

    /**
     * RouteInfo에 있는 routeCoordinates 정보를 바탕으로 TmapService를 통해 RouteInfo에 time과 distance를 설정해줌
     *
     * @param routeInfo time, distance의 정보를 저장할 객체
     * @throws IOException
     */
    private void setRouteInfoByTmap(RouteInfo routeInfo) throws IOException, ParseException {
        List<Node> routeCoordinates = routeInfo.getRouteCoordinates();
        System.out.println(routeCoordinates.toString());
        //최대 5개 경유지 + 시작지 1 + 목적지 1 -> 7개 노드씩 TMap API 호출 가능
        for (int i = 0; i < routeCoordinates.size(); i += 6) {
            int startIdx = i;
            int endIdx = i + 6;
            int remainSize = routeCoordinates.size() - i;
            if (remainSize == 0) {
                break;
            }
            if (remainSize < 6) { // 6보다 작으면 작은만큼 슬라이싱 하기 위한 endIdx 설정
//                log.info("REMAIN SIZE UNDER OF 6");
                endIdx = routeCoordinates.size();
//                log.info("CHANGE ENDIDX TO " + endIdx);
            }

            if (remainSize == 1) { // 남은 노드가 1개 뿐이면, 그전에 했던 노드를 시작노드로, 지금 노드롤 도착노드로 설정해서 tamp 서비스 실행
//                log.info("REAMINE SIZE 1");
//                log.info("SIZE: " + routeCoordinates.size() + ", " + "i : " + i);
                startIdx--; // remainSize = 2가 됨.
            }

            //startIdx -> endIdx - 1 까지 슬라이싱.
            Point[] routePointsForTMap = routeCoordinates.subList(startIdx, endIdx).stream()
                    .map(Node::getCenterPoint)
                    .toArray(Point[]::new);
            Point startPoint = routePointsForTMap[0]; // 첫번째 노드 -> 시작노드, 2개일때도 동일
            Point endPoint = routePointsForTMap[routePointsForTMap.length - 1]; // 마지막 노드 -> 종료 노드, 2개일때도 동일

            Point[] passList = null;
            //경유지 생성
//            log.info("STARTIDX & ENDIDX");
//            log.info(startIdx + " " + endIdx);
//            log.info(Arrays.toString(routePointsForTMap));
            if (routePointsForTMap.length > 3) {
                passList = Arrays.copyOfRange(routePointsForTMap, 1, routePointsForTMap.length - 1); // 첫번째꺼 빼고, 마지막꺼 뺴고
            }
            //API 호출
            Response routeByTMap = tMapService.getRouteByTMap(startPoint, "시작지점", endPoint, "종료지점", passList);
            int code = routeByTMap.code();
            String bodyString = routeByTMap.body().string();
            log.info("RESPONSE CODE : " + code);
            log.info("RESPONSE MESSAGE: " + bodyString);

            //거리 및 시간 추출 및 거리, 시간 추가
            long distance = JSONUtils.parseDistanceFromResponse(bodyString);
            routeInfo.addDistance(distance); // m단위
            long time = JSONUtils.parseTimeFromResponse(bodyString);
            routeInfo.addTime(time); //초 단위
        }
    }

    /**
     * 3개의 경유지 선정하는 메소드
     *
     * @param top30ratioNode
     * @return
     */
    private List<Node> selectPassingNodes(List<Node> top30ratioNode) {
        List<Node> passingNodes = new ArrayList<>();
        while (passingNodes.size() < 3) {
            Node passingNode = getPassingNode(top30ratioNode);
            passingNodes.add(passingNode);
        }
        return passingNodes;
    }

    /**
     * top30ratioNodes 중에서 passingNode(경유 노드)로 선정되지 않은 노드들 중에서 하나 선정
     *
     * @param top30ratioNodes
     * @return 선정된 node
     */
    private Node getPassingNode(List<Node> top30ratioNodes) {
//        log.info("TOP 30 RATIO NODES");
//        log.info(top30ratioNodes.toString());
        while (true) {
            long randomIdx = Math.round(Math.random() * (top30ratioNodes.size() - 1));
            Node node = top30ratioNodes.get((int) randomIdx);
            if (!node.isPassingNode) {
                node.isPassingNode = true;
                return node;
            }
        }
    }

    /**
     * 시작지와 경유지 목록을 넛어서 4개의 구간 만든 후 하나의 노드 리스트로 반환
     *
     * @param board       전체 Board
     * @param startNode
     * @param passingNode
     * @return
     */
    private RouteInfo makeRoute(Board board, Node startNode, List<Node> passingNode) {
        List<Node> routeCoordinates = new ArrayList<>();
        passingNode.add(0, startNode); // 출발지 노드로 사용
        passingNode.add(startNode); // 목적지 노드로 사용
//        log.info("시작지 + 경유지 + 목적지(시작지) 노드 개수");
//        log.info(String.valueOf(passingNode.size()));
        for (int i = 0; i < passingNode.size() - 1; i++) {
            List<Node> sectionRoute = getSectionRoute(board, passingNode.get(i), passingNode.get(i + 1));
            routeCoordinates.addAll(sectionRoute);
        }
        return new RouteInfo(routeCoordinates);
    }

    /**
     * 출발지 목적지 두개의 인자를 받아 구간 경로 생성
     *
     * @param board     전체 Board
     * @param startNode 시작점
     * @param endNode   도착점(경유지 or 마지막 도착지)
     * @return
     */
    private List<Node> getSectionRoute(Board board, Node startNode, Node endNode) {
        List<Node> sectionNodes = new ArrayList<>();
        Node currentNode = startNode;
//        sectionNodes.add(startNode);
        while (!currentNode.equals(endNode)) {
            List<Node> neighborNode = board.getNeighborNodes(currentNode); // 근처 8개 노드 가져옴)
            currentNode = findNextNode(neighborNode, endNode); // 근처 노드들 중 다음 노드 선택(휴리스틱 알고리즘 통한 노드별 값 측정 및 비교)
            sectionNodes.add(currentNode);
//            log.info("SectionNodes");
//            log.info(sectionNodes.toString());
        }
        return sectionNodes;
    }

    private Node findNextNode(List<Node> neighborNode, Node destination) {
        Node nextNode = null;
        double nextNodeFValue = Double.MAX_VALUE;

        for (int i = 0; i < neighborNode.size(); i++) {
            Node node = neighborNode.get(i);
//            if (node.isVisited()) {
//                continue;
//            }
            //목적지라면 바로 가기
            if (node.equals(destination)) {
                node.isVisited = true;
                return node;
            }

            double fValue = node.getFValue(destination);
            log.info(fValue + " " + nextNodeFValue);
            if (fValue < nextNodeFValue) {
                nextNodeFValue = fValue;
                nextNode = node;
            }
        }
        //TODO : 모든 이웃 노드들이 isvisited 상태이면 문제 생김.
        if (nextNode == null) {
            nextNode = neighborNode.get(0);
        }
        nextNode.isVisited = true;
        return nextNode;
    }
}
