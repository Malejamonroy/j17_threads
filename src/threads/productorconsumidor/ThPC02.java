package threads.productorconsumidor;

import java.util.LinkedList;
import java.util.Queue;

import threads.ThreadsUtil;

//vamos hacer una cola como la del super o simularla 
public class ThPC02 {

	private static final Queue<Integer> buffer = new LinkedList<Integer>();
	private static final int CAPACIDAD = 5;
	private static final int CANT_PROD = 3;
	private static final int CANT_CONS = 7;
	private static int valor = 0;
	private static final Object LOCK = new Object();

	public static void main(String[] args) {
		// buffer.peek() busca datos en la cola , buffer.poll() saca/extrae datos de la
		// cola

		Runnable productor = () -> {
			while (true) {
				ThreadsUtil.sleep();// tiempo previo para que arranque a producir
				// creamos un bloque sincronizado que lo maneje un LOCK
				synchronized (LOCK) {// con el lock podemos porner los hilos en espera
					while (buffer.size() == CAPACIDAD) {
						try {
							LOCK.wait(); // ponemos a esperar al consumidor hasta que tengamos hueco y podemos coger de
											// la pila
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// cuando se consuma
					buffer.offer(valor);
					System.out.println(Thread.currentThread().getName() + " produjo " + valor);
					valor++;
					// el que avisa a los hilos que el control ha cambiado (que hay algo para
					// cunsumir)
					LOCK.notifyAll();

					// Control innecesario
					if (buffer.size() > CAPACIDAD) {
						System.err.println(" Error se produjeron " + buffer.size());
						System.exit(1); // para aplicacion
					}
				}
			}
		};
//		//como lo teniamos 
//		//ahora hacemos el consumidor
//		Runnable consumidor = () ->{ //el consumidor solo se fija si tiene algo (la fila) para llevarselo 
//			while (true) {
//				ThreadsUtil.sleep();
//				if(buffer.size() > 0) { //si hay algo?
//					//ThreadsUtil.sleep(); //demora en consumir 
//					int v = buffer.poll(); //no se puede poner a dormir ahi porque hay una exception 
//					System.out.println(Thread.currentThread().getName() + " consumio " + v);
//					
//					//control inneseario
//					if(buffer.size()<0) {
//						System.err.println("Error, el tamaño del buffer es " + buffer.size());
//						System.exit(1); //para la aplicacion
//					}
//				}
//				
//			}
//		};
//		
//		//con el control synchronized 
		// ahora hacemos el consumidor
		Runnable consumidor = () -> { // el consumidor solo se fija si tiene algo (la fila) para llevarselo
			while (true) {
				ThreadsUtil.sleep();
				synchronized (LOCK) {
					while (buffer.size() == 0) {
						try {
							LOCK.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					int v = buffer.poll(); // no se puede poner a dormir ahi porque hay una exception
					System.out.println(Thread.currentThread().getName() + " consumio " + v);
					LOCK.notifyAll();

					// control inneseario
					if (buffer.size() < 0) {
						System.err.println("Error, el tamaño del buffer es " + buffer.size());
						System.exit(1); // para la aplicacion
					}
				}

			}

		};
		// creamos varios consumidores y varios productores

		for (int i = 1; i <= CANT_PROD; i++) {
			new Thread(productor, " productor " + i).start();

		}

		for (int i = 1; i <= CANT_CONS; i++) {
			new Thread(consumidor, " consumidor " + i).start();

		}

	}
}
