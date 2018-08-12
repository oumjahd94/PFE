package ma.mt.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.entity.ScenariosFondsDDT;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IScenariosFondsDDTService;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;
/*
 * Hassan
 */
/**
 * The Class ScenariosFondsDDTRestController.
 */
@RestController
@RequestMapping(value = "/api/scenariosFondsDDT/")
public class ScenariosFondsDDTRestController {

	/** The scenarios fonds DDT service. */
	@Autowired
	private IScenariosFondsDDTService scenariosFondsDDTService;

	/** The type credilog service. */
	@Autowired
	private ITypeCredilogService typeCredilogService;

	/**
	 * Gets the by type credilog.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the by type credilog
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	List<ScenariosFondsDDT> getByTypeCredilog(
			@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		
		List<ScenariosFondsDDT> scenarioFondsDdts = new ArrayList<>();
		
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);  
		
		for (ScenariosFondsDDT scenarioDdt : scenariosFondsDDTService.getByTypeCredilog(typeCredilog)) {
			if (!scenarioDdt.isFlagDeleted()) {
				scenarioFondsDdts.add(scenarioDdt);
			}
		}
		
		return scenarioFondsDdts;
	}
	
	/**
	 * Delete scenarios fonds DDT.
	 *
	 * @param idScenariosFondsDDT
	 *            the id scenarios fonds DDT
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idScenarioFondsDDT}", method = RequestMethod.DELETE)
	public boolean deleteScenariosFondsDDT(@PathVariable(name = "idScenarioFondsDDT") Long idScenariosFondsDDT) {
		return scenariosFondsDDTService.deleteScenariosFondsDDT(idScenariosFondsDDT);
	}

	/**
	 * Adds the scenarios fonds DDT.
	 *
	 * @param scenariosFondsDDT
	 *            the scenarios fonds DDT
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.POST)
	public boolean addScenariosFondsDDT(@RequestBody ScenariosFondsDDT scenariosFondsDDT) {
		return scenariosFondsDDTService.addScenariosFondsDDT(scenariosFondsDDT);
	}

	/**
	 * List scenarios fonds DDT.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ScenariosFondsDDT> listScenarioFondsRa() {
		List<ScenariosFondsDDT> scenarioFondsDdts = new ArrayList<>();
		for (ScenariosFondsDDT scenarioDdt : scenariosFondsDDTService.listScenariosFondsDDT()) {
			if (!scenarioDdt.isFlagDeleted()) {
				scenarioFondsDdts.add(scenarioDdt);
			}
		}
		return scenarioFondsDdts;
	}

	/**
	 * Adds the scenario ra.
	 *
	 * @param dateCreation
	 *            the date creation
	 * @param dateExpiration
	 *            the date expiration
	 * @param defaultScenarioDdt
	 *            the default scenario ddt
	 * @param ScenarioDdt2
	 *            the scenario ddt 2
	 * @param ScenarioDdt3
	 *            the scenario ddt 3
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/scenario", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addScenarioDdt(@RequestParam("dateCreationDdt") String dateCreationDdt,
			@RequestParam("DateExpirationDdt") String DateExpirationDdt,
			@RequestParam("defaultScenarioDdt") String defaultScenarioDdt,
			@RequestParam("ScenarioDdt2") String ScenarioDdt2, @RequestParam("ScenarioDdt3") String ScenarioDdt3) {
		// parse dates
		Date dateCreat = scenariosFondsDDTService.parseStringDate(dateCreationDdt);
		Date dateExpirtion = scenariosFondsDDTService.parseStringDate(DateExpirationDdt);
		// get fonds Sakane object
		TypeCredilog fonds = typeCredilogService.findOne(Constants.idFondsSakane);
		// Create default scenario Ra
		ScenariosFondsDDT defaultScenarioDdtObject = new ScenariosFondsDDT();
		defaultScenarioDdtObject.setDateCreationDdt(dateCreat);
		defaultScenarioDdtObject.setDateExpirationDdt(dateExpirtion);
		defaultScenarioDdtObject.setDefaultTx(true);
		defaultScenarioDdtObject.setFlagDeleted(false);
		defaultScenarioDdtObject.setTypeCredilog(fonds);
		defaultScenarioDdtObject.setValueScenario(Double.parseDouble(defaultScenarioDdt));
		scenariosFondsDDTService.addScenarioFondsDdt(defaultScenarioDdtObject);

		// Create scenario Ddt 1 object
		ScenariosFondsDDT ScenarioDdtObject1 = new ScenariosFondsDDT();
		ScenarioDdtObject1.setDateCreationDdt(dateCreat);
		ScenarioDdtObject1.setDateExpirationDdt(dateExpirtion);
		ScenarioDdtObject1.setDefaultTx(false);
		ScenarioDdtObject1.setTypeCredilog(fonds);
		ScenarioDdtObject1.setFlagDeleted(false);
		ScenarioDdtObject1.setValueScenario(Double.parseDouble(ScenarioDdt2));
		scenariosFondsDDTService.addScenarioFondsDdt(ScenarioDdtObject1);
		// Create second scenario DDt 2 object
		ScenariosFondsDDT ScenarioDdtObject2 = new ScenariosFondsDDT();
		ScenarioDdtObject2.setDateCreationDdt(dateCreat);
		ScenarioDdtObject2.setDateExpirationDdt(dateExpirtion);
		ScenarioDdtObject2.setDefaultTx(false);
		ScenarioDdtObject2.setFlagDeleted(false);
		ScenarioDdtObject2.setTypeCredilog(fonds);
		ScenarioDdtObject2.setValueScenario(Double.parseDouble(ScenarioDdt3));
		scenariosFondsDDTService.addScenarioFondsDdt(ScenarioDdtObject2);
		return true;
	}
}
