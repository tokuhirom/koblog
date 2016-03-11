package me.geso.koblog.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "koblog")
@Data
public class KoblogSettings {
    private String filePath;
}
