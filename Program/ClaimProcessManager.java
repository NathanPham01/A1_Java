package Program;
/**
 * @author <Pham Quang Huy - s3940676>
 */
import java.util.List;
public interface ClaimProcessManager {
    void addClaimToHolder(List<PolicyHolder> holders, String policyHolderId);

    void deleteClaimPolicyHolders(List<PolicyHolder> policyHolders, String policyHolderId);


}
