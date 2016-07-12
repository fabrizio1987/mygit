package rates.service;

import java.util.Date;
import java.util.List;

import rates.entity.Rate;

public interface RateService {

	List<Rate> getAllRates();
	
	List<Rate> getRatesByDate(Date date);
	
	void insertOrUpdateRate(Rate rate);
	
	void deleteRateByDate(Date date);
	
}
