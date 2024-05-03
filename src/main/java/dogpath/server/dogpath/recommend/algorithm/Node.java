package dogpath.server.dogpath.recommend.service;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

@Getter
public class Node {
    Point2D.Double centerPoint;
    Rectangle2D.Double area;
    double score = 0.0;
    double heuristicDistance = 0.0;

    Node(double centerLat, double centerLng) {
        centerPoint = new Point2D.Double();
        area = new Rectangle2D.Double();

        centerPoint.setLocation(centerLat, centerLng);
        
    }
}
