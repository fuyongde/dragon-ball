package com.jason.goku.api.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class UserRequest implements Serializable {

    @Length(min = 6, max = 16)
    private String username;
    @Length(min = 6, max = 16)
    private String password;
    private String mobile;
    private String email;
    private String idCard;

    private UserRequest() {
    }

    private UserRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.mobile = builder.mobile;
        this.email = builder.email;
        this.idCard = builder.idCard;
    }

    private UserRequest(LoginBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    private UserRequest(RegisterBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.mobile = builder.mobile;
        this.email = builder.email;
        this.idCard = builder.idCard;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getIdCard() {
        return idCard;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public static class Builder {
        private String username;
        private String password;
        private String mobile;
        private String email;
        private String idCard;

        public Builder(String username) {
            this.username = username;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withIdCard(String idCard) {
            this.idCard = idCard;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }

    public static class RegisterBuilder {
        private String username;
        private String password;
        private String mobile;
        private String email;
        private String idCard;

        public RegisterBuilder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public RegisterBuilder withMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public RegisterBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public RegisterBuilder withIdCard(String idCard) {
            this.idCard = idCard;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }

    public static class LoginBuilder {
        private String username;
        private String password;

        public LoginBuilder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }
}
