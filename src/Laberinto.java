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

public class Laberinto {
	private JFrame frame;
	public int player_x = 70;
	public int player_y = 70;
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
		frame.setBounds(100, 100, 650, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel juego = new JPanel();
		juego.setForeground(new Color(59, 255, 171));
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
						player_y -=10;
					}
					if(last_prest == 83 && player_y < 385) {
						player_y +=10;
					}
					if(last_prest == 68 && player_x < 575) {
						player_x +=10;
					}
					if(last_prest == 65 && player_x > 25) {
						player_x -=10;
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
		JPanel panel = new JPanel();
		panel.setBackground(new Color(123, 238, 81));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Reiniciar");
		btnNewButton.setFont(new Font("Marker Felt", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				juego.setFocusable(true);
				juego.requestFocus();
			}
		});
		panel.add(btnNewButton);
		
		juego.setFocusable(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(157, 247, 230));
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(157, 247, 230));
		frame.getContentPane().add(panel_2, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(157, 247, 230));
		frame.getContentPane().add(panel_3, BorderLayout.EAST);
		juego.requestFocus();
	}
	
	public class MyGraphics extends JComponent {
		
		private static final long serialVersionUID = 1L;
		
		MyGraphics(){
			setPreferredSize(new Dimension (650,500));
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
	        
	        //player
	        r = new Rect(player_x, player_y, 50, 50, new Color(0,0,0));
	        g.setColor(r.c);
	        g.fillRect(r.x,r.y, r.w, r.h); 
	        
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
