package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@JsonDeserialize(builder = User.Builder.class)
public class User {
    @Id
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean enabled;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("apt_number")
    private String aptNumber;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;

    public User() {}

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.enabled = builder.enabled;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.aptNumber = builder.aptNumber;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAptNumber() {
        return this.aptNumber;
    }

    public User setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public static class Builder {
        @JsonProperty("username")
        private String username;
        @JsonProperty("password")
        private String password;
        @JsonProperty("enabled")
        private boolean enabled;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("apt_number")
        private String aptNumber;
        private String email;
        @JsonProperty("phone_number")
        private String phoneNumber;


        public String getUsername() {
            return username;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAptNumber() {
            return aptNumber;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
