package rates.service;

import java.util.Date;
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
	
	@Override
	public List<Rate> getAllRates() {
		return ratesDao.findAllRates();
	}
	
	@Override
	public List<Rate> getRatesByDate(Date date) {
		return ratesDao.findRatesByDate(date);
	}
	
	@Override
	public void insertOrUpdateRate(Rate rate) {
		ratesDao.saveOrUpdate(rate);
	}
	
	@Override
	public void deleteRateByDate(Date date) {
		ratesDao.deleteRateByDate(date);
	}
}
