package ce.kiran;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
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
import hudson.model.ItemGroup;
import hudson.security.ACL;
import io.jenkins.plugins.oidc_provider.IdTokenCredentials;
import jenkins.model.Jenkins;
import org.acegisecurity.Authentication;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;

public class JenkinsOidcClient implements OidcClient {

    @Override
    public boolean isEnabled(Map<String, String> map) {
        return false;
    }

    @Override
    public OidcToken getIDToken(Map<String, String> map) throws OidcException {
        return null;
    }

    public static IdTokenCredentials getCredentials() {
        IdTokenCredentials credentials = CredentialsMatchers.firstOrNull(
                CredentialsProvider.lookupCredentials(
                        IdTokenCredentials.class,
                        (ItemGroup) Jenkins.getInstanceOrNull(),
                        (Authentication) ACL.SYSTEM,
                        Collections.<DomainRequirement>emptyList()
                ),
                CredentialsMatchers.withId("oidc-token")
        );

        if ( credentials == null) {
            throw new RuntimeException(" Could not find credentials entry with ID 'oidc-token'" );
        }

        return credentials;
    }
}

