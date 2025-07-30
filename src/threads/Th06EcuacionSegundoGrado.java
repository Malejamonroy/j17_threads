package threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Th06EcuacionSegundoGrado {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		double a = 1;
		double b = 1;
		double c = 2;

		//calcular b^2
		CompletableFuture<Double> bCuadraro = CompletableFuture.supplyAsync(() -> { //cuadno termina la tarea entrega el resultdo
			ThreadsUtil.sleep(3000); //calcular b^2 demora 3 seg 
			System.out.println("Calculando b^2");
			
			return b * b;
		}); 
		
		//calcular 4 * a * c8
		CompletableFuture<Double> cuatroAC = CompletableFuture.supplyAsync(() -> {
			ThreadsUtil.sleep(4000);
			System.out.println("Calculando 4ac");
			return 4 * a * c;
		});
		
		//calcular el resultado del discriminante  => b^2 - 4ac
		// A bCuadrado lo convinamos con cuatroAC . esta operacion se realizara cuando los dos hayan finalizado 
		//vamos a usar  thenCombine y le pasaremos una BiFuntion  (tenemos un probedor sin parametros) no podemos asi que necitamos un bifunction  para que reciba 2 parametros que serias b^2 - 4ac y nos devuelve un discriminante o un resultado 
		//thenCombine = cuando termine las operaciones , combinelas 
		CompletableFuture<Double> discriminante = bCuadraro.thenCombine(cuatroAC, (b2,ac4) -> { //b2,ac4 nombre de variables que quieras
			ThreadsUtil.sleep(1000);
			System.out.println("calculando discriminante");
			//trabaamos con los discriminantes
			return b2 - ac4;
		});
		
		//vamos a calcular la raiz del disciminante  y si nos sale en negativo lanzamos una exception 
		//si el discrminante es menor que 0, lanzamos una exception
		CompletableFuture<Double> raizDiscriminante = discriminante.thenApply(d ->{
			ThreadsUtil.sleep(3000);
			if(d <0)
				throw new RuntimeException("Discriminante negativo no hay raices reales");
			System.out.println("calculando rais del discriminante");
			return Math.sqrt(d);
		});
		
		//calcular -b
		CompletableFuture<Double> menosB = CompletableFuture.supplyAsync(() ->{
			ThreadsUtil.sleep(500);
			System.out.println("Calculando -b");
			return -b;
		});
		

		//calcular 2a
		CompletableFuture<Double> dosA = CompletableFuture.supplyAsync(() ->{
			ThreadsUtil.sleep(750);
			System.out.println("Calculando 2a");
			return 2 *a;
		});
		
		//calcular x1 ( (-b + raiz(discriminante) / 2a )
		//combinar menosB con raizDiscriminante y al resultado(cuando termine) lo combinamos con dosA
		CompletableFuture<Double> x1 = menosB.thenCombine(raizDiscriminante, (mB,raiz) ->{
			ThreadsUtil.sleep(500);
			System.out.println("Calculando x1");
			return mB + raiz;
		}).thenCombine(dosA, (num,dA) -> num/dA);
		
		//calcular x2 ( (-b - raiz(discriminante) / 2a )
		//combinar menosB con raizDiscriminante y al resultado(cuando termine) lo combinamos con dosA
		CompletableFuture<Double> x2 = menosB.thenCombine(raizDiscriminante, (mB,raiz) ->{
			ThreadsUtil.sleep(500);
			System.out.println("Calculando x2");
			return mB - raiz;
		}).thenCombine(dosA, (num,dA) -> num/dA);
		
		System.out.println("x1 = " + x1.get());
		System.out.println("x2 = " + x2.get());
		
	}

}
