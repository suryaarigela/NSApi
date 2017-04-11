package com.jaxb.ns;

import java.util.Date;
/**
 * 
 * @author sarigela
 *
 */
public class ReisMogelijkheid
{
	  private String Status;

	  public String getStatus() { return this.Status; }

	  public void setStatus(String Status) { this.Status = Status; }

	  private Object ReisDeel;

	  public Object getReisDeel() { return this.ReisDeel; }

	  public void setReisDeel(Object ReisDeel) { this.ReisDeel = ReisDeel; }

	  private String GeplandeReisTijd;

	  public String getGeplandeReisTijd() { return this.GeplandeReisTijd; }

	  public void setGeplandeReisTijd(String GeplandeReisTijd) { this.GeplandeReisTijd = GeplandeReisTijd; }

	  private String AantalOverstappen;

	  public String getAantalOverstappen() { return this.AantalOverstappen; }

	  public void setAantalOverstappen(String AantalOverstappen) { this.AantalOverstappen = AantalOverstappen; }

	  private String AankomstVertraging;

	  public String getAankomstVertraging() { return this.AankomstVertraging; }

	  public void setAankomstVertraging(String AankomstVertraging) { this.AankomstVertraging = AankomstVertraging; }

	  private String Optimaal;

	  public String getOptimaal() { return this.Optimaal; }

	  public void setOptimaal(String Optimaal) { this.Optimaal = Optimaal; }

	  private Date GeplandeAankomstTijd;

	  public Date getGeplandeAankomstTijd() { return this.GeplandeAankomstTijd; }

	  public void setGeplandeAankomstTijd(Date GeplandeAankomstTijd) { this.GeplandeAankomstTijd = GeplandeAankomstTijd; }

	  private Date ActueleAankomstTijd;

	  public Date getActueleAankomstTijd() { return this.ActueleAankomstTijd; }

	  public void setActueleAankomstTijd(Date ActueleAankomstTijd) { this.ActueleAankomstTijd = ActueleAankomstTijd; }

	  private Date GeplandeVertrekTijd;

	  public Date getGeplandeVertrekTijd() { return this.GeplandeVertrekTijd; }

	  public void setGeplandeVertrekTijd(Date GeplandeVertrekTijd) { this.GeplandeVertrekTijd = GeplandeVertrekTijd; }

	  private String ActueleReisTijd;

	  public String getActueleReisTijd() { return this.ActueleReisTijd; }

	  public void setActueleReisTijd(String ActueleReisTijd) { this.ActueleReisTijd = ActueleReisTijd; }

	  private Date ActueleVertrekTijd;

	  public Date getActueleVertrekTijd() { return this.ActueleVertrekTijd; }

	  public void setActueleVertrekTijd(Date ActueleVertrekTijd) { this.ActueleVertrekTijd = ActueleVertrekTijd; }
	}
