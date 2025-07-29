package threads;

public class Th05Estados2 {

	public static void main(String[] args) {
		
		Object lock =new Object();
		
		
		//hilo en estado  WAITING  
		
		Thread th1 = new Thread(()->{
			synchronized (lock) {
				try {
					lock.wait();  //una espera indefinida. Hasta que otro hijo ejecute notify() o notifyAll()
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		},"primero");//se le puede asignar un nombre a los hilos 
		
		
		Thread th2 = new Thread(()->{
			ThreadsUtil.sleep(5000);//5 segundos
		}, "segundo");
		
		th1.start();
		th2.start();
		
		System.out.println(th1.getName() + ": " + th1.getState());   //Thread-0: WAITING
		System.out.println(th2.getName() + ": " + th2.getState());   // Thread-1: TIMED_WAITING  o con nombre :  segundo: TIMED_WAITING
	}

}
