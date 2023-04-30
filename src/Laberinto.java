import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import javax.swing.SwingConstants;

public class Laberinto {
	
	private JFrame frame;
    private int tamaño = 40;
    private int ancho = 40;
    private int alto = 40;
    private int movimiento = 40;
    
	public int player_x = 40;
	public int player_y = 40;
	public int last_prest;
	

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
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 855, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel juego = new JPanel();
		juego.setBackground(new Color(69, 7, 134));
		juego.setForeground(new Color(255, 255, 255));
		frame.getContentPane().add(juego, BorderLayout.CENTER);
		juego.add(new MyGraphics());
		
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
				if(last_prest == 68 ) { //DERECHA (D)
					if(matrizLaberinto()[player_y/tamaño][player_x/tamaño+1]!=1) { 
						player_x += movimiento;
					}
						System.out.println("D");
					
				}
				if(last_prest == 65) { //IZQUIERDA (A)
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
		levelPanel.setBackground(new Color(69, 7, 134));
		frame.getContentPane().add(levelPanel, BorderLayout.NORTH);
		levelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel levelText = new JLabel("  Level 1");
		levelText.setForeground(new Color(255, 255, 255));
		levelText.setHorizontalAlignment(SwingConstants.RIGHT);
		levelText.setFont(new Font("Marker Felt", Font.PLAIN, 25));
		levelPanel.add(levelText, BorderLayout.WEST);
		
		JPanel Espacio = new JPanel();
		Espacio.setBackground(new Color(69, 7, 134));
		levelPanel.add(Espacio, BorderLayout.NORTH);
		
		JButton btnOpciones = new JButton("-");
		levelPanel.add(btnOpciones, BorderLayout.EAST);
		btnOpciones.setFocusable(false);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(118, 228, 84));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Reiniciar");
		btnNewButton.setForeground(new Color(9, 11, 49));
		btnNewButton.setFont(new Font("Marker Felt", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciar(juego);
			}
		});
		panel.add(btnNewButton);
	}
	
	public void reiniciar(JPanel panel) {
		player_x = 40;
		player_y = 40;
		panel.setFocusable(true);
		panel.requestFocus();
		panel.repaint();
	}
	
	public class MyGraphics extends JComponent {
		
		private static final long serialVersionUID = 1L;
		
		MyGraphics(){
			setPreferredSize(new Dimension (650,500));
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
	        
	        //fondo
			
	        //Laberinto 
	        g.setColor(Color.BLACK);
	        for (int i = 0; i < matrizLaberinto().length; i++) {
	            for (int j = 0; j < matrizLaberinto()[i].length; j++) {
	                if (matrizLaberinto()[i][j] == 1) {
	                	
	                    g.fillRect(j * tamaño, i * tamaño, ancho, alto);
	                }
	            }
	        }
	        
	        //player
	        g.setColor(new Color(118,228,84));
	        g.fillOval(player_x,player_y,tamaño,tamaño);
	        g.setColor(Color.white);
	        g.drawOval(player_x,player_y,tamaño,tamaño);
	     
		}
	}
	
	public int[][] matrizLaberinto() {
		int[][] laberinto = {
			    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			    {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
			    {1, 0, 1, 0, 1, 0, 1, 1, 0, 1},
			    {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
			    {1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
			    {1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
			    {1, 0, 1, 1, 0, 0, 0, 1, 0, 1},
			    {1, 0, 1, 0, 0, 1, 0, 1, 0, 1},
			    {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
			    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
			};

		return laberinto;
	}
	
}