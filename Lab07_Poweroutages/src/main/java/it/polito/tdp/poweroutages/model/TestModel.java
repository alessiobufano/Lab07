package it.polito.tdp.poweroutages.model;

import java.text.*;
import java.time.*;
import java.util.*;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		

		List<PowerOutage> list = new LinkedList<>(model.findBestCombo(model.getNercList().get(15), 4, 200));
		System.out.print("\nTot people affected: "+model.getMaxCustomers()+"\n");
    	DecimalFormat format = new DecimalFormat("0.00"); 
    	System.out.print("Tot hours of power outage: "+format.format(model.getTotHours())+"\n");
    	for(PowerOutage po : list)
    		System.out.print(po.toString()+"\n");
		
		LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 9, 19, 46, 45);
        LocalDateTime fromDateTime = LocalDateTime.of(2011, 8, 10, 7, 41, 55);
        
        Duration d = Duration.between(fromDateTime, toDateTime);
        long l = d.toMinutes();
        double m = ((double) l)/60.0;
        DecimalFormat formatd = new DecimalFormat("0.00"); 
        System.out.println("\n\n*****"+formatd.format(m));
        
        Period p = Period.between(fromDateTime.toLocalDate(), toDateTime.toLocalDate());
        long y = p.getMonths();
        System.out.println("***"+y);

	}

}
