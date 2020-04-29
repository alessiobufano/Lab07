package it.polito.tdp.poweroutages;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;


import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private ObservableList<Nerc> list = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ChoiceBox<Nerc> boxNerc;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;


    @FXML
    void doWorstCaseAnalysis(ActionEvent event) {
    	
    	this.txtResult.clear();
    	Nerc nerc = this.boxNerc.getValue();
    	String years = this.txtYears.getText();
    	String hours = this.txtHours.getText();
    	int maxYears = 0;
    	int maxHours = 0;

    	if(nerc==null) {
    		this.txtResult.setText("Error! Please select a NERC for searching!\n");
    		return;
    	}
    	
    	try {
    		maxYears = Integer.parseInt(years);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Error! The field of Max Years must be a number!\n");
    		return;
    	}
    	
    	try {
    		maxHours = Integer.parseInt(hours);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Error! The field of Max Hours must be a number!\n");
    		return;
    	}
    	
    	List<PowerOutage> list = this.model.findBestCombo(nerc, maxYears, maxHours);
    	this.txtResult.setText("Tot people affected: "+this.model.getMaxCustomers()+"\n");
    	DecimalFormat format = new DecimalFormat("0.00"); 
    	this.txtResult.appendText("Tot hours of power outage: "+format.format(this.model.getTotHours())+"\n");
    	for(PowerOutage po : list)
    		this.txtResult.appendText(po.toString()+"\n");
    	
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxNerc != null : "fx:id=\"boxNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.list.addAll(this.model.getNercList());
		this.boxNerc.setItems(list);
	}
}
