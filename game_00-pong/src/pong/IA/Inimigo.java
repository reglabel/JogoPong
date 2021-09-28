package pong.IA;

import java.awt.Color;
import java.awt.Graphics;

import pong.Jogo;

public class Inimigo {
	
	private double x, y;
	private int largura, altura;
	
	public Inimigo(int x, int y) {
		this.x = x;
		this.y = y;
		this.largura = 2;
		this.altura = 20;
	}
	
	public void tick() {
		y += (Jogo.bola.getY() - y - 6) * 0.01;
		
		if(y+altura > Jogo.ALTURA) {
			y = Jogo.ALTURA - altura;
		} else if(y < 0) {
			y = 0;
		}
	}
	
	public void render(Graphics graficos) {
		graficos.setColor(Color.BLUE);
		graficos.fillRect((int)x,  (int)y, largura, altura);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}
}
