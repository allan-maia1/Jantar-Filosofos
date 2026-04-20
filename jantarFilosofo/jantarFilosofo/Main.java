package jantarFilosofo;

public class Main {
    public static void main(String[] args) {
        Garfo[] garfos = new Garfo[5];
        for (int i = 0; i < 5; i++) garfos[i] = new Garfo(i);

        Filosofo[] filosofos = new Filosofo[5];
        for (int i = 0; i < 5; i++) {
            // Quebra de simetria para evitar Deadlock
            if (i == 4) {
                filosofos[i] = new Filosofo("Filósofo " + i, garfos[0], garfos[4]);
            } else {
                filosofos[i] = new Filosofo("Filósofo " + i, garfos[i], garfos[i + 1]);
            }
            filosofos[i].start();//Cria os 5 filosofos que disputarão recursos
        }

        try {
            Thread.sleep(10000);//Roda o código por 10 segundos
        } catch (InterruptedException e) {}

        // IMPORTANTE: Manda todos pararem antes de imprimir o relatório
        for (Filosofo f : filosofos) {
            f.interrupt();//Manda o sinal para a condição do while em Filosofo
        }

        // Delay para garantir que todos soltaram os garfos e atualizaram seus tempos
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        System.out.println("\n=== RELATÓRIO FINAL DE TEMPO ===");
        for (Filosofo f : filosofos) {
            // Agora o laço percorre TODOS e imprime um por um
            System.out.println(f.nome + ": " + (f.tempoComendo / 1000.0) + " segundos comendo.");
        }
    }
}