package Testing_Space;

import Program.PolicyHolder;
import Program.InsuranceCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.io.IOException;
import java.util.List;


public class MainProgram_4 {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("data.json")){
            Type listType = new TypeToken<List<PolicyHolder>>() {}.getType();

            // Parse JSON file content into list of PolicyHolder objects
            List<PolicyHolder> policyHolders = gson.fromJson(reader, listType);

            for (PolicyHolder policyHolder : policyHolders) {
                System.out.println(policyHolder);
            }
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }
}
