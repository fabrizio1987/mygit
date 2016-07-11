package rates.service;

import java.util.List;

import rates.entity.Rate;

public interface RateService {

	List<Rate> getAllRates();
	
	void insertRate(Rate r);
}
