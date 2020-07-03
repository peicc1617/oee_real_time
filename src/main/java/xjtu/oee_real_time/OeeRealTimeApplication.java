package xjtu.oee_real_time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class OeeRealTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OeeRealTimeApplication.class, args);
    }

}
