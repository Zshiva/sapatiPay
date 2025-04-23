package citytech.global.platform.utils.securityutils.jwt;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
@Serdeable
@Introspected
public class RequestContext {
    int subject;
    String emailId;
    String roles;
    public RequestContext(){
    }
    public RequestContext(int subject, String emailId, String roles) {
        this.subject = subject;
        this.emailId = emailId;
        this.roles = roles;
    }
    public int getSubject() {
        return subject;
    }
    public void setSubject(int subject) {
        this.subject = subject;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getRoles() {
        return roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "subject=" + subject +
                ", emailId='" + emailId + '\'' +
                ", roles=" + roles +
                '}';
    }
}
