package rest.v1;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import business.v1.DnaBusiness;
import vo.v1.Dna;


/**
 * @author Fernando
 *
 */
@RestController
public class MutantController {

	DnaBusiness dnaBusiness = new DnaBusiness();

	@RequestMapping(value="mutant", method = RequestMethod.POST, 
		    consumes = "application/json")
	public @ResponseBody ResponseEntity<String> mutant(@RequestBody DnaWrapper dna){
		if (dnaBusiness.isMutant(dna.getDna())){
			Dna dnaVO = new Dna();
			dnaVO.setDna(Arrays.toString(dna.getDna()));
			if (dnaBusiness.newDna(dnaVO))
				return new ResponseEntity<String>(HttpStatus.OK);
			else
				return new ResponseEntity<String>("Análise deste DNA já existe no banco de dados", HttpStatus.FORBIDDEN);
		}else
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
	}

/*    @GET
    @Path("/")
    @Produces({"application/json"})
    public String getDnas() {

        if (store == null) {
            return "[]";
        }

        List<String> names = new ArrayList<String>();
        for (Dna dna : store.getAll()) {
            String dnaStr = dna.getDna();
            if (name != null){
                names.add(name);
            }
        }
        return new Gson().toJson(names);
    }	*/
	
}