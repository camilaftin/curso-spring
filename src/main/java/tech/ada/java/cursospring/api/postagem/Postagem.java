package tech.ada.java.cursospring.api.postagem;

import jakarta.persistence.*;
import lombok.*;
import tech.ada.java.cursospring.api.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Postagem {

    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    private String titulo;
    private String corpo;
    private LocalDateTime dataCriacao;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;





}
