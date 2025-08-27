package Entrega.CapstoneRodrigoAraujoVieira.Mapa;

import java.util.Random;
import java.util.Set;

public class Parametros {
    private static final Set<Integer> LARGURAS_VALIDAS = Set.of(5, 10, 15, 20, 40, 80);
    private static final Set<Integer> ALTURAS_VALIDAS = Set.of(5, 10, 15, 20, 40);
    private static final Set<Integer> VELOCIDADES_VALIDAS = Set.of(0, 250, 500, 1000, 5000);

    public int largura;
    public int altura;
    public int geracoes;
    public int intervaloMs;
    public int direcao = 1;
    public int[][] mapaInicial;
    public String mapaStr;

    public boolean valido = true;
    public String erro = "";

    public Parametros(String[] args) {
        // Inicializa valores padrão
        geracoes = 0; // infinito por padrão
        intervaloMs = 1000; // padrão

        for (String arg : args) {
            if (!arg.contains("=")) continue;

            String[] dividindoParametro = arg.split("=");
            if (dividindoParametro.length != 2) continue;

            String chave = dividindoParametro[0];
            String valor = dividindoParametro[1];

            try {
                switch (chave) {
                    case "w":
                        largura = Integer.parseInt(valor);
                        if (!LARGURAS_VALIDAS.contains(largura)) {
                            throw new IllegalArgumentException("Largura deve ser: " + LARGURAS_VALIDAS);
                        }
                        break;
                    case "h":
                        altura = Integer.parseInt(valor);
                        if (!ALTURAS_VALIDAS.contains(altura)) {
                            throw new IllegalArgumentException("Altura deve ser: " + ALTURAS_VALIDAS);
                        }
                        break;
                    case "g":
                        geracoes = Integer.parseInt(valor);
                        if (geracoes < 0 || geracoes > 1000) {
                            throw new IllegalArgumentException("Gerações deve estar entre 0-1000");
                        }
                        break;
                    case "s":
                        intervaloMs = Integer.parseInt(valor);
                        if (!VELOCIDADES_VALIDAS.contains(intervaloMs)) {
                            throw new IllegalArgumentException("Velocidade deve ser: " + VELOCIDADES_VALIDAS);
                        }
                        break;
                    case "n":
                        direcao = Integer.parseInt(valor);
                        if (direcao < 1 || direcao > 4) {
                            throw new IllegalArgumentException("Direção inválida (1-4)");
                        }
                        break;
                    case "m":
                        mapaStr = valor;
                        break;
                    default:
                        valido = false;
                        erro = "Parâmetro desconhecido: " + chave;
                        return;
                }
            } catch (Exception e) {
                valido = false;
                erro = "Falha ao ler argumento: " + chave + " - " + e.getMessage();
                return;
            }
        }

        // Validação de parâmetros obrigatórios
        if (largura <= 0) {
            valido = false;
            erro = "Parâmetro 'w' (largura) é obrigatório";
            return;
        }

        if (altura <= 0) {
            valido = false;
            erro = "Parâmetro 'h' (altura) é obrigatório";
            return;
        }

        if (mapaStr == null) {
            valido = false;
            erro = "Parâmetro 'm' (mapa) é obrigatório";
            return;
        }

        // Processando o mapa
        if (mapaStr.equalsIgnoreCase("rnd")) {
            mapaInicial = gerarMapaAleatorio();
        } else {
            mapaInicial = interpretarMapaManual(mapaStr);
            if (mapaInicial == null) {
                valido = false;
                erro = "Erro ao interpretar mapa";
            }
        }
    }

    private int[][] interpretarMapaManual(String texto) {
        String[] linhas = texto.split("#");
        int[][] mapa = new int[altura][largura];

        for (int i = 0; i < altura; i++) {
            if (i >= linhas.length) break;
            String linha = linhas[i];

            for (int j = 0; j < largura; j++) {
                if (j >= linha.length()) continue;

                char c = linha.charAt(j);
                if (c >= '0' && c <= '3') {
                    mapa[i][j] = Character.getNumericValue(c);
                }
            }
        }
        return mapa;
    }

    private int[][] gerarMapaAleatorio() {
        int[][] mapa = new int[altura][largura];
        Random random = new Random();

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                mapa[i][j] = random.nextInt(4); // 0, 1, 2 ou 3 aleatoriamente
            }
        }
        return mapa;
    }

    public void exibirParametros() {
        System.out.println("----- Parâmetros do Ecossistema -----");
        System.out.println("Largura: " + largura);
        System.out.println("Altura: " + altura);
        System.out.println("Gerações: " + (geracoes == 0 ? "Infinitas" : geracoes));
        System.out.println("Intervalo: " + intervaloMs + "ms");
        System.out.println("Direção de movimento: " + obterNomeDirecao());
        System.out.println("Mapa Inicial:");

        String[] grafico = {"\uD83D\uDFEB", "\uD83C\uDF33", "\uD83E\uDDAC", "\uD83C\uDF0A"};
        for (int[] linha : mapaInicial) {
            for (int valor : linha) {
                System.out.print("\t" + grafico[valor]);
            }
            System.out.println();
        }
        System.out.println("------------------------------------");
    }

    private String obterNomeDirecao() {
        switch (direcao) {
            case 1: return "Direita";
            case 2: return "Para baixo";
            case 3: return "Esquerda";
            case 4: return "Para cima";
            default: return "Direita";
        }
    }
}
