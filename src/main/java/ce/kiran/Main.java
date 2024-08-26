package ce.kiran;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import dev.sigstore.KeylessSigner;
import dev.sigstore.KeylessSignerException;
import dev.sigstore.TrustedRootProvider;
import dev.sigstore.bundle.Bundle;
import dev.sigstore.encryption.signers.Signers;
import dev.sigstore.fulcio.client.FulcioClient;
import dev.sigstore.oidc.client.OidcClients;
import dev.sigstore.rekor.client.RekorClient;
import dev.sigstore.tuf.SigstoreTufClient;
import io.jenkins.plugins.oidc_provider.IdTokenCredentials;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, KeylessSignerException {
//        OidcClients clients = OidcClients.of(JenkinsOidcClient.builder().build());
//        KeylessSigner signer = KeylessSigner.builder()
//                .oidcClients(clients)
//                .signer(Signers.newRsaSigner())
//                .trustedRootProvider(TrustedRootProvider.from(SigstoreTufClient.builder().usePublicGoodInstance()))
//                .fulcioUrl(FulcioClient.PUBLIC_GOOD_URI)
//                .rekorUrl(RekorClient.PUBLIC_GOOD_URI)
//                .minSigningCertificateLifetime(KeylessSigner.DEFAULT_MIN_SIGNING_CERTIFICATE_LIFETIME)
//                .build();
//        Bundle result = signer.signFile(Paths.get("src/main/java/ce/kiran/hello.txt"));
//        System.out.println(result.toJson());
        String isCI = System.getenv("CI");
        System.out.println("Does the current environment is a CI? " + isCI);
        IdTokenCredentials token = JenkinsOidcClient.getCredentials();
        System.out.println(token);
    }
}