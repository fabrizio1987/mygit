package rates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rates.dao.RatesDao;
import rates.entity.Rate;

@Service("rateService")
@Transactional
public class RateServiceImpl implements RateService{

	@Autowired
	RatesDao ratesDao;
	
	public List<Rate> getAllRates() {
		return ratesDao.findAllRates();
	}
	
	public void insertRate(Rate rate) {
		ratesDao.saveOrUpdate(rate);
	}
}
