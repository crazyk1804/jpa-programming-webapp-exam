package com.example.jpaprogrammingwebappexam.config;

import com.example.jpaprogrammingwebappexam.persistence.domain.Album;
import com.example.jpaprogrammingwebappexam.persistence.domain.Book;
import com.example.jpaprogrammingwebappexam.persistence.domain.Item;
import com.example.jpaprogrammingwebappexam.persistence.domain.Movie;
import com.example.jpaprogrammingwebappexam.service.ItemService;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Profile("local")
public class H2ServerConfiguration {

	@Bean
	@ConfigurationProperties("spring.datasource.hikari")
	public DataSource dataSource() throws SQLException {
//		defaultRun();
		return new HikariDataSource();
	}

	private Server defaultRun() throws SQLException {
		return Server.createTcpServer(
			"-tcp",
			"-tcpAllowOthers",
			"-ifNotExists",
			"-tcpPort", "9092"
		).start();
	}


}
