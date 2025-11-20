package tech.ada.java.cursospring.api.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    @NotBlank
    private String nome;
    @Email
    private String email;
    @Past
    //@Pattern(regexp = "") precisa especificar qual a regex
    private LocalDate dob;

    public Usuario(UUID uuid, String nome, String email, LocalDate dob) {
        this.uuid = uuid;
        this.nome = nome;
        this.email = email;
        this.dob = dob;
    }
}
