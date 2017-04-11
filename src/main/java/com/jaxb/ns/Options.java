package com.jaxb.ns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 
 * @author sarigela
 *
 */
public class Options {

	public String getNrOfStops() {
		return nrOfStops;
	}
	public void setNrOfStops(String nrOfStops) {
		this.nrOfStops = nrOfStops;
	}
	public String getScheduleHrs() {
		return scheduleHrs;
	}
	public void setScheduleHrs(String scheduleHrs) {
		this.scheduleHrs = scheduleHrs;
	}
	public String getActualHrs() {
		return actualHrs;
	}
	public void setActualHrs(String actualHrs) {
		this.actualHrs = actualHrs;
	}
	public Date getActualDepTime() {
		return actualDepTime;
	}
	public void setActualDepTime(Date actualDepTime) {
		this.actualDepTime = actualDepTime;
	}
	public Date getActualArrivalTime() {
		return actualArrivalTime;
	}
	public void setActualArrivalTime(Date actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TravelSection> getTravelSectionList() {
		return travelSectionList;
	}
	public void setTravelSectionList(List<TravelSection> travelSectionList) {
		this.travelSectionList = travelSectionList;
	}
	private String nrOfStops;
	private String scheduleHrs;
	private String actualHrs;
	private Date actualDepTime;
	private Date actualArrivalTime;
	private String status;
	private List<TravelSection> travelSectionList=new ArrayList<TravelSection>();
}
