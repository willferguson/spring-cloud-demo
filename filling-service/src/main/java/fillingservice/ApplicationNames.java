package fillingservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("will")
public class ApplicationNames {


    private String appName = "test";

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
