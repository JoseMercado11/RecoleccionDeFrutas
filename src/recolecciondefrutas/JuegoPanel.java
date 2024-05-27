/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recolecciondefrutas;

import javax.swing.JPanel; // Importa la creacion un panel en la interfaz gráfica
import javax.swing.Timer; // Para manejar eventos temporizados
import java.awt.Font; // Para definir fuentes de texto
import java.awt.Color; // Para definir colores
import java.awt.Graphics; // Para dibujar gráficos
import java.awt.event.ActionEvent; // Para representar eventos de acción
import java.awt.event.ActionListener; // Pra manejar eventos de acción
import java.awt.event.KeyAdapter; // Para adaptar eventos de teclado
import java.awt.event.KeyEvent; // Para representar eventos de teclado
import java.util.ArrayList; // Para manejar una lista dinámica de frutas
import java.util.HashMap; // Para almacenar puntajes por color de fruta
import java.util.Iterator; // Para iterar sobre las frutas
import java.util.List; // Para manejar listas de frutas
import java.util.Map; // Para almacenar puntajes por color y cantidades por color
import java.util.Random; // Para generar números aleatorios
import javax.swing.JOptionPane; // Para mostrar mensajes emergentes en la interfaz gráfica

public class JuegoPanel extends JPanel implements ActionListener {
    private Jugador jugador; // Declaración de la instancia de Jugador para representar al jugador en el juego
    private List<Fruta> frutas; // Declaración de la lista de frutas para almacenar las frutas en el juego
    private Timer timer; // Declaración del temporizador para controlar el tiempo en el juego
    private int tiempoRestante; // Declaración de una variable para almacenar el tiempo restante en el juego
    private int intentos; // Declaración de una variable para almacenar la cantidad de intentos restantes en el juego
    private boolean juegoTerminado; // Declaración de una bandera para indicar si el juego ha terminado
    private String frutaRecomendada; // Declaración de una variable para almacenar la fruta recomendada para recolectar

    // Constructor de la clase JuegoPanel
    public JuegoPanel() {
        this.jugador = new Jugador(); // Inicializa la instancia de Jugador
        this.frutas = new ArrayList<>(); // Inicializa la lista de frutas como una nueva lista vacía
        this.timer = new Timer(1000 / 60, this); // Inicializa el temporizador con una frecuencia de 60 FPS y lo vincula al ActionListener actual (this)
        this.tiempoRestante = 60; // Inicializa el tiempo restante en 60 segundos
        this.intentos = 3; // Inicializa la cantidad de intentos restantes en 3
        this.juegoTerminado = false; // Inicializa la bandera de juego terminado como falsa
        generarFrutas(); // Genera frutas aleatorias en el campo de juego
        setFocusable(true); // Establece el panel como enfocable para poder escuchar eventos de teclado
        addKeyListener(new TAdapter()); // Agrega un KeyListener al panel para manejar eventos de teclado
        new Timer(1000, new TiempoListener()).start(); // Inicializa un temporizador para disminuir el tiempo cada segundo
        timer.start(); // Inicia el temporizador del juego
        frutaRecomendada = determinarFrutaRecomendada(); // Determina la fruta recomendada para recolectar
        // Muestra un mensaje de recomendación al iniciar el juego
        JOptionPane.showMessageDialog(this, "Recomendación: Comienza recolectando las frutas de color " + frutaRecomendada, "Sugerencia", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Método para generar frutas aleatorias en el campo de juego
    private void generarFrutas() {
        Random rand = new Random(); // Inicializa un generador de números aleatorios
        frutas.clear(); // Limpia la lista de frutas existentes
        for (int i = 0; i < 40; i++) { // Itera para generar 35 frutas aleatorias
            int x = rand.nextInt(780); // Genera una coordenada x aleatoria dentro del campo de juego
            int y = rand.nextInt(580); // Genera una coordenada y aleatoria dentro del campo de juego
            int tipo = rand.nextInt(4); // Genera un tipo de fruta aleatorio entre 0 y 3
            frutas.add(new Fruta(x, y, tipo)); // Agrega una nueva fruta a la lista de frutas en la posición generada
        }
    }
    
    // Método para reiniciar el juego después de que se terminen los intentos o se recojan todas las frutas
    private void reiniciarJuego() {
        this.jugador = new Jugador(); // Reinicia al jugador
        generarFrutas(); // Genera nuevas frutas aleatorias en el campo de juego
        this.tiempoRestante = 60; // Reinicia el tiempo restante en 60 segundos
        this.juegoTerminado = false; // Establece el juego como no terminado
        frutaRecomendada = determinarFrutaRecomendada(); // Determina una nueva fruta recomendada para recolectar
        // Muestra un mensaje con la nueva recomendación
        JOptionPane.showMessageDialog(this, "Recomendación: Comienza recolectando las frutas de color " + frutaRecomendada, "Sugerencia", JOptionPane.INFORMATION_MESSAGE);
        timer.restart(); // Reinicia el temporizador del juego
    }

    // Terminar el juego mostrando el puntaje y tiempo restante
    private void terminarJuego() {
        juegoTerminado = true;
        timer.stop();
        String mensaje = "Fin del juego. Puntaje: " + jugador.getPuntaje() + ". Tiempo restante: " + tiempoRestante + " segundos.";
        JOptionPane.showMessageDialog(this, mensaje, "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Algoritmo voraz para recomendar al jugador cuál fruta recoger primero
    private String determinarFrutaRecomendada() {
        Map<Color, Integer> sumaPuntajePorColor = new HashMap<>();
        Map<Color, Integer> cantidadPorColor = new HashMap<>();
        
        // Calcular el puntaje total y la cantidad de frutas por color
        for (Fruta fruta : frutas) {
            Color color = fruta.getColor();
            sumaPuntajePorColor.put(color, sumaPuntajePorColor.getOrDefault(color, 0) + fruta.getValor());
            cantidadPorColor.put(color, cantidadPorColor.getOrDefault(color, 0) + 1);
        }
        
        // Calcular el puntaje promedio por color
        Map<Color, Double> puntajePromedioPorColor = new HashMap<>();
        for (Color color : sumaPuntajePorColor.keySet()) {
            int sumaPuntaje = sumaPuntajePorColor.get(color);
            int cantidad = cantidadPorColor.get(color);
            puntajePromedioPorColor.put(color, (double) sumaPuntaje / cantidad);
        }
        
        // Determinar el color con el mayor puntaje promedio
        Color colorPrioritario = null;
        double maxPuntajePromedio = 0;
        for (Map.Entry<Color, Double> entry : puntajePromedioPorColor.entrySet()) {
            if (entry.getValue() > maxPuntajePromedio) {
                maxPuntajePromedio = entry.getValue();
                colorPrioritario = entry.getKey();
            }
        }
        
        return obtenerNombreColor(colorPrioritario);
    }

    // Obtener el nombre del color para mostrar en el mensaje
    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.GREEN)) return "Verde";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.ORANGE)) return "Naranja";
        return "Desconocido";
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        jugador.dibujar(g);
        for (Fruta fruta : frutas) {
            fruta.dibujar(g);
        }
        dibujarInfo(g);
    }
    
    private void dibujarInfo(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Puntaje: " + jugador.getPuntaje(), 10, 30);
        g.drawString("Tiempo: " + tiempoRestante, 10, 60);
        g.drawString("Intentos: " + intentos, 10, 90);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!juegoTerminado) {
            // Revisar si el jugador ha recolectado alguna fruta
            Iterator<Fruta> it = frutas.iterator();
            while (it.hasNext()) {
                Fruta fruta = it.next();
                if (jugador.getRect().intersects(fruta.getRect())) {
                    jugador.recolectarFruta(fruta);
                    it.remove();
                }
            }
            // Si se recolectaron todas las frutas, termina el juego
            if (frutas.isEmpty()) {
                terminarJuego();
            }
            repaint();
        }
    }
    
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!juegoTerminado) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    jugador.mover(-10, 0);
                } else if (key == KeyEvent.VK_RIGHT) {
                    jugador.mover(10, 0);
                } else if (key == KeyEvent.VK_UP) {
                    jugador.mover(0, -10);
                } else if (key == KeyEvent.VK_DOWN) {
                    jugador.mover(0, 10);
                }
                repaint();
            }
        }
    }
    
    private class TiempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!juegoTerminado) {
                if (tiempoRestante > 0) {
                    tiempoRestante--;
                } else {
                    intentos--;
                    if (intentos > 0) {
                        JOptionPane.showMessageDialog(JuegoPanel.this, "Intentos restantes: " + intentos, "Intento fallido", JOptionPane.INFORMATION_MESSAGE);
                        reiniciarJuego();
                    } else {
                        terminarJuego();
                        System.out.println("Fin del juego. No hay más intentos.");
                    }
                }
                repaint();
            }
        }
    }
}
