package Entrega.CapstoneRodrigoAraujoVieira.Celulas;

public class Agua extends CelulaAbstrata {
    public Agua() {
        super(3);
    }

    @Override
    public CelulaAbstrata processarNovaGeracao(int x, int y, CelulaAbstrata[][] tabuleiro, int geracao, int direcao) {
        return new Agua();
    }
}
