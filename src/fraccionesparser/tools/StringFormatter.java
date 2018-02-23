package fraccionesparser.tools;

/**
 * Clase encargada de dar formatos adicionales a las cadenas.
 * 
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public final class StringFormatter {

    /**
     * Convierte una cadena de un numerador en una cadena de un denominador.
     * 
     * @param in La cadena a convertir.
     * @return La cadena convertida.
     */
    public static String fraccionar(String in){
        // Juntar las palabras y cambiar la 'y' por 'i'
        in = in.replace(" y ", "i");
        
        // Agregar el sufijo -avo
        if(in.endsWith("a")) return in + "vo";
        return in + "avo";
    }
    
    /**
     * Remueve la 's' que indica plural, si la tiene
     * 
     * @param in la cadena con el plural
     * @return una cadena sin 's' plural
     */
    public static String depluralize(String in){
        if(in.endsWith("s")) return new String(in.substring(0, in.length() - 1));
        return in;
    }
    
    /**
     * Agrega una 's' que indica plural, si la necesita
     * 
     * @param in la cadena sin el plural
     * @return  una cadena con 's' plural
     */
    public static String pluralize(String in){
        if(!in.endsWith("s")) return in + "s";
        return in;
    }   
}
