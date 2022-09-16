package br.com.projeto.aws.cognito.service.impl;

import br.com.projeto.aws.cognito.config.AwsConfig;
import br.com.projeto.aws.cognito.dto.CadastraUsuarioDTO;
import br.com.projeto.aws.cognito.service.UsuarioService;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final AwsConfig awsConfig;
    private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Override
    public UserType cadastraUsuario(CadastraUsuarioDTO dadosUsuario) {

        try {
            final AdminCreateUserRequest signUpRequest = new AdminCreateUserRequest()
                    .withUserPoolId(awsConfig.getCognito().getUserPoolId())
                    // senha temporaria do usuario.
                    .withTemporaryPassword(geraSenhaValida())
                    // Definindo o tipo de autenticacao que sera por e-mail
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
                    .withUsername(dadosUsuario.getEmail())
                    .withMessageAction(MessageActionType.SUPPRESS);


            // Cria um novo usuário
            AdminCreateUserResult retornoUsuarioCriado = awsCognitoIdentityProvider.adminCreateUser(signUpRequest);
            log.info("Created User id: {}", retornoUsuarioCriado.getUser().getUsername());

            // definir senha permanente
            defineNovaSenha(dadosUsuario.getEmail(), dadosUsuario.getPassword());

            return retornoUsuarioCriado.getUser();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    //Gera uma senha válida
    private String geraSenhaValida() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "ERRONEOUS_SPECIAL_CHARS";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

    public AdminSetUserPasswordResult defineNovaSenha(String username, String password) {

        try {
            // Define a senha do usuário especificado em um grupo de usuários como administrador. Funciona em qualquer usuário.
            AdminSetUserPasswordRequest adminSetUserPasswordRequest = new AdminSetUserPasswordRequest()
                    .withUsername(username)
                    .withPassword(password)
                    .withUserPoolId(awsConfig.getCognito().getUserPoolId())
                    .withPermanent(true);

            return awsCognitoIdentityProvider.adminSetUserPassword(adminSetUserPasswordRequest);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
