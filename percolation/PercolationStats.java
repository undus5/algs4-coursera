/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int trials;
    private double[] fractions;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        fractions = new double[trials];
        int row, col;
        Percolation p;
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                row = StdRandom.uniformInt(1, n + 1);
                col = StdRandom.uniformInt(1, n + 1);
                if (!p.isOpen(row, col))
                    p.open(row, col);
            }
            fractions[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        double m = mean();
        double s = stddev();
        return m - CONFIDENCE_95 * s / Math.sqrt(trials);
    }

    public double confidenceHi() {
        double m = mean();
        double s = stddev();
        return m + CONFIDENCE_95 * s / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args.length < 2)
            throw new IllegalArgumentException();

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        StdOut.printf("%-23s = %f\n", "mean", ps.mean());
        StdOut.printf("%-23s = %f\n", "stddev", ps.stddev());
        StdOut.printf("%-23s = [%f, %f]\n", "95% confidence interval",
                      ps.confidenceLo(),
                      ps.confidenceHi());
    }
}
