package Testing_Space;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

class InsuranceCard {
    String cardNumber;
    String cardHolder;
    String policyOwner;
    String expiryDate;
}

class Claim {
    String id;
    String claimDate;
    String insuredPerson;
    String cardNumber;
    String examDate;
    List<String> documents;
    double claimAmount;
    String status;
    String receiverBankingInfo;
}

class Person {
    String id;
    String fullName;
    InsuranceCard insuranceCard;
    List<Claim> claims;
}
public class Program5 {
    public static void main(String[] args) {
        String filePath = "data.json";

        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Person>>(){}.getType();
            List<Person> persons = gson.fromJson(reader, listType);

            for (Person person : persons) {
                System.out.println("ID: " + person.id);
                System.out.println("Full Name: " + person.fullName);
                System.out.println("Insurance Card Number: " + person.insuranceCard.cardNumber);
                System.out.println("Expiry Date: " + person.insuranceCard.expiryDate);
                System.out.println("Claims:");
                for (Claim claim : person.claims) {
                    System.out.println("\tID: " + claim.id);
                    System.out.println("\tClaim Date: " + claim.claimDate);
                    System.out.println("\tInsured Person: " + claim.insuredPerson);
                    System.out.println("\tClaim Amount: " + claim.claimAmount);
                    System.out.println("\tStatus: " + claim.status);
                    System.out.println("\tReceiver Banking Info: " + claim.receiverBankingInfo);
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
