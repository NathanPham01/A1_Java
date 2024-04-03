package Program;
import java.util.List;
public interface ClaimProcessManager {
    void add(Claim claim);
    void update(Claim claim);
    void delete(String id);
    Claim getOne(String id);
    List<Claim> getAll();

}
