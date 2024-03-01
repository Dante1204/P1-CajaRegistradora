import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroProductos extends JFrame {
    private JTextField nombre, cantidad, precio;
    private JButton registrar, generarticket, nuevo, salir;
    private JTextArea registro;

    private String[] nombres;
    private int[] cantidades;
    private double[] precios;
    private int cantidadProductos;
    private JFrame ticketFrame;

    public RegistroProductos() {
        nombres = new String[100];
        cantidades = new int[100];
        precios = new double[100];
        cantidadProductos = 0;

        setTitle("Registro de Productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Nombre:"));
        nombre = new JTextField();
        inputPanel.add(nombre);

        inputPanel.add(new JLabel("Cantidad:"));
        cantidad = new JTextField();
        inputPanel.add(cantidad);

        inputPanel.add(new JLabel("Precio:"));
        precio = new JTextField();
        inputPanel.add(precio);

        registrar = new JButton("Registrar");
        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarProducto();
            }
        });
        inputPanel.add(registrar);

        generarticket = new JButton("Generar Ticket");
        generarticket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generarTicket();
            }
        });
        inputPanel.add(generarticket);

        nuevo = new JButton("Nueva Venta");
        nuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarRegistro();
            }
        });
        inputPanel.add(nuevo);

        salir = new JButton("Salir"); // Botón para salir de la aplicación
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        inputPanel.add(salir);

        panel.add(inputPanel, BorderLayout.NORTH);

        registro = new JTextArea();
        registro.setEditable(false);
        panel.add(new JScrollPane(registro), BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private void registrarProducto() {
        String nombre = this.nombre.getText();
        int cantidad = Integer.parseInt(this.cantidad.getText());
        double precio = Double.parseDouble(this.precio.getText());

        nombres[cantidadProductos] = nombre;
        cantidades[cantidadProductos] = cantidad;
        precios[cantidadProductos] = precio;
        cantidadProductos++;

        registro.append("Producto: " + nombre + ", Cantidad: " + cantidad + ", Precio: $" + precio + "\n");

        this.nombre.setText("");
        this.cantidad.setText("");
        this.precio.setText("");
    }

    private void generarTicket() {
        ticketFrame = new JFrame("Ticket de Compra");
        ticketFrame.setSize(350, 450);
        ticketFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ticketFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel encabezadoLabel = new JLabel("<html><b>¡Bienvenido a Ponchi´s!</b><br> Dirección: Independencia #100, Ixtapan de la Sal<br> Teléfono: 123-456-7890</html>");
        panel.add(encabezadoLabel);

        JTextArea cuerpoTextArea = new JTextArea(10, 30);
        cuerpoTextArea.setEditable(false);
        cuerpoTextArea.append("Producto\tCantidad\tPrecio\tTotal\n");
        for (int i = 0; i < cantidadProductos; i++) {
            cuerpoTextArea.append(nombres[i] + "\t" + cantidades[i] + "\t$" + String.format("%.2f", precios[i]) + "\t$" + String.format("%.2f", cantidades[i] * precios[i]) + "\n");
        }
        cuerpoTextArea.append("\nImporte Total: $" + String.format("%.2f", calcularImporteTotal()));
        panel.add(new JScrollPane(cuerpoTextArea));

        JLabel pieLabel = new JLabel("<html><i>¡Gracias por su compra!</i></html>");
        panel.add(pieLabel);

        ticketFrame.add(panel);
        ticketFrame.setVisible(true);
    }

    private void limpiarRegistro() {
        registro.setText("");
        cantidadProductos = 0;
        if (ticketFrame != null) {
            ticketFrame.dispose();
        }
    }

    private void salir() {
        dispose(); // Cerrar la ventana principal
        ticketFrame.dispose();
    }

    private double calcularImporteTotal() {
        double importeTotal = 0;
        for (int i = 0; i < cantidadProductos; i++) {
            importeTotal += cantidades[i] * precios[i];
        }
        return importeTotal;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroProductos());
    }
}
