package org.etec.network;

import edu.uci.ics.jung.graph.util.EdgeType;
import org.etec.visualizer.GraphVisualizer;
import org.etec.visualizer.GraphicalGraph;
import org.tec.datastructures.general.Edge;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphManager implements ActionListener{

    private static GraphicalGraph graph;

    public GraphManager(GraphicalGraph g){
        this.graph = g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        read_command(e.getActionCommand());
    }

    /**
     * Lee los comandos provenientes de la pestañas de la ventana.
     * @param command el comando.
     */
    private void read_command(String command){
        switch (command){
            case "Añadir Tienda":
                new DataInputDialog(command);
                break;
            case "Añadir Gasolinera":
                new DataInputDialog(command);
                break;
            case "Añadir Centro de Distribución":
                new DataInputDialog(command);
                break;
        }
    }

    /**
     * Agrega un nuevo vértice al grafo gráfico.
     * @param name el nombre del vértice.
     * @param in las conexiones entrantes.
     * @param out las conexiones salientes.
     * @param distance las distancias entre caminos..
     * @param time el tiempo entre caminos.
     * @param danger el peligro entre caminos.
     */
    public void add_new_vertex(String name, String in, String out, String distance, String time, String danger){

        String[] in_result = in.split(",");
        String[] out_result = out.split(",");
        int[] distances_result = to_int(distance.split(","));
        int[] time_result = to_int(time.split(","));
        int[] danger_result = to_int(danger.split(","));

        for(int i = 0; i < out_result.length; i++){
            graph.graph().addEdge(new Edge(distances_result[i] + time_result[i] + danger_result[i]), name, out_result[i], EdgeType.DIRECTED);
        }

        for(int j = distances_result.length - in_result.length; j < distances_result.length; j++){
            int k = in_result.length;
            if (k > j) {
                String current_in = in_result[(j+1)-k];
                graph.graph().addEdge(new Edge(distances_result[j] + time_result[j] + danger_result[j]), current_in, name, EdgeType.DIRECTED);
            }else if (k == j) {
                String  current_in = in_result[j-k];
                graph.graph().addEdge(new Edge(distances_result[j] + time_result[j] + danger_result[j]),current_in,name,EdgeType.DIRECTED);
            }else{
                String current_in = in_result[(j-1)-k];
                graph.graph().addEdge(new Edge(distances_result[j] + time_result[j] + danger_result[j]), current_in, name);
            }
        }

        GraphVisualizer.update();
    }

    /**
     * Convierte un arreglo de strings a uno de enteros.
     * @param s el arreglo de strings.
     * @return el arreglo de enteros.
     */
    private static int[] to_int(String[] s){

        int[] result = new int[s.length];

        for (int i = 0; i < s.length; i++){
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }

    class DataInputDialog extends JDialog{

        private JTextField name_field;
        private JTextField in_connections;
        private JTextField out_connections;
        private JTextField store_category;
        private JTextField distance_field;
        private JTextField time_field;
        private JTextField danger_field;
        private JLabel name_lbl;
        private JLabel in_lbl;
        private JLabel out_lbl;
        private JLabel store_catg_lbl;
        private JLabel distance_lbl;
        private JLabel time_lbl;
        private JLabel danger_lbl;
        private JButton submit_button;
        private String type;

        public DataInputDialog(String command){
            name_field = new JTextField();
            in_connections = new JTextField();
            out_connections = new JTextField();
            store_category = new JTextField();
            distance_field = new JTextField();
            time_field = new JTextField();
            danger_field = new JTextField();
            name_lbl = new JLabel("Nombre:");
            in_lbl = new JLabel("Conexiones entrantes:");
            out_lbl = new JLabel("Conexiones salientes");
            store_catg_lbl = new JLabel("Categoría (Tienda):");
            distance_lbl = new JLabel("Distancias");
            danger_lbl = new JLabel("Peligrosidad");
            time_lbl = new JLabel("Tiempos");
            submit_button = new JButton("Añadir");
            setTitle("Gestor ETEC");
            set_type(command);
            set_ui();
            set_listeners();
        }

        /**
         * Fija el valor del tipo de establecimiento que se crea.
         * @param type_command el tipo de establecimiento.
         */
        private void set_type(String type_command){
            switch (type_command){
                case "Añadir Tienda":
                    type = "Store";
                    break;
                case "Añadir Gasolinera":
                    type = "Station";
                    break;
                case "Añadir Centro de Distribución":
                    type = "Center";
                    break;
            }
        }

        /**
         * Fija el listener del botón.
         */
        private void set_listeners() {
            submit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RequestManager.POST("create", JSONHandler.build_new_establishment_info(name_field.getText(), in_connections.getText(),
                            out_connections.getText(), store_category.getText(), distance_field.getText(), time_field.getText(), danger_field.getText(), type));

                    add_new_vertex(name_field.getText(), in_connections.getText(), out_connections.getText(), distance_field.getText(), time_field.getText(), danger_field.getText());
                }
            });
        }

        /**
         * Fija los componentes gráficos del dialogo.
         */
        private void set_ui() {
            JPanel top_panel = new JPanel(new GridBagLayout());
            JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            button_panel.add(submit_button);

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.insets = new Insets(4, 4, 4, 4);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            top_panel.add(name_lbl, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            top_panel.add(name_field, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            top_panel.add(in_lbl, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.weightx = 1;
            top_panel.add(in_connections, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            top_panel.add(out_lbl, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.weightx = 1;
            top_panel.add(out_connections, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            top_panel.add(store_catg_lbl, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.weightx = 1;
            top_panel.add(store_category, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            top_panel.add(distance_lbl, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.weightx = 1;
            top_panel.add(distance_field, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            top_panel.add(time_lbl, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.weightx = 1;
            top_panel.add(time_field, gbc);

            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            top_panel.add(danger_lbl, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 6;
            gbc.weightx = 1;
            top_panel.add(danger_field, gbc);

            setSize(450,300);
            setVisible(true);
            this.add(top_panel);
            this.add(button_panel, BorderLayout.SOUTH);

        }

    }


}
