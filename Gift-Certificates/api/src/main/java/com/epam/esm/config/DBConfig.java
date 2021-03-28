package com.epam.esm.config;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.db.CertificateDaoImpl;
import com.epam.esm.dao.db.TagDaoImpl;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Defines the configuration for connecting to the database.
 */
@Configuration
@PropertySource("classpath:database.properties")
public class DBConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public AbstractDao<Tag> getTagDao(JdbcTemplate jdbcTemplate){
        return new TagDaoImpl(jdbcTemplate);
    }

    @Bean
    public AbstractDao<Certificate> getCertificateDao(JdbcTemplate jdbcTemplate){
        return new CertificateDaoImpl(jdbcTemplate);
    }
}
