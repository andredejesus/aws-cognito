package br.com.projeto.aws.cognito.dto;

import lombok.*;

/**
 * <h2>BaseResponse</h2>
 *
 * @author aek
 * <p>
 * Description:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UsuarioResponseDTO {

    private Object data;
    private String message;
    private boolean error = true;

    public UsuarioResponseDTO(Object data, String message) {
        this.data = data;
        this.message = message;
    }

}
