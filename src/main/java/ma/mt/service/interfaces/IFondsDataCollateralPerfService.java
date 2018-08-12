package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataCollateralPerformance;
import ma.mt.entity.TypeCredilog;

/**
 * The Interface IFondsDataCollateralPerfService.
 */
public interface IFondsDataCollateralPerfService {
	/**
	 * Delete fonds data collateral perf.
	 *
	 * @param idFondsDataCollateralPerf
	 *            the id fonds data collateral
	 * @return true, if successful
	 */
	boolean deleteFondsDataCollateralPerf(Long idFondsDataCollateralPerf);

	/**
	 * List fonds data perfo.
	 *
	 * @return the list
	 */
	List<FondsDataCollateralPerformance> listFondsDataCollateralPerformance();

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<FondsDataCollateralPerformance> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Adds the fonds data snapshot collateral perf.
	 *
	 * @param fondsDataCollateralPerfFile
	 *            the fonds data collateral perf file
	 * @param datePublication
	 *            the date publication
	 * @param dateExpiration
	 *            the date expiration
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return true, if successful
	 */
	boolean addFondsDataSnapshotCollateralPerf(MultipartFile fondsDataCollateralPerfFile, Date datePublication,
			Date dateExpiration, Long idTypeCredilog);
	
	FondsDataCollateralPerformance addFondsDataSnapshotCollateralPerf(FondsDataCollateralPerformance fondsDataCollateralPerf);
	Date parseStringDate(String date);
}
