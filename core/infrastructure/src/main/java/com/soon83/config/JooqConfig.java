package com.soon83.config;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

	@Bean
	public DSLContext dslContext(DataSource dataSource) {
		org.jooq.Configuration configuration = new DefaultConfiguration()
			.set(dataSource)
			.set(SQLDialect.MARIADB)
			.set(new Settings().withExecuteLogging(false))
			.set(new CustomJooqLoggerListener())
			.set(new org.jooq.conf.Settings().withRenderSchema(false));
		return DSL.using(configuration);
	}
}