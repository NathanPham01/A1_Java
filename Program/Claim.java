package Program;

import java.util.Date;
import java.util.List;

public class Claim {
    private String id;
    private Date claimDate;
    private String insuredPerson;
    private String cardNumber;
    private Date examDate;
    private List<String> documents;
    private double claimAmount;
    private String status;
    private ReceiverBankingInfo receiverBankingInfo;

    public Claim(String id, Date claimDate, String insuredPerson, String cardNumber, Date examDate, List<String> documents,
                 double claimAmount, String status, ReceiverBankingInfo receiverBankingInfo) {
        this.id = id;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReceiverBankingInfo getReceiverBankingInfo() {
        return receiverBankingInfo;
    }
    public void setReceiverBankingInfo(ReceiverBankingInfo receiverBankingInfo) {
        this.receiverBankingInfo = receiverBankingInfo;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id='" + id + '\'' +
                ", claimDate=" + claimDate +
                ", insuredPerson='" + insuredPerson + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", examDate=" + examDate +
                ", documents=" + documents +
                ", claimAmount=" + claimAmount +
                ", status='" + status + '\'' +
                ", receiverBankingInfo=" + receiverBankingInfo +
                '}';
    }
}
