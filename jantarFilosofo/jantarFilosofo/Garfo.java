package jantarFilosofo;

class Garfo { //Recurso crítico; Objeto que causa conflito
    public int id;
    private boolean emUso = false; //Caso seja true, ninguem mais pode pegar este garfo
    							   //private para que apenas a Classe consiga mudar o valor 
    public Garfo(int id) {
        this.id = id;
    }
    
    
  /*Método atômico, que vai verificar se um garfo está livre e
  ocupar ele; Evita que dois filosofos pensem que o garfo está livre
  ao mesmo tempo. O synchronized cria uma Esclusão Mútua, qualquer outro que tente pegar
  o mesmo objeto Garfo ficará na lista de espera até que o primeiro termine */
    public synchronized boolean tentarPegar() { 		
    	//	Entra no loop se for TRUE;					
    	//ou seja, emUso era FALSE						
    	//nenhum filosofo ocupava aquele garfo
    		if (!emUso) {								
            emUso = true; //muda o valor para mostrar que ocupou						
            return true;
        }
        return false;//Se não entrar no loop, aquele garfo estava ocupado
    }

    public synchronized void soltar() {//Metodo para mudar o valor da variavel APÓS terminar de comer
        emUso = false;
    }
}