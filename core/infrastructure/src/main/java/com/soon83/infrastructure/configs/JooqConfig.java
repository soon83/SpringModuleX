package com.soon83.infrastructure.configs;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {
	@Bean
	public DSLContext dslContext(DataSource dataSource) {
		org.jooq.Configuration configuration = new DefaultConfiguration()
			.set(dataSource)
				//.set(SQLDialect.MARIADB)
				.set(SQLDialect.POSTGRES)
			.set(new Settings().withExecuteLogging(false))
			.set(new CustomJooqLoggerListener())
			.set(new org.jooq.conf.Settings().withRenderSchema(false));
		return DSL.using(configuration);
	}
}