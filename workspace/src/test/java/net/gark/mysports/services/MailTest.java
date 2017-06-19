package net.gark.mysports.services;

import net.gark.mysports.Applicationsports;
import net.gark.mysports.services.interfaces.IServiceMail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mark on 16/05/2017.
 */
public class MailTest {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceMailImplJavaMail.class, ServiceVelocityEngine.class);

        ServiceMailImplJavaMail s = ctx.getBean(ServiceMailImplJavaMail.class);

        s.sendMail("howellmark13@gmail.com", "howellmark13@gmail.com", "Test", "Test");
        // s.sendMail("howellmark13@gmail.com", "mark.howell@microfocus.com", "Test", "Test");
    }
}
