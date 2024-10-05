package murach.data;

import java.io.*;
import java.util.*;

import murach.business.*;

public class ProductIO {

    public static Product getProduct(String code, String filepath) {
        try {
            File file = new File(filepath);
            BufferedReader in
                    = new BufferedReader(
                    new FileReader(file));

            String line = in.readLine();
            while (line != null) {
                StringTokenizer t = new StringTokenizer(line, "|");
                String productCode = t.nextToken();
                if (code.equalsIgnoreCase(productCode)) {
                    String description = t.nextToken();
                    Product product = new Product();
                    product.setCode(code);
                    product.setDescription(description);

                    in.close();
                    return product;
                }
                line = in.readLine();
            }
            in.close();
            return null;
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

}