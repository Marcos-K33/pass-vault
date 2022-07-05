package com.pass.vault.utils;

import java.util.Collections;
import java.util.HashMap;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude
public class ResponseWrapper {

    @JsonProperty
    @JsonInclude(value = Include.NON_NULL)
    private String message;

    @JsonProperty
    @JsonInclude(value = Include.NON_NULL)
    private Boolean success;

    @JsonProperty
    @JsonInclude
    private Object data;

    @JsonIgnore
    private HttpStatus status;

    /**
     * 
     */
    public ResponseWrapper() {
        super();
        this.message = "";
        this.data = Collections.emptyMap();
        this.success = false;
        status = HttpStatus.OK;
    }

    /**
     * @param message
     * @param success
     * @param data
     * @param status
     */
    public ResponseWrapper(String message, Boolean success, Object data, HttpStatus status) {
        super();
        this.message = message;
        this.success = success;
        this.data = data;
        this.status = status;
    }

    public void badRequest() {
        setData(new HashMap<>());
        setSuccess(false);
        setStatus(HttpStatus.BAD_REQUEST);
        setMessage("Petición incorrecta, verificar la información enviada");
    }

    public void invalidSession() {
        setData(new HashMap<>());
        setStatus(HttpStatus.UNAUTHORIZED);
        setSuccess(false);
        setMessage("Iniciar sesión nuevamente");
    }

    public void unauthenticated() {
        setData(new HashMap<>());
        setStatus(HttpStatus.FORBIDDEN);
        setSuccess(false);
        setMessage("Iniciar ");
    }

    public void errorServer() {
        setData(new HashMap<>());
        setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        setMessage("Error en el servidor");
        setSuccess(false);
    }

    public void OK() {
        setData(new HashMap<>());
        setStatus(HttpStatus.OK);
        setSuccess(true);
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + data +
                ", message=" + message +
                ", success=" + success +
                ", status=" + status +
                "}";
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
