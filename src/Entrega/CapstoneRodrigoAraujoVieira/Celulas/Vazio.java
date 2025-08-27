package Entrega.CapstoneRodrigoAraujoVieira.Celulas;

public class Vazio extends CelulaAbstrata {
    public Vazio() {
        super(0);
    }

    @Override
    public CelulaAbstrata processarNovaGeracao(int x, int y, CelulaAbstrata[][] tabuleiro, int geracao, int direcao) {
        // Regra 1: Árvore nasce se houver 2+ árvores na vizinhança imediata
        int arvoresImediatas = Regras.contarVizinhos(tabuleiro, x, y, 1, true);
        if (arvoresImediatas >= 2) {
            return new Arvore();
        }

        // Regra 2: Animal nasce em gerações pares
        if (geracao % 2 == 0) {
            int animaisImediatos = Regras.contarVizinhos(tabuleiro, x, y, 2, true);
            boolean temArvoreExtendida = Regras.temVizinhoInternoOuExterno(tabuleiro, x, y, 1);
            boolean temAguaExtendida = Regras.temVizinhoInternoOuExterno(tabuleiro, x, y, 3);

            if (animaisImediatos == 2 && temArvoreExtendida && temAguaExtendida) {
                return new Animal();
            }
        }

        // Regra 3: Água nasce em gerações múltiplas de 3
        if (geracao % 3 == 0) {
            if (Regras.temAguaNaLinhaSuperior(tabuleiro, x, y)) {
                return new Agua();
            }
        }

        return new Vazio();
    }
}