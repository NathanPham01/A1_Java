package Testing_Space;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Program.Claim;
import Program.PolicyHolder;
import Program.ReceiverBankingInfo;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class Program6 {
    public static void main(String[] args) {
        String filePath = "data.json";

        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PolicyHolder>>() {
            }.getType();
            List<PolicyHolder> policyHolders = gson.fromJson(reader, listType);

            for (PolicyHolder policyHolder : policyHolders) {
                System.out.println("ID: " + policyHolder.getId());
                System.out.println("Full Name: " + policyHolder.getFullName());
                System.out.println("Insurance Card Number: " + policyHolder.getInsuranceCard().getCardNumber());
                System.out.println("Expiry Date: " + policyHolder.getInsuranceCard().getExpirationDate());
                System.out.println("Claims:");
                for (Claim claim : policyHolder.getClaims()) {
                    System.out.println("\tID: " + claim.getId());
                    System.out.println("\tClaim Date: " + claim.getClaimDate());
                    System.out.println("\tInsured Person: " + claim.getInsuredPerson());
                    System.out.println("\tClaim Amount: " + claim.getClaimAmount());
                    System.out.println("\tStatus: " + claim.getStatus());
                    System.out.println("\tReceiver Banking Info: " + claim.getReceiverBankingInfo());
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}