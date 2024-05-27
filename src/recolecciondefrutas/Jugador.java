/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolecciondefrutas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Jugador {
    private int x, y, puntaje;
    private Rectangle rect;
    private static final int TAMANO = 20;
    
    public Jugador() {
        this.x = 400;
        this.y = 300;
        this.puntaje = 0;
        this.rect = new Rectangle(x, y, TAMANO, TAMANO);
    }
    
    public void mover(int dx, int dy) {
        x += dx;
        y += dy;
        rect.setLocation(x, y);
    }
    
    public void recolectarFruta(Fruta fruta) {
        if (rect.intersects(fruta.getRect())) {
            puntaje += fruta.getValor();
            fruta = null;
        }
    }
    
    public void dibujar(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, TAMANO, TAMANO);
    }
    
    public int getPuntaje() {
        return puntaje;
    }
    
    public Rectangle getRect() {
        return rect;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}



