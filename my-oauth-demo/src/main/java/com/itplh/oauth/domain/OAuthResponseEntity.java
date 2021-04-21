package com.itplh.oauth.domain;

/**
 * Auto-generated: 2021-04-20 16:14:10
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class OAuthResponseEntity {

    private String access_token;
    private String token_type;
    private long expires_in;
    private String refresh_token;
    private String scope;
    private long created_at;
    private String error;
    private String error_description;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError_description() {
        return error_description;
    }

    @Override
    public String toString() {
        return "OAuthResponseEntity{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", created_at=" + created_at +
                ", error='" + error + '\'' +
                ", error_description='" + error_description + '\'' +
                '}';
    }
}
