package br.com.projeto.aws.cognito.service;

import br.com.projeto.aws.cognito.dto.CadastraUsuarioDTO;
import com.amazonaws.services.cognitoidp.model.UserType;

public interface UsuarioService {

    UserType cadastraUsuario(CadastraUsuarioDTO dadosUsuario);

}
