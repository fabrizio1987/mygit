package rates.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import rates.entity.Rate;

@Repository("ratesDao")
public class RatesDao extends AbstractDao<Rate>{
	
 
	@SuppressWarnings("unchecked")
    public List<Rate> findAllRates() {
		Criteria criteria = getSession().createCriteria(Rate.class);        
        return (List<Rate>) criteria.list();
    }
	
	
}
