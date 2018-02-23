package fraccionesparser.tools;

import java.util.Map;

/**
 * Clase que maneja el diccionario.
 * 
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public final class MapHandler {

    /**
     * Busca la primera llave que coincida con el valor especificado.
     * 
     * @param map El diccionario donde lo buscará
     * @param value El valor que buscará
     * @return La llave si se encuentra, nulo si no
     */
    public static String getKeyByValue(Map<String, Integer> map, Integer value){
        for(String key : map.keySet()){
            if(map.get(key).equals(value)){
                return key;
            }
        }
        return null;
    }
    
    /**
     * Busca el valor de una llave determinada.
     * 
     * @param map El diccionario donde se buscará
     * @param key La llave que se buscará
     * @return El valor de la llave si existe, nulo si no
     */
    public static Integer get(Map<String, Integer> map, String key){
        return map.get(key);
    }
}
