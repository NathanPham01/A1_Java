package Testing_Space;
import Program.Claim;
import Program.PolicyHolder;
import Program.ReceiverBankingInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class MainProgram_2 {
    public static void main(String[] args) {
        PolicyHolder policyHolder = null;
        try {
            Gson gson = new GsonBuilder().create();

            // Read JSON file into PolicyHolder object
            policyHolder = gson.fromJson(new FileReader("data.json"), PolicyHolder.class);

            // Display loaded data
            System.out.println("Loaded Policy Holder Data:");
            System.out.println(policyHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (policyHolder != null) {
            System.out.println("PolicyHolder Details:");
            System.out.println("ID: " + policyHolder.getId());
            System.out.println("Full Name: " + policyHolder.getFullName());
            System.out.println("Insurance Card:");
            System.out.println("  Card Number: " + policyHolder.getInsuranceCard().getCardNumber());
            System.out.println("  Card Holder: " + policyHolder.getInsuranceCard().getCardHolder());
            System.out.println("  Policy Owner: " + policyHolder.getInsuranceCard().getPolicyOwner());
            System.out.println("  Expiration Date: " + policyHolder.getInsuranceCard().getExpirationDate());
            System.out.println("Claims:");
            List<Claim> claims = policyHolder.getClaims();
            for (Claim claim : claims) {
                System.out.println("  Claim ID: " + claim.getId());
                System.out.println("  Claim Date: " + claim.getClaimDate());
                System.out.println("  Insured Person: " + claim.getInsuredPerson());
                System.out.println("  Card Number: " + claim.getCardNumber());
                System.out.println("  Exam Date: " + claim.getExamDate());
                System.out.println("  Documents:");
                List<String> documents = claim.getDocuments();
                for (String document : documents) {
                    System.out.println("    " + document);
                }
                System.out.println("  Claim Amount: " + claim.getClaimAmount());
                System.out.println("  Status: " + claim.getStatus());
                System.out.println("  Receiver Banking Info:");
                ReceiverBankingInfo receiverBankingInfo = claim.getReceiverBankingInfo();
                System.out.println("    Bank: " + receiverBankingInfo.getBankName());
                System.out.println("    Name: " + receiverBankingInfo.getAccountName());
                System.out.println("    Number: " + receiverBankingInfo.getAccountNumber());
                System.out.println();
            }

        }
    }
}
