package org.etec.visualizer;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import org.etec.network.GraphManager;
import org.tec.datastructures.general.Edge;
import javax.swing.*;
import java.awt.*;


public class GraphVisualizer {

    private static VisualizationViewer<String, Edge> visualizer;
    private static Transformer<String,Paint> vertex_painter;
    private static GraphicalGraph graph;

    /**
     * Muestra el grafo en una ventana.
     */
    public void show(){

        graph = new GraphicalGraph();

        Layout<String, Edge> layout = new CircleLayout<>(graph.graph());
        layout.setSize(new Dimension(600, 600));
        visualizer = new VisualizationViewer<>(layout);
        visualizer.setPreferredSize(new Dimension(650, 650));
        paint_vertex(null);
        float dash[] = {10.0f};
        final Stroke edge_stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<Edge, Stroke> edge_stroke_transformer = new Transformer<Edge, Stroke>() {
            @Override
            public Stroke transform(Edge e) {
                return edge_stroke;
            }
        };

        visualizer.getRenderContext().setVertexFillPaintTransformer(vertex_painter);
        visualizer.getRenderContext().setEdgeStrokeTransformer(edge_stroke_transformer);
        visualizer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        visualizer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        DefaultModalGraphMouse mouse = new DefaultModalGraphMouse();
        mouse.setMode(ModalGraphMouse.Mode.PICKING);
        visualizer.setGraphMouse(mouse);
        setUI(visualizer,graph);
    }

    /**
     * Prepara los componentes gráficos del visualizador.
     * @param v el visualizador.
     */
    private void setUI(VisualizationViewer v, GraphicalGraph g){

        final JMenuBar menu_bar = new JMenuBar();

        JMenu management_tab = new JMenu("Gestor de red");

        JMenuItem add_store = new JMenuItem("Añadir Tienda");
        add_store.setActionCommand("Añadir Tienda");
        JMenuItem add_station = new JMenuItem("Añadir Gasolinera");
        add_station.setActionCommand("Añadir Gasolinera");
        JMenuItem add_center = new JMenuItem("Añadir Centro de Distribución");
        add_center.setActionCommand("Añadir Centro de Distribución");

        GraphManager manager = new GraphManager(g);

        add_store.addActionListener(manager);
        add_station.addActionListener(manager);
        add_center.addActionListener(manager);

        management_tab.add(add_store);
        management_tab.add(add_station);
        management_tab.add(add_center);

        menu_bar.add(management_tab);

        JFrame frame = new JFrame("ETEC Network");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(v);
        frame.setJMenuBar(menu_bar);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Repinta el grafo, cuando se hacen cambios.
     */
    public static void update(){
        visualizer.repaint();
    }

    /**
     * @return el visualizador de del grafo.
     */
    public VisualizationViewer<String, Edge> visualizer(){
        return visualizer;
    }

    /**
     * Pinta un vértice específico.
     * @param vertex el vértice que se quiere pintar.
     */
    public void paint_vertex(String[] vertex){

        if (vertex == null) {
            vertex_painter = new Transformer<String, Paint>() {
                @Override
                public Paint transform(String s) {
                    return Color.GREEN;
                }
            };
            visualizer.getRenderContext().setVertexFillPaintTransformer(vertex_painter);
        }else {
            vertex_painter = new Transformer<String, Paint>() {
                @Override
                public Paint transform(String s) {
                    for (int i = 0; i < vertex.length; i++){
                        if (s.equals(vertex[i])) return Color.ORANGE;
                    }
                    return Color.GREEN;
                }
            };
            visualizer.getRenderContext().setVertexFillPaintTransformer(vertex_painter);
        }
    }
}
