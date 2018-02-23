package fraccionesparser.tools;

import java.util.Map;

/**
 * Clase encargada de construir los diccionarios para numeradores y
 * denominadores.
 * 
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public final class MapBuilder {
    
    //Cadenas constantes
    private static final String[] UNIDADES = {
        "cero", "un", "dos", "tres", "cuatro",
        "cinco", "seis", "siete", "ocho", "nueve"
    };
    
    private static final String[] DECENAS = {
       "treinta", "cuarenta", "cincuenta",
        "sesenta", "setenta", "ochenta", "noventa"
    };
    
    private static Map<String, Integer> numeradores, denominadores;
    
    /**
     * Construye los diccionarios para numeradores y denominadores.
     * 
     * @param numeradores El mapa donde se colocará el diccionario de 
     * numeradores
     * @param denominadores El mapa donde se colocará el diccionario de 
     * denominadores
     */
    public static void buildFraccion(Map numeradores, Map denominadores){
        MapBuilder.numeradores = numeradores;
        MapBuilder.denominadores = denominadores;
        
        buildNumeradores();
        buildDenominadores();
    }
    
    /**
     * Construye el diccionario para numeradores.
     */
    private static void buildNumeradores(){
        
        // Unidades
        for(int i = 0; i < UNIDADES.length; i++){
            numeradores.put(UNIDADES[i], i);
        }
        
        // Decenas especiales
        numeradores.put("diez", 10); 
        numeradores.put("once", 11); 
        numeradores.put("doce", 12); 
        numeradores.put("trece", 13); 
        numeradores.put("catorce", 14); 
        numeradores.put("quince", 15);
        
        // Decenas construidas
        for(int i = 6; i < 10; i++){
            numeradores.put("dieci" + UNIDADES[i], 10 + i);
        }
        
        // Grupo del 20 - 29
        numeradores.put("veinte", 20);
        for(int i = 1; i < 10; i++){
            numeradores.put("veinti" + UNIDADES[i], 20 + i);
        }
     
        // Grupo del 30 - 99
        for(int i = 0; i < DECENAS.length; i++){
            numeradores.put(DECENAS[i], i*10 + 30);
            for(int j = 1; j < 10; j++){
                numeradores.put(DECENAS[i] + " y " + UNIDADES[j], i*10 + 30 + j);
            }
        }
        
        // Cien
        numeradores.put("cien", 100);
    }
    
    /**
     * Construye el diccionario para denominadores.
     */
    private static void buildDenominadores(){
        
        // Unidades
        denominadores.put("entero", 1);
        denominadores.put("medio", 2);
        denominadores.put("tercio", 3);
        denominadores.put("cuarto", 4);
        denominadores.put("quinto", 5);
        denominadores.put("sexto", 6);
        denominadores.put("septimo", 7);
        denominadores.put("octavo", 8);  
        denominadores.put("noveno", 9); 
        denominadores.put("decimo", 10);

        /*
        Usa los numeradores para crear los denominadores puesto que sólo hay
        que unir las palabras, cambiar la 'y' por 'i' y agregar el sufijo '-avo'
        */
        for(int i = 11; i < 100; i++){
            denominadores.put(StringFormatter.fraccionar(
                MapHandler.getKeyByValue(numeradores, i)), i);
        }
        
        // Cien
        denominadores.put("centavo", 100);
    }
}
