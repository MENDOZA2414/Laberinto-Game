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
	public int player_x = 150;
	public int player_y = 80;
	public int last_prest;
	private Rect r,p;
	/**
	 * Launch the application.
	 */
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
	/**
	 * Create the application.
	 */
	public Laberinto() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 550, 680);
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
				//System.out.println(e.getKeyChar());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				last_prest = e.getKeyCode();
				
				if(!r.colision(p)) {
					if(last_prest == 87 && player_y > 15) {
						player_y -=15;
					}
					if(last_prest == 83 && player_y < 650) {
						player_y +=15;
					}
					if(last_prest == 68 && player_x < 575) {
						player_x +=15;
					}
					if(last_prest == 65 && player_x > 25) {
						player_x -=15;
					}
					juego.repaint();
					
				}
				else
				{
					System.out.println("Colisionaste");
				}
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
				player_x = 150;
				player_y = 80;
				juego.setFocusable(true);
				juego.requestFocus();
				juego.repaint();
			}
		});
		panel.add(btnNewButton);
	}
	
	public class MyGraphics extends JComponent {
		
		private static final long serialVersionUID = 1L;
		
		MyGraphics(){
			setPreferredSize(new Dimension (650,500));
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
	        
	        //fondo
			g.setColor(new Color(9,11,52));
	        g.fillRect(120, 50, 400, 500); 
	        
	        //player
	        r = new Rect(player_x, player_y, 30, 30, new Color(118,228,84));
	        g.setColor(r.c);
	        g.fillOval(r.x,r.y, r.w, r.h);
	     
	        p = new Rect(300, 60, 40, 200, new Color(100,30,20));
	        g.setColor(p.c);
	        g.fillRect(p.x,p.y, p.w, p.h); 
	        
	        if(r.colision(p)) {
	        	System.out.println(r.colision(p));
	        }
	        
		}
	}
	
	public class Rect {
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		Color c = Color.black;
		
		Rect(int x, int y, int w, int h, Color c){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.c = c;
		}
		
		public boolean colision(Rect target) {
			if(this.x < target.x + target.w &&
			   this.x + this.w > target.x &&
			   this.y < target.y + target.h &&
			   this.y + this.h > target.y) {
				
				return true;
				
			}
			return false;
		}
	}
	
}
