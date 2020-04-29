package it.polito.tdp.poweroutages.model;

import java.time.*;
import java.util.*;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> nercOutages;
	private List<PowerOutage> bestCombo;
	private int maxCustomers;
	private double totHours; 
	
	
	public Model() {
		podao = new PowerOutageDAO();
		bestCombo = new LinkedList<>();
		maxCustomers = 0;
		totHours = 0.0;
	}
	
	public int getMaxCustomers() {
		return maxCustomers;
	}

	public double getTotHours() {
		return totHours;
	}

	public void setNercOutages(Nerc nerc) {
		this.nercOutages = null;
		this.nercOutages = new LinkedList<>(this.podao.getPowerOutagesList(nerc));
	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> findBestCombo(Nerc nerc, int maxYears, int maxHours) {
		
		bestCombo = null;
		maxCustomers = 0;
		totHours = 0.0;
		this.setNercOutages(nerc);
		
		List<PowerOutage> partial = new LinkedList<>();
		
		this.searchCombo(partial, 0, 0.0, maxYears, maxHours);
		
		return bestCombo;
	}

	private void searchCombo(List<PowerOutage> partial, int level, double hours, int maxYears, int maxHours) {

		//terminal cases: 
		
		if(hours>maxHours)
			return;
		
		int monthsPartial = this.monthsPartial(partial);
		if(monthsPartial>12*maxYears)
			return;
		
		int customersPartial = this.customersPartial(partial);
		if(customersPartial>maxCustomers)
		{
			bestCombo = new LinkedList<>(partial);
			maxCustomers = customersPartial;
			totHours = hours;
		}
		
		if(level==this.nercOutages.size())
			return;
		
		//intermediate case:
		
		PowerOutage po = this.nercOutages.get(level);
		partial.add(po);
		hours += po.totalHours();
		this.searchCombo(partial, level+1, hours, maxYears, maxHours);
		partial.remove(partial.size()-1);
		hours -= po.totalHours();
		this.searchCombo(partial, level+1, hours, maxYears, maxHours);
		
	}
	
	private int customersPartial(List<PowerOutage> partial) {
		int customers = 0;
		if(partial.size()>0)
		{
			for(PowerOutage po : partial)
				customers += po.getCustomers_affected();
		}
		return customers;
	}
	
	private int monthsPartial(List<PowerOutage> partial) {
		int months = 0;
		if(partial.size()>1)
		{
			Period period = Period.between(partial.get(0).getBeginDate().toLocalDate(), partial.get(partial.size()-1).getEndDate().toLocalDate());
			months = 12*period.getYears() + period.getMonths();
		}
		return months;
	}

}
