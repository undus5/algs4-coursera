/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first = null;
    private Node last = null;
    private int n = 0;

    private class ItemIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n <= 0;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (oldFirst == null)
            last = first;
        else
            oldFirst.prev = first;
        n++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (oldLast == null)
            first = last;
        else
            oldLast.next = last;
        n++;
    }

    public Item removeFirst() {
        if (n <= 0)
            throw new NoSuchElementException();

        Item item = first.item;
        if (n == 1) {
            first = null;
            last = null;
        }
        else if (n == 2) {
            first = last;
            first.prev = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        n--;
        return item;
    }

    public Item removeLast() {
        if (n <= 0)
            throw new NoSuchElementException();

        Item item = last.item;
        if (n == 1) {
            first = null;
            last = null;
        }
        else if (n == 2) {
            last = first;
            last.next = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        n--;
        return item;
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        String format = "%-60s";

        StdOut.printf(format, "Assert queue should be empty");
        if (deque.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 0");
        if (deque.size() == 0)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        //

        try {
            StdOut.printf(format, "Assert argument of addFirst() should not be null");
            deque.addFirst(null);
            StdOut.printf("FAILED\n");
        }
        catch (IllegalArgumentException e) {
            StdOut.printf("PASS\n");
        }

        try {
            StdOut.printf(format, "Assert argument of addLast() should not be null");
            deque.addLast(null);
            StdOut.printf("FAILED\n");
        }
        catch (IllegalArgumentException e) {
            StdOut.printf("PASS\n");
        }

        //

        try {
            StdOut.printf(format, "Assert removeFirst() should throw exception on empty queue");
            deque.removeFirst();
            StdOut.printf("FAILED\n");
        }
        catch (NoSuchElementException e) {
            StdOut.printf("PASS\n");
        }

        try {
            StdOut.printf(format, "Assert removeLast() should throw exception on empty queue");
            deque.removeLast();
            StdOut.printf("FAILED\n");
        }
        catch (NoSuchElementException e) {
            StdOut.printf("PASS\n");
        }

        //

        StdOut.println("Invoke addFirst(\"a\")");
        deque.addFirst("a");
        StdOut.println("Invoke addFirst(\"b\")");
        deque.addFirst("b");
        StdOut.println("Invoke addFirst(\"c\")");
        deque.addFirst("c");

        StdOut.printf(format, "Assert items should be \"c,b,a\"");
        StringBuilder sb = new StringBuilder();
        for (String s : deque) {
            sb.append(s);
            sb.append(",");
        }
        if (sb.toString().equals("c,b,a,"))
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue should not be empty");
        if (!deque.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 3");
        if (deque.size() == 3)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        //

        try {
            StdOut.printf(format, "Assert iterator should throw exception when no next");
            Iterator<String> iterator = deque.iterator();
            for (int i = 0; i <= deque.size(); i++) {
                iterator.next();
            }
            StdOut.printf("FAILED\n");
        }
        catch (NoSuchElementException e) {
            StdOut.printf("PASS\n");
        }

        //

        StdOut.printf(format, "Assert removeFirst() remove \"c\"");
        if (deque.removeFirst().equals("c")) {
            StdOut.printf("PASS\n");
        }
        else {
            StdOut.printf("FAILED\n");
        }
        StdOut.printf(format, "Assert removeFirst() remove \"b\"");
        if (deque.removeFirst().equals("b")) {
            StdOut.printf("PASS\n");
        }
        else {
            StdOut.printf("FAILED\n");
        }
        StdOut.printf(format, "Assert removeFirst() remove \"a\"");
        if (deque.removeFirst().equals("a")) {
            StdOut.printf("PASS\n");
        }
        else {
            StdOut.printf("FAILED\n");
        }

        StdOut.printf(format, "Assert queue should be empty");
        if (deque.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 0");
        if (deque.size() == 0)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        //

        StdOut.println("Invoke addLast('a')");
        deque.addLast("a");
        StdOut.println("Invoke addLast('b')");
        deque.addLast("b");
        StdOut.println("Invoke addLast('c')");
        deque.addLast("c");

        StdOut.printf(format, "Assert items should be \"a,b,c\"");
        StringBuilder sb2 = new StringBuilder();
        for (String s : deque) {
            sb2.append(s);
            sb2.append(",");
        }
        if (sb2.toString().equals("a,b,c,"))
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue should not be empty");
        if (!deque.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 3");
        if (deque.size() == 3)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        //

        StdOut.printf(format, "Assert removeLast() remove \"c\"");
        if (deque.removeLast().equals("c")) {
            StdOut.printf("PASS\n");
        }
        else {
            StdOut.printf("FAILED\n");
        }
        StdOut.printf(format, "Assert removeLast() remove \"b\"");
        if (deque.removeLast().equals("b")) {
            StdOut.printf("PASS\n");
        }
        else {
            StdOut.printf("FAILED\n");
        }
        StdOut.printf(format, "Assert removeLast() remove \"a\"");
        if (deque.removeLast().equals("a")) {
            StdOut.printf("PASS\n");
        }
        else {
            StdOut.printf("FAILED\n");
        }

        StdOut.printf(format, "Assert queue should be empty");
        if (deque.isEmpty())
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");

        StdOut.printf(format, "Assert queue's size should be 0");
        if (deque.size() == 0)
            StdOut.printf("PASS\n");
        else
            StdOut.printf("FAILED\n");
    }
}
