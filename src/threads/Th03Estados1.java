package threads;

public class Th03Estados1 {

	public static void main(String[] args) throws InterruptedException {
		//creamos un hilo
		Thread th1 = new Thread();
		System.out.println(th1.getState());//que nos muestre el estado --> en este caso esta en new solo creado
		
		//ejecutamos para que pase el siguiente estado
		th1.start();
		System.out.println(th1.getState()); //runnable
		
		//ponemos a DORMIR EL HILO ACTUAL(el main) 200 mili segundos para que podamos ver como termina y aparece el ultimo estado
		Thread.sleep(200);
		
		
		//estado termiando 
		System.out.println(th1.getState()); //terminated
		
	}

}
