package Entrega.CapstoneRodrigoAraujoVieira;

import Entrega.CapstoneRodrigoAraujoVieira.Mapa.Parametros;
import Entrega.CapstoneRodrigoAraujoVieira.Mapa.Tabuleiro;

public class GameOfLife {
    public static void main(String[] args) {
        Parametros p = new Parametros(args);

        if (!p.valido) {
            System.out.println("Erro: " + p.erro);
            return;
        }

        p.exibirParametros();

        Tabuleiro tabuleiro = new Tabuleiro(p);
        tabuleiro.iniciar();
    }
}