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

import ma.mt.entity.FondsDataCollateralPerformance;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataCollateralPerfService;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;
import ma.mt.util.FileProcess;
/*
 * Hassan
 */
/**
 * The Class FondsDataCollateralRestController.
 */
@RestController
@RequestMapping(value = "/api/fondsDataCollateralPerfs")
public class FondsDataCollateralRestController {
	/** The FondsDataHistorique service. */
	@Autowired
	private IFondsDataCollateralPerfService fondsDataCollateralPerfService;

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
	List<FondsDataCollateralPerformance> getByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);
		return fondsDataCollateralPerfService.getByTypeCredilog(typeCredilog);
	}
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataCollateralPerformance> listFondsDataPerformance() {
		return fondsDataCollateralPerfService.listFondsDataCollateralPerformance();
	}
	/**
	 * Delete fonds data historique.
	 *
	 * @param idFondsDataCollateralPerformance the id fonds data collateral performance
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idFondsDataCollateralPerformance}", method = RequestMethod.DELETE)
	public boolean deleteFondsDataHistorique(
			@PathVariable(name = "idFondsDataCollateralPerformance") Long idFondsDataCollateralPerformance) {
		return fondsDataCollateralPerfService.deleteFondsDataCollateralPerf(idFondsDataCollateralPerformance);
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
	 * @param fondCollateralPerfFile the fond collateral perf file
	 * @param datePublication the date publication
	 * @param dateExpiration the date expiration
	 * @param idTypeCredilog the id type credilog
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addFondsDataSnapshot(
			@RequestParam("fondCollateralPerfFile") MultipartFile fondCollateralPerfFile,
			@RequestParam("datePublication") Date datePublication,
			@RequestParam("dateExpiration") Date dateExpiration,
			@RequestParam("idTypeCredilog") Long idTypeCredilog) {
		return fondsDataCollateralPerfService.addFondsDataSnapshotCollateralPerf(fondCollateralPerfFile,
				datePublication, dateExpiration, idTypeCredilog);
	}
	@CrossOrigin
	@RequestMapping(value = "/performance", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean handleFileUpload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("datePublication") String datePublication,
			@RequestParam("dateExpiration") String dateExpiration) {
		
		String message;
		Date datePubltion = fondsDataCollateralPerfService.parseStringDate(datePublication);
		Date dateExp = fondsDataCollateralPerfService.parseStringDate(dateExpiration); 
		
		try {
			String name = file.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/performance");
			File repertoireSnapshot = new File(tmpDirectory + "/" + "fonds/performance" + "/" + dateString);
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireSnapshot.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/performance");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			Files.copy(file.getInputStream(), path);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			FondsDataCollateralPerformance fondsDataCollateralPerformance = new FondsDataCollateralPerformance();
			fondsDataCollateralPerformance.setDateExpiration(dateExp);
			fondsDataCollateralPerformance.setDatePublication(datePubltion);
			fondsDataCollateralPerformance.setFicheCollateral(dateString + File.separator + name);
			TypeCredilog fonds = typeCredilogService.findOne(Constants.idFondsSakane);
			fondsDataCollateralPerformance.setTypeCredilog(fonds);
			fondsDataCollateralPerfService.addFondsDataSnapshotCollateralPerf(fondsDataCollateralPerformance);
			return true;
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return false;
		}
	}
}
