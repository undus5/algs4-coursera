/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int size;
    private int opened = 0;
    private boolean percolate = false;
    private boolean[] open;
    private boolean[] full;
    private boolean[] bottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        int len = n * n;
        uf = new WeightedQuickUnionUF(len);
        open = new boolean[len];
        full = new boolean[len];
        bottom = new boolean[len];
        for (int i = 0; i < len; i++) {
            open[i] = false;
            full[i] = false;
            bottom[i] = false;
        }
    }

    private int i(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException();
        return (row - 1) * size + (col - 1);
    }

    private void markFullI(int i) {
        int r = uf.find(i);
        full[r] = true;
    }

    private void markBottomI(int i) {
        int r = uf.find(i);
        bottom[r] = true;
    }

    private boolean isFullI(int i) {
        int r = uf.find(i);
        return full[r];
    }

    private boolean reachBottomI(int i) {
        int r = uf.find(i);
        return bottom[r];
    }

    private void union(int p, int q) {
        boolean hasFull = isFullI(p) || isFullI(q);
        boolean hasBottom = reachBottomI(p) || reachBottomI(q);
        uf.union(p, q);
        if (hasFull)
            markFullI(p);
        if (hasBottom)
            markBottomI(p);

        if (hasFull && hasBottom)
            percolate = true;
    }

    public boolean isFull(int row, int col) {
        int i = i(row, col);
        return isFullI(i);
    }

    public boolean isOpen(int row, int col) {
        return open[i(row, col)];
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return percolate;
    }

    public void open(int row, int col) {
        int i = i(row, col);
        if (open[i]) return;
        open[i] = true;
        opened++;
        if (row == 1) {
            full[i] = true;
        }
        if (row == size) {
            bottom[i] = true;
        }

        if (row > 1) {
            int up = i(row - 1, col);
            if (open[up]) union(i, up);
        }

        if (row < size) {
            int down = i(row + 1, col);
            if (open[down]) union(i, down);
        }

        if (col > 1) {
            int left = i(row, col - 1);
            if (open[left]) union(i, left);
        }

        if (col < size) {
            int right = i(row, col + 1);
            if (open[right]) union(i, right);
        }
    }

    public static void main(String[] args) {
        String format = "%-60s";

        StdOut.printf(format, "Assert constructor argument should be greater than 0");
        try {
            Percolation p = new Percolation(0);
            p.open(0, 0);
            StdOut.println("FAILED");
        }
        catch (IllegalArgumentException e) {
            StdOut.println("PASS");
        }

        StdOut.printf(format, "Assert open() should throw exception on invalid row,col");
        try {
            Percolation p0 = new Percolation(1);
            p0.open(0, 0);
            StdOut.println("FAILED");
        }
        catch (IllegalArgumentException e) {
            StdOut.println("PASS");
        }

        StdOut.println("======================================================================");

        StdOut.println("Create new instance");
        Percolation p1 = new Percolation(3);

        StdOut.printf(format, "Assert system should not percolate");
        if (!p1.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(1, 1)");
        p1.open(1, 1);

        StdOut.printf(format, "Assert (1, 1) should be open");
        if (p1.isOpen(1, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (1, 1) should be full");
        if (p1.isFull(1, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert numberOfOpenSites() should be 1");
        if (p1.numberOfOpenSites() == 1)
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should not percolate");
        if (!p1.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(2, 1)");
        p1.open(2, 1);

        StdOut.printf(format, "Assert (2, 1) should be open");
        if (p1.isOpen(2, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (2, 1) should be full");
        if (p1.isFull(2, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert numberOfOpenSites() should be 2");
        if (p1.numberOfOpenSites() == 2)
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should not percolate");
        if (!p1.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(3, 1)");
        p1.open(3, 1);

        StdOut.printf(format, "Assert (3, 1) should be open");
        if (p1.isOpen(3, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (3, 1) should be full");
        if (p1.isFull(3, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert numberOfOpenSites() should be 3");
        if (p1.numberOfOpenSites() == 3)
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should percolate");
        if (p1.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("======================================================================");

        StdOut.println("Create new instance");
        Percolation p2 = new Percolation(3);

        StdOut.printf(format, "Assert system should not percolate");
        if (!p2.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(3, 3)");
        p2.open(3, 3);

        StdOut.printf(format, "Assert (3, 3) should be open");
        if (p2.isOpen(3, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (3, 3) should not be full");
        if (!p2.isFull(3, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should not percolate");
        if (!p2.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(2, 3)");
        p2.open(2, 3);

        StdOut.printf(format, "Assert (2, 3) should be open");
        if (p2.isOpen(2, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (2, 3) should not be full");
        if (!p2.isFull(2, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should not percolate");
        if (!p2.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(1, 3)");
        p2.open(1, 3);

        StdOut.printf(format, "Assert (1, 3) should be open");
        if (p2.isOpen(1, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (1, 3) should be full");
        if (p2.isFull(1, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (2, 3) should be full");
        if (p2.isFull(2, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (3, 3) should be full");
        if (p2.isFull(3, 3))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should percolate");
        if (p2.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("======================================================================");

        StdOut.println("Create new instance");
        Percolation p3 = new Percolation(1);

        StdOut.printf(format, "Assert system should not percolate");
        if (!p3.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(1, 1)");
        p3.open(1, 1);

        StdOut.printf(format, "Assert (1, 1) should be open");
        if (p3.isOpen(1, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (1, 1) should be full");
        if (p3.isFull(1, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should percolate");
        if (p3.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("======================================================================");

        StdOut.println("Create new instance");
        Percolation p4 = new Percolation(3);

        StdOut.printf(format, "Assert system should not percolate");
        if (!p4.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(1, 1)");
        p4.open(1, 1);

        StdOut.printf(format, "Assert (1, 1) should be open");
        if (p4.isOpen(1, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (1, 1) should be full");
        if (p4.isFull(1, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert numberOfOpenSites() should be 1");
        if (p4.numberOfOpenSites() == 1)
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should not percolate");
        if (!p4.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(3, 1)");
        p4.open(3, 1);

        StdOut.printf(format, "Assert (3, 1) should be open");
        if (p4.isOpen(3, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (3, 1) should not be full");
        if (!p4.isFull(3, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert numberOfOpenSites() should be 2");
        if (p4.numberOfOpenSites() == 2)
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should not percolate");
        if (!p4.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.println("Invoke open(2, 1)");
        p4.open(2, 1);

        StdOut.printf(format, "Assert (2, 1) should be open");
        if (p4.isOpen(2, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (2, 1) should be full");
        if (p4.isFull(2, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert (3, 1) should be full");
        if (p4.isFull(3, 1))
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert numberOfOpenSites() should be 3");
        if (p4.numberOfOpenSites() == 3)
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");

        StdOut.printf(format, "Assert system should percolate");
        if (p4.percolates())
            StdOut.println("PASS");
        else
            StdOut.println("FAILED");
    }
}
