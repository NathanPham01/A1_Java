package Program;
/**
 * @author <Pham Quang Huy - s3940676>
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class START implements ClaimProcessManager  {
    public static void main(String[] args) throws Exception {
        int idInput;
        String idInput_string;

        //Load JSON data into a list of PolicyHolder objects and Dependent objects
        List<PolicyHolder> holders = loadPolicyHoldersFromJson("list_policyHolder.json");
        System.out.println("Policy Holders loaded");
        List<Dependent> dependents = loadDependentFromJson("list_dependent.json");
        System.out.println("Dependents loaded");

        Date date = parseDate("2025-01-01");
        List<String> holder_ids = extractIDsFromFile("list_policyHolder.json");
        List<String> dependent_ids = extractIDsFromFile("list_dependent.json");

        System.out.println("Welcome to the Insurance Claim Management System");

        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            System.out.println("Please enter 1 for Dependent sign in or 2 for Policy Holder sign in:");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // consume non-integer input
            }
            input = scanner.nextInt();
            if (input != 1 && input != 2) {
                System.out.println("Invalid input. Please enter 1 or 2.");
            }
        } while (input != 1 && input != 2);

        // Display message based on input
        if (input == 1) {
            System.out.println("Depedent sign in");
            String enteredId = signIn(dependent_ids);
            String outputId_dependent = extractId(enteredId);

            System.out.println("You have these functions:");
            System.out.println("1. View Your Specific Claim");
            System.out.println("2. View All Your Claims");

            while (true){
                try{
                    System.out.print("Enter a number from 1 to 2 (0 to exit): ");
                    int choice_d = scanner.nextInt();
                    switch (choice_d){
                        case 0:
                            System.out.println("Exiting program.");
                            scanner.close();
                            return;
                        case 1:
                            printClaimDependent(dependents, outputId_dependent);
                            break;
                        case 2:
                            printAllClaimsDependent(dependents, outputId_dependent);
                            break;
                    }
                }
                catch (Exception e){
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.next(); // Clear invalid input
                }
            }

        } else {
            System.out.println("Policy Holder sign in");
            String enteredId = signIn(holder_ids);
            String outputId = extractId(enteredId);

            System.out.println("You have these functions:");
            System.out.println("1. Add Claim");
            System.out.println("2. Delete Claim");
            System.out.println("3. Modify Claim");
            System.out.println("4. View Your Specific Claim");
            System.out.println("5. View All Your Claims");


            while (true) {
                try {
                    System.out.print("Enter a number from 1 to 5 (0 to exit): ");
                    int choice_h = scanner.nextInt();
                    switch (choice_h) {
                        case 0:
                            System.out.println("Exiting program.");
                            scanner.close();
                            return;
                        case 1:
                            new START().addClaimToHolder(holders, outputId);
                            break;
                        case 2:
                            new START().deleteClaimPolicyHolders(holders, outputId);
                            break;
                        case 3:
                            modifyAttributeMenu(holders, outputId);
                            break;
                        case 4:
                            printClaimHolders(holders,outputId);
                            break;
                        case 5:
                            printAllClaimsHolders(holders, outputId);
                            break;
                        default:
                            System.out.println("Invalid input. Please enter a number between 0 and 5.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.next(); // Clear invalid input
                }
            }
        }
    }



    public static String extractId(String input) {
        // Extract the ID from the input string
        return input.substring(2);
    }

    public static String signIn(List<String> accepted) {
        Scanner scanner = new Scanner(System.in);
        boolean signedIn = false;
        String input = "";

        while (!signedIn) {
            System.out.print("Enter id number (7 digits): ");
            int inputValue;

            try {
                inputValue = Integer.parseInt(scanner.nextLine());
                input = "c-" + inputValue;

                if (accepted.contains(input)) {
                    System.out.println("Signed in");
                    signedIn = true;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter a valid integer value.");
            }
        }
        return "c-"+input;
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
    public void addClaimToHolder(List<PolicyHolder> holders, String policyHolderId) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter claimID(f-10 numbers): ");
        String claim_id = scan.nextLine();

        System.out.println("Enter claim year (beyond 2024): ");
        int date_year_claim = scan.nextInt();
        while (date_year_claim <= 2024) {
            System.out.println("Invalid input! Please enter a year greater than 2024:");
            date_year_claim = scan.nextInt();
        }

        System.out.println("Enter claim month");
        int date_month_claim;
        do {
            System.out.println("Please enter a month between 1 and 12:");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a month between 1 and 12:");
                scan.next();
            }
            date_month_claim = scan.nextInt();
        } while (date_month_claim < 1 || date_month_claim > 12);

        System.out.println("Enter claim day");
        int date_day_claim;
        do {
            System.out.println("Please enter a day between 1 and 31: ");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a day between 1 and 31: ");
                scan.next();
            }
            date_day_claim = scan.nextInt();
        } while (date_day_claim < 1 || date_day_claim > 31);

        String claim_date = date_year_claim + "-" + date_month_claim + "-" +
                date_day_claim;

        Date claim_Date = parseDate(claim_date);


        boolean valid_card_input = false;
        int cardNumber;
        String cardNumString = "";
        while (!valid_card_input) {
            System.out.print("Enter Card Number: ");
            if (scan.hasNextInt()) {
                cardNumber = scan.nextInt();
                cardNumString = Integer.toString(cardNumber);
                valid_card_input = true;
            } else {
                scan.next();
                System.out.println("Invalid input. Please enter an integer.");
            }
        }


        System.out.println("Enter insuredPerson: ");
        String insured_Person = scan.nextLine();


        System.out.println("Enter exam year(beyond 2024): ");
        int date_year_exam = scan.nextInt();
        while (date_year_exam <= 2024) {
            System.out.println("Invalid input! Please enter a year greater than 2024:");
            date_year_exam = scan.nextInt();
        }


        System.out.println("Enter exam month");
        int date_month_exam;
        do {
            System.out.println("Please enter a month between 1 and 12:");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a month between 1 and 12:");
                scan.next();
            }
            date_month_exam = scan.nextInt();
        } while (date_month_exam < 1 || date_month_exam > 12);

        System.out.println("Enter exam day");
        int date_day_exam;
        do {
            System.out.println("Please enter a day between 1 and 31: ");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a day between 1 and 31: ");
                scan.next();
            }
            date_day_exam = scan.nextInt();
        } while (date_day_exam < 1 || date_day_exam > 31);

        String exam_date = date_year_exam + "-" + date_month_exam + "-" + date_day_exam;
        Date exam_date_claim = parseDate(exam_date);

        List<String> documents = new ArrayList<>();

        System.out.println("Enter documents' name (type 'done' to finish):");
        String input = scan.nextLine();
        while (!input.equalsIgnoreCase("done")) {
            documents.add(input);
            input = scan.nextLine();
        }

        List<String> documentsWithExtension = new ArrayList<>();
        for (String document : documents) {
            documentsWithExtension.add(document + ".pdf");
        }


        System.out.println("Enter claim amount: ");
        String claim_amount = scan.nextLine();
        double amount_double = Double.parseDouble(claim_amount);

        System.out.println("Status: ");
        System.out.println("1.New");
        System.out.println("2.Processing");
        System.out.println("3.Done");

        int status_int;
        do {
            System.out.print("Enter an integer between 1 and 3: ");
            while (!scan.hasNextInt()) {
                System.out.println("That's not an integer!");
                System.out.print("Enter an integer between 1 and 3: ");
                scan.next();
            }
            status_int = scan.nextInt();
        } while (status_int < 1 || status_int > 3);

        String claim_status;
        switch (status_int) {
            case 1:
                claim_status = "New";
                break;
            case 2:
                claim_status = "Processing";
                break;
            case 3:
                claim_status = "Done";
                break;
            default:
                claim_status = "Invalid input";
        }

        System.out.println("Receiver Banking Info");

        System.out.println("Bank Name:");
        String bank_info = scan.nextLine();

        System.out.println("Account Name: ");
        String acc_name = scan.nextLine();

        System.out.println("Account number: ");
        String acc_number = scan.nextLine();

        ReceiverBankingInfo newR_Info = new ReceiverBankingInfo(bank_info, acc_name, acc_number);


        String filePath = "list_policyHolder.json";
        Claim claim = new Claim(claim_id, claim_Date, insured_Person, cardNumString, exam_date_claim,
                documentsWithExtension, amount_double, claim_status, newR_Info);

        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(policyHolderId)) {
                holder.getClaims().add(claim);
                System.out.println(claim);
                System.out.println("Added claim");
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

    public void deleteClaimPolicyHolders(List<PolicyHolder> policyHolders, String policyHolderId) {
        List<String> claimIdList = extractClaimIDPolicyHolder(policyHolders, policyHolderId);
        String filePath = "list_policyHolder.json";
        System.out.println("Claim IDs for Policy Holder with ID "+ policyHolderId+ ":");
        for (String claimID : claimIdList) {
            System.out.println(claimID);
        }
        Scanner scan = new Scanner(System.in);

        String claimId ="";
        boolean validClaimId = false;
        while(!validClaimId){
            System.out.println("Enter a claim ID to modify: ");
            claimId = scan.nextLine();

            if (claimIdList.contains(claimId)){
                System.out.println("Valid ClaimID");
                validClaimId = true;
            }
            else{
                System.out.println("Invalid ClaimID. Please try again.");
            }
        }



        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(policyHolderId)) {
                List<Claim> claims = policyHolder.getClaims();
                for (Iterator<Claim> iterator = claims.iterator(); iterator.hasNext();) {
                    Claim claim = iterator.next();
                    if (claim.getId().equals(claimId)) {
                        iterator.remove();
                        System.out.println("Deleted");
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

    public static void modifyAttributeMenu(List<PolicyHolder> holders, String policyHolderId){
        List<String> claimIdList = extractClaimIDPolicyHolder(holders, policyHolderId);
        System.out.println("List of Claim IDs for Policy Holder with ID "+ policyHolderId+ ":");
        for (String claimID : claimIdList) {
            System.out.println(claimID);
        }

        Scanner scan = new Scanner(System.in);


        String claimId ="";
        boolean validClaimId = false;
        while(!validClaimId){
            System.out.println("Enter a claim ID to modify: ");
            claimId = scan.nextLine();

            if (claimIdList.contains(claimId)){
                System.out.println("Valid ClaimID");
                validClaimId = true;
            }
            else{
                System.out.println("Invalid ClaimID. Please try again.");
            }
        }


        int choice;
        System.out.println("Which info do you want to modify? (1-8):");
        System.out.println("1.ID");
        System.out.println("2.Insured Person");
        System.out.println("3.Card Number");
        System.out.println("4.Claim Amount:");
        System.out.println("5.Status");
        System.out.println("6.Claim Date");
        System.out.println("7.Exam Date");
        System.out.println("8.Receiver Banking Info");

        int modChoice = scan.nextInt();

        if (modChoice >= 1 && modChoice <= 8) {
            switch (modChoice) {
                case 1:
                    modifyClaimAttributeHolder_ID(holders, policyHolderId, claimId);
                    break;
                case 2:
                    modifyClaimAttributeHolder_InsuredPerson(holders, policyHolderId, claimId);
                    break;
                case 3:
                    modifyClaimAttributeHolder_cardNumber(holders, policyHolderId, claimId);
                    break;
                case 4:
                    modifyClaimAttributeHolder_claimAmount(holders, policyHolderId, claimId);
                    break;
                case 5:
                    modifyClaimAttributeHolder_status(holders, policyHolderId, claimId);
                    break;
                case 6:
                    modifyClaimDatePolicyHolder(holders, policyHolderId, claimId);
                    break;
                case 7:
                    modifyExamDatePolicyHolder(holders, policyHolderId, claimId);
                    break;
                case 8:
                    modBankingInfo(holders, policyHolderId, claimId);
                    break;
            }
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 8.");
        }
    }


    public static void modifyClaimAttributeHolder_ID(List<PolicyHolder> holders, String holderId, String claimId) {
        String filePath = "list_policyHolder.json";

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter ClaimID (must be 10 digits number): ");
        long newIdNum = 0;

        boolean validInput = false;
        while (!validInput) {
            try {
                newIdNum = scan.nextLong();
                if (String.valueOf(newIdNum).length() == 10) {
                    validInput = true;
                } else {
                    System.out.print("Invalid input. Please enter a 10-digit number: ");
                }
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scan.nextLine(); // Clear the buffer
            }
        }

        String newValue = "f-" + newIdNum;

        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(holderId)) {
                for (Claim claim : holder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setId(newValue);
                        writePolicyHoldersToJson(holders, filePath);
                        System.out.println("Modified");
                        return;
                    }
                }
            }
        }
        System.out.println("Claim not found.");
    }

    public static void modifyClaimAttributeHolder_InsuredPerson(List<PolicyHolder> holders, String holderId, String claimId) {
        String filePath = "list_policyHolder.json";

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter insured Person name:");
        String newValue = scan.nextLine();

        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(holderId)) {
                for (Claim claim : holder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setInsuredPerson(newValue);
                        writePolicyHoldersToJson(holders, filePath);
                        System.out.println("Modified");
                        return;
                    }
                }
            }
        }
        System.out.println("Claim not found.");
    }

    public static void modifyClaimAttributeHolder_cardNumber(List<PolicyHolder> holders, String holderId, String claimId) {
        String filePath = "list_policyHolder.json";

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter card number:");
        String newValue = scan.nextLine();

        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(holderId)) {
                for (Claim claim : holder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setCardNumber(newValue);
                        writePolicyHoldersToJson(holders, filePath);
                        System.out.println("Modified");
                        return;
                    }
                }
            }
        }
        System.out.println("Claim not found.");
    }

    public static void modifyClaimAttributeHolder_claimAmount(List<PolicyHolder> holders, String holderId, String claimId) {
        String filePath = "list_policyHolder.json";

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter claim amount:");
        String newValue = scan.nextLine();

        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(holderId)) {
                for (Claim claim : holder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setClaimAmount(Double.parseDouble(newValue));
                        writePolicyHoldersToJson(holders, filePath);
                        System.out.println("Modified");
                        return;
                    }
                }
            }
        }
        System.out.println("Claim not found.");
    }

    public static void modifyClaimAttributeHolder_status(List<PolicyHolder> holders, String holderId, String claimId) {
        String filePath = "list_policyHolder.json";
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose a status by number 1-3: ");

        System.out.println("Status ");
        System.out.println("1.New");
        System.out.println("2.Processing");
        System.out.println("3.Done");

        int status_int;
        do {
            System.out.print("Enter an integer between 1 and 3: ");
            while (!scan.hasNextInt()) {
                System.out.println("That's not an integer!");
                System.out.print("Enter an integer between 1 and 3: ");
                scan.next();
            }
            status_int = scan.nextInt();
        } while (status_int < 1 || status_int > 3);

        String claim_status;
        switch (status_int) {
            case 1:
                claim_status = "New";
                break;
            case 2:
                claim_status = "Processing";
                break;
            case 3:
                claim_status = "Done";
                break;
            default:
                claim_status = "Invalid input";
        }


        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(holderId)) {
                for (Claim claim : holder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setStatus(claim_status);
                        writePolicyHoldersToJson(holders, filePath);
                        System.out.println("Modified");
                        return;
                    }
                }
            }
        }
        System.out.println("Claim not found.");
    }


    public static void modifyExamDatePolicyHolder(List<PolicyHolder> holders, String policyHolderId, String claimId) {
        String filePath = "list_policyHolder.json";
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter exam year(beyond 2024): ");
        int date_year_exam = scan.nextInt();
        while (date_year_exam <= 2024) {
            System.out.println("Invalid input! Please enter a year greater than 2024:");
            date_year_exam = scan.nextInt();
        }


        System.out.println("Enter exam month");
        int date_month_exam;
        do {
            System.out.println("Please enter a month between 1 and 12:");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a month between 1 and 12:");
                scan.next();
            }
            date_month_exam = scan.nextInt();
        } while (date_month_exam < 1 || date_month_exam > 12);

        System.out.println("Enter exam day");
        int date_day_exam;
        do {
            System.out.println("Please enter a day between 1 and 31: ");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a day between 1 and 31: ");
                scan.next();
            }
            date_day_exam = scan.nextInt();
        } while (date_day_exam < 1 || date_day_exam > 31);

        String newValue = date_year_exam + "-" + date_month_exam + "-" + date_day_exam;

        for (PolicyHolder policyHolder : holders) {
            if (policyHolder.getId().equals(policyHolderId)) {
                for (Claim claim : policyHolder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        Date date = parseDate(newValue);
                        claim.setClaimDate(date);
                        writePolicyHoldersToJson(holders, filePath);
                        return;
                    }
                }
            }
        }
        System.out.println("Claim or policy holder not found.");
    }

    public static void modifyClaimDatePolicyHolder(List<PolicyHolder> holders, String policyHolderId, String claimId) {
        String filePath = "list_policyHolder.json";
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter claim year (beyond 2024): ");
        int date_year_claim = scan.nextInt();
        while (date_year_claim <= 2024) {
            System.out.println("Invalid input! Please enter a year greater than 2024:");
            date_year_claim = scan.nextInt();
        }

        System.out.println("Enter claim month");
        int date_month_claim;
        do {
            System.out.println("Please enter a month between 1 and 12:");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a month between 1 and 12:");
                scan.next();
            }
            date_month_claim = scan.nextInt();
        } while (date_month_claim < 1 || date_month_claim > 12);

        System.out.println("Enter claim day");
        int date_day_claim;
        do {
            System.out.println("Please enter a day between 1 and 31: ");
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input! Please enter a day between 1 and 31: ");
                scan.next();
            }
            date_day_claim = scan.nextInt();
        } while (date_day_claim < 1 || date_day_claim > 31);

        String newValue = date_year_claim + "-" + date_month_claim + "-" +
                date_day_claim;

        for (PolicyHolder policyHolder : holders) {
            if (policyHolder.getId().equals(policyHolderId)) {
                for (Claim claim : policyHolder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        Date date = parseDate(newValue);
                        claim.setExamDate(date);
                        writePolicyHoldersToJson(holders, filePath);
                        return;
                    }
                }
            }
        }
        System.out.println("Claim or policy holder not found.");
    }


    public static void modBankingInfo(List<PolicyHolder> holders, String policyHolderId, String claimId){
        String filePath ="list_policyHolder.json";
        Scanner scan = new Scanner(System.in);


        System.out.println("Enter Bank Name: ");
        String bank_name = scan.nextLine();

        System.out.println("Account Name: ");
        String acc_name_new = scan.nextLine();

        System.out.println("Account number: ");
        String acc_num_new = scan.nextLine();

        ReceiverBankingInfo newReceiverBankingInfo = new ReceiverBankingInfo(bank_name, acc_name_new, acc_num_new);
        for (PolicyHolder policyHolder : holders) {
            if (policyHolder.getId().equals(policyHolderId)) {
                for (Claim claim : policyHolder.getClaims()) {
                    if (claim.getId().equals(claimId)) {
                        claim.setReceiverBankingInfo(newReceiverBankingInfo);
                        writePolicyHoldersToJson(holders, filePath);
                        return;
                    }
                }
            }
        }
        System.out.println("Claim or policy holder not found.");
    }

    public static void printAllClaimsHolders(List<PolicyHolder> holders, String holderId){
        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(holderId)) {
                List<Claim> claims = holder.getClaims();

                // Iterate through the claims and print them
                for (Claim claim : claims) {
                    System.out.println(claim);
                }
                return;
            }
        }
        System.out.println("PolicyHolder not found.");
    }

    public static void printAllClaimsDependent(List<Dependent> dependents, String dependentId){
        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(dependentId)) {
                List<Claim> claims = dependent.getClaims();

                // Iterate through the claims and print them
                for (Claim claim : claims) {
                    System.out.println(claim);
                }
                return;
            }
        }
        System.out.println("Dependent not found.");
    }

    public static void printClaimHolders(List<PolicyHolder> holders, String holderId){
        List<String> claimIdList = extractClaimIDPolicyHolder(holders, holderId);
        System.out.println("List of Claim IDs for Policy Holder with ID "+ holderId+ ":");
        for (String claimID : claimIdList) {
            System.out.println(claimID);
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter claimID(copy ClaimID from list above): ");
        String claimId = scan.nextLine();

        for (PolicyHolder holder : holders){
            if(holder.getId().equals(holderId)){
                List<Claim> claims = holder.getClaims();

                for (Claim claim : claims) {
                    if (claim.getId().equals(claimId)) {
                        System.out.println(claim);
                        return;
                    }
                }
                System.out.println("Claim with ID " + claimId + " not found for policy holder " + holderId);
                return;
            }
        }
        System.out.println("PolicyHolder with ID " + holderId + " not found.");
    }

    public static void printClaimDependent(List<Dependent> dependents, String dependentId){
        List<String> claimIdList = extractClaimIdDependent(dependents, dependentId);
        System.out.println("List of Claim IDs for Dependent with ID "+ dependentId+ ":");
        for (String claimID : claimIdList) {
            System.out.println(claimID);
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter claimID (f-10 digits)(Copy claimID from list above): ");
        String claimId = scan.nextLine();

        for (Dependent dependent : dependents){
            if(dependent.getId().equals(dependentId)){
                List<Claim> claims = dependent.getClaims();

                for (Claim claim : claims) {
                    if (claim.getId().equals(claimId)) {
                        System.out.println(claim);
                        return;
                    }
                }
                System.out.println("Claim with ID " + claimId + " not found for dependent " + dependentId);
                return;
            }
        }
        System.out.println("Dependent with ID " + dependentId + " not found.");
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

    public static List<String> extractClaimIDPolicyHolder(List<PolicyHolder> holders, String holderId) {
        List<String> claimIDs = new ArrayList<>();

        for (PolicyHolder holder : holders) {
            if (holder.getId().equals(holderId)) {
                List<Claim> claims = holder.getClaims();

                // Iterate through the claims and extract their IDs
                for (Claim claim : claims) {
                    claimIDs.add(claim.getId());
                }
                return claimIDs;
            }
        }
        System.out.println("PolicyHolder not found.");
        return claimIDs; // Return an empty list if policy holder is not found
    }

    public static List<String> extractClaimIdDependent(List<Dependent> dependents, String DependentId) {
        List<String> claimIDs = new ArrayList<>();

        for (Dependent dependent : dependents) {
            if (dependent.getId().equals(DependentId)) {
                List<Claim> claims = dependent.getClaims();

                // Iterate through the claims and extract their IDs
                for (Claim claim : claims) {
                    claimIDs.add(claim.getId());
                }
                return claimIDs;
            }
        }
        System.out.println("PolicyHolder not found.");
        return claimIDs; // Return an empty list if policy holder is not found
    }

    public static List<String> extractIDsFromFile(String filePath){
        Gson gson = new Gson();
        List<Dependent> dependents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Type dependentListType = new TypeToken<List<Dependent>>(){}.getType();
            dependents = gson.fromJson(br, dependentListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> ids = new ArrayList<>();
        for (Dependent dependent : dependents) {
            ids.add(dependent.getId());
        }

        return ids;
    }
}



