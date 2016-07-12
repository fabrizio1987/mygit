package rates.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import rates.entity.Rate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	@Value("classpath:*.DAT")
	private Resource[] resources;

	
	@Bean 
	public MultiResourceItemReader<RateLine> multiReader() {
		MultiResourceItemReader<RateLine> reader = new MultiResourceItemReader<RateLine>();
		reader.setResources(resources);
		reader.setDelegate(reader());
		return reader;
		
	}

	// tag::readerwriterprocessor[]
	@Bean
	public FlatFileItemReader<RateLine> reader() {
		FlatFileItemReader<RateLine> reader = new FlatFileItemReader<RateLine>();
		reader.setResource(new ClassPathResource("rates-2016-01-01.DAT"));
		reader.setLineMapper(new DefaultLineMapper<RateLine>() {
			{
				Range[] range = new Range[4];
				range[0] = new Range(1, 8);
				range[1] = new Range(9, 16);
				range[2] = new Range(17, 19);
				range[3] = new Range(20, 22);
				setLineTokenizer(new FixedLengthTokenizer() {
					{
						setNames(new String[] { "date", "rate", "buy", "sell" });

						setColumns(range);
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<RateLine>() {
					{
						setTargetType(RateLine.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public RateItemProcessor processor() {
		return new RateItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Rate> writer() {
		JdbcBatchItemWriter<Rate> writer = new JdbcBatchItemWriter<Rate>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Rate>());
		writer.setSql(
				"INSERT INTO rate (buyCurrency, rate, sellCurrency, validDate) VALUES (:buyCurrency, :rate, :sellCurrency, :validDate)");
		writer.setDataSource(dataSource);
		return writer;
	}
	// end::readerwriterprocessor[]

	// tag::listener[]

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener(new JdbcTemplate(dataSource));
	}

	// end::listener[]

	// tag::jobstep[]
	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("importRatesJob").incrementer(new RunIdIncrementer()).listener(listener())
				.flow(step1()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<RateLine, Rate> chunk(10).reader(multiReader()).processor(processor())
				.writer(writer()).build();
	}
	// end::jobstep[]

	// // tag::readerwriterprocessor[]
	// @Bean
	// public FlatFileItemReader<Person> reader() {
	// FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
	// reader.setResource(new ClassPathResource("sample-data.csv"));
	// reader.setLineMapper(new DefaultLineMapper<Person>() {
	// {
	// setLineTokenizer(new DelimitedLineTokenizer() {
	// {
	// setNames(new String[] { "firstName", "lastName" });
	// }
	// });
	// setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
	// {
	// setTargetType(Person.class);
	// }
	// });
	// }
	// });
	// return reader;
	// }
	//
	// @Bean
	// public PersonItemProcessor processor() {
	// return new PersonItemProcessor();
	// }
	//
	// @Bean
	// public JdbcBatchItemWriter<Person> writer() {
	// JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
	// writer.setItemSqlParameterSourceProvider(new
	// BeanPropertyItemSqlParameterSourceProvider<Person>());
	// writer.setSql("INSERT INTO people (first_name, last_name) VALUES
	// (:firstName, :lastName)");
	// writer.setDataSource(dataSource);
	// return writer;
	// }
	// // end::readerwriterprocessor[]
	//
	// // tag::listener[]
	//
	// @Bean
	// public JobExecutionListener listener() {
	// return new JobCompletionNotificationListener(new
	// JdbcTemplate(dataSource));
	// }
	//
	// // end::listener[]
	//
	// // tag::jobstep[]
	// @Bean
	// public Job importUserJob() {
	// return jobBuilderFactory.get("importUserJob").incrementer(new
	// RunIdIncrementer()).listener(listener())
	// .flow(step1()).end().build();
	// }
	//
	// @Bean
	// public Step step1() {
	// return stepBuilderFactory.get("step1").<Person, Person>
	// chunk(10).reader(reader()).processor(processor())
	// .writer(writer()).build();
	// }
	// // end::jobstep[]
}
