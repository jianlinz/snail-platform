package indi.jl.sp.test;

import indi.jl.sp.core.constant.CoreConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = CoreConstant.SCAN_PACKAGE_NAME)
public class SpTestApplication {

    static void main(String[] args) {
        SpringApplication.run(SpTestApplication.class, args);
    }

}