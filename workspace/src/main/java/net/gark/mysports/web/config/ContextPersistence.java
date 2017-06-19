package net.gark.mysports.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("net.gark.mysports.*")
@EntityScan("net.gark.mysports.*")
public class ContextPersistence {

    boolean usePropertyFile = true;
    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if (usePropertyFile) {
            dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            dataSource.setUrl(env.getProperty("spring.datasource.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.password"));
        } else {
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/javasavvy");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
        }
        return dataSource;
    }

    private Properties jpaProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.hibernate.show-sql"));
        properties.put("hibernate.format_sql", env.getProperty("spring.jpa.hibernate.format-sql"));
        properties.put("hibernate.jdbc.time_zone", env.getProperty("spring.jpa.hibernate.jdbc.time_zone"));
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setJpaVendorAdapter(getJpaVendorAdapter());
        lcemfb.setDataSource(getDataSource());
        lcemfb.setPersistenceUnitName("spring-jpa-unit");
        lcemfb.setPackagesToScan("net.gark.mysports.*");
        lcemfb.setJpaProperties(jpaProperties());
        return lcemfb;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "transactionManager")
    @Autowired
    public PlatformTransactionManager getTransactionManager(final EntityManagerFactory emf) throws NamingException {
        final JpaTransactionManager jpaTransaction = new JpaTransactionManager();
        jpaTransaction.setEntityManagerFactory(emf);
        return jpaTransaction;
    }
}