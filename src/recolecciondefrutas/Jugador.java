/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolecciondefrutas;

//Define al jugador con su posición y puntaje.
//Métodos para mover al jugador, recolectar frutas y dibujarse en la pantalla.


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// Clase Jugador que representa al jugador en el juego
public class Jugador {
    // Variables de instancia para la posición, puntaje y área del jugador
    private int x, y, puntaje;
    private Rectangle rect;
    // Constante para el tamaño del jugador
    private static final int TAMANO = 20;
    
    // Constructor de la clase Jugador
    public Jugador() {
        this.x = 400; // Posición inicial en el eje x
        this.y = 300; // Posición inicial en el eje y
        this.puntaje = 0; // Puntaje inicial
        this.rect = new Rectangle(x, y, TAMANO, TAMANO); // Área rectangular que representa al jugador
    }
    
    // Método para mover al jugador
    public void mover(int dx, int dy) {
        x += dx; // Actualiza la posición x del jugador
        y += dy; // Actualiza la posición y del jugador
        rect.setLocation(x, y); // Actualiza la ubicación del rectángulo del jugador
    }
    
    // Método para recolectar una fruta
    public void recolectarFruta(Fruta fruta) {
        if (rect.intersects(fruta.getRect())) { // Verifica si el jugador intersecta con la fruta
            puntaje += fruta.getValor(); // Incrementa el puntaje del jugador con el valor de la fruta
            fruta = null; // Elimina la fruta (no es necesario en este contexto ya que se maneja en otra parte)
        }
    }
    
    // Método para dibujar al jugador
    public void dibujar(Graphics g) {
        g.setColor(Color.BLACK); // Establece el color del jugador
        g.fillRect(x, y, TAMANO, TAMANO); // Dibuja el rectángulo que representa al jugador
    }
    
    // Método para obtener el puntaje del jugador
    public int getPuntaje() {
        return puntaje; // Devuelve el puntaje actual del jugador
    }
    
    // Método para obtener el área del jugador
    public Rectangle getRect() {
        return rect; // Devuelve el rectángulo que representa al jugador
    }

    // Método para obtener la posición x del jugador
    public int getX() {
        return x; // Devuelve la posición x actual del jugador
    }

    // Método para obtener la posición y del jugador
    public int getY() {
        return y; // Devuelve la posición y actual del jugador
    }
}
