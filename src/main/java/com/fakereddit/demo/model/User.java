package com.fakereddit.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotEmpty;
//import static javax.persistence.GenerationType.IDENTITY;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long userId;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;


    @NotBlank(message = "Email is required.")
    private String email;
    private Instant created;
    private boolean enabled;



//    @NotBlank(message = "Username is required")
//    private String username;
//    @NotBlank(message = "Password is required")
//    private String password;
//    @Email
//    @NotEmpty(message = "Email is required")
//    private String email;
//    private Instant created;
//    private boolean enabled;
}
