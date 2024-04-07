package Program;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class START implements ClaimProcessManager  {
    public static void main(String[] args) throws Exception {
        //Load JSON data into a list of PolicyHolder objects and Dependent objects
        List<PolicyHolder> holders = loadPolicyHoldersFromJson("data.json");
        List<Dependent> dependents = loadDependentFromJson("list_dependent.json");





    }

    public static List<PolicyHolder> loadPolicyHoldersFromJson(String filePath) {
        List<PolicyHolder> holders = null;
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PolicyHolder>>(){}.getType();
            holders = gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holders;
    }

    public static List<Dependent> loadDependentFromJson(String filePath) {
        List<Dependent> dependents = null;
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Dependent>>(){}.getType();
            dependents = gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dependents;
    }


    @Override
    public void addClaimToHolder(List<PolicyHolder> holders, String policyHolderId, Claim claim, String filePath) {
        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(policyHolderId)) {
                holder.getClaims().add(claim);
                try {
                    // Write the updated list of dependents to JSON file
                    writePolicyHoldersToJson(holders, filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        System.out.println("PolicyHolder not found.");
    }

    @Override
    public void addClaimToDependent(List<Dependent> dependents, String dependentId, Claim claim, String filePath){
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(dependentId)) {
                dependent.getClaims().add(claim);
                try {
                    // Write the updated list of dependents to JSON file
                    writeDependentsToJson(dependents, filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        System.out.println("Dependent not found.");
    }

    @Override
    public void deleteClaimDependent(List<Dependent> dependents, String dependentId, String claimId, String filePath){
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(dependentId)) {
                List<Claim> claims = dependent.getClaims();
                for (Iterator<Claim> iterator = claims.iterator(); iterator.hasNext();) {
                    Claim claim = iterator.next();
                    if (claim.getId().equals(claimId)) {
                        iterator.remove();
                        try {
                            // Write the updated list of dependents to JSON file
                            writeDependentsToJson(dependents, filePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                System.out.println("Claim with ID " + claimId + " not found for dependent with ID " + dependentId);
                return;
            }
        }
        System.out.println("Dependent with ID " + dependentId + " not found.");
    }

    public void deleteClaimPolicyHolders(List<PolicyHolder> policyHolders, String policyHolderId,
                                                String claimId, String filePath) {
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(policyHolderId)) {
                List<Claim> claims = policyHolder.getClaims();
                for (Iterator<Claim> iterator = claims.iterator(); iterator.hasNext();) {
                    Claim claim = iterator.next();
                    if (claim.getId().equals(claimId)) {
                        iterator.remove();
                        try {
                            // Write the updated list of policy holders to JSON file
                            writePolicyHoldersToJson(policyHolders, filePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                System.out.println("Claim with ID " + claimId + " not found for policy holder with ID " + policyHolderId);
                return;
            }
        }
        System.out.println("Policy holder with ID " + policyHolderId + " not found.");
    }

    public static void modifyClaimAttributeDependent(List<Dependent> dependents, String dependentId, String claimId, String attribute,
                                                     String newValue, String filePath) {
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(dependentId)) {
                for (Claim claim : dependent.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        switch (attribute) {
                            case "id":
                                claim.setId(newValue);
                                break;
                            case "insuredPerson":
                                claim.setInsuredPerson(newValue);
                                break;
                            case "cardNumber":
                                claim.setCardNumber(newValue);
                                break;
                            case "claimAmount":
                                // Assuming newValue is a double
                                claim.setClaimAmount(Double.parseDouble(newValue));
                                break;
                            case "status":
                                claim.setStatus(newValue);
                                break;
                            default:
                                System.out.println("Invalid attribute.");
                                return;
                        }
                        writeDependentsToJson(dependents, filePath);
                        return;
                    }
                }
            }
        }
        System.out.println("Claim or dependent not found.");
    }

    public static void modifyReceiverBankingInfo(List<Dependent> dependents, String dependentId, String claimId,
                                                 ReceiverBankingInfo newReceiverBankingInfo, String filePath){
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(dependentId)) {
                for (Claim claim : dependent.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setReceiverBankingInfo(newReceiverBankingInfo);
                        writeDependentsToJson(dependents, filePath);
                        return;
                    }
                }
            }
        }
        System.out.println("Claim or dependent not found.");
    }

    public static void modifyDateDependent(List<Dependent> dependents, String dependentId, String claimId, String attribute,
                                           String newValue , String filePath){
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(dependentId)) {
                for (Claim claim : dependent.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        switch (attribute) {
                            case "examDate":
                            case "claimDate":
                                Date date = parseDate(newValue);
                                if (date != null) {
                                    if (attribute.equals("examDate")) {
                                        claim.setExamDate(date);
                                    } else if (attribute.equals("claimDate")) {
                                        claim.setClaimDate(date);
                                    }
                                    //Write updated JSON file
                                    writeDependentsToJson(dependents, filePath);
                                } else {
                                    System.out.println("Invalid date format for " + attribute);
                                }
                                break;
                            default:
                                System.out.println("Attribute " + attribute + " cannot be modified.");
                                return;
                        }
                        return;
                    }
                }
            }
        }
        System.out.println("Claim or dependent not found.");
    }

    public static void modifyDocumentsDependent(List<Dependent> dependents, String dependentId, String claimId,
                                                List<String> newDocuments, String filePath){
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(dependentId)) {
                for (Claim claim : dependent.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setDocuments(newDocuments);
                        writeDependentsToJson(dependents, filePath);
                        return;
                    }
                }
            }
        }
        System.out.println("Claim or dependent not found.");
    }

    public static void writeDependentsToJson(List<Dependent> dependents, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(dependents, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writePolicyHoldersToJson(List<PolicyHolder> policyHolders, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(policyHolders, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> extractRootIds(List<Dependent> dependents) {
        List<String> rootIds = new ArrayList<>();

        for (Dependent dependent : dependents) {
            rootIds.add(dependent.getId());
        }

        return rootIds;
    }



}



