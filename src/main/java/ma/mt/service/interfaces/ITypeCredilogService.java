package ma.mt.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.TypeCredilog;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITypeCredilogService.
 */
public interface ITypeCredilogService {
	
	/**
	 * Delete type credilog.
	 *
	 * @param idTypeCredilog the id type credilog
	 * @return true, if successful
	 */
	boolean deleteTypeCredilog(Long idTypeCredilog);
	/**
	 * List type credilog.
	 *
	 * @return the list
	 */
	List<TypeCredilog> listTypeCredilog();
	
	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @param intituleTypeCredilog the intitule type credilog
	 * @return true, if successful
	 */
	boolean uploadTypeCredilogPresentationFile(MultipartFile file, String intituleTypeCredilog);
	
	/**
	 * Find one.
	 *
	 * @param idTypeCredilog the id type credilog
	 * @return the type credilog
	 */
	TypeCredilog findOne(Long idTypeCredilog);
}
