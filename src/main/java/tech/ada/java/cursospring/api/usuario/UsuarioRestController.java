package tech.ada.java.cursospring.api.usuario;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.cursospring.api.exception.NaoEncontradoException;

import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/usuarios")
public class UsuarioRestController {

    private final List<Usuario> usuarioList = new ArrayList<>();
    private final UsuarioJpaRepository repository;

    public UsuarioRestController(UsuarioJpaRepository repository){
        this.repository = repository;
    }

    @GetMapping("/dummy")
    public Usuario dummyUsuario(){
        return new Usuario(UUID.randomUUID(), "Jose", "jose@gmail.com", LocalDate.now());
    }

    @GetMapping
    public List<Usuario> listarTodos(){
        return this.repository.findAll();
    }

    @GetMapping("/{uuid}")
    public Usuario buscarPorUuid(@PathVariable UUID uuid){
        return this.repository.findByUuid(uuid)
                .orElseThrow(() -> new NaoEncontradoException("usuario nao encontrado"));

//        return usuarioList.stream()
//                .filter(usuario -> usuario.getUuid().equals(uuid))
//                .findFirst()
//                .orElseThrow();
    }

    @PostMapping("/")
    public Usuario criarUsuario(@RequestBody @Valid Usuario usuario){
        return this.repository.save(usuario);
//        this.usuarioList.add(usuario);
//        return usuario;
    }

    @PutMapping("/{uuid}")
    public Usuario atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid Usuario usuarioNovo){
        Usuario usuario = this.buscarPorUuid(id);
        usuarioNovo.setId(usuario.getId());
        return this.repository.save(usuarioNovo);
//        this.usuarioList.set(this.usuarioList.indexOf(usuario), usuarioNovo);
//        return usuarioNovo;
    }

    @PatchMapping("/{uuid}/alterar-nome")
    public Usuario alterarNome(@PathVariable UUID uuid, @RequestBody Usuario usuarioAlterado){
        Usuario usuario = this.buscarPorUuid(uuid);
        usuario.setNome(usuarioAlterado.getNome());
        this.usuarioList.set(this.usuarioList.indexOf(usuario), usuarioAlterado);
        return usuarioAlterado;

    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void deletarUsuario(@PathVariable UUID uuid){
        this.repository.deleteByUuid(uuid);
//        this.usuarioList.removeIf(usuario -> usuario.getUuid().equals(id));
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(
//            MethodArgumentNotValidException ex){
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName,errorMessage);
//        });
//        return errors;
//    }

}
