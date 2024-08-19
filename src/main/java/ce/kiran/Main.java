package ce.kiran;

import com.cloudbees.plugins.credentials.Credentials;
import com.cloudbees.plugins.credentials.common.IdCredentials;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import hudson.model.ItemGroup;
import jenkins.model.Jenkins;
import org.acegisecurity.Authentication;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        System.out.println(env);
        List<? extends Credentials> creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
          com.cloudbees.plugins.credentials.common.StandardCredentials.class, (ItemGroup) Jenkins.getInstanceOrNull(),
                (Authentication) null, (DomainRequirement)  null
        );
        for (Credentials c : creds) {
            System.out.println(c.getDescriptor());
        }
    }
}