package videos.props;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(CustomProperties.class)
public class ConfigProps {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ConfigProps.class, args);
        var bean = ctx.getBean(CustomProperties.class);
        System.out.println(bean.getFooter() + " " + bean.getHeader());
    }

}

@Component
@ConfigurationProperties(prefix="custom")
class CustomProperties {
    // if we need a default value, assign it here or the constructor
    private String header, footer;

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
