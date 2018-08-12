package ma.mt.service.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataSnapshot;
import ma.mt.entity.TypeCredilog;

// TODO: Auto-generated Javadoc
/**
 * The Interface IFondsDataSnapshotService.
 */
public interface IFondsDataSnapshotService {

	/**
	 * Delete fonds data snapshot.
	 *
	 * @param idFondsDataSnapshot
	 *            the id fonds data snapshot
	 * @return true, if successful
	 */
	boolean deleteFondsDataSnapshot(Long idFondsDataSnapshot);

	/**
	 * Adds the fonds data snapshot.
	 *
	 * @param snapshotFile
	 *            the snapshot file
	 * @param datePublication
	 *            the date publication
	 * @param dateExpiration
	 *            the date expiration
	 * @param arrete
	 *            the arrete
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return true, if successful
	 */
	boolean addFondsDataSnapshot(MultipartFile snapshotFile, Date datePublication, Date dateExpiration, Date arrete,
			Long idTypeCredilog);

	/**
	 * List fonds data snapshot.
	 *
	 * @return the list
	 */
	List<FondsDataSnapshot> listFondsDataSnapshot();

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	List<FondsDataSnapshot> getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Parses the string date.
	 *
	 * @param date the date
	 * @return the date
	 */
	Date parseStringDate(String date);
	
	/**
	 * Adds the fonds data snapshot.
	 *
	 * @param fondsDataSnapshot the fonds data snapshot
	 * @return true, if successful
	 */
	FondsDataSnapshot addFondsDataSnapshot(FondsDataSnapshot fondsDataSnapshot);
}
