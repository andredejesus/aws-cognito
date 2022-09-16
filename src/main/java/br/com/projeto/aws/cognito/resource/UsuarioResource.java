package br.com.projeto.aws.cognito.resource;

import br.com.projeto.aws.cognito.dto.CadastraUsuarioDTO;
import br.com.projeto.aws.cognito.dto.UsuarioResponseDTO;
import br.com.projeto.aws.cognito.service.UsuarioService;
import com.amazonaws.services.cognitoidp.model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> signUp(@RequestBody @Validated CadastraUsuarioDTO dadosUsuario) {
        UserType response = usuarioService.cadastraUsuario(dadosUsuario);
        return new ResponseEntity<>(new UsuarioResponseDTO(response, "Usuário criado com sucesso!!", false), HttpStatus.CREATED);
    }

}
