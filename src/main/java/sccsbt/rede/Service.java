package sccsbt.rede;

import java.io.IOException;
import java.util.ArrayList;

public class Service{
    ArrayList<String> paraMapear;
    String rede1 = "L: \\\\192.168.1.31\\rede1";
    String rede2 = "M: \"\\\\192.168.0.63\\rede 2\\000_pasta 1\"";
    String rede3 = "N: \"\\\\192.168.0.63\\analise jornal\\000_ANALISE_OK\"";
    String rede4 = "O: \\\\192.168.1.37\\playout2";
    String rede5 = "P: \"\\\\192.168.53.81\\sala de off\"";
    String rede6 = "Q: \"\\\\192.168.0.2\\switch gravados\"";
    
    public Service(ArrayList paraMapear) {
        this.paraMapear = paraMapear;
    }
    
    public String mapearRede(ArrayList paraMapear) {
        StringBuilder resultado = new StringBuilder();

        if (paraMapear.contains("Ingest")) {
            resultado.append(mapeador(rede1, "Ingest "));
        }
        if (paraMapear.contains("Aprovacao")) {
            resultado.append(mapeador(rede2, "Analise Aprovacao "));
        }
        if (paraMapear.contains("Ok")) {
            resultado.append(mapeador(rede3, "Analise Ok "));
        }
        if (paraMapear.contains("Playout")) {
            resultado.append(mapeador(rede4, "Playout "));
        }
        if (paraMapear.contains("Off")) {
            resultado.append(mapeador(rede5, "Sala de off "));
        }
        if (paraMapear.contains("Gravados")) {
            resultado.append(mapeador(rede6, "Switch Gravados "));
        }
        
        return resultado.toString();
    }
    
    public String mapeador (String path, String retorno) {
        try {
        String command = String.format("net use %s", path);
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
                return retorno + "mapeado com sucesso.\n";
            } else {
                return "Falha ao mapear " + retorno + ". Código de saída: " + exitCode + "\n";
            }
        } catch (IOException | InterruptedException e) {
            return "Erro durante o mapeamento de " + path + ": " + e.getMessage() + "\n";
        }
    }
}
