package lpetlinski.bargain.server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String _id;
    @NotNull
    @Indexed(unique = true)
    private String _name;
    @Transient
    private String _password;
    @NotNull
    private String _encryptedPassword;
    @NotNull
    private List<String> _roles = new ArrayList<>();
    @NotNull
    private Boolean _enabled = true;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public String getEncryptedPassword() {
        return _encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        _encryptedPassword = encryptedPassword;
    }

    public List<String> getRoles() {
        return _roles;
    }

    public void setRoles(List<String> roles) {
        _roles = roles;
    }

    public Boolean getEnabled() {
        return _enabled;
    }

    public void setEnabled(Boolean enabled) {
        _enabled = enabled;
    }
}
