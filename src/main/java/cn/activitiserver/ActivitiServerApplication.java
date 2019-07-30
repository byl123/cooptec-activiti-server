package cn.activitiserver;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 去除activiti.spring.boot 中的Security验证
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ActivitiServerApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    public static void main(String[] args) {
        SpringApplication.run(ActivitiServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>>>>>>>>>>>>>> Activiti service started <<<<<<<<<<<<<<<");
    }
}
