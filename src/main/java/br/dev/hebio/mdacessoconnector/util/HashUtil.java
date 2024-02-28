package br.dev.hebio.mdacessoconnector.util;

import br.dev.hebio.mdacessoconnector.model.colaborador.ColaboradorDadosView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String calculateHash(ColaboradorDadosView colaborador) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = colaborador.matricula() + colaborador.nome() + colaborador.cpf() + colaborador.situacao() + colaborador.dataAdmissao() + colaborador.dataDemissao();
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}