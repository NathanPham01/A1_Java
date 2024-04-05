package Program;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class json_deleteById {
    public static void main(String[] args) {
        String IdToDelete = "f-2468013579";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try{
            // Parse JSON file
            JsonElement jsonElement = JsonParser.parseReader(new FileReader("data.json"));

            // Check if it's an array
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                // Loop through each element in the array
                Iterator<JsonElement> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    JsonElement element = iterator.next();
                    JsonObject jsonObj = element.getAsJsonObject();

                    // Check if the object has claims
                    if (jsonObj.has("claims")) {
                        JsonArray claims = jsonObj.getAsJsonArray("claims");

                        // Loop through claims
                        Iterator<JsonElement> claimIterator = claims.iterator();
                        while (claimIterator.hasNext()) {
                            JsonElement claimElement = claimIterator.next();
                            JsonObject claim = claimElement.getAsJsonObject();

                            // Check if the claim's ID matches the ID to be deleted
                            if (claim.has("id") && claim.get("id").getAsString().equals(IdToDelete)) {
                                // Remove the claim from the array
                                claimIterator.remove();
                                System.out.println("Claim with ID " + IdToDelete + " deleted.");
                            }
                        }
                    }
                }

                // Write modified JSON back to file
                try (FileWriter file = new FileWriter("data.json")) {
                    gson.toJson(jsonArray, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
