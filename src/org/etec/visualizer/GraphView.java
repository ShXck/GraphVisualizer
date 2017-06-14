package org.etec.visualizer;

import org.etec.network.RequestManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class GraphView {

    public static void main(String[] args) {

        GraphVisualizer visualizer = new GraphVisualizer();
        visualizer.show();

        update_graph(visualizer);
        check_for_paths_updates(visualizer);
    }

    /**
     * Petición para obtener el estado de los paquetes en tránsito.
     */
    private static void update_graph(GraphVisualizer v){

        Timer request_timer = new Timer();

        request_timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                RequestManager.GET("packages/active");
                RequestManager.wait_for_response(500);
                process_info(RequestManager.GET_REQUEST_DATA(),v);
            }
        },5000,5000);
    }

    /**
     * Verifica cambios en las rutas de la red.
     * @param v el visualizador del grafo.
     */
    private static void check_for_paths_updates(GraphVisualizer v){
        Timer request_timer = new Timer();

        request_timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                RequestManager.GET("paths");
                RequestManager.wait_for_response(500);
                process_paths_updates(RequestManager.GET_REQUEST_DATA(),v);
            }
        },7000,5000);
    }

    /**
     * Procesa la información de los paquetes en tránsito.
     * @param details la información en json.
     * @param v el visualizador del grafo.
     */
    private static void process_info(String details, GraphVisualizer v){

        JSONObject json = new JSONObject(details);
        try {
            JSONArray active_packages = json.getJSONArray("packages");
            String[] vertexes = new String[active_packages.length()];

            for (int i = 0; i < active_packages.length(); i++){
                vertexes[i] = (String) active_packages.get(i);
            }
            v.paint_vertex(vertexes);
            v.update();
        }catch (JSONException e){
            return;
        }
    }

    /**
     * Verifica el resultado del cierre pedido, si es positivo se procede a cerrar la ruta.
     * @param info la verificación del servidor.
     * @param v el visualizador del grafo.
     */
    private static void process_paths_updates(String info, GraphVisualizer v){
        JSONObject details = new JSONObject(info);
        boolean result = details.getBoolean("result");
        if (result){
            String from = details.getString("from");
            String to = details.getString("to");
            v.graph_manager().remove_path(from,to);
        }
    }
}
