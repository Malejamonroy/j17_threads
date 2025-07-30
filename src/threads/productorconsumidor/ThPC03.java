package threads.productorconsumidor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import threads.ThreadsUtil;

//vamos hacer una cola con las interfaces que ya implementan las interfaces adaptadas para trabajar con  hilos
public class ThPC03 {


	private static final int CAPACIDAD = 5;
	private static final LinkedBlockingQueue<Integer> buffer = new LinkedBlockingQueue<Integer>(CAPACIDAD); //esta preparada para bloquear y sincronizar los hilos y ademas tiene la capacidad 
	
	private static final int CANT_PROD = 3;
	private static final int CANT_CONS = 7;
	private static AtomicInteger valor = new AtomicInteger(0); 
	

	public static void main(String[] args) {
		//con esta interfaces nos ahorramos los controles por ellos ya lo hacen 
		
		ExecutorService executor = Executors.newFixedThreadPool(8); //este executor es fijo   Executor define cuantos hilos le pasamos o cuantos consumidores y productores tiene que tener el sistema operativo 
		
		System.out.println(Runtime.getRuntime().availableProcessors()); //cuantos nucleors hay disponibles en sistema operativo
		

		Runnable productor = () -> {
			while (true) {
				ThreadsUtil.sleep();
				int v = valor.getAndIncrement();
				buffer.add(v);
				System.out.println(Thread.currentThread().getName()+ "produjo " + v);
			}
		};
		// ahora hacemos el consumidor
		Runnable consumidor = () -> { 
			while (true) {
				ThreadsUtil.sleep();
				int v;
				
				try {
					v = buffer.take();
					System.out.println(Thread.currentThread().getName() + " consumidor " + v);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		};
		// creamos varios consumidores y varios productores con excecuter

		for (int i = 1; i <= CANT_PROD; i++) {
			executor.submit(productor); 

		}

		for (int i = 1; i <= CANT_CONS; i++) {
			executor.submit(consumidor);

		}

	}
}
