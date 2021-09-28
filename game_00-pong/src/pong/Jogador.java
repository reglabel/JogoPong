package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Jogador {
	public boolean acima, abaixo;
	private int x, y, largura, altura;
	
	public void tick() {
		if(acima) {
			y--;
		} else if (abaixo){
			y++;
		}
		
		if(y+altura > Jogo.ALTURA) {
			y = Jogo.ALTURA - altura;
		} else if(y < 0) {
			y = 0;
		}
	}
	
	public Jogador(int x, int y) {
		this.x = x;
		this.y = y;
		this.largura = 2;
		this.altura = 20;
	}
	
	public void render(Graphics graficos) {
		graficos.setColor(Color.PINK);
		graficos.fillRect(x, y, largura, altura);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}
}
