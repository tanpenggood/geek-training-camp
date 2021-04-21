package com.itplh.oauth.domain;

/**
 * Auto-generated: 2021-04-20 16:14:10
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class OAuthRequestEntity {

    private String grant_type;
    private String code;
    private String client_id;
    private String redirect_uri;
    private String client_secret;

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getClient_secret() {
        return client_secret;
    }

    @Override
    public String toString() {
        return "OAuthRequestEntity{" +
                "grant_type='" + grant_type + '\'' +
                ", code='" + code + '\'' +
                ", client_id='" + client_id + '\'' +
                ", redirect_uri='" + redirect_uri + '\'' +
                ", client_secret='" + client_secret + '\'' +
                '}';
    }
}
