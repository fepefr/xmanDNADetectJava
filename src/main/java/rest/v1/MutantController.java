package application.rest.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MutantController {

	//Our database store
    DnaStore store = DnaStoreFactory.getInstance();
    DnaBusiness dnaBusiness = new DnaBusiness();

	@RequestMapping(value="mutant", method = RequestMethod.POST, 
		    consumes = "application/json")
	public @ResponseBody ResponseEntity<String> mutant(@RequestBody DnaWrapper dna){
		String[] seqDnaMut = { "AAAA", "CCCC", "TTTT", "GGGG" };
		if (dnaBusiness.isMutant(dna.getDna(), seqDnaMut)){
			Dna dnaVo = new Dna();
			dna.setDna(new String(dna.toString());
			if (dnaBusiness.newDna(dnaVo))
				return new ResponseEntity<String>(HttpStatus.OK);
			else
				return return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}else
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
	}

    @GET
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
    }	
	
}