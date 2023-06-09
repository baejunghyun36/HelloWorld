package com.project.helloworld.security.oauth2;

import lombok.Setter;

import java.util.Map;

@Setter
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getUserSeq();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
