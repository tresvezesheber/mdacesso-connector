package br.dev.hebio.mdacessoconnector.util;

public class AbreviaNome {
    public static String abreviarNomeETransformaEmMaiusculo(String nomeCompleto) {
        String[] nomes = nomeCompleto.split(" ");
        StringBuilder nomeAbreviado = new StringBuilder();

        for (int i = 0; i < nomes.length; i++) {
            if (i == 0 || i == nomes.length - 1) {
                nomeAbreviado.append(nomes[i]).append(" ");
            } else if (nomes[i].length() > 3) {
                nomeAbreviado.append(nomes[i].charAt(0)).append(". ");
            }
        }

        return nomeAbreviado.toString().trim().toUpperCase();
    }
}
