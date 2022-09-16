package br.com.projeto.aws.cognito.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CadastraUsuarioDTO {

    private String email;
    private String password;
    private String name;
    private String nationality;
    private String lastname;
    private String phoneNumber;
    private Set<String> roles;

}
