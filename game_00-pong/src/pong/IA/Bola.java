package pong.IA;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import pong.Jogo;

public class Bola {
	private double x, y, dx, dy, velocidade;
	private int largura, altura, angulo;
	
	public double getY() {
		return y;
	}
	
	private void gerarAngulo() {
		angulo =  new Random().nextInt(35);
		dx = Math.cos(Math.toRadians(angulo));
		dy = Math.sin(Math.toRadians(angulo));
	}
	
	public Bola(int x, int y) {
		this.x = x;
		this.y = y;
		this.largura = 6;
		this.altura = 5;
		this.velocidade = 0.3;
		
		dx = 4.0;
		dy = 4.0;
		
		gerarAngulo();
	}
	
	public void tick() {
		if(y+(dy*velocidade) + altura >= Jogo.ALTURA) {
			dy *= -1;
		} else if(y+(dy*velocidade) + altura < 0) {
			dy *= -1;
		}
		
		if(x >= Jogo.LARGURA + 10) {
			System.out.println("Ponto do Jogador!");
			x = (int)((Jogo.LARGURA/2) - 6);
			y = (int)((Jogo.ALTURA/2) - 5);

			gerarAngulo();

		} else if(x < -10) {
			System.out.println("Ponto do Inimigo!");
			x = (int)((Jogo.LARGURA/2) - 6);
			y = (int)((Jogo.ALTURA/2) - 5);
			
			gerarAngulo();

		}
		
		Rectangle limites = new Rectangle((int)(x+(dx*velocidade)), (int)(y+(dy*velocidade)), largura, altura);
		Rectangle limitesJogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Jogo.jogador.getLargura(), Jogo.jogador.getAltura());
		Rectangle limitesInimigo = new Rectangle((int)(Jogo.inimigo.getX()), (int)(Jogo.inimigo.getY()), Jogo.inimigo.getLargura(), Jogo.inimigo.getAltura());
		
		if(limites.intersects(limitesJogador)) {
			gerarAngulo();
			if(dx < 0) {
				dx *= -1;
			}
			
		} else if (limites.intersects(limitesInimigo)) {
			gerarAngulo();
			if(dx > 0) {
				dx *= -1;
			}
		}
		
		x += dx*velocidade;
		y += dy*velocidade;
	}
	
	public void render(Graphics graficos) {
		graficos.setColor(Color.YELLOW);
		graficos.fillOval((int)x,  (int)y, largura, altura);
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}
	
	
}
