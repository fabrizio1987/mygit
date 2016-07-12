package rates.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import rates.entity.Rate;

@Repository("ratesDao")
public class RatesDao extends AbstractDao<Rate>{
	 
	@SuppressWarnings("unchecked")
    public List<Rate> findAllRates() {
		Criteria criteria = getSession().createCriteria(Rate.class);        
        return (List<Rate>) criteria.list();
    }

	public List<Rate> findRatesByDate(Date date) {
		Criteria criteria = getSession().createCriteria(Rate.class); 
		criteria.add(Restrictions.eq("validDate", date));
        return (List<Rate>) criteria.list();
	}

	public void deleteRateByDate(Date date) {
		String hql = "delete from rates where validDate = :validDate";
		Query query = getSession().createQuery(hql);
		query.setDate("validDate", date);
		query.executeUpdate();		
	}
	
}
