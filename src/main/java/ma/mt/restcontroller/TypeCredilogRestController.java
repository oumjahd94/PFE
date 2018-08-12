package ma.mt.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.FichierTraitement;
/*
 * Hassan
 */
/**
 * The Class TypeCredilogRestController.
 */
@RequestMapping(value = "/api/typecredilogs")
@RestController
public class TypeCredilogRestController {
	/** The type credilog service. */
	@Autowired
	private ITypeCredilogService typeCredilogService;

	/**
	 * Delete type credilog.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "{idTypeCredilog}", method = RequestMethod.DELETE)
	public boolean deleteTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		return typeCredilogService.deleteTypeCredilog(idTypeCredilog);
	}

	/**
	 * List type credilog.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<TypeCredilog> listTypeCredilog() {
		return typeCredilogService.listTypeCredilog();
	}

	/**
	 * Handle upload.
	 *
	 * @param uploadedFile
	 *            the uploaded file
	 * @param intituleTypeCredilog
	 *            the intitule type credilog
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/typeCredilog", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean handleUploadTypeCredilogPresentationFile(@RequestParam("uploadedFile") MultipartFile uploadedFile,
			@RequestParam("intituleTypeCredilog") String intituleTypeCredilog) {
		return typeCredilogService.uploadTypeCredilogPresentationFile(uploadedFile, intituleTypeCredilog);
	}

	// get presentation file of typeCredilog
	public void showPresentationTypeCredilogFile(String fichePresentationTypeCredilogFile) {
		String fichePresentationTYpeCredilogFile = System.getProperty("java.io.tmpdir") + "/"
				+ fichePresentationTypeCredilogFile;
		if (FichierTraitement.existe(fichePresentationTYpeCredilogFile)) {
			
		}
	}

}
