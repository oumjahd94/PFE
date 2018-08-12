package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.EcheancierPrevisionnel;
import ma.mt.entity.TypeCredilog;

/**
 * The Interface IEcheancierPrevisionnelService.
 */
public interface IEcheancierPrevisionnelService {

	/**
	 * Delete echeancier previsionnel.
	 *
	 * @param idEcheancierPrevisionnel
	 *            the id echeancier previsionnel
	 * @return true, if successful
	 */
	boolean deleteEcheancierPrevisionnel(Long idEcheancierPrevisionnel);

	/**
	 * Adds the echeancier previsionnel.
	 *
	 * @param echeancierPrevisionnel
	 *            the echeancier previsionnel
	 * @return true, if successful
	 */
	boolean addEcheancierPrevisionnel(EcheancierPrevisionnel echeancierPrevisionnel);

	/**
	 * List echeancier previsionnel.
	 *
	 * @return the list
	 */
	List<EcheancierPrevisionnel> listEcheancierPrevisionnel();

	/**
	 * Creates the new echeancier previsionnel.
	 *
	 * @param partP1
	 *            the part P 1
	 * @param partP2
	 *            the part P 2
	 * @param partS
	 *            the part S
	 * @param datePublication
	 *            the date publication
	 * @param dateExpiration
	 *            the date expiration
	 * @param scenariosFondsDDTs
	 *            the scenarios fonds DD ts
	 * @param scenariosFondsRa
	 *            the scenarios fonds ra
	 * @param idTypeCredilogType
	 *            the id type credilog type
	 * @return true, if successful
	 */
	boolean createNewEcheancierPrevisionnel(MultipartFile partP1, MultipartFile partP2, MultipartFile partS,
			Date datePublication, Date dateExpiration, Long scenariosFondsDDTs, Long scenariosFondsRa,
			Long idTypeCredilogType);

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<EcheancierPrevisionnel> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Parses the string date.
	 *
	 * @param date the date
	 * @return the date
	 */
	Date parseStringDate(String date);

	/**
	 * Adds the fonds data echeancer.
	 *
	 * @param echeancierPrevisionnel the echeancier previsionnel
	 * @return the echeancier previsionnel
	 */
	EcheancierPrevisionnel addFondsDataEcheancer(EcheancierPrevisionnel echeancierPrevisionnel);

	/**
	 * Adds the new echeancer previsionnel.
	 *
	 * @param filePart1 the file part 1
	 * @param filePart2 the file part 2
	 * @param filePart3 the file part 3
	 * @param datePublication the date publication
	 * @param dateException the date exception
	 * @param scenarioRa the scenario ra
	 * @param scenarioDdt the scenario ddt
	 * @return true, if successful
	 */
	boolean addNewEcheancerPrevisionnel(MultipartFile filePart1, MultipartFile filePart2, MultipartFile filePart3,
			String datePublication, String dateException, String scenarioRa, String scenarioDdt);
}
