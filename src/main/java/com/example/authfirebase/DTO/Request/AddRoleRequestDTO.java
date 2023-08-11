package com.example.authfirebase.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleRequestDTO {
    @NotNull
    private String idUser;
    @NotNull
    private String idRole;
}
