package com.jmccann.capstone.domain;

import lombok.Data;

@Data
public class PasswordUpdate {

    String oldPassword;
    String newPassword;
}
