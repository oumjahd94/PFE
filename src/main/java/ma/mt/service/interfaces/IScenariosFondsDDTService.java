package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import ma.mt.entity.ScenariosFondsDDT;
import ma.mt.entity.TypeCredilog;

// TODO: Auto-generated Javadoc
/**
 * The Interface IScenariosFondsDDTService.
 */
public interface IScenariosFondsDDTService {

	/**
	 * Delete scenarios fonds DDT.
	 *
	 * @param idScenariosFondsDDT
	 *            the id scenarios fonds DDT
	 * @return true, if successful
	 */
	boolean deleteScenariosFondsDDT(Long idScenariosFondsDDT);

	/**
	 * Adds the scenarios fonds DDT.
	 *
	 * @param scenariosFondsDDT
	 *            the scenarios fonds DDT
	 * @return true, if successful
	 */
	boolean addScenariosFondsDDT(ScenariosFondsDDT scenariosFondsDDT);

	/**
	 * List scenarios fonds DDT.
	 *
	 * @return the list
	 */
	List<ScenariosFondsDDT> listScenariosFondsDDT();

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<ScenariosFondsDDT> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Find scenario ddt by id.
	 *
	 * @param idScenarioDdt the id scenario ddt
	 * @return the scenarios fonds DDT
	 */
	ScenariosFondsDDT findScenarioDdtById(Long idScenarioDdt);

	/**
	 * Parses the string date.
	 *
	 * @param date the date
	 * @return the date
	 */
	Date parseStringDate(String date);

	/**
	 * Adds the scenario fonds ddt.
	 *
	 * @param scenariosFondsDDT the scenarios fonds DDT
	 * @return true, if successful
	 */
	boolean addScenarioFondsDdt(ScenariosFondsDDT scenariosFondsDDT);
}
