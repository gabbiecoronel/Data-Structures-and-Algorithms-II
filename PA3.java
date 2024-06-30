/*      Summer 24
    COP 3503C Assignment 3
This program is written by: Gabrielle Coronel */

import java.util.ArrayList;
import java.util.Scanner;

public class PA3 {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        /* Initializing the variables
         n - number of computers in the enemy network, m - number of connections between pairs of computers
         d - number of connections to destroy */
        int n = scanner.nextInt(), m = scanner.nextInt(), d = scanner.nextInt();

        // creating the array list of edges
        ArrayList<Edge> edges = new ArrayList<>(m);

        // for loop runs through the number of connections between pairs of computers (m)
        for (int i = 0; i < m; i++) {
            // getting the edges
            int u = scanner.nextInt() - 1;
            int v = scanner.nextInt() - 1;
            // adding the edges into the list
            edges.add(new Edge(u, v, true));
        }

        // store the order the list to be destroyed of connections of computers
        int[] destroy = new int[d];

        // for loop runs through the number of connections to destroy
        for (int i = 0; i < d; i++) {
            destroy[i] = scanner.nextInt() - 1;
            // set the edge's exist to false since it's destroyed
            edges.get(destroy[i]).setExist(false);
        }

        // initialize my disjoint set with n elements
        PA3 dj = new PA3(n);

        // for loop runs through the number of connections between pairs of computers (m)
        for (int i = 0; i < m; i++) {
            // checks if the edge exists (is true)
            if (edges.get(i).isExist()) {
                // perform union operation
                dj.union(edges.get(i).getU(), edges.get(i).getV());
            }
        }

        // result array to be printed
        long[] result = new long[d + 1];
        result[d] = curConnect;

        // for loop runs in reverse order
        for (int i = d - 1; i >= 0; i--) {
            // perform union operation of the destroyed connections
            dj.union(edges.get(destroy[i]).getU(), edges.get(destroy[i]).getV());
            result[i] = curConnect;
        }

        // for loop runs through the result array
        for (long i : result)
            System.out.println(i);
    }

    private pair[] parents;
    private static long curConnect;

    // create the initial state of a disjoint set of n elements, 0 to n-1
    public PA3(int n) {
        curConnect = n;
        // all nodes start as leaf nodes
        parents = new pair[n];
        // for loop runs through the number of computers in the enemy network (n)
        for (int i = 0; i < n; i++)
            parents[i] = new pair(i, 0, 1);
    }

    // find method returns the root node of the tree storing id
    public int find(int id) {

        // I am the root of the tree)
        if (id == parents[id].getID())
            return id;

        // Find my parent's root
        int res = find(parents[id].getID());

        // if res is not mu existing parent, make it parent
        if (res != parents[id].getID())
            // Attach me directly to the root of my tree.
            parents[id].setID(res);

        return res;
    }

    // union method performs union operation
    public boolean union(int id1, int id2) {

        // find the parents of both nodes
        int root1 = find(id1), root2 = find(id2);

        // no union needed
        if (root1 == root2)
            return false;

        // calculate the current connectivity by subtracting the size of tree 1 squared and the size of tree 2 squared
        curConnect -= (long) parents[root1].getSize() * parents[root1].getSize() + (long) parents[root2].getSize() * parents[root2].getSize();

        // attach tree 2 to tree 1
        if (parents[root1].getHeight() > parents[root2].getHeight()) {
            parents[root2].setID(root1);
            // increase the size of tree 1 since tree 2 got connected to tree 1
            parents[root1].incSize(parents[root2].getSize());
            // calculate the current connectivity by adding the size of tree 1 squared
            curConnect += (long) parents[root1].getSize() * parents[root1].getSize();
        }

        // attach tree 1 to tree 2
        else if (parents[root2].getHeight() > parents[root1].getHeight()) {
            parents[root1].setID(root2);
            // increase the size of tree 2 since tree 1 got connected to tree 2
            parents[root2].incSize(parents[root1].getSize());
            // calculate the current connectivity by adding the size of tree 2 squared
            curConnect += (long) parents[root2].getSize() * parents[root2].getSize();
        }

        // same height case - attach tree 2 to tree 1
        else {
            parents[root2].setID(root1);
            parents[root1].incHeight();
            // increase the size of tree 1 since tree 2 got connected to tree 1
            parents[root1].incSize(parents[root2].getSize());
            // calculate the current connectivity by adding the size of tree 1 squared
            curConnect += (long) parents[root1].getSize() * parents[root1].getSize();
        }
        // union was successful
        return true;
    }
}

// class of pair
class pair {

    private int ID, height, size;

    // constructor for the pair
    public pair(int myNum, int myHeight, int mySize) {
        ID = myNum;
        height = myHeight;
        size = mySize;
    }

    // getters and setters
    public int getHeight() {
        return height;
    }

    public int getID() {
        return ID;
    }

    public void incHeight() {
        height++;
    }

    public void setID(int newID) {
        ID = newID;
    }

    public void incSize(int incSize) {
        size += incSize;
    }

    public int getSize() {
        return size;
    }
}
// class for Edge
class Edge {

    // initializing the variables
    private int u, v;
    private boolean exist;

    // constructor for the edge
    public Edge(int myU, int myV, boolean myExist) {
        u = myU;
        v = myV;
        exist = myExist;
    }

    // getters and setters
    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public boolean isExist() {
        return exist;
    }
}