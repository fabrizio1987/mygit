package rates.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "rate", schema = "entropay")
public class Rate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column
	private String file;
	
	@Column
	private String buyCurrency;
	
	@Column
	private String sellCurrency;
	
	public Rate() {
		// TODO Auto-generated constructor stub
	}
	
	public Rate(String buyCurrency,  BigDecimal rate, String sellCurrency, Date validDate) {
		super();
		this.buyCurrency = buyCurrency;
		this.sellCurrency = sellCurrency;
		this.validDate = validDate;
		this.rate = rate;
	}
	@Column
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date validDate;
	
	@Column
	private BigDecimal rate;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getBuyCurrency() {
		return buyCurrency;
	}
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}
	public String getSellCurrency() {
		return sellCurrency;
	}
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyCurrency == null) ? 0 : buyCurrency.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((sellCurrency == null) ? 0 : sellCurrency.hashCode());
		result = prime * result + ((validDate == null) ? 0 : validDate.hashCode());
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
		Rate other = (Rate) obj;
		if (buyCurrency == null) {
			if (other.buyCurrency != null)
				return false;
		} else if (!buyCurrency.equals(other.buyCurrency))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (sellCurrency == null) {
			if (other.sellCurrency != null)
				return false;
		} else if (!sellCurrency.equals(other.sellCurrency))
			return false;
		if (validDate == null) {
			if (other.validDate != null)
				return false;
		} else if (!validDate.equals(other.validDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Rate [id=" + id + ", file=" + file + ", buyCurrency=" + buyCurrency + ", sellCurrency=" + sellCurrency
				+ ", validDate=" + validDate + ", rate=" + rate + "]";
	}
	
	


}
