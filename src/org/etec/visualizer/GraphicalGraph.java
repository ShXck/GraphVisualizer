package org.etec.visualizer;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.tec.datastructures.general.Edge;


public class GraphicalGraph {

    private SparseGraph<String, Edge> graphical_graph;

    public GraphicalGraph(){
        graphical_graph = new SparseGraph<>();
        build_graph();
    }

    /**
     * Construye el grafo gráfico, usando el grafo del servidor como base.
     */
    private void build_graph(){
        graphical_graph.addVertex("Azalea");
        graphical_graph.addVertex("A+R");
        graphical_graph.addVertex("HGX");
        graphical_graph.addVertex("Exxon");
        graphical_graph.addVertex("AXXES");
        graphical_graph.addVertex("Venice");
        graphical_graph.addVertex("Jack Spade");
        graphical_graph.addVertex("WeegoHome");
        graphical_graph.addVertex("NXT");
        graphical_graph.addVertex("Krystal");
        graphical_graph.addVertex("Shell");
        graphical_graph.addVertex("Jenni Kayne");
        graphical_graph.addVertex("Texaco");
        graphical_graph.addVertex("Dahlia");

        graphical_graph.addEdge(new Edge(15), "Azalea", "A+R", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(14), "A+R", "Azalea", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(23), "Exxon", "Azalea", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(10), "Exxon", "A+R", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(9), "A+R", "Exxon", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(8), "A+R", "HGX", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(7), "HGX", "A+R", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(15), "HGX", "Venice", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(11), "Exxon", "AXXES", EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(7), "AXXES", "Venice",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(3), "Venice", "Jack Spade",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(8), "Venice", "NXT",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(5), "Venice", "Exxon",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(6), "Jack Spade", "NXT",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(15), "AXXES", "WeegoHome",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(13), "WeegoHome", "AXXES",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(6), "Jack Spade", "HGX",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(12), "Jack Spade", "A+R",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(4), "NXT", "WeegoHome",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(20), "WeegoHome", "Exxon",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(4), "NXT", "Krystal",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(3), "Krystal", "Jack Spade",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(13), "Krystal", "Texaco",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(12), "NXT", "Jenni Kayne",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(11), "Jenni Kayne", "NXT",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(11), "Jenni Kayne", "Texaco",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(7), "Texaco", "Krystal",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(16), "Texaco", "WeegoHome",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(10), "WeegoHome", "Shell",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(13), "Shell", "WeegoHome",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(9), "Shell", "Jenni Kayne",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(7), "Shell", "Dahlia",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(8), "Dahlia", "Jenni Kayne",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(9), "Dahlia", "Shell",EdgeType.DIRECTED);
        graphical_graph.addEdge(new Edge(10), "Texaco", "Dahlia",EdgeType.DIRECTED);
    }

    /**
     * @return el grafo gráfico.
     */
    public SparseGraph<String, Edge> graph(){
        return this.graphical_graph;
    }

}
