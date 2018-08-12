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

import ma.mt.entity.ScenariosFondsRA;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IScenariosFondsRaService;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;
/*
 * Hassan
 */
/**
 * The Class ScenariosFondsRaRestController.
 */
@RestController
@RequestMapping(value = "/api/scenariosFondsRa/")
public class ScenariosFondsRaRestController {

	/** The scenarios fonds ra service. */
	@Autowired
	private IScenariosFondsRaService scenariosFondsRaService;

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
	List<ScenariosFondsRA> getByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
	
		List<ScenariosFondsRA> scenarioFondsRas = new ArrayList<>();
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);
		
		for (ScenariosFondsRA scenarioRa : scenariosFondsRaService.getByTypeCredilog(typeCredilog)) {
			if (!scenarioRa.isFlagDeleted()) {
				scenarioFondsRas.add(scenarioRa);
			}
		}
		return scenarioFondsRas;
	}

	/**
	 * Delete scenario fonds ra.
	 *
	 * @param idScenarioFondsRa
	 *            the id scenario fonds ra
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idScenarioFondsRa}", method = RequestMethod.DELETE)
	public boolean deleteScenarioFondsRa(@PathVariable(name = "idScenarioFondsRa") Long idScenarioFondsRa) {
		try {
			return scenariosFondsRaService.deleteScenariosFondsRA(idScenarioFondsRa);
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Adds the scenario fonds ra.
	 *
	 * @param scenariosFondsRA
	 *            the scenarios fonds RA
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.POST)
	public boolean addScenarioFondsRa(@RequestBody ScenariosFondsRA scenariosFondsRA) {
		return scenariosFondsRaService.addScenarioFondsRa(scenariosFondsRA);
	}

	/**
	 * List scenario fonds ra.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ScenariosFondsRA> listScenarioFondsRa() {
		List<ScenariosFondsRA> scenarioFondsRas = new ArrayList<>();
		for (ScenariosFondsRA scenarioRa : scenariosFondsRaService.listScenarioFondsRa()) {
			if (!scenarioRa.isFlagDeleted()) {
				scenarioFondsRas.add(scenarioRa);
			}
		}
		return scenarioFondsRas;
	}

	@CrossOrigin
	@RequestMapping(value = "/scenario", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addScenarioRa(
			
			@RequestParam("dateCreation") String dateCreation,
			@RequestParam("dateExpiration") String dateExpiration,
			@RequestParam("defaultScenarioRa") String defaultScenarioRa,
			@RequestParam("ScenarioRa2") String ScenarioRa2,
			@RequestParam("ScenarioRa3") String ScenarioRa3) {
		
		     Date dateCreat = scenariosFondsRaService.parseStringDate(dateCreation);
		     Date dateExpirtion = scenariosFondsRaService.parseStringDate(dateExpiration);
		
		     // get fonds Sakane object
	    	TypeCredilog fonds = typeCredilogService.findOne(Constants.idFondsSakane);
	    	
		// Create default scenario Ra
		ScenariosFondsRA defaultScenarioRaObject = new ScenariosFondsRA(); 
		
		defaultScenarioRaObject.setDateCreation(dateCreat);
		defaultScenarioRaObject.setDateExpiration(dateExpirtion);
		defaultScenarioRaObject.setDefaultTx(true);
		defaultScenarioRaObject.setFlagDeleted(false);
		defaultScenarioRaObject.setTypeCredilog(fonds);
		defaultScenarioRaObject.setValueScenarioFondsRa(Double.parseDouble(defaultScenarioRa)); 
		
		scenariosFondsRaService.addScenarioFondsRa(defaultScenarioRaObject);

		// Create scenario Ra 1 object
		ScenariosFondsRA defaultScenarioRaObject1 = new ScenariosFondsRA();
		defaultScenarioRaObject1.setDateCreation(dateCreat);
		defaultScenarioRaObject1.setDateExpiration(dateExpirtion);
		defaultScenarioRaObject1.setDefaultTx(false);
		defaultScenarioRaObject1.setTypeCredilog(fonds);
		defaultScenarioRaObject1.setFlagDeleted(false);
		defaultScenarioRaObject1.setValueScenarioFondsRa(Double.parseDouble(ScenarioRa2));
		scenariosFondsRaService.addScenarioFondsRa(defaultScenarioRaObject1);
		
		
		// Create second scenario Ra 2 object
		ScenariosFondsRA defaultScenarioRaObject2 = new ScenariosFondsRA();
		defaultScenarioRaObject2.setDateCreation(dateCreat);
		defaultScenarioRaObject2.setDateExpiration(dateExpirtion);
		defaultScenarioRaObject2.setDefaultTx(false);
		defaultScenarioRaObject2.setFlagDeleted(false);
		defaultScenarioRaObject2.setTypeCredilog(fonds);
		defaultScenarioRaObject2.setValueScenarioFondsRa(Double.parseDouble(ScenarioRa3));
		scenariosFondsRaService.addScenarioFondsRa(defaultScenarioRaObject2);
		return true;
	}

}
