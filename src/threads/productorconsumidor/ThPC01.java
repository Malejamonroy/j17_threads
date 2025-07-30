package threads.productorconsumidor;

import java.util.LinkedList;
import java.util.Queue;

import threads.ThreadsUtil;
//vamos hacer una cola como la del super o simularla 
public class ThPC01 {

	private static final Queue<Integer> buffer = new LinkedList<Integer>();
	private static final int CAPACIDAD = 5;
	private static int valor = 0;
	
	public static void main(String[] args) {
		 //buffer.peek() busca datos en la cola , buffer.poll() saca datos de la cola
		
		Runnable productor = () -> {
			while(true) {
				ThreadsUtil.sleep();//tiempo previo para que arranque a producir
				if(buffer.size() < CAPACIDAD){//si el baffer es menor que capacidad 
					buffer.offer(valor); //guardo un 0
					System.out.println(Thread.currentThread().getName()+ " produjo " + valor);
					valor++;
					
					//Control innecesario
					if (buffer.size() > CAPACIDAD) {
						System.out.println(" Error se produjeron " + buffer.size());
						System.exit(1);
					}
				}
				
			}
		};
		
		//ahora hacemos el consumidor
		Runnable consumidor = () ->{ //el consumidor solo se fija si tiene algo (la fila) para llevarselo 
			while (true) {
				ThreadsUtil.sleep();
				if(buffer.size() > 0) { //si hay algo?
					int v = buffer.poll();
					System.out.println(Thread.currentThread().getName() + " consumio " + v);
					
					//control inneseario
					if(buffer.size()<0) {
						System.err.println("Error, el tamaño del buffer es " + buffer.size());
						System.exit(1);
					}
				}
				
			}
		};
		
		//creamos los hilos
		
		new Thread(productor, "productor" ).start();
		new Thread(consumidor, "consumidor" ).start();
		
	}
}
