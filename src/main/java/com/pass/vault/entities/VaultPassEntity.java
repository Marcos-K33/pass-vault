package com.pass.vault.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vault_pass")
public class VaultPassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encrypt_value")
    private String encryptValue;

    @Column(name = "account")
    private String account;

    @Column(name = "platform")
    private String platform;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "id_user")
    private Long idUser;

    public VaultPassEntity() {
        super();
    }

    /**
     * @param encryptValue
     * @param account
     * @param platform
     * @param createdAt
     * @param updatedAt
     * @param idUser
     */
    public VaultPassEntity(String encryptValue, String account, String platform, Date createdAt, Date updatedAt,
            Long idUser) {
        this.encryptValue = encryptValue;
        this.account = account;
        this.platform = platform;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idUser = idUser;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the encryptValue
     */
    public String getEncryptValue() {
        return encryptValue;
    }

    /**
     * @param encryptValue the encryptValue to set
     */
    public void setEncryptValue(String encryptValue) {
        this.encryptValue = encryptValue;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return the idUser
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

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
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", encryptValue=" + encryptValue +
                ", account=" + account +
                ", platform=" + platform +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", idUser=" + idUser +
                "}";
    }
}
