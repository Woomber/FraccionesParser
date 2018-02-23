package fraccionesparser;

import fraccionesparser.tools.MapHandler;
import fraccionesparser.tools.MapBuilder;
import fraccionesparser.tools.StringFormatter;
import fraccionesparser.models.Fraccion;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de conversiones texto-fracción y fracción-texto, así como
 * solución de operaciones.
 * 
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public class Parser {
    
    //Diccionarios con numeradores y denominadores
    private final Map<String, Integer> numeradores;
    private final Map<String, Integer> denominadores;
    
    /**
     * Constructor de la clase
     * Llama al MapBuilder para que genere el diccionario de numeradores
     * y denominadores.
     */
    public Parser() {
        numeradores = new HashMap<>();
        denominadores = new HashMap<>();
        MapBuilder.buildFraccion(numeradores, denominadores);
    }
    
    /**
     * Resuelve una operación fraccionaria recibida en forma de texto.
     * 
     * Ejemplos de entrada:
     * "un quinto menos cinco cuartos"
     * "diecisiete treintaicincoavos entre cuarenta y tres enteros"
     * 
     * Rango de las fracciones:
     * Numerador 0 - 100
     * Denominador 1 - 100
     * 
     * Operaciones disponibles
     * Suma: "mas"
     * Resta: "menos"
     * Multiplicación: "por"
     * División: "entre"
     * 
     * @param arg La operación fraccionaria a recibir. No es sensible a
     * mayúsculas.
     * @return La cadena "El resultado es: " seguido del resultado de la
     * operación, si fue exitosa. Un mensaje de error si no lo fue.
     */
    public String solve(String arg) {
        
        List<String> args;
        ArrayList<String>[] fracciones;
        Fraccion fraccA, fraccB, resultado;
        int nFraccion = 0;
        int nOperacion = 0;
        
        // Preparar cadena
        arg = arg.toLowerCase();
        args =  Arrays.<String>asList(arg.split(" "));
        
        // Separación de fracciones
        fracciones = new ArrayList[2];
        fracciones[0] = new ArrayList<>();
        fracciones[1] = new ArrayList<>();
        
        /*
        Separa ambas fracciones usando el operador, coloca cada una en su
        respectivo arreglo. Detecta si no hay operador o si hay más de uno.
        */
        for(String a : args){
            if(nFraccion > 2){
                System.out.println("Hay más de 2 fracciones");
            }
            switch(a){
                case "mas":
                    nOperacion = 1;
                    nFraccion++;
                    break;
                case "menos":
                    nOperacion = 2;
                    nFraccion++;
                    break;
                case "por":
                    nOperacion = 3;
                    nFraccion++;
                    break;
                case "entre":
                    nOperacion = 4;
                    nFraccion++;
                    break;
                default:
                    fracciones[nFraccion].add(a);
            }
        }
        
        //Conversión a fracciones para hacer la operación
        fraccA = stringToFraccion(fracciones[0]);
        fraccB = stringToFraccion(fracciones[1]);
        
        if(fraccA == null)
            return "La primera fraccion no tiene formato correcto";
        if(fraccB == null)
            return "La segunda fraccion no tiene formato correcto";
        
        //Realizar la operación
        switch(nOperacion){
            case  1: resultado = Fraccion.sumar(fraccA, fraccB); break;
            case  2: resultado = Fraccion.restar(fraccA, fraccB); break;
            case  3: resultado = Fraccion.multiplicar(fraccA, fraccB); break;
            case  4: resultado = Fraccion.dividir(fraccA, fraccB); break;
            default: return "No se ha presentado una operacion.";
        }
        
        return "El resultado es: " + fraccionToString(resultado);
    }
    
    /**
     * Convierte un conjunto de cadenas de palabras en una fracción.
     * 
     * Las fracciones pueden tener dos o cuatro palabras. El denominador
     * siempre es la última palabra, el numerador puede ser de una o tres.
     *  
     * Ejemplos:
     * "treinta y siete quintos": 4 palabras
     * "ocho cuarentaitresavos": 2 palabras
     *  
     * Se usa StringFormatter.depluralize para remover el plural del
     * denominador para facilitar la búsqueda en el diccionario.
     * 
     * @param in Conjunto de cadenas que forman una fracción
     * @return La fracción convertida si el parámetro es una cadena de fracción
     * válida, valor nulo en caso contrario.
     */
    Fraccion stringToFraccion(ArrayList<String> in){  
        //Usando Integer porque MapHandler.get puede retornar null
        Integer numerador, denominador;
       
        // Selección de 2 o 4 palabras, de lo contrario no es válido.
        switch(in.size()){
            case 2:
                numerador = MapHandler.get(
                    numeradores,
                    in.get(0)
                );
                denominador = MapHandler.get(
                    denominadores,
                    StringFormatter.depluralize(in.get(1))
                );
                break;

            case 4:
                numerador = MapHandler.get(
                    numeradores,
                    String.join(" ", in.subList(0, 3))
                );
                denominador = MapHandler.get(
                    denominadores,
                    StringFormatter.depluralize(in.get(3))
                );
                break;
            
            default:
                return null;
        }
        
        // No se encontró el valor en el diccionario
        if(numerador == null || denominador == null) return null;
        
        return new Fraccion(numerador, denominador);
    }
    
     /**
     * Convierte una fracción a su equivalente escrito.
     * @param in la fracción
     * @return una cadena con la fracción escrita
     */
    String fraccionToString(Fraccion in){
        return numeradorToString(in.numerador) + " " 
            + denominadorToString(in.denominador, Math.abs(in.numerador) != 1);
    }
    
    /**
     * Convierte el numerador de una fracción a su equivalente escrito.
     * 
     * Rango de conversión asegurado: -100 000 a 100 000, incluyente.
     * 
     * @param numerador El número a convertir a escrito.
     * @return Una cadena con el número escrito.
     */
    String numeradorToString(long numerador){
        String cadena = "";
        String tmp;
        
        // Negativos
        if(numerador < 0) {
            cadena += "menos ";
            numerador *= -1;
        }
        
        // Millares
        if(numerador/1000 > 0){
            tmp = MapHandler.getKeyByValue(numeradores, (int) numerador/1000);
            if(numerador / 1000 == 1) tmp = "";
            cadena += tmp + " mil ";
        }
        numerador %= 1000;
        
        // Centenas
        if(numerador/100 == 1){
            cadena += (numerador % 100 > 0)? "ciento " : "cien ";
        }
        if(numerador/100 > 1) {
            switch((int) numerador/100){
                case 5: cadena += "quinientos "; break;
                case 7: cadena += "setecientos "; break;
                case 9: cadena += "novecientos "; break;
                default:
                    cadena += MapHandler.getKeyByValue(numeradores, (int) numerador/100);
                    cadena += "cientos ";
                
            }
        }
        numerador %= 100;
        
        //Decenas y unidades
        if(numerador < 30){
            cadena += MapHandler.getKeyByValue(numeradores, (int) numerador);
        } else {
            cadena += MapHandler.getKeyByValue(numeradores, (int) numerador/10*10);
            if(numerador % 10 > 0){
                cadena += " y ";
                cadena += MapHandler.getKeyByValue(numeradores, (int) numerador%10);
            }
        }
        
        return cadena;
    }
    
    /**
     * Convierte el denominador de una fracción a su equivalente escrito.
     * 
     * Rango de conversión asegurado: -31 000 a 31 000, excluyente.
     * 
     * @param denominador El denominador a convertir a escrito.
     * @param plural Si el denominador debería ser plural (Agrega 's' final).
     * @return Una palabra con el denominador escrito.
     */
    String denominadorToString(long denominador, boolean plural){
        String cadena = "";
        String tmp;
        
        // Millares
        if(denominador/1000 > 0){
            tmp = MapHandler.getKeyByValue(numeradores, (int) denominador/1000);
            if(denominador / 1000 == 1) tmp = "";
            cadena += tmp + ((denominador % 1000 > 0)? "mil":"milesimo");
        }
        denominador %= 1000;
        
        // Centenas
        if(denominador/100 > 0) {
            tmp = MapHandler.getKeyByValue(numeradores, (int) denominador/100);
            if(denominador / 100 == 1)
                cadena += (denominador %100 > 0)? "ciento":"centavo";
            else {
                switch((int)denominador/100){
                    case  5: cadena += "quin"; break;
                    case  7: cadena += "setec"; break;
                    case  9: cadena += "novec"; break;
                    default: cadena += tmp + "c";   
                }
                cadena += (denominador% 100 > 0)? "ientos" : "ientosavo";
            }
           
        }
        denominador %= 100;
        
        // Decenas y unidades
        if(denominador == 1 && !cadena.equals("")){
            cadena += "unavo";
        }
        else if(denominador > 0){
              cadena += MapHandler.getKeyByValue(denominadores, (int) denominador);
        }
     
        return (plural)? StringFormatter.pluralize(cadena) : cadena;

    }
}
