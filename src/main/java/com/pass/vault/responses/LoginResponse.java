package com.pass.vault.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    @JsonInclude
    @JsonProperty
    private String email;

    @JsonInclude
    @JsonProperty
    private String token;

    public LoginResponse() {
        super();
    }

    /**
     * Default args consturctor
     * 
     * @param email
     * @param token
     */
    public LoginResponse(String email, String token) {
        this.email = email;
        this.token = token;
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
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "LoginResponse {email=" + email +
                ", token=" + token +
                "}";
    }

}
