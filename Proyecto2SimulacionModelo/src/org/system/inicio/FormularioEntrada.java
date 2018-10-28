/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.system.inicio;

import java.awt.Color;
import java.awt.Toolkit;
import org.thread.hilos.HiloResultadosFinales;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JOptionPane;
import org.timer.temporizador.SystemTimer;

/*
 Interfaz donde se muestra el formulario para ingresar las condiciones iniciales
 */
public class FormularioEntrada extends javax.swing.JFrame {

    /*
     Atributos de la clase
     */
    public static int LimiteLlegada = -1;
    public static int LimiteServicio = -1;
    public static double E1 = -1;
    public static double E2 = -1;
    public static double E3 = -1;
    public static int NroEntidades = -1;
    private SystemTimer systemTimer = null;

    /*
     Método constructor de la clase
     */
    private void constructor() {
        LimiteLlegada = -1;
        LimiteServicio = -1;
        E1 = -1;
        E2 = -1;
        E3 = -1;
        NroEntidades = -1;
        systemTimer = null;
    }
    
    /*
        Métodos getter and setter del systemTimer
    */
    public SystemTimer getSystemTimer() {
        return systemTimer;
    }

    public void setSystemTimer(SystemTimer systemTimer) {
        this.systemTimer = systemTimer;
    }

    /*
     Bloque de instrucciones que cargan los componentes principales de la interfaz,
     y su respectivo temporizador, llevando el control de la hora del Sistema.
     */
    public FormularioEntrada() {
        initComponents();
        constructor();
        CondicionParada.setText("0,00");
        NumeroEntidades.setText("0");
        TPL.setText("0,00");
        TPS.setText("0,00");
        TiempoProximaLlegada.setVisible(false);
        TiempoProximoServicio.setVisible(false);
        TPL.setVisible(false);
        TPS.setVisible(false);
        eventos();
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/icons/iconos/clock.png")));
        getContentPane().setBackground(new Color(204, 255, 255));
        setLocationRelativeTo(null);
    }

    /*
     Método donde se establecen los eventos tipo focus para los campos de la entrada de datos
     */
    private void eventos() {
        CondicionParada.addFocusListener(
                new FocusListener() {

                    @Override
                    public void focusGained(FocusEvent evt
                    ) {
                    }

                    @Override
                    public void focusLost(FocusEvent evt
                    ) {
                        if (TipoParada.getSelectedIndex() == 0) {
                            if (CondicionParada.getText().isEmpty()) {
                                CondicionParada.setText("0,00");
                                return;
                            }
                            try {
                                DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                                simbolos.setDecimalSeparator(',');
                                DecimalFormat formato = new DecimalFormat("0.00", simbolos);
                                CondicionParada.setText(formato.format(Double.parseDouble(CondicionParada.getText().replace(',', '.'))));
                            } catch (Exception ex) {
                                CondicionParada.setText("0,00");
                            }
                        } else {
                            if (CondicionParada.getText().isEmpty()) {
                                CondicionParada.setText("0");
                            }
                        }
                    }
                }
        );

        NumeroEntidades.addFocusListener(
                new FocusListener() {

                    @Override
                    public void focusGained(FocusEvent evt
                    ) {
                    }

                    @Override
                    public void focusLost(FocusEvent evt
                    ) {
                        if (NumeroEntidades.getText().isEmpty()) {
                            NumeroEntidades.setText("0");
                        }
                    }
                }
        );

        TPL.addFocusListener(
                new FocusListener() {

                    @Override
                    public void focusGained(FocusEvent evt
                    ) {
                    }

                    @Override
                    public void focusLost(FocusEvent evt
                    ) {
                        if (TPL.getText().isEmpty()) {
                            TPL.setText("0,00");
                            return;
                        }
                        try {
                            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                            simbolos.setDecimalSeparator(',');
                            DecimalFormat formato = new DecimalFormat("0.00", simbolos);
                            TPL.setText(formato.format(Double.parseDouble(TPL.getText().replace(',', '.'))));
                        } catch (Exception ex) {
                            TPL.setText("0,00");
                        }
                    }
                }
        );

        TPS.addFocusListener(
                new FocusListener() {

                    @Override
                    public void focusGained(FocusEvent evt
                    ) {
                    }

                    @Override
                    public void focusLost(FocusEvent evt
                    ) {
                        if (TPS.getText().isEmpty()) {
                            TPS.setText("0,00");
                            return;
                        }
                        try {
                            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                            simbolos.setDecimalSeparator(',');
                            DecimalFormat formato = new DecimalFormat("0.00", simbolos);
                            TPS.setText(formato.format(Double.parseDouble(TPS.getText().replace(',', '.'))));
                        } catch (Exception ex) {
                            TPS.setText("0,00");
                        }
                    }
                }
        );

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        hora = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        fondo_pantalla = new javax.swing.JPanel();
        datos_entrada = new javax.swing.JLabel();
        condicion_parada = new javax.swing.JLabel();
        CondicionParada = new javax.swing.JTextField();
        aceptar = new javax.swing.JButton();
        numero_entidades = new javax.swing.JLabel();
        NumeroEntidades = new javax.swing.JTextField();
        TPS = new javax.swing.JTextField();
        TipoParada = new javax.swing.JComboBox();
        TiempoProximoServicio = new javax.swing.JCheckBox();
        Tiempos = new javax.swing.JCheckBox();
        TiempoProximaLlegada = new javax.swing.JCheckBox();
        TPL = new javax.swing.JTextField();
        volver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Datos de entrada");
        setBackground(new java.awt.Color(209, 233, 239));
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(1110, 700));
        getContentPane().setLayout(new java.awt.FlowLayout());

        jPanel1.setBackground(new java.awt.Color(209, 233, 239));
        jPanel1.setMinimumSize(new java.awt.Dimension(1180, 680));
        jPanel1.setPreferredSize(new java.awt.Dimension(1180, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Titulo.setFont(new java.awt.Font("Cambria", 1, 48)); // NOI18N
        Titulo.setForeground(new java.awt.Color(84, 19, 1));
        Titulo.setText("CONDICIONES INICIALES");
        jPanel1.add(Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 580, 70));

        hora.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        hora.setForeground(new java.awt.Color(84, 19, 1));
        hora.setText("Hora:");
        jPanel1.add(hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 188, 35));

        fecha.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        fecha.setForeground(new java.awt.Color(84, 19, 1));
        fecha.setText("Fecha:");
        jPanel1.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 80, 188, 34));

        fondo_pantalla.setBackground(new java.awt.Color(225, 242, 225));
        fondo_pantalla.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fondo_pantalla.setMinimumSize(new java.awt.Dimension(1180, 680));
        fondo_pantalla.setPreferredSize(new java.awt.Dimension(1180, 680));
        fondo_pantalla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        datos_entrada.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        datos_entrada.setForeground(new java.awt.Color(84, 19, 1));
        datos_entrada.setText("DATOS DE ENTRADA");
        fondo_pantalla.add(datos_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 275, 54));

        condicion_parada.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        condicion_parada.setForeground(new java.awt.Color(84, 19, 1));
        condicion_parada.setText("<html> <body> Condición de <br> parada</body> </html>");
        fondo_pantalla.add(condicion_parada, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 120, -1));

        CondicionParada.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        CondicionParada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CondicionParadaKeyTyped(evt);
            }
        });
        fondo_pantalla.add(CondicionParada, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 80, 30));

        aceptar.setBackground(new java.awt.Color(255, 255, 255));
        aceptar.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        aceptar.setForeground(new java.awt.Color(84, 19, 1));
        aceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/icons/iconos/bloggif_58743a5b078d5.png"))); // NOI18N
        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });
        fondo_pantalla.add(aceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 380, -1, 50));

        numero_entidades.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        numero_entidades.setForeground(new java.awt.Color(84, 19, 1));
        numero_entidades.setText("<html> <body> Número de <br> Entidades </body> </html>");
        fondo_pantalla.add(numero_entidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 100, 35));

        NumeroEntidades.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        NumeroEntidades.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NumeroEntidadesKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NumeroEntidadesKeyTyped(evt);
            }
        });
        fondo_pantalla.add(NumeroEntidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 250, 35));

        TPS.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        TPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TPSKeyTyped(evt);
            }
        });
        fondo_pantalla.add(TPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, 80, 30));

        TipoParada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tiempo de Simulación (minutos)", "Nro. de Clientes que llegan al Sistema", "Nro. de Entidades servidas" }));
        TipoParada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoParadaActionPerformed(evt);
            }
        });
        fondo_pantalla.add(TipoParada, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 250, 30));

        TiempoProximoServicio.setBackground(new java.awt.Color(225, 242, 225));
        TiempoProximoServicio.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        TiempoProximoServicio.setForeground(new java.awt.Color(84, 19, 1));
        TiempoProximoServicio.setText("Tiempo del próximo servicio (minutos)");
        TiempoProximoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TiempoProximoServicioActionPerformed(evt);
            }
        });
        fondo_pantalla.add(TiempoProximoServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        Tiempos.setBackground(new java.awt.Color(225, 242, 225));
        Tiempos.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        Tiempos.setForeground(new java.awt.Color(84, 19, 1));
        Tiempos.setText("Tiempos del próximo evento");
        Tiempos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TiemposActionPerformed(evt);
            }
        });
        fondo_pantalla.add(Tiempos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, -1, -1));

        TiempoProximaLlegada.setBackground(new java.awt.Color(225, 242, 225));
        TiempoProximaLlegada.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        TiempoProximaLlegada.setForeground(new java.awt.Color(84, 19, 1));
        TiempoProximaLlegada.setText("Tiempo de la próxima llegada (minutos)");
        TiempoProximaLlegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TiempoProximaLlegadaActionPerformed(evt);
            }
        });
        fondo_pantalla.add(TiempoProximaLlegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 370, -1));

        TPL.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        TPL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TPLKeyTyped(evt);
            }
        });
        fondo_pantalla.add(TPL, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 80, 30));

        jPanel1.add(fondo_pantalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 770, 460));

        volver.setBackground(new java.awt.Color(255, 255, 255));
        volver.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        volver.setForeground(new java.awt.Color(84, 19, 1));
        volver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/icons/iconos/Back.png"))); // NOI18N
        volver.setText("Volver");
        volver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });
        jPanel1.add(volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 140, 140, 40));

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     Esta sección de código corresponde a los eventos de la interfaz.
     Los correspondientes a botones, formularios, entre otros.
     */
    private void CondicionParadaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CondicionParadaKeyTyped
        if (TipoParada.getSelectedIndex() == 0) {
            char c = evt.getKeyChar();
            if (Character.isDigit(c) && !CondicionParada.getText().contains(",") && CondicionParada.getText().length() == 4) {
                evt.consume();
                return;
            }
            if (CondicionParada.getText().length() == 7) {
                evt.consume();
                return;
            }
            String cad = CondicionParada.getText() + c;
            if (!Character.isDigit(c) && c != ',' && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_BACK_SPACE) {
                evt.consume();
                return;
            }
            if (!org.validator.validaciones.Validar.validarPrecision(cad, 2)) {
                evt.consume();
            }
        } else {
            if (CondicionParada.getText().length() == 3) {
                evt.consume();
                return;
            }
            char ch = evt.getKeyChar();
            if (!Character.isDigit(ch)) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_CondicionParadaKeyTyped

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        int index = (int)TipoParada.getSelectedIndex();
        if (index!=0 && Integer.parseInt(CondicionParada.getText())==0) {
            String message=index==1?"El número de clientes que llegan al Sistema debe ser mayor a 0":"El número de entidades servidas debe ser mayor a 0";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Integer.parseInt(NumeroEntidades.getText()) == 0 && TiempoProximaLlegada.isSelected() && TiempoProximoServicio.isSelected()) {
            if (Double.parseDouble(TPS.getText().replace(",", ".")) <= Double.parseDouble(TPL.getText().replace(",", "."))) {
                JOptionPane.showMessageDialog(this, "El tiempo del próximo servicio debe ser mayor\nal tiempo de la próxima llegada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (Tiempos.isSelected()) {
            if (!TiempoProximaLlegada.isSelected() && !TiempoProximoServicio.isSelected()) {
                JOptionPane.showMessageDialog(this, "Debe suministrar los próximos eventos (tiempo de ocurrencia)", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }
        }
        switch (TipoParada.getSelectedIndex()) {
            case 0:
                E3 = Double.parseDouble(CondicionParada.getText().replace(",", "."));
                break;
            case 1:
                LimiteLlegada = Integer.parseInt(CondicionParada.getText().replace(",", "."));
                break;
            case 2:
                LimiteServicio = Integer.parseInt(CondicionParada.getText().replace(",", "."));
                break;
        }
        NroEntidades = Integer.parseInt(NumeroEntidades.getText());
        if (TiempoProximaLlegada.isSelected()) {
            E1 = Double.parseDouble(TPL.getText().replace(",", "."));
        }
        if (TiempoProximoServicio.isSelected()) {
            E2 = Double.parseDouble(TPS.getText().replace(",", "."));
        }
        this.setVisible(false);
        org.system.resultados.TablaResultados ResultadosFinales = new org.system.resultados.TablaResultados();
        ResultadosFinales.setVisible(true);
        if (systemTimer != null) {
            systemTimer.stopTimer();
        }
        ResultadosFinales.setSystemTimer(new SystemTimer());
        ResultadosFinales.getSystemTimer().createTimerFormat12Hour(ResultadosFinales, "fecha", "hora");
        
        /*
            MULTIHILOS
            
            Se crea un nuevo hilo perteneciente a la clase TablaResultados. Consultar clase 
            HiloResultadosFinales del paquete org.thread.hilos
        */
        HiloResultadosFinales nuevoHilo = new HiloResultadosFinales(ResultadosFinales);
        nuevoHilo.start();
    }//GEN-LAST:event_aceptarActionPerformed

    private void NumeroEntidadesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NumeroEntidadesKeyTyped
        if (NumeroEntidades.getText().length() == 7) {
            evt.consume();
            return;
        }
        char ch = evt.getKeyChar();
        if (!Character.isDigit(ch)) {
            evt.consume();
        }
    }//GEN-LAST:event_NumeroEntidadesKeyTyped

    private void TPSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPSKeyTyped
        char c = evt.getKeyChar();
        String cad = TPS.getText() + c;
        if (Character.isDigit(c) && !TPS.getText().contains(",") && TPS.getText().length() == 4) {
            evt.consume();
            return;
        }
        if (TPS.getText().length() == 7) {
            evt.consume();
            return;
        }
        if (!Character.isDigit(c) && c != ',' && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            return;
        }
        if (!org.validator.validaciones.Validar.validarPrecision(cad, 2)) {
            evt.consume();
        }
    }//GEN-LAST:event_TPSKeyTyped

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
        this.setVisible(false);
        PantallaPrincipal InterfazInicio = new PantallaPrincipal();
        InterfazInicio.setVisible(true);
        if (systemTimer != null) {
            systemTimer.stopTimer();
        }
        InterfazInicio.setSystemTimer(new SystemTimer());
        InterfazInicio.getSystemTimer().createTimerFormat12Hour(InterfazInicio, "fecha", "hora");
    }//GEN-LAST:event_volverActionPerformed

    private void TPLKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPLKeyTyped
        char c = evt.getKeyChar();
        String cad = TPL.getText() + c;
        if (Character.isDigit(c) && !TPL.getText().contains(",") && TPL.getText().length() == 4) {
            evt.consume();
            return;
        }
        if (TPL.getText().length() == 7) {
            evt.consume();
            return;
        }
        if (!Character.isDigit(c) && c != ',' && c != KeyEvent.VK_DELETE && c != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            return;
        }
        if (!org.validator.validaciones.Validar.validarPrecision(cad, 2)) {
            evt.consume();
        }
    }//GEN-LAST:event_TPLKeyTyped

    private void TipoParadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoParadaActionPerformed
        if (TipoParada.getSelectedIndex() == 0) {
            CondicionParada.setText("0,00");
        } else {
            CondicionParada.setText("0");
        }
    }//GEN-LAST:event_TipoParadaActionPerformed

    private void TiemposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TiemposActionPerformed
        if (Tiempos.isSelected()) {
            TiempoProximaLlegada.setVisible(true);
            TPL.setVisible(true);
            TPL.setEditable(false);
            if (Integer.parseInt(NumeroEntidades.getText()) > 0) {
                TiempoProximoServicio.setVisible(true);
                TPS.setVisible(true);
                TPS.setEditable(false);
            }
        } else {
            TPL.setText("0,00");
            TPS.setText("0,00");
            TiempoProximaLlegada.setVisible(false);
            TiempoProximaLlegada.setSelected(false);
            TPL.setVisible(false);
            TPL.setEditable(false);
            TiempoProximoServicio.setVisible(false);
            TiempoProximoServicio.setSelected(false);
            TPS.setVisible(false);
            TPS.setEditable(false);
        }
    }//GEN-LAST:event_TiemposActionPerformed

    private void TiempoProximaLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TiempoProximaLlegadaActionPerformed
        if (TiempoProximaLlegada.isSelected()) {
            TPL.setEditable(true);
            TiempoProximoServicio.setVisible(true);
            TPS.setVisible(true);
        } else {
            TPL.setText("0,00");
            TPL.setEditable(false);
            if (Integer.parseInt(NumeroEntidades.getText()) == 0) {
                TiempoProximoServicio.setVisible(false);
                TiempoProximoServicio.setSelected(false);
                TPS.setText("0,00");
                TPS.setEditable(false);
                TPS.setVisible(false);
            }
        }
    }//GEN-LAST:event_TiempoProximaLlegadaActionPerformed

    private void TiempoProximoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TiempoProximoServicioActionPerformed
        if (TiempoProximoServicio.isSelected()) {
            TPS.setEditable(true);
        } else {
            TPS.setText("0,00");
            TPS.setEditable(false);
        }
    }//GEN-LAST:event_TiempoProximoServicioActionPerformed

    private void NumeroEntidadesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NumeroEntidadesKeyReleased
        if (NumeroEntidades.getText().equals("") || Integer.parseInt(NumeroEntidades.getText()) == 0) {
            if (!TiempoProximaLlegada.isSelected()) {
                TiempoProximoServicio.setVisible(false);
                TiempoProximoServicio.setSelected(false);
                TPS.setText("0,00");
                TPS.setEditable(false);
                TPS.setVisible(false);
            }
        } else {
            if (Tiempos.isSelected()) {
                TiempoProximoServicio.setVisible(true);
                TPS.setVisible(true);
            }
        }
    }//GEN-LAST:event_NumeroEntidadesKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormularioEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            FormularioEntrada EntradaDatos = new FormularioEntrada();
            EntradaDatos.setVisible(true);
            if (EntradaDatos.getSystemTimer() != null) {
                EntradaDatos.getSystemTimer().stopTimer();
            }
            EntradaDatos.setSystemTimer(new SystemTimer());
            EntradaDatos.getSystemTimer().createTimerFormat12Hour(EntradaDatos, "fecha", "hora");
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField CondicionParada;
    public static javax.swing.JTextField NumeroEntidades;
    public static javax.swing.JTextField TPL;
    public static javax.swing.JTextField TPS;
    private javax.swing.JCheckBox TiempoProximaLlegada;
    private javax.swing.JCheckBox TiempoProximoServicio;
    private javax.swing.JCheckBox Tiempos;
    private javax.swing.JComboBox TipoParada;
    private static javax.swing.JLabel Titulo;
    private javax.swing.JButton aceptar;
    public static javax.swing.JLabel condicion_parada;
    private static javax.swing.JLabel datos_entrada;
    public static javax.swing.JLabel fecha;
    public static javax.swing.JPanel fondo_pantalla;
    public static javax.swing.JLabel hora;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel numero_entidades;
    public static javax.swing.JButton volver;
    // End of variables declaration//GEN-END:variables

}
