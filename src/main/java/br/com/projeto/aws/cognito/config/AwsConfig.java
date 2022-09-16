package br.com.projeto.aws.cognito.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties specific to aws client.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@Data
@Component
@ConfigurationProperties(prefix = "aws", ignoreUnknownFields = false)
public class AwsConfig {

    /**
     * Código da chave de acesso Aws
     */
    private String accessKey;


    /**
     * Chave de acesso secreta da Aws
     */
    private String secretKey;

    /**
     * Região da Aws
     */
    private String region;

    /**
     * Propriedades do AWS Cognito
     */
    private final Cognito cognito = new Cognito();

    @Getter
    @Setter
    public static class Cognito {
        private String userPoolId;
        private String appClientId;
        private String appClientSecret;
    }


}
