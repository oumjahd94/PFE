package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataPricing;
import ma.mt.entity.TypeCredilog;

/**
 * The Interface IFondsDataPricingService.
 */
public interface IFondsDataPricingService {

	/**
	 * Delete fonds data pricing.
	 *
	 * @param idFondsDataPricing
	 *            the id fonds data pricing
	 * @return true, if successful
	 */
	boolean deleteFondsDataPricing(Long idFondsDataPricing);

	/**
	 * Adds the fonds data pricing.
	 *
	 * @param fondsDataPricing
	 *            the fonds data pricing
	 * @return true, if successful
	 */
	boolean addFondsDataPricing(FondsDataPricing fondsDataPricing);

	/**
	 * List fonds data pricing.
	 *
	 * @return the list
	 */
	List<FondsDataPricing> listFondsDataPricing();

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<FondsDataPricing> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Adds the pricing fonds.
	 *
	 * @param PricingFondsFile
	 *            the pricing fonds file
	 * @param datePublication
	 *            the date publication
	 * @param dateExpiration
	 *            the date expiration
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return true, if successful
	 */
	boolean addPricingFonds(MultipartFile PricingFondsFile, Date datePublication, Date dateExpiration,
			Long idTypeCredilog);


	Date parseStringDate(String date);
}
