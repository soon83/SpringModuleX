package com.soon83.config;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Query;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomJooqLoggerListener implements ExecuteListener {
	@Override
	public void executeStart(ExecuteContext ctx) {
		log.info(
			"==================================================================================================================================================================");
		log.info("=> QUERY: {}", ctx.sql());
		Query query = ctx.query();
		if (query != null) {
			log.info("=> PARAMETERS: {}", query.getBindValues());
		}
		log.info(
			"==================================================================================================================================================================");
	}

	@Override
	public void exception(ExecuteContext ctx) {
		log.error("Exception occurred during query execution", ctx.sqlException());
	}
}