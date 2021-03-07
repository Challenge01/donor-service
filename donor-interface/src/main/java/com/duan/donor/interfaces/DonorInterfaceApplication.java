package com.duan.donor.interfaces;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.duan.donor.business.mapper")
@SpringBootApplication(scanBasePackages = {"com.duan.donor.business.service.*","com.duan.donor.interfaces.*"})
public class DonorInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonorInterfaceApplication.class, args);
    }

}
