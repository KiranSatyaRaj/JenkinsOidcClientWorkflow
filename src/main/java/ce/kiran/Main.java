package ce.kiran;

import java.io.IOException;
import java.util.Map;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, String> env = System.getenv();
        String token = env.get("IDTOKEN");
        JsonWebSignature jws = JsonWebSignature.parse(new GsonFactory(), token);
        System.out.println(jws.getHeader());
    }
}