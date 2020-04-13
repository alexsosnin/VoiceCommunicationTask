package org.vc.task.vct01.db.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan (basePackages = "org.vc.task.vct01.db.model")
@PropertySource (value = "classpath:/properties/database.properties")
@EnableTransactionManagement
public class DbConfig {

	private final Environment env;

	@Autowired
	public DbConfig(Environment env) {
		this.env = env;
	}

	@Bean (destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		ds.setUrl(env.getProperty("jdbc.url"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));

		ds.setInitialSize(Integer.parseInt(env.getProperty("connection.pool.initial.size")));
		ds.setMaxActive(Integer.parseInt(env.getProperty("active.connections.maximum.number")));

		return ds;
	}

	@Bean
	public NamedParameterJdbcTemplate npJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public DataSourceTransactionManager jdbcTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
