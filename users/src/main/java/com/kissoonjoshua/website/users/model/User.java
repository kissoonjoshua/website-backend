package com.kissoonjoshua.website.users.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class User {
    @Id
    @NotBlank
    private String id;
    @NotBlank(message = "Missing Username")
    private String username;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Missing Email")
    private String email;
    @NotNull
    private List<String> categories;
    @NotNull
    private List<String> events;
}
