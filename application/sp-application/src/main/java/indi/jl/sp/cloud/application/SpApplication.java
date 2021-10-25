package indi.jl.sp.cloud.application;

import indi.jl.sp.core.constant.CoreConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = CoreConstant.SCAN_PACKAGE_NAME)
public class SpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpApplication.class, args);
    }
}
