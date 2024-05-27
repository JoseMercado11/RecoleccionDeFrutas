/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolecciondefrutas;

import java.awt.Color; // Para manejar colores
import java.awt.Graphics; // Para dibujar en pantalla
import java.awt.Rectangle; // Para representar áreas rectangulares
import java.util.HashMap; // Para usar HashMap (estructura de datos)
import java.util.Map; // Para usar Map (interfaz para mapas)
import java.util.Random; // Para generar números aleatorios

public class Fruta {
    private int x, y, tipo; // Coordenadas (x, y) y tipo de la fruta
    private int valor; // Puntaje de la fruta
    private Color color; // Color de la fruta
    private Rectangle rect; // Representación rectangular de la fruta en la pantalla
    private static final int TAMANO = 20; // Tamaño de la fruta
    private static final Random rand = new Random(); // Generador de números aleatorios
    private static final Map<Color, Integer> puntajesPorColor = new HashMap<>(); // Mapa de puntajes por color de fruta

    // Constructor de la clase Fruta
    public Fruta(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.color = obtenerColor(tipo); // Asignar color basado en el tipo de fruta
        this.rect = new Rectangle(x, y, TAMANO, TAMANO); // Crear rectángulo que representa la fruta en la pantalla
        this.valor = obtenerPuntajePorColor(color); // Asignar puntaje basado en el color de la fruta
    }

    // Método privado para obtener el color de la fruta basado en el tipo
    private Color obtenerColor(int tipo) {
        switch (tipo) {
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.ORANGE;
            default:
                return Color.RED;
        }
    }

    // Método privado para obtener el puntaje de la fruta basado en su color
    private int obtenerPuntajePorColor(Color color) {
        if (!puntajesPorColor.containsKey(color)) { // Verificar si el color ya tiene un puntaje asignado
            // Generar un puntaje aleatorio entre 5 y 30 para el color y guardarlo en el mapa
            puntajesPorColor.put(color, rand.nextInt(26) + 5);
        }
        return puntajesPorColor.get(color); // Devolver el puntaje asociado al color
    }

    // Método público para dibujar la fruta en la pantalla
    public void dibujar(Graphics g) {
        g.setColor(color); // Establecer el color de dibujo
        g.fillOval(x, y, TAMANO, TAMANO); // Dibujar un óvalo en la posición (x, y) con el tamaño definido
    }

    // Método público para obtener el rectángulo que representa la fruta en la pantalla
    public Rectangle getRect() {
        return rect;
    }

    // Método público para obtener el puntaje de la fruta
    public int getValor() {
        return valor;
    }

    // Método público para obtener el color de la fruta
    public Color getColor() {
        return color;
    }

    // Método público para obtener la coordenada x de la fruta
    public int getX() {
        return x;
    }

    // Método público para obtener la coordenada y de la fruta
    public int getY() {
        return y;
    }
}

