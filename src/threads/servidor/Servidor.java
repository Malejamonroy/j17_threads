package threads.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private int puerto;
	
	public Servidor(int puerto) {
		this.puerto = puerto;
	}

	public void start() { //lavanta el servidor y escucha conexiones 
		System.out.println("servidor esperando conexiones");
		try(ServerSocket server = new ServerSocket(puerto)) {
			
			while (true) {
				Socket Cliente = server.accept(); //cuadno recibe una concexion con el accept es crear un objeto socket
				//atender al cliente
				new AtiendeCliente(Cliente);
			}
			
		} catch (Exception e) {
			System.out.println(" No se puede abrir el puerto" + puerto);
		}
		
	}
	
	//hacemos una clase interna bonita 
	private class AtiendeCliente implements Runnable{

		private Socket socket;
		public AtiendeCliente(Socket socket) {
			this.socket = socket; //
			new Thread(this).start(); //aqui crea el nuevo hilo y vuelve a escuchar conexiones 
		}
		@Override
		public void run() {
			//infraesctructura para habalar y escribir
			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true) ;
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
					
				//hacemos un servidor eco (lo que escribimos no lo escriba de nuevo )
				out.println("Excelente Alumna... APROBADA");
				
				String linea;
				while ((linea = in.readLine()) != null) {
					out.println("SRV: " + linea);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	public static void main(String[] args) {
		new Servidor(1234).start();
	}
}
