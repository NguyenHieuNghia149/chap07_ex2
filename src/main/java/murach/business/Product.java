package murach.business;

import java.io.Serializable;
import java.text.NumberFormat;

public class Product implements Serializable {
    private String code;
    private String description;

    public Product() {
        code = "";
        description = "";

    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
