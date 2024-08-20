package ce.kiran;

import dev.sigstore.oidc.client.OidcClient;
import dev.sigstore.oidc.client.OidcException;
import dev.sigstore.oidc.client.OidcToken;

import java.util.Map;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;

public class JenkinsOidcClient implements OidcClient {
    @Override
    public boolean isEnabled(Map<String, String> map) {
        return false;
    }

    @Override
    public OidcToken getIDToken(Map<String, String> map) throws OidcException {
        Map<String, String > env = System.getenv();
        try {
            JsonWebSignature jws = (JsonWebSignature) JsonWebSignature.fromCompactSerialization(env.get("IDTOKEN"));
        } catch (JoseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
