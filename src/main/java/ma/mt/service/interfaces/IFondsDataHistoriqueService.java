package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataHistorique;
import ma.mt.entity.TypeCredilog;

/**
 * The Interface IFondsDataHistoriqueService.
 */
public interface IFondsDataHistoriqueService {

	/**
	 * Delete fonds data historique.
	 *
	 * @param idFondsDataHistorique
	 *            the id fonds data historique
	 * @return true, if successful
	 */
	boolean deleteFondsDataHistorique(Long idFondsDataHistorique);

	/**
	 * List fonds data historique.
	 *
	 * @return the list
	 */
	List<FondsDataHistorique> listFondsDataHistorique();

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<FondsDataHistorique> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Adds the fonds historique.
	 *
	 * @param fondsDataHistoriqueFile the fonds data historique file
	 * @param datePublication the date publication
	 * @param dateExpiration the date expiration
	 * @param idTypeCredilog the id type credilog
	 * @return true, if successful
	 */
	boolean addFondsHistorique(MultipartFile fondsDataHistoriqueFile, Date datePublication, Date dateExpiration,
			Long idTypeCredilog);
	Date parseStringDate(String date);
	FondsDataHistorique addFondsDataHistorique(FondsDataHistorique fondsDataHistorique);
 } 