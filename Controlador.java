package packclass;

import java.util.Scanner;

public class Controlador {
	
	private Partida partida;
	private Scanner in;
	
	public Controlador(Partida partida){
		this.partida = partida;
		this.in = new java.util.Scanner(System.in);
	}
	
	// ----------------------------- EJECUCIÓN DEL PROGRAMA ---------------------------------------
	public void run(){
		// Variables locales
		Ficha color = Ficha.BLANCA, winner;
		int col;		
		boolean conecta4 = false;
		
		// Presenta la información del principio de la partida.
		partida.dibujarTablero();
		System.out.println("Juegan Blancas");
		System.out.println("salir / poner / deshacer / reiniciar");
		System.out.println("Que quieres hacer?: ");
		// Se solicita al usuario que introduzca la acción a realizar.
		String orden = this.in.nextLine();
		
		while(!orden.equalsIgnoreCase("salir")){
			// Si el usuario elige "poner"...
			if(orden.equalsIgnoreCase("poner")){
				// ...se le pide que introduzca la columna en la que quiere introducir la ficha.
				System.out.println("Elige una columna: ");
				col = this.in.nextInt() - 1;
				//Llamamos a ejecutaMovimiento, que se encarga de controlar cada jugada
				if(partida.ejecutaMovimiento(color, col)){
					winner = partida.ganadorPartida();
					if(winner == Ficha.BLANCA){
						System.out.println("Ganan las Blancas");
					}
					else{
						System.out.println("Ganan las Negras");
					}
					conecta4 = true;
				}
				// ...y si no gana, se comprueba si se producen tablas.
				else {
					if(partida.tablas()){
						conecta4 = true;
						System.out.println("La partida ha acabado en tablas");
					}
					//si no hay tablas, se cambia el turno para el siguiente jugador
					else{
						color = partida.cambiaTurno();
					}
				}
			}	
			// Si el usuario elige "deshacer"... 
			else if(orden.equalsIgnoreCase("deshacer")){
				//...si no existe movimiento previo que deshacer.
				if(!partida.deshacer()){
					System.out.println("Imposible deshacer");
				}
				//...si se ha podido, se le informa.
				else{
					System.out.println("Se ha deshecho el ultimo movimiento");
					//...y se cambia el turno.
					partida.cambiaTurno();
				}
			}
			
			// Si el usuario elige "reiniciar"...
			else if(orden.equalsIgnoreCase("reiniciar")){
				// ...se resetea el tablero por completo y se avisa al usuario.
				partida.reset();
				System.out.println("Tablero reiniciado");
			}
			
			// Si el usuario introduce cualquier instrucción que no hayamos contemplado, muestra un mensaje de error.
			else{
				System.out.println("La orden introducida no existe");
			}
		
			// Si nadie ha ganado, y tampoco hay tablas: la partida continua.
			if(!conecta4){
				partida.dibujarTablero();
				// Se indica al jugador quién juega.
				if(color == Ficha.BLANCA){
					System.out.println("Juegan Blancas");
				}
				else{
					System.out.println("Juegan Negras");
				}
				System.out.println("salir / poner / deshacer / reiniciar");
				System.out.println("Que quieres hacer?: ");
				// Se vuelve a introducir una instrucción y se repite todo el proceso de nuevo.
				orden = this.in.next();
			}
			else{
				partida.dibujarTablero();
				orden = "salir";
			}
		}
	}
}