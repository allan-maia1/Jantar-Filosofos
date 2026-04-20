package jantarFilosofo;

class Filosofo {
    public String nome;
    public Garfo garfoEsquerda;
    public Garfo garfoDireita;
    
    public boolean temGarfoEsquerda = false;
    public boolean temGarfoDireita = false;

    public Filosofo(String nome, Garfo esquerda, Garfo direita) {
        this.nome = nome;
        this.garfoEsquerda = esquerda;
        this.garfoDireita = direita;
    }

    public void agir() {
        if (temGarfoEsquerda && temGarfoDireita) {
            comer();
        } else {
            tentarPegarGarfos();
        }
    }

    private void tentarPegarGarfos() { //Tenta pegar garfo da esquerda; Pega caso não esteja em uso
        if (!temGarfoEsquerda && !garfoEsquerda.emUso) {
            garfoEsquerda.emUso = true;
            temGarfoEsquerda = true;
            System.out.println(nome + " pegou o garfo ESQUERDO (" + garfoEsquerda.num + ")");
        }

        if (!temGarfoDireita && !garfoDireita.emUso) { //Tenta pegar o garfo da direita; Pega caso não esteja em uso
            garfoDireita.emUso = true;
            temGarfoDireita = true;
            System.out.println(nome + " pegou o garfo DIREITO (" + garfoDireita.num + ")");
        }
        
        if (!temGarfoEsquerda || !temGarfoDireita) { //Se não possui algum dos garfos, fica aguardando
            System.out.println(nome + " está aguardando liberação de garfo...");
        }
    }

    private void comer() {
        System.out.println(">>> " + nome + " está COMENDO com os garfos " + garfoEsquerda.num + " e " + garfoDireita.num);
       
        garfoEsquerda.emUso = false; //Terminou de comer e vai soltar os garfos
        garfoDireita.emUso = false;
        temGarfoEsquerda = false;
        temGarfoDireita = false;
        
        System.out.println(nome + " terminou de comer e liberou os garfos.");
    }
}