import java.util.Scanner;

public class JacobiMethod {
    
    // Función para resolver el sistema de ecuaciones lineales utilizando el método de Jacobi
    public static double[] jacobi(double[][] A, double[] b, double tol, int maxIter) {
        int n = A.length;
        double[] x = new double[n]; // Vector de incógnitas inicializado en ceros
        double[] xPrev = new double[n]; // Vector de incógnitas de la iteración anterior
        
        // Realizamos las iteraciones
        for (int iter = 0; iter < maxIter; iter++) {
            // Copiamos el vector de incógnitas actual al vector de la iteración anterior
            System.arraycopy(x, 0, xPrev, 0, n);
            
            // Actualizamos las incógnitas
            for (int i = 0; i < n; i++) {
                double sum = 0.0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        sum += A[i][j] * xPrev[j];
                    }
                }
                x[i] = (b[i] - sum) / A[i][i];
            }
            
            // Verificamos la convergencia
            if (converged(x, xPrev, tol)) {
                System.out.println("Convergencia alcanzada en la iteración " + (iter + 1));
                return x;
            }
        }
        
        System.out.println("El método de Jacobi no convergió después de " + maxIter + " iteraciones.");
        return null;
    }
    
    // Función para verificar la convergencia
    private static boolean converged(double[] x, double[] xPrev, double tol) {
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i] - xPrev[i]) > tol) {
                return false;
            }
        }
        return true;
    }
    
    // Método para ingresar manualmente las ecuaciones del sistema
    public static void ingresarEcuaciones(double[][] A, double[] b, Scanner scanner) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            System.out.println("Ingrese la ecuación " + (i + 1) + " en el formato 'ax + by + cz = d':");
            System.out.print("Ecuación: ");
            String equation = scanner.nextLine();
            parseEcuacion(A, b, i, equation);
        }
    }
    
    // Método para parsear y almacenar los coeficientes de una ecuación en la matriz y el vector correspondientes
    public static void parseEcuacion(double[][] A, double[] b, int ecuacionIndex, String equation) {
        String[] parts = equation.split("\\s*[+=]\\s*");
        for (int j = 0; j < parts.length - 1; j++) {
            String[] coeficientes = parts[j].split("[a-z]");
            double coeficiente = Double.parseDouble(coeficientes[0]);
            A[ecuacionIndex][j] = coeficiente;
        }
        b[ecuacionIndex] = Double.parseDouble(parts[parts.length - 1]);
    }
    
    // Método para imprimir la matriz de coeficientes y el vector de términos constantes
    public static void imprimirMatriz(double[][] A, double[] b) {
        System.out.println("Matriz de coeficientes A:");
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println(" | " + b[i]);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Solicitar al usuario el número de ecuaciones en el sistema
        System.out.print("Ingrese el número de ecuaciones en el sistema: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea pendiente
        
        // Inicializar la matriz de coeficientes A y el vector de términos constantes b
        double[][] A = new double[n][n];
        double[] b = new double[n];
        
        // Ingresar manualmente las ecuaciones del sistema
        ingresarEcuaciones(A, b, scanner);
        
        // Imprimir la matriz de coeficientes y el vector de términos constantes
        imprimirMatriz(A, b);
        
        // Especificar la tolerancia y el número máximo de iteraciones
        double tol = 1e-6;
        int maxIter = 1000;
        
        // Resolver el sistema utilizando el método de Jacobi
        double[] solution = jacobi(A, b, tol, maxIter);
        
        // Imprimir la solución
        if (solution != null) {
            System.out.println("Solución encontrada:");
            for (int i = 0; i < n; i++) {
                System.out.println("x" + (i + 1) + " = " + solution[i]);
            }
        }
        
        scanner.close();
    }
}
