/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int n = 0;

    public FastCollinearPoints(Point[] points) {
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
        Point[] pointsLo = new Point[points.length];
        Point[] pointsHi = new Point[points.length];

        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsCopy[i] = points[i];
        }

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(pointsCopy, points[i].slopeOrder());

            int copyI = 1;
            int[] lo = new int[points.length];
            int[] hi = new int[points.length];
            int ii = 0;
            lo[ii] = copyI;
            hi[ii] = copyI;

            double slope = points[i].slopeTo(pointsCopy[copyI++]);

            while (true) {
                double slopeCopy = points[i].slopeTo(pointsCopy[copyI]);
                while (slopeCopy == slope) {
                    hi[ii] = copyI;
                    if (copyI < points.length - 1) {
                        slopeCopy = points[i].slopeTo(pointsCopy[++copyI]);
                    }
                    else {
                        slopeCopy = Double.NEGATIVE_INFINITY;
                    }
                }
                if (slopeCopy == Double.NEGATIVE_INFINITY) break;
                if (copyI - lo[ii] >= 3) {
                    ii++;
                }
                else {
                    slope = slopeCopy;
                }
                lo[ii] = copyI;
                hi[ii] = copyI;
            }

            for (int j = 0; j <= ii; j++) {
                if (hi[j] - lo[j] < 2) continue;

                int min = 0;
                int max = 0;
                for (int k = lo[j]; k <= hi[j]; k++) {
                    if (pointsCopy[k].compareTo(pointsCopy[min]) < 0) min = k;
                    if (pointsCopy[k].compareTo(pointsCopy[max]) > 0) max = k;
                }

                if (n >= segments.length) {
                    LineSegment[] newSegments = new LineSegment[n * 2];
                    for (int k = 0; k < n; k++) {
                        newSegments[k] = segments[k];
                    }
                    segments = newSegments;

                    Point[] newLo = new Point[n * 2];
                    for (int k = 0; k < n; k++) {
                        newLo[k] = pointsLo[k];
                    }
                    pointsLo = newLo;

                    Point[] newHi = new Point[n * 2];
                    for (int k = 0; k < n; k++) {
                        newHi[k] = pointsHi[k];
                    }
                    pointsHi = newHi;
                }

                boolean exists = false;
                for (int k = 0; k < n; k++) {
                    if (pointsCopy[min] == pointsLo[k] && pointsCopy[max] == pointsHi[k]) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    LineSegment segment = new LineSegment(pointsCopy[min], pointsCopy[max]);
                    pointsLo[n] = pointsCopy[min];
                    pointsHi[n] = pointsCopy[max];
                    segments[n++] = segment;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
