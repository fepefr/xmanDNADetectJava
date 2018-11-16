package application.rest.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import application.business.v1.DnaBusiness;
import application.business.v1.DuplicatedDnaException;
import vo.v1.Dna;


/**
 * @author Fernando
 *
 */
@RestController
public class MutantController {

	private static final String MUTANTE = "DNA mutante: ";
	private static final String NAO_MUTANTE = "DNA não mutante: ";
	@Autowired
	DnaBusiness dnaBusiness;

	@RequestMapping(value="mutant", method = RequestMethod.POST, 
		    consumes = "application/json")
	public @ResponseBody ResponseEntity<String> mutant(@RequestBody DnaWrapper dna){
		Dna dnaVO = new Dna();
		dnaVO.setDna(String.join("",dna.getDna()));
			try{
				dnaVO.setMutante(dnaBusiness.isMutant(dna.getDna()));
				dnaVO = dnaBusiness.newDna(dnaVO);
			}catch (DuplicatedDnaException e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
			}
			if (dnaVO.isMutante()){
				return new ResponseEntity<String>(MUTANTE + dnaVO.getDna(), HttpStatus.OK);
			}else
				return new ResponseEntity<String>(NAO_MUTANTE+ dnaVO.getDna(), HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value="stats", method = RequestMethod.GET, consumes = "application/json")
    public @ResponseBody ResponseEntity<VerificacoesWrapper> stats() {
        VerificacoesWrapper result = dnaBusiness.getStats();
        return new ResponseEntity<VerificacoesWrapper>(result, HttpStatus.OK);
    }	
	
}