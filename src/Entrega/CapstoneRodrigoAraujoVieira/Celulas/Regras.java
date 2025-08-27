package Entrega.CapstoneRodrigoAraujoVieira.Celulas;

import java.util.*;

public class Regras {

    // vizinhança imediata (interno - 8 células)
    private static final int[][] VIZINHANCA_IMEDIATA = {
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1}, { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
    };

    // vizinhança externa (externo - 16 células)
    private static final int[][] VIZINHANCA_EXTERNA = {
            {-2, -2}, {-2, -1}, {-2, 0}, {-2, 1}, {-2, 2},
            {-1, -2}, {-1, 2},
            { 0, -2}, { 0, 2},
            { 1, -2}, { 1, 2},
            { 2, -2}, { 2, -1}, { 2, 0}, { 2, 1}, { 2, 2}
    };

    // linha superior da vizinhança imediata
    private static final int[][] LINHA_SUPERIOR = {
            {-1, -1}, {-1, 0}, {-1, 1}
    };

    //Conta vizinhos em anel interno (imediato) ou externo

    public static int contarVizinhos(CelulaAbstrata[][] tab, int x, int y, int tipo, boolean interno) {
        int[][] offsets = interno ? VIZINHANCA_IMEDIATA : VIZINHANCA_EXTERNA;
        int count = 0;

        for (int[] offset : offsets) {
            int nx = x + offset[0];
            int ny = y + offset[1];

            if (nx >= 0 && ny >= 0 && nx < tab.length && ny < tab[0].length) {
                if (tab[nx][ny].getTipo() == tipo) {
                    count++;
                }
            }
        }
        return count;
    }

    //Verifica se tem vizinho do tipo especificado na vizinhança imediata OU externa

    public static boolean temVizinhoInternoOuExterno(CelulaAbstrata[][] tab, int x, int y, int tipo) {
        return contarVizinhos(tab, x, y, tipo, true) > 0 ||
                contarVizinhos(tab, x, y, tipo, false) > 0;
    }

    //Verifica se tem água na linha superior da vizinhança imediata
    public static boolean temAguaNaLinhaSuperior(CelulaAbstrata[][] tab, int x, int y) {
        for (int[] offset : LINHA_SUPERIOR) {
            int nx = x + offset[0];
            int ny = y + offset[1];

            if (nx >= 0 && ny >= 0 && nx < tab.length && ny < tab[0].length) {
                if (tab[nx][ny].isAgua()) {
                    return true;
                }
            }
        }
        return false;
    }

    //Processa movimento de todos os animais no tabuleiro
    public static CelulaAbstrata[][] processarMovimentoAnimais(CelulaAbstrata[][] tabuleiro, int direcao) {
        int altura = tabuleiro.length;
        int largura = tabuleiro[0].length;

        // Copia o tabuleiro atual
        CelulaAbstrata[][] novoTabuleiro = new CelulaAbstrata[altura][largura];
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                novoTabuleiro[i][j] = criarCelula(tabuleiro[i][j].getTipo());
            }
        }

        // Encontra todos os animais e seus destinos
        List<MovimentoAnimal> movimentos = new ArrayList<>();

        for (int x = 0; x < altura; x++) {
            for (int y = 0; y < largura; y++) {
                if (tabuleiro[x][y].isAnimal()) {
                    int[] destino = calcularDestino(x, y, direcao, altura, largura);
                    int destX = destino[0];
                    int destY = destino[1];

                    if (podeMoverse(tabuleiro, destX, destY)) {
                        movimentos.add(new MovimentoAnimal(x, y, destX, destY));
                    }
                }
            }
        }

        // Aplica movimentos
        for (MovimentoAnimal mov : movimentos) {
            novoTabuleiro[mov.origemX][mov.origemY] = new Vazio();
            novoTabuleiro[mov.destinoX][mov.destinoY] = new Animal();
        }

        return novoTabuleiro;
    }

    private static int[] calcularDestino(int x, int y, int direcao, int altura, int largura) {
        int destX = x, destY = y;

        switch (direcao) {
            case 1 -> destY++; // direita
            case 2 -> destX++; // baixo
            case 3 -> destY--; // esquerda
            case 4 -> destX--; // cima
        }

        // Verifica se está dentro dos limites
        if (destX < 0 || destY < 0 || destX >= altura || destY >= largura) {
            return new int[]{x, y}; // mantém na posição original
        }

        return new int[]{destX, destY};
    }

    private static boolean podeMoverse(CelulaAbstrata[][] tabuleiro, int destX, int destY) {
        int altura = tabuleiro.length;
        int largura = tabuleiro[0].length;

        // Verifica limites
        if (destX < 0 || destY < 0 || destX >= altura || destY >= largura) {
            return false;
        }

        CelulaAbstrata destino = tabuleiro[destX][destY];

        // Animal se move se o destino for vazio ou árvore (que será consumida)
        // Não se move se for água ou outro animal
        return destino.isVazio() || destino.isArvore();
    }

    private static CelulaAbstrata criarCelula(int tipo) {
        switch (tipo) {
            case 0:
                return new Vazio();
            case 1:
                return new Arvore();
            case 2:
                return new Animal();
            case 3:
                return new Agua();
            default:
                return new Vazio();
        }
    }

    // Classe auxiliar para movimentos
    private static class MovimentoAnimal {
        final int origemX, origemY, destinoX, destinoY;

        MovimentoAnimal(int origemX, int origemY, int destinoX, int destinoY) {
            this.origemX = origemX;
            this.origemY = origemY;
            this.destinoX = destinoX;
            this.destinoY = destinoY;
        }
    }
}