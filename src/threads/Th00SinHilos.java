package threads;

public class Th00SinHilos {
	
	
	private String mensaje;
	
	public Th00SinHilos(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
	public void run() {
		while (true) {
			System.out.println(mensaje);
		}
	}
	public static void main(String[] args) {
		Th00SinHilos h1 =new Th00SinHilos("Soy el primero");
		h1.run();
		
		Th00SinHilos h2 =new Th00SinHilos("Soy el segundo");
		h2.run();
		
		Th00SinHilos h3 =new Th00SinHilos("Soy el tercero");
		h3.run();
		
	}

}
