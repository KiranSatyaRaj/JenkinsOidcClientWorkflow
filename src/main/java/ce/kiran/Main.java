package ce.kiran;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws JoseException {
        Map<String, String> env = System.getenv();
        String token = env.get("IDTOKEN");
        JsonWebSignature jws = (JsonWebSignature) JsonWebSignature.fromCompactSerialization(token);
        System.out.println(jws);
    }
}