/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        if (args.length <= 0)
            throw new IllegalArgumentException();

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        int size = rq.size();
        int n = k <= size ? k : size;
        Iterator<String> iterator = rq.iterator();
        for (int i = 0; i < n; i++) {
            StdOut.println(iterator.next());
        }
    }
}
