package com.laioffer.communitymanagement.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public record PasswordChangeBody(@JsonProperty("old_password") String oldPassword,
                                 @JsonProperty("new_password") String newPassword,
                                 @JsonProperty("first_name") String firstName,
                                 @JsonProperty("last_name") String lastName,
                                 String email,
                                 @JsonProperty("phone_number") String phoneNumber) {
}
