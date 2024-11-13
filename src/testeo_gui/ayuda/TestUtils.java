package testeo_gui.ayuda;

import java.awt.Point;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.*;

/**
 * Clase que ofrece metodos estáticos para ayudar al testeo de GUI utilizando la clase Robot
 */
public class TestUtils

{
    private static int delay = 20;

    /**
     * @param delay atributo estatico que indica la cantidad de milisegundos de espera que usara el Robot.
     */
    public static void setDelay(int delay) {
        TestUtils.delay = delay;
    }

    /**
     * @return devuelve el atributo estatico delay que indica la cantidad de milisegundos de espera que usara el Robot
     */
    public static int getDelay() {
        return delay;
    }

    /**
     * Metodo que devuelve una referencia a un componente perteneciente a un contenedor discriminado por su nombre
     * Si el componente no se encuentra, se devuelve null
     * @param parent Contenedor donde se buscara el componente
     * @param name nombre del componente a buscar
     * @return devuelve el componente con el nombre indicado. Si no hay ningun componente con el nombre buscado, se devolvera null
     */
    public static Component getComponentForName(Component parent, String name) {
        Component retorno = null;
        if (parent.getName() != null && parent.getName().equals(name))
            retorno = parent;
        else
        {
            if (parent instanceof Container)
            {
                Component[] hijos = ((Container) parent).getComponents();
                int i = 0;
                while (i < hijos.length && retorno == null)
                {
                    retorno = getComponentForName(hijos[i], name);
                    i++;
                }
            }
        }
        return retorno;
    }

    /**
     * Metodo que devuelve el punto central de un componente
     * @param componente Componente del que se quiere obtener el punto central
     * @return Punto central del componente requerido.
     */
    public static Point getCentro(Component componente) {
        Point retorno = null;
        if (componente != null)
            retorno = componente.getLocationOnScreen();
        retorno.x += componente.getWidth() / 2;
        retorno.y += componente.getHeight() / 2;
        return retorno;
    }

    /**MEtodo que hace click en un componente utilizando la clase Robot
     * @param component Componente sobre el que se quiere hacer click
     * @param robot Referencia al Robot que se utilizará
     */
    public static void clickComponent(Component component, Robot robot) {
        Point punto = TestUtils.getCentro(component);
        robot.mouseMove(punto.x, punto.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(getDelay());
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(getDelay());
    }
    
    public static void clickear_jlist(JList miJList,int indice,Robot robot) {
    	//miJList.setSelectedIndex(indice);
    	// Obtener la posición en pantalla de la JList
        Point puntoLista = miJList.getLocationOnScreen();

        // Obtener el rectángulo del primer elemento en la lista (el área visible de la celda)
        Rectangle rectElemento = miJList.getCellBounds(indice, indice);  // Elemento 2, índice 1

        // Calcular las coordenadas absolutas en la pantalla del centro del elemento
        int x = puntoLista.x + rectElemento.x + rectElemento.width / 2;
        int y = puntoLista.y + rectElemento.y + rectElemento.height / 2;

        // Mover el ratón a las coordenadas del centro del elemento
        robot.mouseMove(x, y);
        robot.delay(getDelay());
        // Simular el clic del ratón
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(getDelay());
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        robot.delay(getDelay());
    }

    /**
     * Metodo que tipea un texto utilizando la clase Robot.
     * Solo se aplica a textos que contengan letras de la 'a' a la 'z', tanto mayusculas como minusculas. 
     * @param texto Texto que se desea tipear. Debera contener solamente letras de la 'a' a la 'z', tanto mayusculas como minusculas. 
     * @param robot Referencia al Robot que se utilizará
     */
    public static void tipeaTexto(String texto, Robot robot) {
        String mayusculas = texto.toUpperCase();
        char letras[] = mayusculas.toCharArray();
        for (int i = 0; i < letras.length; i++)
        {
            robot.delay(getDelay());
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z')
                robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(letras[i]);
            robot.delay(getDelay());
            robot.keyRelease(letras[i]);
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z')
                robot.keyRelease(KeyEvent.VK_SHIFT);

        }
    }

    /**
     * Borra el texto de un JTextField simulando el pulsado de la tecla 'Delete' utilizando la clase Robot
     * @param jtextfield JTextField al que se desea borrar su texto
     * @param robot Referencia al Robot que se utilizará
     */
    public static void borraJTextField(JTextField jtextfield, Robot robot) {
        Point punto = null;
        if (jtextfield != null)
        {
            robot.delay(getDelay());
            punto = jtextfield.getLocationOnScreen();
            robot.mouseMove(punto.x, punto.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(getDelay());
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.delay(getDelay());
            while (!jtextfield.getText().isEmpty())
            {
                robot.delay(getDelay());
                robot.keyPress(KeyEvent.VK_DELETE);
                robot.delay(getDelay());
                robot.keyRelease(KeyEvent.VK_DELETE);
            }
        }
    }
}