package com.pass.vault.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShowPasswordResponse {

    @JsonProperty
    private String account;

    @JsonProperty
    private String password;

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
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
        return "ShowPasswordResponse {account=" + account
                + ", password=" + password
                + "}";
    }
}
