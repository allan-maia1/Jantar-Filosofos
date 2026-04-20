package jantarFilosofo;

class Filosofo extends Thread {//Java executa uma linha por vez; extends threads, faz com que a classe seja um
    public String nome;		   //processo independente. Sempre que é chamado cria uma execução paralela
    private Garfo esquerda, direita;
    public long tempoComendo = 0;

    public Filosofo(String nome, Garfo esq, Garfo dir) {
        this.nome = nome;
        this.esquerda = esq;
        this.direita = dir;
    }

    @Override
    public void run() {//Método que inclui tudo que o Filosofo fará
        while (!Thread.currentThread().isInterrupted()) {//Antes verifica se ele algo mandou PARAR
            try {//Java exige que o método sleep seja envolvido por try catch; Se o sleep for interrompido
                System.out.println(nome + " está pensando...");
                Thread.sleep((long) (Math.random() * 1000));

                if (esquerda.tentarPegar()) {
                    if (direita.tentarPegar()) {
                        long inicio = System.currentTimeMillis();//cronômetro inicia em 0; long é um tipo numerico de 64bits
                        System.out.println(">>> " + nome + " está COMENDO.");
                        
                        Thread.sleep((long) (Math.random() * 1000));
                        
                        tempoComendo += (System.currentTimeMillis() - inicio);
                        
                        direita.soltar();
                        esquerda.soltar();
                        System.out.println(nome + " soltou os garfos e voltou a pensar.");
                    } else {
                        esquerda.soltar();
                    }
                }
            } catch (InterruptedException e) {
                // Se for interrompido durante o sono, sai do loop imediatamente
                break;
            }
        }
    }
}