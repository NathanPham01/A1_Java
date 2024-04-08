package Program;
import java.util.List;
public interface ClaimProcessManager {
    void addClaimToHolder(List<PolicyHolder> holders, String policyHolderId);

    void deleteClaimPolicyHolders(List<PolicyHolder> policyHolders, String policyHolderId);


}
