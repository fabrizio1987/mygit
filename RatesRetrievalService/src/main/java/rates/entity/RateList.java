package rates.entity;

import java.util.LinkedList;
import java.util.List;

public class RateList {
	
	private List<Rate> rateList = new LinkedList<Rate>();
	
	public List<Rate> getRateList() {
		return rateList;
	}
	
	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rateList == null) ? 0 : rateList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RateList other = (RateList) obj;
		if (rateList == null) {
			if (other.rateList != null)
				return false;
		} else if (!rateList.equals(other.rateList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RateList [rateList=" + rateList + "]";
	}
	
	

}
