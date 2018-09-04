package packclass;
public class Partida {
	
	// Atributos privados
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada;
	private Ficha ganador;
	private Pila pila;
	private int ancho = 7;
	private int alto = 7;
		
	public Partida() {
		tablero = new Tablero(ancho, alto);
		turno = Ficha.BLANCA;
		terminada = false;
		pila = new Pila();
	}
	
	// ------------------------- RESET -------------------------------------
	// Reinicia la partida por completo
	public void reset() {
		tablero.reset();
		terminada = false;
		pila = new Pila();
	}
	// ----------------------------- TABLAS --------------------------------
	// Comprueba si la partida ha quedado en tablas
	public boolean tablas() {
		boolean tbls = comprobarTablas();
		return tbls;
	}
	
	// ----------------------------------- CAMBIAR TURNO ---------------------------------
	// Cambia el color de la ficha a introducir según el turno que corresponda.
	public Ficha cambiaTurno() {
		if (turno == Ficha.NEGRA || turno == Ficha.VACIA) {
			turno = Ficha.BLANCA;
		}
		else {
			turno = Ficha.NEGRA;
		}
		return turno;
	}
	
	// ---------------------------------- COMPROBAR COLUMNA --------------------------------------
	// Comprueba si hay fichas en la columna para decidir en qué fila introducir la nueva ficha.
	private int comprobarCol(int col) {
		boolean encontrado = false;
		int fila = 0;
		// Si la columna introducida no es válida, no hace nada y devuelve -1.
		if(col < 0 || col >= tablero.getAncho()){
			fila = -1;
		}
		// Si la columna introducida es válida, se prosigue con la ejecución:
		else{
			while(!encontrado && fila < this.tablero.getAlto()){
				// En el caso de que se encuentre una ficha blanca o negra en la columna introducida, se coloca la siguiente justo encima.
				if (this.tablero.getFicha(col, fila) == Ficha.BLANCA ||this.tablero.getFicha(col, fila) == Ficha.NEGRA){
					encontrado = true;
					fila--;
				}
				// Si no se encuentra ninguna ficha en toda la columna, se coloca la ficha en la casilla de más abajo.
				else if (fila == tablero.getAlto() - 1){
					encontrado = true;
				}
				// Si no se encuentra una ficha en esa fila, se pasa a la siguiente.
				else {
					fila++;
				}
			}
		}
		return fila;
	}
	
	// ------------------------------------- EJECUTAR MOVIMIENTO ----------------------------------------
	// Coloca una ficha en el tablero y comprueba si se acaba la partida o no.
	public boolean ejecutaMovimiento(Ficha color, int col) {
		//Primero comprobamos si la columna introducida es válida
		int fila = comprobarCol(col);
		// Si lo es: se coloca la ficha y se añade a la pila.
		if(fila != -1){
			tablero.colocarFicha(col, fila, color);
			pila.poner(col);
			// A continuación se comprueba si se forma una cadena de cuatro fichas iguales en vertical, horizontal o diagonal.
			if(comprobarColumna(col, fila)){
				this.terminada = true;
			}
			else if(comprobarFila(col, fila)){
				this.terminada = true;
			}
			else if(comprobarDiagonalDescendente(col,  fila)){
				this.terminada = true;
			}
			else if(comprobarDiagonalAscendente(col, fila)){
				this.terminada = true;
			}
			// En caso de no encontrar ninguna, se devuelve un valor falso y se prosigue con el juego.
			else{
				this.terminada = false;
			}
		}
		else{
			System.out.println("La columna introducida no es válida");
		}
		return this.terminada;
	}
	// ---------------------------------------- DECIDIR GANADOR ------------------------------------------
	// Decide el ganador de la partida según la útima ficha introducida.
	public Ficha ganadorPartida(){
		ganador = turno;
		return ganador;
	}
	
	// ------------------------------------- DESHACER MOVIMIENTO -----------------------------------------
	// Deshace la última jugada. Evidentemente, solo en el caso de que se haya realizado alguno previamente.
	public boolean deshacer() {
		boolean deshecho = false;
		int ret = pila.sacar();
		if(ret != -1){
			int fila = comprobarCol(ret);
			tablero.colocarFicha(ret, fila + 1, Ficha.VACIA); //Uso fila - 1, porque tenemos que sobreescribir la casilla.
			deshecho = true;
		}
		return deshecho;
	}
	
	// -------------------------------------- COMPROBAR FILA --------------------------------------------
	// Comprueba si se han enlazado cuatro fichas del mismo color en una fila del tablero.
	private boolean comprobarFila(int col, int fila){
		boolean hayCuatro = false;
		int contFichas = 1, colAux = col;
		// Va sumando mientras las fichas en columnas consecutivas sean iguales y no se salga del tablero...
		while (!hayCuatro && (colAux < tablero.getAncho() - 1) && (tablero.getFicha(col, fila) == tablero.getFicha(colAux+1, fila))) {
			contFichas++;
			colAux++;
			// Si encuentra 4 fichas seguidas, se pone a true.
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		//Reiniciamos colAux a su valor original.
		colAux = col;
		// Repite el procedimiento con columnas anteriores.
		while(!hayCuatro && (colAux > 0) && (tablero.getFicha(col, fila) == tablero.getFicha(colAux-1, fila)) ){
			contFichas++;
			colAux--;
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		return hayCuatro;
	}
	
	// ----------------------------------- COMPROBAR COLUMNA ----------------------------------
	// Comprueba si hay cuatro fichas del mismo color en una columna del tablero.
	private boolean comprobarColumna(int col, int fila){
		boolean hayCuatro = false;
		int contFichas = 1, filaAux = fila;
		// Va sumando mientras las fichas en filas consecutivas sean del mismo color y no se salga del tablero...
		while (!hayCuatro && (filaAux < tablero.getAlto() - 1) && (tablero.getFicha(col, fila) == tablero.getFicha(col, filaAux+1))) {
			contFichas++;
			filaAux++;
			// Si encuentra cuatro fichas seguidas, se pone a true.
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		//Reiniciamos filaAux a su valor original
		filaAux = fila;
		// Realiza el mismo procedimiento con filas anteriores.
		while(!hayCuatro && (filaAux > 0) && (tablero.getFicha(col, fila) == tablero.getFicha(col, filaAux-1))){
			contFichas++;
			filaAux--;
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		return hayCuatro;
	}
		
	// --------------------------------------- COMPROBAR DIAGONAL DESCENDENTE-------------------------------
	// Comprueba si hay cuatro fichas del mismo color consecutivas en una diagonal del tablero.
	private boolean comprobarDiagonalDescendente(int col, int fila){
		boolean hayCuatro = false;
		int contFichas = 1, colAux = col, filaAux = fila;
		// Va sumando si encuentra fichas del mismo color consecutivas avanzando una fila y una columna y no se sale del tablero...
		while(!hayCuatro && (colAux < tablero.getAncho() - 1) && (filaAux < tablero.getAlto() - 1) && (tablero.getFicha(col, fila) == tablero.getFicha(colAux+1, filaAux+1))){
			contFichas++;
			colAux++;
			filaAux++;
			// Si encuentra cuatro fichas consecutivas, se pone a true.
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		//Reiniciamos el valor de colAux y filaAux a su valor original.
		colAux = col;
		filaAux = fila;
		// Realiza el mismo procedimiento con filas y columnas anteriores.
		while(!hayCuatro && (colAux > 0) && (filaAux > 0) && (tablero.getFicha(col, fila) == tablero.getFicha(colAux-1, filaAux-1))){
			contFichas++;
			colAux--;
			filaAux--;
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		return hayCuatro;
	}
	
	// --------------------------------------- COMPROBAR DIAGONAL ASCENDENTE-------------------------------
	// Comprueba si hay cuatro fichas del mismo color consecutivas en una diagonal del tablero.
	private boolean comprobarDiagonalAscendente(int col, int fila){
		boolean hayCuatro = false;
		int contFichas = 1, colAux = col, filaAux = fila;
		// Va sumando si encuentra fichas del mismo color consecutivas avanzando una fila y una columna y no se sale del tablero...
		while(!hayCuatro && (colAux < tablero.getAncho() - 1) && (filaAux > 0) && (tablero.getFicha(col, fila) == tablero.getFicha(colAux+1, filaAux-1))){
			contFichas++;
			colAux++;
			filaAux--;
			// Si encuentra cuatro fichas consecutivas, se pone a true.
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		//Reiniciamos el valor de colAux y filaAux a su valor original.
		colAux = col;
		filaAux = fila;
		// Realiza el mismo procedimiento con filas y columnas anteriores.
		while(!hayCuatro && (colAux > 0) && (filaAux < tablero.getAlto() - 1) && (tablero.getFicha(col, fila) == tablero.getFicha(colAux-1, filaAux+1))){
			contFichas++;
			colAux--;
			filaAux++;
			if(contFichas == 4){
				hayCuatro = true;
			}
		}
		return hayCuatro;
	}
	
	//---------------------COMPROBAR SI HAY TABLAS----------------------------------
	//Cuenta el número de posiciones ocupadas en el tablero, para ver si hay tablas.
	private boolean comprobarTablas(){
		boolean tablas = false;
		int c_ocupadas = 0;
		for(int i = 0; i < tablero.getAncho(); i++){
			for(int j = 0; j < tablero.getAlto(); j++){
				if(tablero.getFicha(i, j) == Ficha.BLANCA || tablero.getFicha(i, j) == Ficha.NEGRA){
					c_ocupadas = c_ocupadas + 1;
				}
			}
		}
		if(c_ocupadas == tablero.getAncho()*tablero.getAlto()){
			tablas = true;
		}
		return tablas;
	}
	
	// ---------------------------- DIBUJAR TABLERO ----------------------------
	// Dibuja el tablero colocando las fichas correspondientes.
	public void dibujarTablero() {
		int fila = 1, columna = 1;
		Ficha aux;
		String string = "";
		for (int c = 0; c < tablero.getAncho(); c++) {
			string = string + fila;
			for (int f = 0; f < tablero.getAlto(); f++) {
				aux = tablero.getFicha(f, c);
				string = string + " | " + aux.toString();
			}
			string = string + " |\n";
			fila++;
		}
		for (int a = 0; a < tablero.getAncho(); a++) {
			string = string + "   " + columna;
			columna++;
		}
		string = string + "   ";
		System.out.println(string);
	}
} 

