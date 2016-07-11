package rates.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rates.entity.Rate;
import rates.entity.RateList;
import rates.service.RateService;

@RestController
public class HelloRestController {

	@Autowired
	RateService rateService;

	@RequestMapping("/")
	public String welcome() {// Welcome page, non-rest

		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping("/rest/{rate}")
	public RateList message(@PathVariable String rate) {// REST Endpoint.

		Rate r = new Rate();
		r.setRate(new BigDecimal(5));

		// rateService.insertRate(r);

		List<Rate> rList = rateService.getAllRates();

		Rate r1 = new Rate();
		// r1.setId(2);
		r1.setRate(new BigDecimal(10));
		r1.setValidDate(new Date());
		rList.add(r1);

		RateList rateList = new RateList();
		rateList.setRateList(rList);

		return rateList;
	}

	@RequestMapping("/batch/start")
	public String startTask() {

		try {
			startJob();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "job error";
		}

		return "job started";
	}

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	public boolean startJob() throws Exception {

		boolean result = false;

		try {

			final JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.nanoTime())
					.toJobParameters();

			final JobExecution execution = jobLauncher.run(job, jobParameters);
			final ExitStatus status = execution.getExitStatus();

			if (ExitStatus.COMPLETED.getExitCode().equals(status)) {
				result = true;
			}

		} catch (Exception ex) {
			System.err.println("errore job " + ex);
		}

		return false;
	}
}