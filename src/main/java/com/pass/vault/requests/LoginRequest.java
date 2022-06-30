package com.pass.vault.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {

    private static final String EMAIL_REGEX = ".+@.+\\..+";
    // private static final String EMAIL_REGEX =
    // "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    @JsonProperty
    @NotNull(message = "El correo es obligatorio")
    @Email(regexp = EMAIL_REGEX, message = "El correo no tiene una estructura válida")
    private String email;

    @JsonProperty
    @NotNull(message = "la contraseña es obligatoria")
    @Length(min = 10, max = 18)
    private String password;

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
