package threads;

public class Th01Threds  extends Thread{
	
	
	private String mensaje;
	
	public Th01Threds(String mensaje) {
		this.mensaje = mensaje;
	}

	
	@Override
	public void run() {
		while (true) {
			System.out.println(mensaje);
		}
	}
	public static void main(String[] args) {
		Th01Threds h1 =new Th01Threds("Soy el primero");
		h1.start();
		
		Th01Threds h2 =new Th01Threds("Soy el segundo");
		h2.start();
		
		Th01Threds h3 =new Th01Threds("Soy el tercero");
		h3.start();
		
	}

}
