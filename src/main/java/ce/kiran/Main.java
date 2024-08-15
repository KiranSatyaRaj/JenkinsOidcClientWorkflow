package ce.kiran;

import java.nio.charset.StandardCharsets;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        byte[] val = env.get("JENKINS_OIDC_CREDENTIAL").getBytes(StandardCharsets.UTF_8);
        String token = new String(val);
        System.out.println(token);
    }
}