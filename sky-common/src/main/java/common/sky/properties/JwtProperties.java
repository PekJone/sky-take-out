package common.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:04
 */
@Component
@Data
@ConfigurationProperties(prefix = "sky.jwt")
public class JwtProperties {
    private String adminSecretKey;
    private long adminTtl;

    private String adminTokenName;


    private String userSecretKey;
    private long userTtl;

    private String userTokenName;
}
