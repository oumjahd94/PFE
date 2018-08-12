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

import ma.mt.entity.FondsDataCashflow;
import ma.mt.entity.TypeCredilog;
import ma.mt.service.interfaces.IFondsDataCashflowService;
import ma.mt.service.interfaces.ITypeCredilogService;

/*
 * Hassan
 */
/**
 * The Class FondsDataCashflowRestController.
 */
@RequestMapping(value = "/api/fondsDataCashflows/")
@RestController
public class FondsDataCashflowRestController {

	/** The echeancier previsionnel service. */
	@Autowired
	private IFondsDataCashflowService fondsDataCashflowService;

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
	List<FondsDataCashflow> getByTypeCredilog(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		TypeCredilog typeCredilog = typeCredilogService.findOne(idTypeCredilog);
		return fondsDataCashflowService.getByTypeCredilog(typeCredilog);
	}

	/**
	 * Delete fondsDataCashflow.
	 *
	 * @param idFondsDataCashflow
	 *            the id fonds data cashflow
	 * @return true, if successful
	 */
	@CrossOrigin
	@RequestMapping(value = "{idFondsDataCashflow}", method = RequestMethod.DELETE)
	public boolean deleteEcheancierPrevisionnel(
			@PathVariable(name = "idFondsDataCashflow") Long idFondsDataCashflow) 
	{
		return fondsDataCashflowService.deleteFondsDataCashflow(idFondsDataCashflow);
	}

	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataCashflow> listFondsDataCashflow() {
		return fondsDataCashflowService.listFondsDataCashflows();
	}

	@CrossOrigin
	@RequestMapping(value = "/cashflow", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public boolean addNewCashflow(
			@RequestParam("file") MultipartFile file,
			@RequestParam("datePublication") String datePublication,
			@RequestParam("dateExpiration") String dateExpiration, 
			@RequestParam("scenariora") String scenariora,
			@RequestParam("scenarioddt") String scenarioddt) {
		// call service for processing add item
		return fondsDataCashflowService.addNewCashflow(file, datePublication, dateExpiration, scenariora, scenarioddt);
	}

}
