package threads.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ServidorChat {
	
	private int puerto;
	
	public ServidorChat(int puerto) {
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
		private String user;
		private PrintWriter out;
		
		private static int cant;
		private static Map<String, AtiendeCliente> sala = new HashMap<>();
		
		public AtiendeCliente(Socket socket) {
			this.socket = socket; //
			new Thread(this).start(); //aqui crea el nuevo hilo y vuelve a escuchar conexiones 
		}
		@Override
		public void run() {
			
			log ("nuevo cliente conectado");
			
			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true) ;
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				
				this.out =out; //esta guardado como atributo de clase
					
				//hacemos un servidor eco (lo que escribimos, el no lo escribe de nuevo)
				out.println(" Excelente Alumna... APROBADA!!!"); //mensaje que el otro ve
				
				out.println(" Quien es ud???");
				user = in.readLine();//para recibir el usuario 
				user = user.replaceAll(" ", "_"); //si se identifiaca con el nombre completo que le ponga los _
				
				while (sala.containsKey(user) || user.length() == 0) {
					out.println(" Ese noo!! escriba bien");
					out.println(" Identificate bien ");
					user = in.readLine();//para recibir el usuario 
	
					
				}
				
				out.println(user + " Abrobados todos... ganamos el curso "); //ya esta en la sala 
				help(); //una vez identificado que le usuario que le mande la ayuda de los comandos 
				sala.put(user,this);
				cant++;
				log(user + " se ha conectado"); //consola quien se ha conectado 
				log("hay" + cant + " usuarios en la sala "); //consola cuantos usuarios se han conectado
				difusion("SRV: " + user + " Bienvendio a la Mejor Clase "); //mensaje que el el usuario 
				
				String linea;
				while ((linea = in.readLine()) != null) {
					
					if(linea.length() > 0 && linea.charAt(0) == '@') { //controlar que no mande nada vacio y que tenga un @ si lo de adelante no esta vacio //separa el numbre de usuario y el reto que es el mensaje //mensaje privado
						if(linea.contains(" ")) {
							String usrDestino = linea.substring(1,linea.indexOf(" ")); //
							String mensaje = linea.substring(linea.indexOf(" ")+ 1); 
							if(sala.containsKey(usrDestino)){//si existe el usuarioo
								sala.get(usrDestino).out.print("PRIVADO DE " + user + ": " + mensaje);//mandamos el mensaje 
							}else {
								out.println(usrDestino + " no está conectado");
							}
						}else {
							out.println("Formato incorrecto. No se ha enviado el mensaje");
						}
						
						
					}else {// no es un mensaje privado
						switch (linea.toLowerCase()) {
						case "-w", "-who":
							for (String usr : sala.keySet()) {
								out.println("SRV: " + usr);
							}
							break;
						case "-h", "-help":
							help();
							break;
						default:
							difusion(user + ": " + linea);
						}
			
					}
					
					//out.println("SRV: " + linea);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		private void log(String mje) {
			System.out.println(now()+" - "+ mje);
		}
		
		private String now() {
			LocalDateTime ahora = LocalDateTime.now();//LocalDateTime = Clase que nos permite guardar dia y hora fecha horarias //LocalDateTime.now() = hora actual 
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss"); //darle un formato 
			return dtf.format(ahora); //formateame esta fecha y hora 
		}
		
		
		private void difusion(String mje) {
			for (AtiendeCliente cli : sala.values()) {
				cli.out.println(mje);
			}
		}
		private void help() {
			out.println("----------------------------------------- ");
			out.println("Ayuda del Curso");
			out.println("----------------------------------------- ");
			out.println(" -q: terminar sesion");
			out.println(" -h: mostrar esta ayuda");
			out.println(" -w: consultar usuarios");
			out.println(" @Usuario mensaje: para mensaje privados");
			out.println("----------------------------------------- ");

			
		}
	}
	
	
	public static void main(String[] args) {
		new ServidorChat(1234).start();
	}
}
