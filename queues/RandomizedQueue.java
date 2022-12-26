/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    private class ItemIterator implements Iterator<Item> {
        private Item[] s;
        private int k = 0;

        public ItemIterator() {
            s = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                s[i] = items[i];
            }
            StdRandom.shuffle(s);
        }

        public boolean hasNext() {
            return k < n;
        }

        public Item next() {
            if (k >= n)
                throw new NoSuchElementException();
            return s[k++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (n == items.length) resize(items.length * 2);
        items[n++] = item;
    }

    public Item dequeue() {
        if (n <= 0)
            throw new NoSuchElementException();

        int i = StdRandom.uniformInt(n);
        Item m = items[i];
        if (i != --n) {
            items[i] = items[n];
            items[n] = null;
        }
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return m;
    }

    public Item sample() {
        if (n <= 0)
            throw new NoSuchElementException();

        int i = StdRandom.uniformInt(n);
        return items[i];
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        String format = "%-60s";

        StdOut.printf(format, "Assert queue should be empty");
        if (rq.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 0");
        if (rq.size() == 0)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        //

        try {
            StdOut.printf(format, "Assert argument of enqueue() should not be null");
            rq.enqueue(null);
            StdOut.printf("FAILED\n");
        }
        catch (IllegalArgumentException e) {
            StdOut.printf("PASS\n");
        }

        try {
            StdOut.printf(format, "Assert dequeue() should throw exception on empty queue");
            rq.dequeue();
            StdOut.printf("FAILED\n");
        }
        catch (NoSuchElementException e) {
            StdOut.printf("PASS\n");
        }

        //

        StdOut.println("Invoke enqueue(\"a\")");
        rq.enqueue("a");
        StdOut.println("Invoke enqueue(\"b\")");
        rq.enqueue("b");
        StdOut.println("Invoke enqueue(\"c\")");
        rq.enqueue("c");

        StdOut.printf(format, "Assert queue should not be empty");
        if (!rq.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 3");
        if (rq.size() == 3)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        //

        StdOut.printf("Invoke sample() return ");
        StdOut.println(rq.sample());
        StdOut.printf("Invoke sample() return ");
        StdOut.println(rq.sample());
        StdOut.printf("Invoke sample() return ");
        StdOut.println(rq.sample());

        //

        StdOut.printf("Iterate items return ");
        StringBuilder sb = new StringBuilder();
        for (String s : rq) {
            sb.append(s);
            sb.append(",");
        }
        StdOut.println(sb.toString());

        //

        StdOut.printf("Invoke dequeue() remove ");
        StdOut.println(rq.dequeue());
        StdOut.printf("Invoke dequeue() remove ");
        StdOut.println(rq.dequeue());
        StdOut.printf("Invoke dequeue() remove ");
        StdOut.println(rq.dequeue());

        StdOut.printf(format, "Assert queue should be empty");
        if (rq.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 0");
        if (rq.size() == 0)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");
    }
}
