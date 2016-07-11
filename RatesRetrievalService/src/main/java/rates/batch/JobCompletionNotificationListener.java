package rates.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import rates.entity.Rate;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("!!! JOB FINISHED! Time to verify the results");

			List<Rate> results = jdbcTemplate.query("SELECT buyCurrency, rate, sellCurrency, validDate FROM rate", new RowMapper<Rate>() {
				@Override
				public Rate mapRow(ResultSet rs, int row) throws SQLException {
					return new Rate(rs.getString(1), rs.getBigDecimal(2), rs.getString(3), rs.getDate(4));
				}
			});

			for (Rate rate : results) {
				System.out.println("Found <" + rate + "> in the database.");
			}

		}
	}
}
