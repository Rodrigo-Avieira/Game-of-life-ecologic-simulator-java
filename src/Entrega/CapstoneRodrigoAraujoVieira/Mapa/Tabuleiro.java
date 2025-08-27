package Entrega.CapstoneRodrigoAraujoVieira.Mapa;

import Entrega.CapstoneRodrigoAraujoVieira.Celulas.*;
import Entrega.CapstoneRodrigoAraujoVieira.Celulas.*;

public class Tabuleiro {
    private CelulaAbstrata[][] mapa;
    private final int largura;
    private final int altura;
    private final int geracoes;
    private final int intervaloMs;
    private final int direcao;

    private final String[] grafico = {"\uD83D\uDFEB", "\uD83C\uDF33", "\uD83E\uDDAC", "\uD83C\uDF0A"};

    public Tabuleiro(Parametros p) {
        this.largura = p.largura;
        this.altura = p.altura;
        this.geracoes = p.geracoes;
        this.intervaloMs = p.intervaloMs;
        this.direcao = p.direcao;

        // Inicializa grid com objetos das classes certas
        this.mapa = new CelulaAbstrata[altura][largura];
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                switch (p.mapaInicial[i][j]) {
                    case 0 -> mapa[i][j] = new Vazio();
                    case 1 -> mapa[i][j] = new Arvore();
                    case 2 -> mapa[i][j] = new Animal();
                    case 3 -> mapa[i][j] = new Agua();
                }
            }
        }
    }

    public void iniciar() {
        int geracao = 0;
        imprimir(geracao);

        while (geracoes == 0 || geracao < geracoes) {
            esperar();
            geracao++;
            atualizar(geracao);
            imprimir(geracao);
        }
    }

    private void imprimir(int geracao) {
        int[] contagem = new int[4];
        System.out.println("\n=== Geração " + geracao + " ===");

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                int tipo = mapa[i][j].getTipo();
                contagem[tipo]++;
                System.out.print("\t" + grafico[tipo] + " ");
            }
            System.out.println();
        }

        System.out.printf("Vazio: %d | Árvores: %d | Animais: %d | Água: %d%n",
                contagem[0], contagem[1], contagem[2], contagem[3]);
        System.out.println("----------------------------------");
    }

    private void esperar() {
        if (intervaloMs > 0) {
            try {
                Thread.sleep(intervaloMs);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void atualizar(int geracao) {
        // Primeira fase: aplicar regras de evolução
        CelulaAbstrata[][] novoMapa = new CelulaAbstrata[altura][largura];

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                CelulaAbstrata celula = mapa[i][j];
                novoMapa[i][j] = celula.processarNovaGeracao(i, j, mapa, geracao, direcao);
            }
        }

        // Segunda fase: movimento dos animais (apenas em gerações pares)
        if (geracao % 2 == 0) {
            novoMapa = Regras.processarMovimentoAnimais(novoMapa, direcao);
        }

        mapa = novoMapa;
    }
}