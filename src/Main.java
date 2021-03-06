import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Main extends JFrame {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}
	
	private Graph network = new Graph();
	
	private Vertex start = null;
	
	private Vertex target = null;
	
	private boolean control = true;
	
	
	private ArrayList<int[]> connecting_vertices = new ArrayList<int[]>();
	
	
	private class MapPanel extends JPanel implements MouseListener, KeyListener{
		
		private Image img;
		
		public boolean set = false;
		
		public boolean editing = true;
		
		public boolean set_first_vertex = true;
		
  		public boolean set_second_vertex = false;
  		
		
		public MapPanel(String img_path) {
			img = new ImageIcon(img_path).getImage();
			 Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			 setPreferredSize(size);
			 setMinimumSize(size);
			 setMaximumSize(size);
			 setSize(size);
			 setLayout(null);
		}
		
		 
		
		public void paintComponent(Graphics g) {
			
			g.drawImage(img, 0, 0, null);
			
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setStroke(new BasicStroke(3));
			
			for (Vertex vertex  : network.vertices) {
				g.setColor(vertex.color);
				g.fillOval(vertex.x-10, vertex.y-10, 20, 20);
				
				for (Edge edge : vertex.edges) {
					g.setColor(edge.active ? Color.RED : Color.BLACK);
					g2.drawLine(vertex.x, vertex.y, edge.get(vertex).x, edge.get(vertex).y);
				}
				
			}
			
			
			repaint();
			
			
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			set = true;
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	MapPanel panel;
	
	public Main() {
		
		setUpGraphics();
		
		buildVerticesEdges();
		
		panel.repaint();
		
		
		
		while(control) {
			
			try {
				Thread.sleep(30);
			}
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				
			}
			
				
				
				if(panel.set) {
					
					
					
					for (Vertex v : network.vertices) {
						
						//find which vertex eas clicked
						if(v.intersects((int)panel.getMousePosition().getX(), (int)panel.getMousePosition().getY(), 1, 1)) {
							v.color = Color.RED;
							//set to first clicked vertex to starting vertex
							if(panel.set_first_vertex) {
								start = v;
								panel.set_first_vertex = false;
								panel.set_second_vertex = true;
							}else if (panel.set_second_vertex) {
								//set second clicked vertex to target vertex
								target = v;
								
								//start searching
								network.search(start, target);
								repaint();
								panel.set_second_vertex = false;
							}
							
							break;
						}
						
						
					}
					
					panel.set = false;
					
					
					
				}
				
				
			
			
		}
		System.out.println("Done searching");
		
		
	}
	
	
	public void setUpGraphics() {
		panel = new MapPanel("imgs/map.jpg");
		
		this.setSize(2000, 1280);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(panel);
		
		this.setLocationRelativeTo(null);
		
		//The frame IS a listener because it implements MouseListener
		panel.addMouseListener(panel);
		
		panel.addKeyListener(panel);
		
		this.setVisible(true);
		
		panel.setFocusable(true);
	}
	
	
	//function for setting up graph from txt file
	public void buildVerticesEdges() {
		
		FileReader stream = null;
		try {
			stream = new FileReader("data/data.txt");
			
			BufferedReader in = new BufferedReader(stream);
			
			String line = "";
			
			//once for vertices
			
			while((line = in.readLine()) != null) {
				String[] entry = line.split(",");
				network.addVertex(Integer.parseInt(entry[0]), Integer.parseInt(entry[1])); 
		
			}
			
			in.close();
			stream.close();
			stream = new FileReader("data/data.txt");
			in = new BufferedReader(stream);
			
			line = "";
			
			//once again for edges
			while((line = in.readLine()) != null) {
				String[] entry = line.split(",");
				if(entry.length >= 3) {
					String[] connections = entry[2].split("#");
					for (int i = 0; i < connections.length; i++) {
						String[] xy = connections[i].split("-");
						network.addEdge(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
					}
				}
				
		
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}
