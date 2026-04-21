package jantarFilosofo;

public class Main {
    public static void main(String[] args) {
        Garfo[] garfos = new Garfo[5]; //Recursos
        for (int i = 0; i < 5; i++) {
            garfos[i] = new Garfo(i);
        }
        Filosofo[] filosofos = new Filosofo[5];
        
        for (int i = 0; i < 4; i++) {     //O filosofo i pega o garfo i(esquerda) e i+1(direita)
            filosofos[i] = new Filosofo("Filósofo " + i, garfos[i], garfos[i + 1]);
        }
        filosofos[4] = new Filosofo("Filósofo 4", garfos[0], garfos[4]); //O filosofo 4 vai pegar o garfo 0(direita)
        																 //e o 4(esquerda). Muda ordem.

        System.out.println("=== TESTE ===\n");
        
        for (int t = 1; t <= 6; t++) { //Teste por tentativas t;
            System.out.println("--- Instante " + t + " ---");
            for (Filosofo f : filosofos) { //Funciona igual a: for (int i = 0; i < filosofos.length; i++)
                f.agir();				   //Leitura: Para cada Objeto do tipo Filosofo (f - variável temp.) faça...
            }
            System.out.println();
        }
    }
}