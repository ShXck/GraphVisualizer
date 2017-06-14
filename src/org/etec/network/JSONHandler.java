package org.etec.network;

import org.json.JSONObject;

public class JSONHandler {

    /**
     * Construye un json con la información del nuevo establecimiento creado.
     * @param name el nombre.
     * @param in las conexiones entrantes.
     * @param out las conexiones salientes.
     * @param category la categoría, aplica solo para las tiendas.
     * @param distance la distancia entre los vértices.
     * @param time el tiempo que recorre entre vértices.
     * @param danger el peligro entre los caminos.
     * @return el json con los detalles.
     */
    public static String build_new_establishment_info(String name, String in, String out, String category, String distance, String time, String danger, String type){

        JSONObject info = new JSONObject();

        info.put("name", name);
        info.put("in",in);
        info.put("out",out);
        info.put("category",category);
        info.put("type", type);
        info.put("distance",distance);
        info.put("time",time);
        info.put("danger",danger);
        System.out.println(info.toString());
        return  info.toString();
    }

    /**
     * Construye un json con la información del camino que se va a cerrar.
     * @param from el punto de inicio.
     * @param to el punto final.
     * @return un json con la información.
     */
    public static String build_closed_path_info(String from, String to){
        JSONObject info = new JSONObject();
        info.put("from",from);
        info.put("to",to);
        return info.toString();
    }
}
