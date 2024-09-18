import java.util.ArrayList;
import java.util.List;

public class Main {

    // Function to convert a number from a given base to decimal
    public static long baseToDecimal(String value, int base) {
        return Long.parseLong(value, base);
    }

    // Function to perform Lagrange interpolation to find f(0), the constant term
    public static double lagrangeInterpolation(List<long[]> points, int k) {
        double f0 = 0;
        
        for (int j = 0; j < k; j++) {
            double product = 1;
            long xj = points.get(j)[0];
            long yj = points.get(j)[1];
            
            for (int i = 0; i < k; i++) {
                if (i != j) {
                    long xi = points.get(i)[0];
                    product *= (0.0 - xi) / (xj - xi);
                }
            }
            f0 += yj * product;
        }
        
        return f0;
    }

    // Manually parsing the JSON input and converting to a usable format
    public static long findConstantTerm(String inputJson) {
        // Extract the number of keys 'n' and minimum 'k' from the input string
        int n = Integer.parseInt(inputJson.split("\"n\":")[1].split(",")[0].trim());
        int k = Integer.parseInt(inputJson.split("\"k\":")[1].split("}")[0].trim());

        // Extract the points and convert them to base-10 integers
        List<long[]> points = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            String searchKey = "\"" + i + "\":";
            if (inputJson.contains(searchKey)) {
                String baseStr = inputJson.split(searchKey)[1].split("\"base\":")[1].split(",")[0].trim().replace("\"", "");
                String valueStr = inputJson.split(searchKey)[1].split("\"value\":")[1].split("}")[0].trim().replace("\"", "");
                int base = Integer.parseInt(baseStr);
                long value = baseToDecimal(valueStr, base);
                points.add(new long[]{i, value});
            }
        }

        // Perform Lagrange interpolation to find the constant term (f(0))
        double constantTerm = lagrangeInterpolation(points, k);
        
        return Math.round(constantTerm);
    }

    public static void main(String[] args) {
        // New test case in JSON format
        String inputJson = "{"
                + "\"keys\": {"
                + "    \"n\": 9,"
                + "    \"k\": 6"
                + "},"
                + "\"1\": {"
                + "    \"base\": \"10\","
                + "    \"value\": \"28735619723837\""
                + "},"
                + "\"2\": {"
                + "    \"base\": \"16\","
                + "    \"value\": \"1A228867F0CA\""
                + "},"
                + "\"3\": {"
                + "    \"base\": \"12\","
                + "    \"value\": \"32811A4AA0B7B\""
                + "},"
                + "\"4\": {"
                + "    \"base\": \"11\","
                + "    \"value\": \"917978721331A\""
                + "},"
                + "\"5\": {"
                + "    \"base\": \"16\","
                + "    \"value\": \"1A22886782E1\""
                + "},"
                + "\"6\": {"
                + "    \"base\": \"10\","
                + "    \"value\": \"28735619654702\""
                + "},"
                + "\"7\": {"
                + "    \"base\": \"14\","
                + "    \"value\": \"71AB5070CC4B\""
                + "},"
                + "\"8\": {"
                + "    \"base\": \"9\","
                + "    \"value\": \"122662581541670\""
                + "},"
                + "\"9\": {"
                + "    \"base\": \"8\","
                + "    \"value\": \"642121030037605\""
                + "}"
                + "}";

        // Call the function with the new input
        long constantTerm = findConstantTerm(inputJson);
        System.out.println(constantTerm);
    }
}
//output:28735619723864