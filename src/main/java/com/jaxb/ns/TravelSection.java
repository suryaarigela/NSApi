package com.jaxb.ns;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author sarigela
 *
 */
public class TravelSection {
	
	private String carrier;
	private String transportationType;
	private String tripNr;
	private String status;
	private List<StopsDetails> stopsList=new ArrayList<StopsDetails>();
	
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getTransportationType() {
		return transportationType;
	}
	public void setTransportationType(String transportationType) {
		this.transportationType = transportationType;
	}
	public String getTripNr() {
		return tripNr;
	}
	public void setTripNr(String tripNr) {
		this.tripNr = tripNr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<StopsDetails> getStopsList() {
		return stopsList;
	}
	public void setStopsList(List<StopsDetails> stopsList) {
		this.stopsList = stopsList;
	}


}
