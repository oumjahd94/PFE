package ma.mt.transverse;

import java.util.Date;

public class ScenarioRaDTO { 
	
	private Date datePublication;
	private Date dateExpiration;
	private Double defaultScenarioRa;
	private Double scenarioRa1;
	private Double scenarioRa2;

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}  

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Double getDefaultScenarioRa() {
		return defaultScenarioRa;
	}

	public void setDefaultScenarioRa(Double defaultScenarioRa) {
		this.defaultScenarioRa = defaultScenarioRa;
	}

	public Double getScenarioRa1() {
		return scenarioRa1;
	}

	public void setScenarioRa1(Double scenarioRa1) {
		this.scenarioRa1 = scenarioRa1;
	}

	public Double getScenarioRa2() {
		return scenarioRa2;
	}

	public void setScenarioRa2(Double scenarioRa2) {
		this.scenarioRa2 = scenarioRa2;
	}

	public ScenarioRaDTO() {
		super();
	}
	
}
