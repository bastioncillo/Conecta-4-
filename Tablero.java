package packclass;
public class Tablero {
	private Ficha[][] tablero;
	private int ancho;
	private int alto;
	
	//------------------------ CONSTRUCTORA ----------------------------------------
	// Crea el objeto tablero (matriz de fichas).
	public Tablero(int ancho, int alto){
		tablero = new Ficha[ancho][alto];
		this.ancho = ancho;
		this.alto = alto;
		reset();
	}
	
	//---------------------------- RESET -------------------------------------------
	// Reinicia el juego vaciando todas las casillas.
	public void reset() {
		for(int i = 0; i < this.ancho; i++){
			for(int j = 0; j < this.alto; j++){
				tablero[i][j] = Ficha.VACIA;
			}
		}
	}
	//------------------------- RECIBIR ANCHURA ------------------------------------
	// Consulta el numero de columnas del tablero.
	public int getAncho() {
		return this.ancho;
	}
	
	//-------------------------- RECIBIR ALTURA ------------------------------------
	// Consulta el numero de filas del tablero.
	public int getAlto() {
		return this.alto;
	}
	
	//-------------------------- RECIBIR FICHA -------------------------------------
	// Consulta el color de una ficha en una casilla concreta.
	public Ficha getFicha(int col, int fila) {
		return tablero[col][fila];
	}
	
	//---------------------- COLOCAR FICHA -----------------------------------------
	// Coloca una ficha en el tablero.
	public void colocarFicha(int col, int fila, Ficha color) {
		this.tablero[col][fila] = color;
	}
}