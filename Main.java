package packclass;
public class Main {
	
	
	public static void main(String[] args) {
		// El main crea los objetos para partida y controlador e inicia la ejecución del programa.
		Controlador controlador = new Controlador(new Partida());
		controlador.run();
	}

}
