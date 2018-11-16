package application.business.v1;

import java.util.Arrays;

import org.springframework.stereotype.Component;
@Component
public class DnaHelper {
	protected String[] transformToVertArray(final char[][] dna2dArray) {

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

	protected char[][] transformTo2DArray(String[] pDna, boolean reverse) {
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

	protected String[] reverseMatrix(String[] pDna, boolean reverse) {
		String[] dna = new String[pDna.length];
		if (reverse) {
			for (int i = 0; i < pDna.length; i++) {
				dna[i] = new StringBuffer(pDna[i]).reverse().toString();
			}
		} else
			dna = pDna;
		return dna;
	}

	protected int countDnaMutant(String[] dna, String[] dnaMut) {
		int seqDnaMutCount = 0;
		for (String ds : dnaMut) {
			seqDnaMutCount += Arrays.stream(dna).parallel().filter(g -> g.contains(ds)).count();
		}
		return seqDnaMutCount;
	}

	protected String[] transformToDiagArray(char[][] dnaMatrix) {
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
