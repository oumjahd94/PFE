package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataCashflow;
import ma.mt.entity.TypeCredilog;

// TODO: Auto-generated Javadoc
/**
 * The Interface IFondsDataCashflowService.
 */
public interface IFondsDataCashflowService {

	/**
	 * Delete fonds data cashflow.
	 *
	 * @param idFondsDataCashFlow
	 *            the id fonds data cash flow
	 * @return true, if successful
	 */
	boolean deleteFondsDataCashflow(Long idFondsDataCashFlow);

	/**
	 * Adds the fonds data cash flow.
	 *
	 * @param fondsDataCashflow
	 *            the fonds data cashflow
	 * @return true, if successful
	 */

	/**
	 * List fonds data cashflows.
	 *
	 * @return the list
	 */
	List<FondsDataCashflow> listFondsDataCashflows();

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<FondsDataCashflow> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Parses the string date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	Date parseStringDate(String date);

	/**
	 * Adds the fonds data cashflow.
	 *
	 * @param fondsDataCashflow
	 *            the fonds data cashflow
	 * @return the fonds data cashflow
	 */
	FondsDataCashflow addFondsDataCashflow(FondsDataCashflow fondsDataCashflow);

	/**
	 * Adds the new cashflow.
	 *
	 * @param file
	 *            the file
	 * @param datePublication
	 *            the date publication
	 * @param dateExpiration
	 *            the date expiration
	 * @param scenariora
	 *            the scenariora
	 * @param scenarioddt
	 *            the scenarioddt
	 * @return true, if successful
	 */



	boolean addNewCashflow(MultipartFile file, String datePublication, String dateExpiration, String scenariora,
			String scenarioddt);

}
