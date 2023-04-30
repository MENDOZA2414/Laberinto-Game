import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.util.Random;

public class Laberinto {
	
	private JFrame frame;
	private JLabel tiempoNum = new JLabel("00:00:00");;
	
    private int tamaño;
    private int ancho;
    private int alto;
    private int movimiento;
    
	public int player_x;
	public int player_y;
	public int last_prest;
	private Timer tiempo;
	private  Clip clip;
	private String sonido = "silencio.png";
	private Random random = new Random();
	private int nivelAleatorio = random.nextInt(2) + 1;

	private boolean mute = true;
	private int centecimas = 0, segundos = 0, minutos = 0, horas = 	0;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Laberinto window = new Laberinto();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Laberinto() {
		initialize();
		try {
			sonido();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize() {
		tiempo = new Timer(10,accion);
		frame = new JFrame();
		frame.setBounds(0, 0, 525, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		JPanel juego = new JPanel();
		juego.setBackground(Color.BLACK);
		frame.getContentPane().add(juego, BorderLayout.CENTER);
		juego.add(new MyGraphics());
		System.out.println(nivelAleatorio);
		if(nivelAleatorio == 1) {
       	 tamaño = 25;
       	 ancho = 25;
       	 alto = 25;
       	 movimiento = 25;

       	 player_x = 0;
       	 player_y = 125;

       }
       if(nivelAleatorio == 2) {
       	tamaño = 20;
       	 ancho = 20;
       	 alto = 20;
       	 movimiento = 20;

       	 player_x = 20;
       	 player_y = 20;
       }
		juego.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println();
				last_prest = e.getKeyCode();
	
				if(last_prest == 87) { //ARRIBA
					if(matrizLaberinto()[player_y/tamaño-1][player_x/tamaño]!=1) { 
						player_y -= movimiento;
					}
						System.out.println("W");
				}
				if(last_prest == 83 ) {  //ABAJO
					if(matrizLaberinto()[player_y/tamaño+1][player_x/tamaño]!=1) { 
						player_y += movimiento;
					}
					System.out.println("S");
				}
				if(last_prest == 68 && player_x!=425) { //DERECHA (D)
					if(matrizLaberinto()[player_y/tamaño][player_x/tamaño+1]!=1) { 
						player_x += movimiento;
					}
						System.out.println("D");
						System.out.println(player_x);
					
				}
				if(last_prest == 65 && player_x!=0) { //IZQUIERDA (A)
					if(matrizLaberinto()[player_y/tamaño][player_x/tamaño-1]!=1) { 
						player_x -= movimiento;
					}
					System.out.println("A");
				}
				juego.repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		juego.setFocusable(true);
		
		JPanel levelPanel = new JPanel();
		levelPanel.setBackground(Color.BLACK);
		frame.getContentPane().add(levelPanel, BorderLayout.NORTH);
		levelPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		
		ImageIcon imagenMenu = new ImageIcon("menu.png");
		Image imagenRedimensionada = imagenMenu.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon imagenMenuRedimensionada = new ImageIcon(imagenRedimensionada);
		JButton btnOpciones = new JButton(imagenMenuRedimensionada);
		
		btnOpciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu();
			}
		});
		
		JLabel levelText = new JLabel("  Level " + nivelAleatorio);
		levelText.setForeground(new Color(202, 14, 3));
		levelText.setHorizontalAlignment(SwingConstants.CENTER);
		levelText.setFont(new Font("Marker Felt", Font.PLAIN, 34));
		levelPanel.add(levelText);
		levelPanel.add(tiempoNum);
		
		tiempoNum.setHorizontalAlignment(SwingConstants.CENTER);
		tiempoNum.setFont(new Font("Marker Felt", Font.PLAIN, 40));
		tiempoNum.setForeground(new Color(87, 201, 0));
		btnOpciones.setContentAreaFilled(false);
	    btnOpciones.setBorderPainted(false);
		
		levelPanel.add(btnOpciones);
		btnOpciones.setFocusable(false);
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		ImageIcon imagenReiniciar = new ImageIcon("reiniciar.png");
		panel.setLayout(new BorderLayout(0, 0));
		JButton btnReiniciar = new JButton(imagenReiniciar);
		
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciar(juego);
			}
		});
		btnReiniciar.setContentAreaFilled(false);
		btnReiniciar.setBorderPainted(false);
		
		panel.add(btnReiniciar, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel.add(panel_1, BorderLayout.SOUTH);
		tiempo.start();
		
		
	}
	
	public void menu() {
		ImageIcon iconoSonido = new ImageIcon(sonido);
		ImageIcon iconoSalir = new ImageIcon("salir.png");
		ImageIcon iconoAtras = new ImageIcon("atras.png");
		
		JLabel mensaje = new JLabel("         MENU");
		mensaje.setForeground(new Color(202, 14, 3));
		mensaje.setFont(new Font("Marker Felt", Font.PLAIN, 35));
		
		Object[] opciones = {iconoAtras, iconoSonido, iconoSalir};
        int seleccion = JOptionPane.showOptionDialog(null, mensaje, "Ve detrás tuyo", JOptionPane.NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        
        switch(seleccion){
        	case 0:
        		break;
        		
	        case 1:
	        	if(mute) {
        			clip.stop();
					sonido = "volumen.png";
					mute = false;
				}
        		else {
        			clip.start();
        			sonido = "silencio.png";
        			mute = true;
        		}
        		break;
  
        	case 2: 
        		System.exit(0);
        		break;
        }
	}
	
	public void reiniciar(JPanel panel) {
		player_x = 0;
		player_y = 125;
		panel.setFocusable(true);
		panel.requestFocus();
		panel.repaint();
	}
	
	public class MyGraphics extends JComponent {
		
		private static final long serialVersionUID = 1L;
		
		MyGraphics(){
			if(nivelAleatorio == 2) {
				setPreferredSize(new Dimension (430,650));
			}else {
				setPreferredSize(new Dimension (450,650));
			}
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
	        //Laberinto 
	        g.setColor(new Color(118,228,84));
	        for (int i = 0; i < matrizLaberinto().length; i++) {
	            for (int j = 0; j < matrizLaberinto()[i].length; j++) {
	                if (matrizLaberinto()[i][j] == 1) {
	                	
	                    g.fillRect(j * tamaño, i * tamaño, ancho, alto);
	                }
	            }
	        }
	       
	        //player
	        g.setColor(Color.white);
	        g.fillOval(player_x,player_y,tamaño,tamaño);
	        g.setColor(Color.red);
	        g.drawOval(player_x,player_y,tamaño,tamaño);

	        g.setColor(Color.BLACK);
	        g.fillOval(player_x+12, player_y+4, 7, 7);
	        g.fillOval(player_x+3, player_y+4, 5, 5);
	    
	        g.setColor(Color.red);
	        g.drawArc(player_x+6, player_y+10, 12, 7, 0, -180);
	     
		}
	}
	
	public int[][] matrizLaberinto() {
		
		int[][] laberinto1 = {
			    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			    {1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
			    {1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1},
			    {1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1},
			    {1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1},
			    {0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
			    {1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1},
			    {1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1},
			    {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
			    {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
			    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1},
			    {1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1},
			    {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
			    {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1},
			    {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
			    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1},
			    {1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0},
			    {1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1},
			    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			    
			};
		int[][] laberinto2 = {
				{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			    {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1},
			    {1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1},
			    {1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
			    {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
			    {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
			    {1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
			    {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
			    {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
			    {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
			    {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1},
			    {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1},
			    {1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1},
			    {1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1},
			    {1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1},
			    {1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
			    {1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
			    {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
			    {1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
			    {1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1},
			    {1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1},
			    {1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
			    {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			    {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0},
			    {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1},
			    {1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			};
		if(nivelAleatorio == 1) {
			return laberinto1;
		}
		else {
			return laberinto2;
		}
	}
	public void actualizarTiempo() {  //ACTUALIZA EL TIEMPO
		String tiempo = (horas <= 9?"0":"")+ horas +":" +(minutos <= 9?"0":"")+ 
				minutos +":"+(segundos <= 9?"0":"")+ segundos;
		tiempoNum.setText(tiempo);
	}
	
	public void reiniciarTiempo() {  //REINICIAR EL TIEMPO
		if(tiempo.isRunning()) {
			tiempo.stop();
		}
		centecimas = 0;
		segundos = 0;
		minutos = 0;
		horas = 0;
		
		actualizarTiempo();
		
	}
	private ActionListener accion = new ActionListener() {  //HACE LA FUNCION DE UN CRONOMETRO
		public void actionPerformed(ActionEvent e) {
			centecimas++;
			
			if(centecimas == 100) {
				segundos++;
				centecimas = 0;
			}
			
			if(segundos == 60) {
				minutos++;
				segundos = 0;
			}
			
			if(minutos == 60) {
				horas++;
				minutos = 0;
			}
			
			if(horas == 24) {
				horas = 0;
			}
			actualizarTiempo();	
			
		}	
	};
	 
    public void sonido() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	File archivoSonido = new File("sonido.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}