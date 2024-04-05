package Program;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class json_modify {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try{
            JsonElement jsonElement = JsonParser.parseReader(new FileReader("data.json"));

            // Check if it's an array
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = getJsonElements(jsonElement);

                // Write modified JSON back to file
                try (FileWriter file = new FileWriter("data_2.json")) {
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

    private static JsonArray getJsonElements(JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        // Loop through each element in the array
        for (JsonElement element : jsonArray) {
            JsonObject jsonObj = element.getAsJsonObject();

            // Check if the object has id = 2
            if (jsonObj.get("id").getAsString().equals("2")) {
                JsonArray claims = jsonObj.getAsJsonArray("claims");

                // Loop through claims
                for (JsonElement claimElement : claims) {
                    JsonObject claim = claimElement.getAsJsonObject();
                    // Modify claimAmount
                    claim.addProperty("claimAmount", 5000.00); // Modify to desired value
                }
            }
        }
        return jsonArray;
    }
}
