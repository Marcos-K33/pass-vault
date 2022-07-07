package com.pass.vault.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {

    private static final String EMAIL_REGEX = ".+@.+\\..+";

    @JsonProperty
    private String name;

    @JsonProperty
    @NotBlank
    @Email(regexp = EMAIL_REGEX, message = "correo inválido")
    private String email;

    @JsonProperty
    @NotBlank
    private String password;

    /**
     * 
     */
    public RegisterRequest() {
        super();
    }

    /**
     * @param name
     * @param email
     * @param password
     */
    public RegisterRequest(String name,
            @NotBlank @Email(regexp = EMAIL_REGEX, message = "correo inválido") String email,
            @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "RegisterRequest {email=" + email
                + ", name=" + name
                + ", password=" + password
                + "}";
    }

}
