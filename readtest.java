import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Function to convert a number from a given base to decimal
    public static int baseToDecimal(String value, int base) {
        return Integer.parseInt(value, base);
    }

    // Function to perform Lagrange interpolation to find f(0), the constant term
    public static double lagrangeInterpolation(List<int[]> points, int k) {
        double f0 = 0;
        
        for (int j = 0; j < k; j++) {
            double product = 1;
            int xj = points.get(j)[0];
            int yj = points.get(j)[1];
            
            for (int i = 0; i < k; i++) {
                if (i != j) {
                    int xi = points.get(i)[0];
                    product *= (0.0 - xi) / (xj - xi);
                }
            }
            f0 += yj * product;
        }
        
        return f0;
    }

    // Manually parsing the JSON input and converting to a usable format
    public static int findConstantTerm(String inputJson) {
        // Extract the number of keys 'n' and minimum 'k' from the input string
        int n = Integer.parseInt(inputJson.split("\"n\":")[1].split(",")[0].trim());
        int k = Integer.parseInt(inputJson.split("\"k\":")[1].split("}")[0].trim());

        // Extract the points and convert them to base-10 integers
        List<int[]> points = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            String searchKey = "\"" + i + "\":";
            if (inputJson.contains(searchKey)) {
                String baseStr = inputJson.split(searchKey)[1].split("\"base\":")[1].split(",")[0].trim().replace("\"", "");
                String valueStr = inputJson.split(searchKey)[1].split("\"value\":")[1].split("}")[0].trim().replace("\"", "");
                int base = Integer.parseInt(baseStr);
                int value = baseToDecimal(valueStr, base);
                points.add(new int[]{i, value});
            }
        }

        // Perform Lagrange interpolation to find the constant term (f(0))
        double constantTerm = lagrangeInterpolation(points, k);
        
        return (int) Math.round(constantTerm);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user to enter the JSON input
        System.out.println("Enter the JSON input:");

        // Use a StringBuilder to capture multi-line JSON input from the user
        StringBuilder inputJsonBuilder = new StringBuilder();
        String line;
        
        // Continue reading lines until user inputs an empty line
        while (!(line = scanner.nextLine()).isEmpty()) {
            inputJsonBuilder.append(line);
        }
        
        // Convert the input from the StringBuilder to a single string
        String inputJson = inputJsonBuilder.toString();
        
        // Call the function to find the constant term (f(0))
        int constantTerm = findConstantTerm(inputJson);
        System.out.println("Constant term (f(0)): " + constantTerm);

        scanner.close();
    }
}
//testcase1:output:3
//testcase2:output:28735619723864