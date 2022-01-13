package com.example.rtpgui;

// Ford-Fulkerson algorith in Java

import java.util.LinkedList;

public class FordFulkerson {

    public int V = 6;

    // Using BFS as a searching algorithm
    boolean bfs(int[][] Graph, int s, int t, int[] p) {
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; ++i)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        p[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (!visited[v] && Graph[u][v] > 0) {
                    queue.add(v);
                    p[v] = u;
                    visited[v] = true;
                }
            }
        }

        return (visited[t]);
    }

    // Applying ford-fulkerson algorithm
    int fordFulkerson(int[][] graph, int s, int t) {
        int u, v;
        V = t + 1;
        int[][] Graph = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                Graph[u][v] = graph[u][v];

        int p[] = new int[V];

        int max_flow = 0;

        while (bfs(Graph, s, t, p)) {
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = p[v]) {
                u = p[v];
                path_flow = Math.min(path_flow, Graph[u][v]);
            }

            for (v = t; v != s; v = p[v]) {
                u = p[v];
                Graph[u][v] -= path_flow;
                Graph[v][u] += path_flow;
            }

            max_flow += path_flow;
        }

        return max_flow;
    }

}