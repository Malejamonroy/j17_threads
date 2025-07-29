package threads;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Ventana extends JFrame {
	
	public Ventana () {
		this.setBounds(20, 20, 800, 600);
		this.setVisible(true);
	
		
		//esto es un evento 
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("me voy");
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		 new Ventana();
		 int cont = 0;
		 
		 while(true) {
			 
			 //demora
			 for (int i = 0; i < 1000_000; i++) ;
			 
			 System.out.println(cont++);
		 }
	}

}
