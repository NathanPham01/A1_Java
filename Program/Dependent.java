package Program;
/**
 * @author <Pham Quang Huy - s3940676>
 */
import java.util.List;

public class Dependent extends Customer{

    public Dependent(String id, String fullName, InsuranceCard insuranceCard, List<Claim> claims) {
        super(id, fullName, insuranceCard, claims);
    }
}
