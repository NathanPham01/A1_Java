package Program;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;


public class json_read {
    public static void main(String[] args) {
        String filePath = "list_policyHolder.json";

        try (Reader reader = new FileReader(filePath)){
            Gson gson = new Gson();

            //you can say that this line loads the array of PolicyHolder objects into the program's memory,
            // making it available for use in subsequent parts of the code.
            PolicyHolder[] holders = gson.fromJson(reader, PolicyHolder[].class);

            for(PolicyHolder holder:holders){
                System.out.println(holder);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
