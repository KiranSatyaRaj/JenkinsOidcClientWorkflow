package ce.kiran;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import dev.sigstore.http.HttpClients;
import dev.sigstore.http.HttpParams;
import dev.sigstore.http.ImmutableHttpParams;
import dev.sigstore.oidc.client.ImmutableOidcToken;
import dev.sigstore.oidc.client.OidcClient;
import dev.sigstore.oidc.client.OidcException;
import dev.sigstore.oidc.client.OidcToken;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class JenkinsOidcClient implements OidcClient {
    private static final Logger log = Logger.getLogger(JenkinsOidcClient.class.getName());
    private final String audience;
    private final HttpParams httpParams;

    public static Builder builder() {
        return new Builder();
    }

    private JenkinsOidcClient(HttpParams httpParams, String audience) {
        this.audience = audience;
        this.httpParams = httpParams;
    }

    public boolean isEnabled(Map<String, String> env) {
        String idToken = env.get("IDTOKEN");
        if (idToken != null && !idToken.isEmpty()) {
            return true;
        } else {
            log.info("Jenkins OIDC token not found: skipping OIDC token retrieval");
            return false;
        }
    }

    public OidcToken getIDToken(Map<String, String> env) throws OidcException {
        String idToken = env.get("IDTOKEN");
        if (idToken == null) {
            throw new OidcException("Could not get Jenkins environment variable 'IDTOKEN'");
        }

        // Use the issuer URL as the base URL
        String issuerUrl = "https://127.0.0.1:8082/oidc";
        GenericUrl url = new GenericUrl(issuerUrl + "/?audience=" + this.audience);

        try {
            HttpRequest req = HttpClients.newRequestFactory(this.httpParams).buildGetRequest(url);
            req.setParser(new GsonFactory().createJsonObjectParser());
            req.getHeaders().setAuthorization("Bearer " + idToken);
            req.getHeaders().setAccept("application/json; api-version=2.0");
            req.getHeaders().setContentType("application/json");

            JenkinsOidcJsonResponse resp = req.execute().parseAs(JenkinsOidcJsonResponse.class);
            String tokenValue = resp.getValue();
            JsonWebSignature jws = JsonWebSignature.parse(new GsonFactory(), tokenValue);

            return ImmutableOidcToken.builder()
                    .idToken(tokenValue)
                    .issuer(jws.getPayload().getIssuer())
                    .subjectAlternativeName(jws.getPayload().getSubject())
                    .build();
        } catch (IOException e) {
            throw new OidcException("Could not obtain Jenkins OIDC token", e);
        }
    }

    public static class JenkinsOidcJsonResponse {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Builder {
        private HttpParams httpParams = ImmutableHttpParams.builder().build();
        private String audience = "sigstore";

        private Builder() {
        }

        public Builder audience(String audience) {
            this.audience = audience;
            return this;
        }

        public Builder httpParams(HttpParams httpParams) {
            this.httpParams = httpParams;
            return this;
        }

        public JenkinsOidcClient build() {
            return new JenkinsOidcClient(this.httpParams, this.audience);
        }
    }
}

