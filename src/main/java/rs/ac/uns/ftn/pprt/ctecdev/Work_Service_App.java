package rs.ac.uns.ftn.pprt.ctecdev;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        org.activiti.spring.boot.RestApiAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class})
@EnableWebMvc
public class Work_Service_App {
	
	
    
	public static void main(String[] args) {
        SpringApplication.run(Work_Service_App.class, args);
        System.out.println("<<<>>>	 Work_Service_App STARTED	<<<>>>");
    }

    @Bean
    public ProcessEngineConfiguration getProcessEngineConfiguration(
    		SpringProcessEngineConfiguration pec) {
    	
        pec.setMailServerHost("localhost");
        pec.setMailServerPort(25);
        
        return pec;
    }
    
    

}
