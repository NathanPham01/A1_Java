package Program;

import java.util.List;

public class PolicyHolder extends Customer{
    private List<String> dependent_list;

    public PolicyHolder(String id, String fullName, InsuranceCard insuranceCard, List<Claim> claims) {
        super(id, fullName, insuranceCard, claims);
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
