package application.business.v1;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudant.client.org.lightcouch.DocumentConflictException;

import application.rest.v1.VerificacoesWrapper;
import store.v1.DnaStore;
import store.v1.DnaStoreFactory;
import vo.v1.Dna;
@Component
public class DnaBusiness {

	private static final String DNA_EXISTENTE = "Já existe análise para o DNA: ";
	private static final String[] DNA_MUT = { "AAAA", "CCCC", "TTTT", "GGGG" };
	// Our database store
	DnaStore store = DnaStoreFactory.getInstance();
	@Autowired
	DnaHelper dnaHelper;
	public boolean isMutant(final String[] dna) {
		int seqDnaMutCount = 0;
		// Check dna values horizontally
		seqDnaMutCount += dnaHelper.countDnaMutant(dna, DNA_MUT);
		if (seqDnaMutCount > 1)
			return true;

		// Check dna values vertically
		char[][] dna2DArray = dnaHelper.transformTo2DArray(dna, false);
		String[] dnaVert = dnaHelper.transformToVertArray(dna2DArray);
		seqDnaMutCount += dnaHelper.countDnaMutant(dnaVert, DNA_MUT);
		if (seqDnaMutCount > 1)
			return true;

		// Check dna values diagonally
		String[] dnaDiagonal = dnaHelper.transformToDiagArray(dna2DArray);
		seqDnaMutCount += dnaHelper.countDnaMutant(dnaDiagonal, DNA_MUT);
		if (seqDnaMutCount > 1)
			return true;

		// Check dna values diagonally reverse
		char[][] dna2DArrayRev = dnaHelper.transformTo2DArray(dna, true);
		String[] dnaDiagRev = dnaHelper.transformToDiagArray(dna2DArrayRev);
		seqDnaMutCount += dnaHelper.countDnaMutant(dnaDiagRev, DNA_MUT);
		if (seqDnaMutCount > 1)
			return true;
		return false;
	}

	public Dna newDna(Dna dna) throws DuplicatedDnaException {
		Dna persist = null;
		if (store == null) {
			return persist;
		}
		dna.set_id("dna:"+dna.getDna());
		try{
		 persist = store.persist(dna) ;
		}catch (DocumentConflictException e) {
			throw new DuplicatedDnaException(DNA_EXISTENTE+dna.getDna());
		}
		return persist;
	}

	public VerificacoesWrapper getStats() {
		VerificacoesWrapper result = new VerificacoesWrapper();
		Collection<Dna> all = store.getAll();
		long size = all.size();
		double countMut = all.stream().filter(dna -> dna.isMutante()==true).count();
		double countHum = size - countMut;
		double ratio = countMut / countHum;
		result.setCount_human_dna(countHum);
		result.setCount_mutant_dna(countMut);
		result.setRatio(ratio);
		return result;
	}
}