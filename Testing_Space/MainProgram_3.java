package Testing_Space;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import Program.PolicyHolder;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class MainProgram_3 {
    public static void main(String[] args) {

        Gson gson =  new Gson();
        try (Reader reader = new FileReader("data.json")) { // Replace "data.json" with your JSON file path
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject policyHolderObject = jsonArray.get(i).getAsJsonObject();

                // Accessing PolicyHolder attributes
                String id = policyHolderObject.get("id").getAsString();
                String fullName = policyHolderObject.get("fullName").getAsString();

                // Accessing insuranceCard attributes
                JsonObject insuranceCardObject = policyHolderObject.getAsJsonObject("insuranceCard");
                String cardNumber = insuranceCardObject.get("cardNumber").getAsString();
                String cardHolder = insuranceCardObject.get("cardHolder").getAsString();
                String policyOwner = insuranceCardObject.get("policyOwner").getAsString();
                String expiryDate = insuranceCardObject.get("expiryDate").getAsString();

                // Use the loaded data as needed
                System.out.println("Policy Holder ID: " + id);
                System.out.println("Full Name: " + fullName);
                System.out.println("Insurance Card Number: " + cardNumber);
                System.out.println("Card Holder: " + cardHolder);
                System.out.println("Policy Owner: " + policyOwner);
                System.out.println("Expiry Date: " + expiryDate);
            }

        }

        catch (IOException e){
            e.printStackTrace();
        }

        catch (JsonParseException e){
            e.printStackTrace();
        }
    }


}
