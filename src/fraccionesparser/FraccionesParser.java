package fraccionesparser;

import java.util.Scanner;

/**
 *
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public class FraccionesParser {
    
    static Scanner scanner = new Scanner(System.in);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String data;
        Parser parser = new Parser();
        
        scanner.useDelimiter("\n");
        
        do {
            System.out.println("Ingrese operacion en texto> ");
            data = scanner.next(); 
            System.out.println(parser.solve(data) + "\n");      
        }while(!data.equals(""));
    }
    
}
