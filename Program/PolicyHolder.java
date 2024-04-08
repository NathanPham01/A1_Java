package Program;
/**
 * @author <Pham Quang Huy - s3940676>
 */

import java.util.List;

public class PolicyHolder extends Customer{
    private List<String> dependent_list;

    public PolicyHolder(String id, String fullName, InsuranceCard insuranceCard, List<Claim> claims, List<String> dependent_list) {
        super(id, fullName, insuranceCard, claims);
        this.dependent_list = dependent_list;
    }

    public List<String> getDependents() {
        return dependent_list;
    }

    public void setDependents(List<String> dependents){
        this.dependent_list = dependents;
    }

    @Override
    public String toString() {
        return super.toString() + "PolicyHolder{" +
                "dependent_list=" + dependent_list +
                '}';
    }
}
