package threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//los hilos estan desincronizados totalmente 
public class Th04Sincronizacion {
		public static void main(String[] args) {
//			m1();
			
			//m1() CASO 1 Hilos desincronizados
//			Thread th1 = new Thread(()->{				//Thread : espera un rennable (una interfaz funcional) con una expresion "  ()->{}  "landan se puede pasar el rannable y es una programacion funcional 
//
//			while (true) {
//				ThreadsUtil.sleep();
//				m1();
//			}
//			});
//			
//			//dos que llamen al main (m1)
//			Thread th2 = new Thread(()->{
//			while (true) {
//				ThreadsUtil.sleep();
//				m1();
//			}
//			});
//			
//			th1.start();
//			th2.start();
//			
//			
//			//caso  2  hilos sincronizados 
//			
//			Thread th1 = new Thread(()->{				
//
//				while (true) {
//					ThreadsUtil.sleep();
//					m2();
//				}
//				});
//				
//
//				Thread th2 = new Thread(()->{
//				while (true) {
//					ThreadsUtil.sleep();
//					m2();
//				}
//				});
//				
//				th1.start();
//				th2.start();
//				
//				
//				//caso  3  Bloqueo un pedazo del codigo y lo sincronizamos 
//				Object lock = new Object();
//				Thread th1 = new Thread(()->{				
//
//					while (true) {
//						ThreadsUtil.sleep();
//						m3(lock);
//					}
//					});
//					
//
//					Thread th2 = new Thread(()->{
//					while (true) {
//						ThreadsUtil.sleep();
//						m3(lock);
//					}
//					});
//					
//					th1.start();
//					th2.start();
			
			//caso  4   FORMA MAS VERSATIL
			Lock lock = new ReentrantLock();
			Thread th1 = new Thread(()->{				

				while (true) {
					ThreadsUtil.sleep();
					m4(lock);
				}
				});
				

				Thread th2 = new Thread(()->{
				while (true) {
					ThreadsUtil.sleep();
					m4(lock);
				}
				});
				
				th1.start();
				th2.start();
		}

		public static void m1() {
			System.out.println(Thread.currentThread().getName()+ "entrando");
			ThreadsUtil.sleep();
			System.out.println(Thread.currentThread().getName()+ "saliendo");
			System.out.println("----------------------");
		}

	
		public static synchronized void m2() {  //synchronized  SINCRONIZA LOS HILOS tiene limitaciones:  que lo limita todo  //aqui el que gestiona es java 
			System.out.println(Thread.currentThread().getName()+ "entrando");
			ThreadsUtil.sleep();
			System.out.println(Thread.currentThread().getName()+ "saliendo");
			System.out.println("----------------------");
		}

		public static void m3(Object lock) {  //para que bloquee un trozo del codigo necesitamos on objeto que se haga cargo del bloqueo como un MONITOR y lo puede hacer cualquier objeto java 
			System.out.println(Thread.currentThread().getName() + "hace cosas");
			
			synchronized (lock) { //aqui el objeto es el controla y lo hacemos como el monitor
				System.out.println(Thread.currentThread().getName()+ "entrando");
				ThreadsUtil.sleep();
				System.out.println(Thread.currentThread().getName()+ "saliendo");
				System.out.println("----------------------");	
			}
			
		}
	
		
		public static void m4(Lock lock) {  //forma mas versátil de hacer el bloqueo de codigo OJOOOOO TIENE QUE PONER EL INICIO Y EL FIN DEL BLOQEO..
			System.out.println(Thread.currentThread().getName() + "hace cosas");
			
			lock.lock(); //inicio del bloqueo (bloquear)
				System.out.println(Thread.currentThread().getName()+ "entrando");
				ThreadsUtil.sleep();
				System.out.println(Thread.currentThread().getName()+ "saliendo");
				System.out.println("----------------------");	
			lock.unlock();// ((desbloquear) fin del bloqueo 
			
		}

}
