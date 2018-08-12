package ma.mt.restcontroller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.entity.FondsDataPricing;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataPricingService;
import ma.mt.service.interfaces.ITypeCredilogService;
import ma.mt.util.Constants;
/*
 * Hassan
 */
/**
 * The Class FondsDataPricingRestController.
 */
@RequestMapping(value = "/api/fondsDataPricings/")
@RestController
public class FondsDataPricingRestController {

	/** The fonds data pricing service. */
	@Autowired
	private IFondsDataPricingService fondsDataPricingService;

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
	@RequestMapping(value = "{idTypeCredilog}", method = RequestMethod.GET)
	List<FondsDataPricing> getByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);
		return fondsDataPricingService.getByTypeCredilog(typeCredilog);
	}

	/**
	 * Delete fonds data pricing.
	 *
	 * @param idFondsDataPricing the id fonds data pricing
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "{idFondsDataPricing}", method = RequestMethod.DELETE)
	public boolean deleteFondsDataPricing(@PathVariable(name = "idFondsDataPricing") Long idFondsDataPricing) {
		return fondsDataPricingService.deleteFondsDataPricing(idFondsDataPricing);
	}

	/**
	 * Adds the fonds data historique.
	 *
	 * @param fondPricingFile the fond pricing file
	 * @param datePublication the date publication
	 * @param dateExpiration the date expiration
	 * @param idTypeCredilog the id type credilog
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addFondsDataPricing(
			@RequestParam("fondPricingFile") MultipartFile fondPricingFile,
			@RequestParam("datePublication") Date datePublication,
			@RequestParam("dateExpiration") Date dateExpiration,
			@RequestParam("idTypeCredilog") Long idTypeCredilog) { 
		
		return fondsDataPricingService.addPricingFonds(fondPricingFile, datePublication, dateExpiration,
				idTypeCredilog);
	} 
	
	
	
	@CrossOrigin
	@RequestMapping(value = "/pricing", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean handleFileUpload(
			@RequestParam("file") MultipartFile file, 
			@RequestParam("datePublication") String datePublication,
			@RequestParam("dateExpiration") String dateExpiration) {  
		
		String message;
		Date datePubltion = fondsDataPricingService.parseStringDate(datePublication);
		Date dateExp = fondsDataPricingService.parseStringDate(dateExpiration);
		try {
			String name = file.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = Constants.uploadPathFile;
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			
			String dateString = formatter.format(Calendar.getInstance().getTime());
			File repertoireFonds = new File(tmpDirectory + "/" + "fonds");
			File repertoire = new File(tmpDirectory + "/" + "fonds/pricing");
			File repertoireSnapshot = new File(tmpDirectory + "/" + "fonds/pricing" + "/" + dateString); 
			
			// create repertoireFonds
			repertoireFonds.mkdirs();
			// create repertoire
			repertoire.mkdirs();
			repertoireSnapshot.mkdirs();
			// repertoire created successfully !
			Path rootLocation = Paths.get(tmpDirectory + "/" + "fonds" + "/pricing");
			String tmpFolder = dateString + File.separator + name;
			Path path = rootLocation.resolve(tmpFolder);
			Files.copy(file.getInputStream(), path);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			FondsDataPricing fondsDataPricing = new FondsDataPricing();
			fondsDataPricing.setDateExpiration(dateExp);
			fondsDataPricing.setDatePublication(datePubltion);
			fondsDataPricing.setFichePricing(dateString + File.separator + name);
			
			TypeCredilog fonds = typeCredilogService.findOne(Constants.idFondsSakane);
			fondsDataPricing.setTypeCredilog(fonds); 
			
			fondsDataPricingService.addFondsDataPricing(fondsDataPricing);
			return true;
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return false;
		}
	}
}
