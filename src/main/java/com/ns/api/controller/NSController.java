package com.ns.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jaxb.ns.Options;
import com.jaxb.ns.ReisMogelijkheid;
import com.jaxb.ns.Root;
import com.jaxb.ns.Station;
import com.jaxb.ns.StationDetails;
import com.jaxb.ns.Stations;
import com.jaxb.ns.StopsDetails;
import com.jaxb.ns.TravelSection;

@RestController
@RequestMapping("/stations")
/**
 * 
 * @author sarigela
 *
 */
public class NSController {

	private Logger logger = LoggerFactory.getLogger(NSController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ArrayList<StationDetails> getStation() {
		logger.debug("Getting stations");
		return getStationsFromNS();
	}
	
	 @RequestMapping(value = "/transferDetails/{fromStation}/{toStation}",method = RequestMethod.GET)
	    @ResponseStatus(HttpStatus.OK)
	    public List<Options> getStationDetails(@PathVariable("fromStation") String fromStation,@PathVariable("toStation") String toStation) throws ClientProtocolException, IOException, JSONException {
	        return getStationDetailsFromNS(fromStation,toStation);
	    }

	private List<Options> getStationDetailsFromNS(String fromStation, String toStation) throws ClientProtocolException, IOException, JSONException {
		// TODO Auto-generated method stub

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		Credentials credentials = new UsernamePasswordCredentials(
				"suri243@gmail.com",
				"prDdOX81wFxHcyQIDdD77oNPD31TtPzRw01OsjZ-DKLG3KAY-MnpYQ");
		credentialsProvider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(credentialsProvider).build();
		// String url= "http://webservices.ns.nl/ns-api-stations-v2";
		String url = "http://webservices.ns.nl/ns-api-treinplanner?fromStation="+fromStation+"&toStation="+toStation+"&departure=true";
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String line = "";
		String textView = "";
		while ((line = rd.readLine()) != null) {

			textView = textView + line;
		}

		String xml = textView.replaceAll(">\\s+<", "><");

		System.out.println(xml);
		JSONObject xmlJSONObj = XML.toJSONObject(xml.trim());
		Gson gson = new Gson();
		String jsonUserIdentifier = xmlJSONObj.getString("ReisMogelijkheden");
		System.out.println(jsonUserIdentifier);
		Root mypojo = gson.fromJson(jsonUserIdentifier, Root.class);
		ArrayList<ReisMogelijkheid> reisMogelijkheidList = mypojo
				.getReisMogelijkheid();
		List<Options> optionsList = new ArrayList<Options>();
		for (ReisMogelijkheid reisMogelijkheid : reisMogelijkheidList) {
			Options options = new Options();
			options.setNrOfStops(reisMogelijkheid.getAantalOverstappen());// Nr
																			// Of
																			// stops
			options.setScheduleHrs(reisMogelijkheid.getGeplandeReisTijd());// Scheduled
																			// Hrs
			options.setActualHrs(reisMogelijkheid.getActueleReisTijd());// Actual
																		// Hrs
			options.setActualDepTime(reisMogelijkheid.getActueleVertrekTijd());// Actual
																				// Departure
																				// Time
			options.setActualArrivalTime(reisMogelijkheid
					.getActueleAankomstTijd());// Actual Arrival Time
			options.setStatus(reisMogelijkheid.getStatus());// Status
			List<TravelSection> travList = new ArrayList<TravelSection>();
			com.google.gson.internal.LinkedTreeMap listmap = null;
			try {
				listmap = (com.google.gson.internal.LinkedTreeMap) reisMogelijkheid
						.getReisDeel();
				TravelSection tr = new TravelSection();
				tr.setStatus((String) listmap.get("Status"));
				tr.setTripNr((String) listmap.get("RitNummer"));
				tr.setCarrier((String) listmap.get("Vervoerder"));
				// String reisSoort = (String) listmap.get("reisSoort");
				tr.setTransportationType((String) listmap.get("VervoerType"));

				List<StopsDetails> stopList = new ArrayList<StopsDetails>();
				if (listmap != null) {
					try {
						java.util.ArrayList<com.google.gson.internal.LinkedTreeMap> reisStopList = (java.util.ArrayList<com.google.gson.internal.LinkedTreeMap>) listmap
								.get("ReisStop");

						for (com.google.gson.internal.LinkedTreeMap reisStop : reisStopList) {
							StopsDetails stops = new StopsDetails();
							com.google.gson.internal.LinkedTreeMap spoor = (com.google.gson.internal.LinkedTreeMap) reisStop
									.get("Spoor");
							if (spoor != null)
								stops.setPlatForm((String) spoor.get("content"));
							stops.setStationName((String) reisStop.get("Naam"));
							stops.setTime((String) reisStop.get("Tijd"));
							stopList.add(stops);
						}
					} catch (ClassCastException e1) {
						com.google.gson.internal.LinkedTreeMap reisStopList = (com.google.gson.internal.LinkedTreeMap) listmap
								.get("ReisStop");
						System.out.println(reisStopList);
					}
				}
				tr.setStopsList(stopList);
				travList.add(tr);

			} catch (Exception e) {
				java.util.ArrayList list = (java.util.ArrayList) reisMogelijkheid
						.getReisDeel();
				for (Object object : list) {
					listmap = (com.google.gson.internal.LinkedTreeMap) object;
					TravelSection tr = new TravelSection();
					tr.setStatus((String) listmap.get("Status"));
					tr.setTripNr((String) listmap.get("RitNummer"));
					tr.setCarrier((String) listmap.get("Vervoerder"));
					// String reisSoort = (String) listmap.get("reisSoort");
					tr.setTransportationType((String) listmap
							.get("VervoerType"));
				//	travList.add(tr);
					List<StopsDetails> stopList = new ArrayList<StopsDetails>();

					if (listmap != null) {
						try {
							java.util.ArrayList<com.google.gson.internal.LinkedTreeMap> reisStopList = (java.util.ArrayList<com.google.gson.internal.LinkedTreeMap>) listmap
									.get("ReisStop");

							for (com.google.gson.internal.LinkedTreeMap reisStop : reisStopList) {
								StopsDetails stops = new StopsDetails();
								com.google.gson.internal.LinkedTreeMap spoor = (com.google.gson.internal.LinkedTreeMap) reisStop
										.get("Spoor");
								if (spoor != null)
									stops.setPlatForm((String) spoor
											.get("content"));
								stops.setStationName((String) reisStop
										.get("Naam"));
								stops.setTime((String) reisStop.get("Tijd"));
								stopList.add(stops);
							}
						} catch (ClassCastException e1) {
							com.google.gson.internal.LinkedTreeMap reisStopList = (com.google.gson.internal.LinkedTreeMap) listmap
									.get("ReisStop");
							System.out.println(reisStopList);
						}
					}
					tr.setStopsList(stopList);
					travList.add(tr);

				}

			}
			options.setTravelSectionList(travList);

			optionsList.add(options);
		}
		System.out.println(optionsList);
		return optionsList;
	
	}

	private ArrayList<StationDetails> getStationsFromNS() {

		JSONObject xmlJSONObj=null;
		try {
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			Credentials credentials = new UsernamePasswordCredentials(
					"suri243@gmail.com",
					"prDdOX81wFxHcyQIDdD77oNPD31TtPzRw01OsjZ-DKLG3KAY-MnpYQ");
			credentialsProvider.setCredentials(AuthScope.ANY, credentials);
			HttpClient client = HttpClientBuilder.create()
					.setDefaultCredentialsProvider(credentialsProvider).build();
			String url = "http://webservices.ns.nl/ns-api-stations-v2";
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			String textView = null;
			while ((line = rd.readLine()) != null) {

				textView = textView + line;
			}

			xmlJSONObj = XML.toJSONObject(textView);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String jsonUserIdentifier = xmlJSONObj.getString("Stations");
		Stations mypojo = gson.fromJson(jsonUserIdentifier ,Stations.class);
		ArrayList<Station> listStation=mypojo.getStation();
		ArrayList<StationDetails> stDetails=new ArrayList<StationDetails>();
		for (Station station : listStation) {
			StationDetails details=new StationDetails();
			details.setCode(station.getCode());
			details.setName(station.getNamen().getKort());
			stDetails.add(details);
		}
		

		return stDetails;

	}
}
