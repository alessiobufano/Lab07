package it.polito.tdp.poweroutages.model;

import java.text.*;
import java.time.*;

public class PowerOutage {
	
	private Nerc nerc;
	private int customers_affected;
	private LocalDateTime beginDate;
	private LocalDateTime endDate;
	
	public PowerOutage(Nerc nerc, int customers_affected, LocalDateTime beginDate, LocalDateTime endDate) {
		this.nerc = nerc;
		this.customers_affected = customers_affected;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public int getCustomers_affected() {
		return customers_affected;
	}

	public void setCustomers_affected(int customers_affected) {
		this.customers_affected = customers_affected;
	}

	public LocalDateTime getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDateTime beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((nerc == null) ? 0 : nerc.hashCode());
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
		PowerOutage other = (PowerOutage) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (nerc == null) {
			if (other.nerc != null)
				return false;
		} else if (!nerc.equals(other.nerc))
			return false;
		return true;
	}
	
	public double totalHours() {
		Duration duration = Duration.between(beginDate, endDate);
		long min = duration.toMinutes();
	    return ((double) min)/60.0;
	}

	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("0.00"); 
		return "Power Outage last from "+ beginDate + " to " + endDate + " ; total hours: "+ format.format(this.totalHours()) + " ; customers affected: " + customers_affected;
	}
	
	
	
	

}
