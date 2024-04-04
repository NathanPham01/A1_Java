package Program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainProgram {
    public static void main(String[] args) {
        List<PolicyHolder> policyHolders = new ArrayList<>();
        List<Dependent> dependents = new ArrayList<>();
        List<Claim> claims = new ArrayList<>();
        List<InsuranceCard> insuranceCards = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("Program/sample_data.txt"));
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                String type = parts[0];
                switch(type){
                    case "POLICY_HOLDER":
                        PolicyHolder policyHolder = createPolicyHolder(parts);
                        policyHolders.add(policyHolder);
                        break;
                    case "DEPENDENT":
                        Dependent dependent = createDependent(parts);
                        dependents.add(dependent);
                        break;
                    case "CLAIM":
                        Claim claim = createClaim(parts);
                        claims.add(claim);
                        break;
                    case "INSURANCE_CARD":
                        InsuranceCard insuranceCard = createInsuranceCard(parts);
                        insuranceCards.add(insuranceCard);
                        break;
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



    private static Dependent createDependent(String[] parts) {
        return new Dependent(parts[1], parts[2], createInsuranceCard(parts), new ArrayList<>());
    }


    private static PolicyHolder createPolicyHolder(String[] parts) {
        return new PolicyHolder(parts[1], parts[2], createInsuranceCard(parts), new ArrayList<>());
    }

    private static InsuranceCard createInsuranceCard(String[] parts) {
        return new InsuranceCard(parts[3], parts[2], parts[4], parseDate(parts[5]));
    }

    private static Claim createClaim(String[] parts) {
        return new Claim(parts[1], parseDate(parts[2]), parts[3], parts[4], parseDate(parts[5]), null,
                Double.parseDouble(parts[6]), parts[7], null);
    }

    private static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
