package jantar;

import java.util.concurrent.Semaphore;

class Filosofo extends Thread {//extends Thread: Transforma a execução do codigo paralelamente, cada filosofo é independente
    private final int id;
    private final Semaphore garfoEsquerdo;//Cada garfo na mesa é um semaforo, se tiver sendo utilizado, ninguem mais pode usar
    private final Semaphore garfoDireito;//até que seja liberado

    public long tempoTotalComendo = 0;//Variáveis de tempo;Vão acumular o tempo de ação de cada filosofo
    public long tempoTotalPensando = 0;

    public Filosofo(int id, Semaphore esquerdo, Semaphore direito) {//Construtor
        this.id = id;
        if (id == 4) {//Condição para quebrar o Deadlock;O ultimo filosofo tenta pegar Direita pra esquerda
            this.garfoEsquerdo = direito;
            this.garfoDireito = esquerdo;
        } else {//Todos os outros filosofos tentam da Esquerda pra Direita
            this.garfoEsquerdo = esquerdo;
            this.garfoDireito = direito;
        }
    }

    private long realizarAcao(String acao) throws InterruptedException {//Método que retorn o tempo que durou uma ação
        long inicio = System.currentTimeMillis();//System.currentTimeMillis - Guarda o tempo ATUAL
        System.out.println("Filósofo " + id + " está " + acao + "...");
        Thread.sleep((long) (Math.random() * 2000));//Tempo MAX que passa PENSANDO/COMENDO
        return (System.currentTimeMillis() - inicio);//Diferença entre inicio e currentTimeMillis = Tempo que passou
    }

    @Override //Apenas para poder escrever o proprio metodo run(), não utilizar o padrao do compilador
    public void run() {//Método executado quando filosofo[i].start(); Criar as execuções para cada filosofo;
        try {
            for (int i = 0; i < 5; i++) {//Filosofo executa o ciclo 5 vezes
                tempoTotalPensando += realizarAcao("PENSANDO");//O filosofo está em sleepPENSANDO por tempo aleatorio

                System.out.println("Filósofo " + id + " está com FOME.");//Fica com fome e vai tentar pegar os garfos;
                garfoEsquerdo.acquire();//Tenta pegar o garfo (semaforo) i caso esteja disponivel, se nao fica bloqueado
                garfoDireito.acquire();//garfo i+1
                //Qnd um filosofo pega um garfo, o contador do semaforo fica 0
                
                tempoTotalComendo += realizarAcao("COMENDO"); //Filosofo fica em sleepCOMENDO

                garfoDireito.release();//Solta o garfo i e, disponibiliza para alguma Thread interrompida nesse momento 
                garfoEsquerdo.release();//garof i+1
                System.out.println("Filósofo " + id + " soltou os garfos.");//As Threads que estavam bloqueadas no instante de um garfo
            }																//são acordadas nesse momento de release
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        final int NUM_FILOSOFOS = 5;
        Semaphore[] garfos = new Semaphore[NUM_FILOSOFOS];
        Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];

        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            garfos[i] = new Semaphore(1);//EXCLUSÃO MÚTUA: Semaphore(1) garante que somente um Filosofo pode 
        }							     //usar um garfo especifico
        
        System.out.println("--- O Jantar começou ---\n");

        for (int i = 0; i < NUM_FILOSOFOS; i++) {//Instanciando o objeto Filosofo
            filosofos[i] = new Filosofo(i, garfos[i], garfos[(i + 1) % NUM_FILOSOFOS]);//(num, esquerdo, direito)
            filosofos[i].start();							//%5, no ultimo filosofo faz com que o garfo 0 seja 
        }													//o da direita dele. Quebra a ESPERA CIRCULAR: Filosofo 4
        													//tenta pegar o garfo 0 primeiro (que ja está ocupado pelo Filosofo 0)
        													// e então fica Bloqueado, deixando liberado assim o garfo 4 para o Filosofo 3.
        try {
        	for (int i = 0; i < NUM_FILOSOFOS; i++) {
                // O main para nesta linha e espera a thread filosofos[i] "morrer"
                filosofos[i].join();// .join() faz o main finalizar apenas qnd os filosofos terminarem o ciclo
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //RELATÓRIO FINAL
        System.out.println("\n================================================");
        System.out.println("                 RELATÓRIO  ");
        System.out.println("================================================");
        System.out.printf("%-12s | %-15s | %-15s%n", "Filósofo", "Tempo Comendo", "Tempo Pensando");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < NUM_FILOSOFOS; i++) {//Tempo de cada filosofo
            System.out.printf("Filósofo %-3d | %-12d ms | %-12d ms%n", 
                i, filosofos[i].tempoTotalComendo, filosofos[i].tempoTotalPensando);
        }
        System.out.println("================================================");
    }
}