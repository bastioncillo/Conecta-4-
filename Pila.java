package packclass;

public class Pila {
	// Atributos privados y constantes
	public static final int MAX_PILA = 10;
	private int[ ] undoStack;
	private int numUndo;
    
	//------------------------------- CONSTRUCTORA ------------------------------
	// Crea un objeto pila (array de enteros).
	public Pila(){
		this.undoStack = new int[MAX_PILA];
		this.numUndo = 0;
	}
	
	//--------------------------- COMPROBAR VACÍA -------------------------------
	// Comprueba si la pila está completamente vacía.
	public boolean estaVacia(){
		return(this.numUndo == 0);
	}
	
	//----------------------------- COLOCAR EN PILA -----------------------------
	// Tras realizar un movimiento, se coloca en la pila por si quiere deshacerse.
	// Si la pila está vacía, introduce el nuevo movimiento.
	public void poner(int mov){
		if(!estaLlena()){
			undoStack[numUndo] = mov;
			numUndo++;
		}
	// Si la pila está llena, se mueve todo el array, eliminando el movimiento más antiguo y
		// se introduce el nuevo.
		else{
			for(int i = 0; i < numUndo - 1; i++){
				undoStack[i] = undoStack[i+1];
			}
			undoStack[numUndo - 1] = mov;
		}
	}
	
	//------------------------------ DESHACER MOVIMIENTO ----------------------------
	public int sacar(){
		int ret;
		// Si la pila está vacía, no puede deshacer nada y devuelve -1.
		if(estaVacia()){
			ret = -1;
		}
		// Si hay algo almacenado en la pila, deshace el último movimiento y libera un espacio.
		else{
			ret = undoStack[numUndo - 1];
			numUndo--;
		}
		return ret;
	}
	
	// ---------------------------- COMPROBAR LLENA -----------------------------------
	// Comprueba si la pila está completamente llena.
	public boolean estaLlena(){
		boolean llena = false;
		
		if(this.numUndo == MAX_PILA){
			llena = true;
		}
		return llena;
	}
}
