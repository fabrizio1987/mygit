package rates.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class RateRestController {

	@Autowired
	RateService rateService;
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;
	

	/**
	 * Start the batch job.
	 *
	 * @return the string
	 */
	@RequestMapping("/batch/start")
	public String startTask() {
		
		try {
			startJob();
			return "job started";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "job error";
		}		
	}

	

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

		return result;
	}

	@RequestMapping("/")
	public String welcome() {

		return "Welcome to RestTemplate Example.";
	}
	
	

	@RequestMapping("/rest/{rate}")
	public RateList message(@PathVariable String rate) {

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
	
	
	@RequestMapping("/rest/rates/{date}")
	public RateList getRatesByDate(@PathVariable String date) {	

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Rate> rList = rateService.getRatesByDate(d);
		RateList rateList = new RateList();
		rateList.setRateList(rList);

		return rateList;
		
	}

}