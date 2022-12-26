/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int n = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                if (points[j].compareTo(points[i]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        if (points.length < 4) return;

        segments = new LineSegment[points.length];

        for (int i = 0; i <= points.length - 4; i++) {
            for (int j = i + 1; j <= points.length - 3; j++) {
                double ijSlope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k <= points.length - 2; k++) {
                    double ikSlope = points[i].slopeTo(points[k]);
                    for (int m = k + 1; m <= points.length - 1; m++) {
                        double imSlope = points[i].slopeTo(points[m]);
                        if (ijSlope == ikSlope && ijSlope == imSlope) {
                            int min = i;
                            int max = i;
                           
                            if (points[j].compareTo(points[min]) < 0) min = j;
                            if (points[k].compareTo(points[min]) < 0) min = k;
                            if (points[m].compareTo(points[min]) < 0) min = m;

                            if (points[j].compareTo(points[max]) > 0) max = j;
                            if (points[k].compareTo(points[max]) > 0) max = k;
                            if (points[m].compareTo(points[max]) > 0) max = m;

                            segments[n++] = new LineSegment(points[min], points[max]);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            s[i] = segments[i];
        }
        return s;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
