package Program;

import java.util.List;

public class PolicyHolders extends Customer{
    private List<Customer> dependents;

    public PolicyHolders(String id, String fullName, InsuranceCard insuranceCard, List<Claim> claims) {
        super(id, fullName, insuranceCard, claims);
    }

    public List<Customer> getDependents() {
        return dependents;
    }

    public void setDependents(List<Customer> dependents){
        this.dependents = dependents;
    }
}
