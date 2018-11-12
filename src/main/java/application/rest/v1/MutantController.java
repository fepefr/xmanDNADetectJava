package application.rest.v1;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MutantController {

	@RequestMapping(value="mutant", method = RequestMethod.POST, 
		    consumes = "application/json")
	public @ResponseBody ResponseEntity<String> example(@RequestBody DnaWrapper dna){
		String[] seqDnaMut = { "AAAA", "CCCC", "TTTT", "GGGG" };
		if (isMutant(dna.getDna(), seqDnaMut))
			return new ResponseEntity<String>(HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
	}

	public boolean isMutant(final String[] dna, final String[] seqDnaMut) {
		int seqDnaMutCount = 0;
		// Check dna values horizontally
		seqDnaMutCount += countDnaMutant(dna, seqDnaMut);
		if (seqDnaMutCount > 1)
			return true;

		// Check dna values vertically
		char[][] dna2DArray = transformTo2DArray(dna, false);
		String[] dnaVert = transformToVertArray(dna2DArray);
		seqDnaMutCount += countDnaMutant(dnaVert, seqDnaMut);
		if (seqDnaMutCount > 1)
			return true;

		// Check dna values diagonally
		String[] dnaDiagonal = transformToDiagArray(dna2DArray);
		seqDnaMutCount += countDnaMutant(dnaDiagonal, seqDnaMut);
		if (seqDnaMutCount > 1)
			return true;

		// Check dna values diagonally reverse
		char[][] dna2DArrayRev = transformTo2DArray(dna, true);
		String[] dnaDiagRev = transformToDiagArray(dna2DArrayRev);
		seqDnaMutCount += countDnaMutant(dnaDiagRev, seqDnaMut);
		if (seqDnaMutCount > 1)
			return true;
		return false;
	}

	private String[] transformToVertArray(final char[][] dna2dArray) {

		String[] result = new String[dna2dArray.length];
		for (int i = 0; i < dna2dArray.length; i++) {
			for (int j = i + 1; j < dna2dArray.length; j++) {
				char temp = dna2dArray[i][j];
				dna2dArray[i][j] = dna2dArray[j][i];
				dna2dArray[j][i] = temp;
			}
			result[i] = new String(dna2dArray[i]);
		}
		return result;
	}

	private char[][] transformTo2DArray(String[] pDna, boolean reverse) {
		String[] dna = null;
		if (reverse) {
			dna = reverseMatrix(pDna, reverse);
		} else {
			dna = pDna;
		}

		char[][] dnaMatrix = new char[dna.length][dna.length];
		int lin = 0;
		for (String dnaLine : dna) {
			// char[] dnaLineChar = dnaLine.chars().mapToObj(c -> (char)
			// c).toArray(char[]::new);
			char[] dnaLineChar = dnaLine.toCharArray();
			dnaMatrix[lin++] = dnaLineChar;
		}
		return dnaMatrix;
	}

	private String[] reverseMatrix(String[] pDna, boolean reverse) {
		String[] dna = new String[pDna.length];
		if (reverse) {
			for (int i = 0; i < pDna.length; i++) {
				dna[i] = new StringBuffer(pDna[i]).reverse().toString();
			}
		} else
			dna = pDna;
		return dna;
	}

	private int countDnaMutant(String[] dna, String[] dnaMut) {
		int seqDnaMutCount = 0;
		for (String ds : dnaMut) {
			seqDnaMutCount += Arrays.stream(dna).parallel().filter(g -> g.contains(ds)).count();
		}
		return seqDnaMutCount;
	}

	private String[] transformToDiagArray(char[][] dnaMatrix) {
		int linhas = dnaMatrix.length;
		int colunas = dnaMatrix.length;
		int qtdDiag = linhas + colunas - 1;

		String[] result = new String[qtdDiag];
		Arrays.fill(result, "");
		for (int diag = 0; diag < qtdDiag; diag++) {
			for (int indLin = 0; indLin < linhas; indLin++) {
				for (int indCol = 0; indCol < colunas; indCol++) {
					if (indLin + indCol == diag) {
						result[diag] += dnaMatrix[indCol][indLin];
					}
				}
			}
		}
		return result;
	}
}