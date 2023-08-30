package com.laioffer.communitymanagement.model;

public record PasswordChangeBody(String username, String oldPassword, String newPassword,
                                 String firstName, String lastName, String email,
                                 String phoneNumber) {
}
