/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolecciondefrutas;

//Crea la ventana del juego y añade el JuegoPanel.

/**
 *
 * @author josel
 */
import javax.swing.JFrame; // Para crear la ventana del juego

public class RecoleccionDeFrutas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Juego de Recolección de Frutas"); // Crea una instancia de JFrame con el título "Juego de Recolección de Frutas"
        JuegoPanel juegoPanel = new JuegoPanel(); // Crea una instancia de JuegoPanel, que es donde se desarrolla el juego
        frame.add(juegoPanel); // Agrega el panel del juego al JFrame
        frame.setSize(1000, 800); // Establece el tamaño de la ventana del juego en 1000x800 píxeles
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establece la operación predeterminada cuando se cierra la ventana: terminar el programa
        frame.setVisible(true); // Hace visible la ventana del juego
    }
}


