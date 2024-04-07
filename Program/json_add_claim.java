package Program;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class json_add_claim {
    public static void main(String[] args) {
        try{
            //Read JSON file
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader("data.json"));

            //Find objects with id 2
            JsonObject objectWithId2 = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                if (jsonObject.get("id").getAsString().equals("2")) {
                    objectWithId2 = jsonObject;
                    break;
                }
            }

            // Add a new claim to the found object
            if (objectWithId2 != null) {
                JsonObject newClaim = getJsonObject();

                JsonObject receiverBankingInfo = new JsonObject();
                receiverBankingInfo.addProperty("bankName", "Wells Fargo");
                receiverBankingInfo.addProperty("accountName", "Emily Smith");
                receiverBankingInfo.addProperty("accountNumber", "54321098");

                JsonArray claims = objectWithId2.getAsJsonArray("claims");
                claims.add(newClaim);
            }

            // Write JSON file back
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter("data.json");
            gson.toJson(jsonArray, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Claim added successfully!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject getJsonObject() {
        JsonObject newClaim = new JsonObject();
        newClaim.addProperty("id", "f-6543210987"); // Example claim ID
        newClaim.addProperty("claimDate", "2024-04-05");
        newClaim.addProperty("insuredPerson", "Jane Smith");
        newClaim.addProperty("cardNumber", "0987654321");
        newClaim.addProperty("examDate", "2024-03-30");

        JsonArray documents = new JsonArray();
        documents.add("claim789_0987654321_document1.pdf");
        newClaim.add("documents", documents);

        newClaim.addProperty("claimAmount", 2000.00);
        newClaim.addProperty("status", "New");
        return newClaim;
    }
}
