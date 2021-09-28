package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import pong.IA.Bola;
import pong.IA.Inimigo;

public class Jogo extends Canvas implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private JFrame tela;
	private Thread thread;
	private boolean taRodando;
	public static int LARGURA = 180, ALTURA = 120, ESCALA = 3;
	
	public static Jogador jogador;
	public static Inimigo inimigo;
	public static Bola bola;
	private BufferedImage camada;
	
	public static void main (String[] args) {
		Jogo jogo = new Jogo();
		jogo.comecar();
	}
	
	public Jogo() {
		jogador = new Jogador(10, (int)((ALTURA / 2)-20));
		inimigo = new Inimigo(LARGURA - 10, (int)((ALTURA / 2)-20));
		bola = new Bola((int)(LARGURA/2) - 6, (int)((ALTURA / 2)-5));
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(LARGURA*ESCALA, ALTURA*ESCALA));
		camada = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB);
		tela = new JFrame("Pong");
		tela.setResizable(false);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.add(this);
		tela.pack();
		tela.setLocationRelativeTo(null);
		tela.setVisible(true);
	}
	
	public synchronized void comecar() {
		thread = new Thread(this);
		taRodando = true;
		thread.start();
	}
	
	public synchronized void parar() {
		taRodando = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		jogador.tick();
		inimigo.tick();
		bola.tick();
	}
	
	public void render() {
		BufferStrategy estrategia = this.getBufferStrategy();
		if(estrategia == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics graficos = camada.createGraphics();
		graficos.setColor(Color.BLACK);
		graficos.fillRect(0, 0, LARGURA, ALTURA);
		jogador.render(graficos);
		inimigo.render(graficos);
		bola.render(graficos);
		
		graficos = estrategia.getDrawGraphics();
		graficos.drawImage(camada, 0, 0, LARGURA*ESCALA, ALTURA*ESCALA, null);
		estrategia.show();
	}
	
	@Override
	public void run() {
		requestFocus();
		long ultimaVez = System.currentTimeMillis();
		double numeroDeTicks = 60.0;
		double nanos = 1000000000/numeroDeTicks;
		double delta = 0.0;
		
		while(taRodando) {
			long agora = System.nanoTime();
			delta += (agora - ultimaVez) / nanos;
			ultimaVez = agora;
			
			if(delta >= 1) {
				tick();
				render();
				delta--;
			}
		}
		
		parar();
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			jogador.acima = true;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			jogador.abaixo = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			jogador.acima = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			jogador.abaixo = false;
		}		
	}
	
}
