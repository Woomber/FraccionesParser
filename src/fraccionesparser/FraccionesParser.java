package fraccionesparser;

import java.util.Scanner;

/**
 * Clase principal del proyecto.
 * 
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public class FraccionesParser {
    
    // Lector de consola
    static Scanner scanner = new Scanner(System.in);
    
    /**
     * Función principal.
     * 
     * @param args Argumentos de consola
     */
    public static void main(String[] args) {
        String data;
        Parser parser = new Parser();
        
        // Establecer línea nueva como delimitador
        scanner.useDelimiter("\n");
        
        //Ciclo principal
        do {
            // Lectura de datos
            System.out.println("Ingrese operacion en texto> ");
            data = scanner.next();

            if(data.trim().equals("")) return;
            
            // Resolver e imprimir
            System.out.println(parser.solve(data) + "\n");  
            
        }while(!data.equals(""));
    }
    
}
