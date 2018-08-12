package ma.mt.restcontroller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataHistorique;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataHistoriqueService;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;
import ma.mt.util.FileProcess;
/*
 * Hassan
 */
/**
 * The Class FondsDataHistoriqueRestController.
 */
@RequestMapping(value = "/api/fondsDataHistoriques")
@RestController
public class FondsDataHistoriqueRestController {
	/** The FondsDataHistorique service. */
	@Autowired
	private IFondsDataHistoriqueService fondsDataHistoriqueService;

	/** The type credilog service. */
	@Autowired
	private ITypeCredilogService typeCredilogService;

	/**
	 * Gets the by type credilog.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the by type credilog
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	List<FondsDataHistorique> getByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);
		return fondsDataHistoriqueService.getByTypeCredilog(typeCredilog);
	}

	/**
	 * Delete fonds data historique.
	 *
	 * @param idFondsDataHistorique
	 *            the id fonds data historique
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idFondsDataHistorique}", method = RequestMethod.DELETE)
	public boolean deleteFondsDataHistorique(@PathVariable(name = "idFondsDataHistorique") Long idFondsDataHistorique) {
		return fondsDataHistoriqueService.deleteFondsDataHistorique(idFondsDataHistorique);
	}
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataHistorique> listFondsDataHistorique() {
		return fondsDataHistoriqueService.listFondsDataHistorique();
	}
	@CrossOrigin
	@RequestMapping(value = "/download/{documentName}", method = RequestMethod.GET)
	public Response downloadDocument(@PathVariable(name = "documentName") String documentName) {
		String uploadPathFile = Constants.uploadPathFile + File.separator + "fonds" + File.separator + "snapshot"
				+ File.separator + documentName;
		if (FileProcess.exist(uploadPathFile)) {
			File file = new File(uploadPathFile);
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=" + documentName);
			return response.build();
		}
		return null;

	}
	/**
	 * Adds the fonds data snapshot.
	 *
	 * @param fondPricingFile the fond pricing file
	 * @param datePublication the date publication
	 * @param dateExpiration the date expiration
	 * @param idTypeCredilog the id type credilog
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addFondsDataHistorique(@RequestParam("fondHistoriqueFile") MultipartFile fondHistoriqueFile,
			@RequestParam("datePublication") Date datePublication, @RequestParam("dateExpiration") Date dateExpiration,
			@RequestParam("idTypeCredilog") Long idTypeCredilog) {
		return fondsDataHistoriqueService.addFondsHistorique(fondHistoriqueFile, datePublication, dateExpiration,
				idTypeCredilog);
	}  
	
	@CrossOrigin
	@RequestMapping(value = "/historique", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean handleFileUpload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("datePublication") String datePublication,
			@RequestParam("dateExpiration") String dateExpiration) {
		String message;	
		Date datePubltion = fondsDataHistoriqueService.parseStringDate(datePublication);
		Date dateExp = fondsDataHistoriqueService.parseStringDate(dateExpiration);
		
		try {
			String name = file.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/historique");
			File repertoireSnapshot = new File(tmpDirectory + "/" + "fonds/historique" + "/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireSnapshot.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/historique");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			Files.copy(file.getInputStream(), path);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			FondsDataHistorique fondsDataHistorique = new FondsDataHistorique(); 
			
			fondsDataHistorique.setDateExpiration(dateExp);
			fondsDataHistorique.setDatePublication(datePubltion);
			fondsDataHistorique.setFicheHistorique(dateString + File.separator + name);
			TypeCredilog fonds = typeCredilogService.findOne(Constants.idFondsSakane);
			fondsDataHistorique.setTypeCredilog(fonds);
			fondsDataHistoriqueService.addFondsDataHistorique(fondsDataHistorique);
			return true; 
			
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return false;
		}
	}
}
