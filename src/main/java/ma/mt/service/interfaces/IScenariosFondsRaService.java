package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import ma.mt.entity.ScenariosFondsRA;
import ma.mt.entity.TypeCredilog;

/**
 * The Interface IScenariosFondsRaService.
 */
public interface IScenariosFondsRaService {

	/**
	 * Delete scenario fonds ra.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return true, if successful
	 */
	boolean deleteScenarioFondsRa(Long idTypeCredilog);

	/**
	 * Adds the scenario fonds ra.
	 *
	 * @param scenariosFondsRA
	 *            the scenarios fonds RA
	 * @return true, if successful
	 */
	boolean addScenarioFondsRa(ScenariosFondsRA scenariosFondsRA);

	/**
	 * List scenario fonds ra.
	 *
	 * @return the list
	 */
	List<ScenariosFondsRA> listScenarioFondsRa();

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<ScenariosFondsRA> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Delete scenarios fonds RA.
	 *
	 * @param idScenariosFondsRA the id scenarios fonds RA
	 * @return true, if successful
	 */
	boolean deleteScenariosFondsRA(Long idScenariosFondsRA);

	/**
	 * Find scenario ra by id.
	 *
	 * @param idScenarioRa the id scenario ra
	 * @return the scenarios fonds RA
	 */
	ScenariosFondsRA findScenarioRaById(Long idScenarioRa);
	Date parseStringDate(String date);
}
