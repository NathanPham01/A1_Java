package Program;
import java.util.List;
public interface ClaimProcessManager {
    void addClaimToHolder(List<PolicyHolder> holders, String policyHolderId,Claim claim, String filePath);

    void addClaimToDependent(List<Dependent> dependents, String dependentId, Claim claim, String filePath);

    void deleteClaimDependent(List<Dependent> dependents, String dependentId, String claimId, String filePath);

    void deleteClaimPolicyHolders(List<PolicyHolder> policyHolders, String policyHolderId, String claimId, String filePath);


}
