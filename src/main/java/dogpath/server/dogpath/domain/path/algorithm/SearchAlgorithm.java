package dogpath.server.dogpath.domain.path.algorithm;


import com.squareup.okhttp.Response;
import dogpath.server.dogpath.domain.tmap.TMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchAlgorithm {

    private final TMapService tMapService;

    public RouteInfo findRouteByHeuristic(Node startNode, List<Node> calculatedNodes) throws IOException {
        /*
         * step 1 : 상위 30% value 노드 들 중 3개의 경유지 선정
         * step 2 : 경유지 순서 정하기.(value 높은순? or else)
         * step 3 : 출발지, 경유지 설정 및 구간 탐색
         * step 4 : step3 반복해서 경유지 == 출발지 일때까지 4개의 구간 생성
         * step 5 : 4개의 구간 -> 1개의 경로 변환 및 tmapService 통해서 거리 및 시간 정보 확인
         * step 6 : 결과 리턴
         */
        List<Point> routePoints = new ArrayList<>();

        //step 1
        calculatedNodes.sort((node1, node2) -> {
            if (node1.getScore() > node2.getScore()) return -1;
            else return 1;
        });
        List<Node> top30ratioNodes = calculatedNodes.subList(0, (int) (routePoints.size() * 0.3));

        //경유지 목록 생성
        //TODO : 경유지 순서는 어떻게 하지?
        List<Node> passingNodes = getPassingNodes(top30ratioNodes);

        //step3, step4 : 3개의 경유지, 1개의 출발지겸 목적지 총 5개의 노드로 전체 경로 생성하여 routeinfo내 저장
        RouteInfo routeInfo = makeRoute(calculatedNodes, startNode, passingNodes);


        //step5 :routeinfo에 있는 List<Node> 기반으로 tmapService 사용하여 결과값 가져와서 response 객체 저장
        setRouteInfoByTmap(routeInfo);

        //step 6: 결과값 리턴
        return routeInfo;
    }

    /**
     * RouteInfo에 있는 routeCoordinates 정보를 바탕으로 TmapService를 통해 RouteInfo에 time과 distance를 설정해줌
     * @param routeInfo time, distance의 정보를 저장할 객체
     * @throws IOException
     */
    private void setRouteInfoByTmap(RouteInfo routeInfo) throws IOException {
        List<Node> routeCoordinates = routeInfo.getRouteCoordinates();

        //최대 5개 경유지 + 시작지 1 + 목적지 1 -> 7개 노드씩 TMap API 호출 가능
        for (int i = 0; i < routeCoordinates.size(); i += 7) {
            //시작지, 출발지, 경유지 리스트 설정
            Point startPoint = routeCoordinates.get(i).getCenterPoint();
            Point endPoint = routeCoordinates.get(i + 6).getCenterPoint();
            Point[] passList = (Point[]) routeCoordinates.subList(i + 1, i + 6).stream()
                    .map(Node::getCenterPoint).toArray();

            //API 호출
            Response routeByTMap = tMapService.getRouteByTMap(startPoint, "시작지점", endPoint, "종료지점", passList);

            //거리 및 시간 추출 및 거리, 시간 추가
            double distance = JSONUtils.parseDistanceFromResponse(routeByTMap);
            routeInfo.addDistance(distance);
            long time = JSONUtils.parseTimeFromResponse(routeByTMap);
            routeInfo.addTime(time);
        }
    }

    /**
     * 3개의 경유지 선정하는 메소드
     *
     * @param top30ratioNode
     * @return
     */
    private List<Node> getPassingNodes(List<Node> top30ratioNode) {
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
        while (true) {
            long randomIdx = Math.round(Math.random() * top30ratioNodes.size());
            Node node = top30ratioNodes.get((int) randomIdx);
            if (!node.isPassingNode) {
                node.isPassingNode = true;
                return node;
            }
        }
    }

    /**
     * 시작지와 경유지 목록을 넛어서 4개의 구간 만든 후 하나의 노드 리스트로 반환
     * @param calculatedNodes 전체 Board
     * @param startNode
     * @param passingNode
     * @return
     */
    private RouteInfo makeRoute(List<Node> calculatedNodes,Node startNode, List<Node> passingNode) {
        List<Node> routeCoordinates = new ArrayList<>();
        passingNode.add(0, startNode);
        for (int i = 0; i < passingNode.size(); i++) {
            List<Node> sectionRoute = getSectionRoute(calculatedNodes, passingNode.get(i), passingNode.get(i+1));
            routeCoordinates.addAll(sectionRoute);
        }
        return new RouteInfo(routeCoordinates);
    }

    /**
     * 출발지 목적지 두개의 인자를 받아 구간 경로 생성
     * @param calculatedNodes 전체 Board
     * @param startNode 시작점
     * @param endNode 도착점(경유지 or 마지막 도착지)
     * @return
     */
    private List<Node> getSectionRoute(List<Node> calculatedNodes, Node startNode, Node endNode) {
        //TODO : ㄹㅇ 휴리스틱 알고리즘으로 출발지, 목적지, 근처 노드를 계산해서 돌리면 됨.
        List<Node> sectionNodes = new ArrayList<>();

        return sectionNodes;
    }
}
