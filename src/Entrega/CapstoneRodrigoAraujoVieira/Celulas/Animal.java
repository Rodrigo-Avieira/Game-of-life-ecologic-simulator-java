package Entrega.CapstoneRodrigoAraujoVieira.Celulas;

public class Animal extends CelulaAbstrata {
    public Animal() {
        super(2);
    }

    @Override
    public CelulaAbstrata processarNovaGeracao(int x, int y, CelulaAbstrata[][] tabuleiro, int geracao, int direcao) {
        // Animal sobrevive se houver pelo menos uma árvore E uma água na vizinhança estendida
        boolean temArvore = Regras.temVizinhoInternoOuExterno(tabuleiro, x, y, 1);
        boolean temAgua = Regras.temVizinhoInternoOuExterno(tabuleiro, x, y, 3);

        if (temArvore && temAgua) {
            return new Animal();
        } else {
            return new Vazio(); // Animal morre
        }
    }
}