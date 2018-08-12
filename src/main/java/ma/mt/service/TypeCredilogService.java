package ma.mt.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.dao.TypeCredilogJpaRepository;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class TypeCredilogService.
 */
@Service
public class TypeCredilogService implements ITypeCredilogService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(TypeCredilogService.class);

	/** The type credilog jpa repository. */
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.ITypeCredilogService#deleteTypeCredilog(java.lang.Long)
	 */
	@Override
	public boolean deleteTypeCredilog(Long idTypeCredilog) {
		try {
			typeCredilogJpaRepository.delete(idTypeCredilog);
			LOG.trace("Element has deleted successfully!");
			return true;
		} catch (Exception e) {
			LOG.info("Element hasn't deleted successfully !" + e.getMessage());
			return false;
		}
	}

	/*
	 * (non-Javadoc)file
	 * 
	 * @see ma.mt.service.ITypeCredilogService#listTypeCredilog()
	 */
	@Override
	public List<TypeCredilog> listTypeCredilog() {
		try {
			return typeCredilogJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listTypeCredilog items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.ITypeCredilogService#uploadTypeCredilogPresentationFile(org.
	 * springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	@Override
	public boolean uploadTypeCredilogPresentationFile(MultipartFile file, String intituleTypeCredilog) {
		if (file == null || file.isEmpty()) {
			LOG.info("Null or empty file to upload");
			return false;
		}
		try {
			// get file name
			String name = file.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			LOG.info("File upload path: %s" + path);
			Files.copy(file.getInputStream(), path);
			TypeCredilog typeCredilog = new TypeCredilog();
			typeCredilog.setEntitledTypeCredilog(intituleTypeCredilog);
			typeCredilog.setFichePresentation(dateString + File.separator + name);
			typeCredilogJpaRepository.save(typeCredilog);
			LOG.trace("TypeCredilogService : file uploaded successfully");
		} catch (IOException e) {
			LOG.info("TypeCredilogService : Error uploading file");
			return false;
		}
		return true;
	}

	@Override
	public TypeCredilog findOne(Long idTypeCredilog) {
		return typeCredilogJpaRepository.findOne(idTypeCredilog);
	}

}
