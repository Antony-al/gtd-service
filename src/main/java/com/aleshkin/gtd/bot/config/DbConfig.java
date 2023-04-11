package com.aleshkin.gtd.bot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
public class DbConfig {

	@Bean(name = "appDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "applicationJdbcTemplate")
	public JdbcTemplate applicationDataConnection() {
		return new JdbcTemplate(dataSource());
	}

//	
//	@Bean
//    @Qualifier("bot-db")
//    @ConfigurationProperties(prefix = "app.db.bot-db")
//    SpringDataJdbcProperties gitlabJdbcProperties() {
//        return new SpringDataJdbcProperties();
//    }
//
//    @Bean
//    @Qualifier("bot-db")
//    public DataSource gitlabDataSource(@Qualifier("bot-db") SpringDataJdbcProperties properties) {
//        return hikariDataSource("db", properties);
//    }
//
//    @Bean
//    @Qualifier("bot-db")
//    JdbcTemplate gitlabJdbcTemplate(@Qualifier("bot-db") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Data
//    @NoArgsConstructor
//    public static class SpringDataJdbcProperties {
//
//        // constants
//        private static final String H2_DATABASE_DRIVER = "org.h2.Driver";
//
//        String url;
//        String driver;
//        String user;
//        String password;
//        String poolSize;
//        int minPoolSize = 4;
//        int maxPoolSize = 10;
//        long idleTimeout;
//        long maxLifetime;
//        Integer bulkSize;
//
//        public SpringDataJdbcProperties(
//                String url, String driver, String user, String password, String poolSize, Integer bulkSize) {
//            this.url = url;
//            this.driver = driver;
//            this.user = user;
//            this.password = password;
//            this.poolSize = poolSize;
//            this.bulkSize = bulkSize;
//        }
//
//
//        public boolean isH2Database() {
//            return driver.equals(H2_DATABASE_DRIVER);
//        }
//
//
//		@Override
//		public String toString() {
//			return "SpringDataJdbcProperties [url=" + url + ", driver=" + driver + ", user=" + user + ", password="
//					+ password + ", poolSize=" + poolSize + ", minPoolSize=" + minPoolSize + ", maxPoolSize="
//					+ maxPoolSize + ", idleTimeout=" + idleTimeout + ", maxLifetime=" + maxLifetime + ", bulkSize="
//					+ bulkSize + "]";
//		}
//
//
//    }
}
