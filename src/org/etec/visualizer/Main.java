package org.etec.visualizer;

import org.etec.network.RequestManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static boolean is_changed = false;

    public static void main(String[] args) {

        GraphVisualizer visualizer = new GraphVisualizer();
        visualizer.show();

        update_graph(visualizer);
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
}
