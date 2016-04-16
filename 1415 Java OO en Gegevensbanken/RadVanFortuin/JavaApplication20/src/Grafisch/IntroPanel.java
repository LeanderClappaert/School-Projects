package Grafisch;
import javax.swing.JFrame;
import Database.DatabaseConnectie;

/**
 * Dit is het startpanel van het spel. Deze bepaalt of er een SpelSolo of TeamSpel wordt opgestart.
 * @author Leander Clappaert
 * @version 28/05/2015
 */
public class IntroPanel extends javax.swing.JPanel {
    private final JFrame parentFrame;
    private DatabaseConnectie db;
    
    /**
     * Creates new form SpelSolo
     */
    public IntroPanel(JFrame frame) {
        initComponents();
        this.parentFrame = frame;
        jExceptionLabel.setVisible(false);
        db = new DatabaseConnectie();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpeelButton = new javax.swing.JButton();
        jExceptionLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jVraagAantalSpelers = new javax.swing.JLabel();
        jBackground = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(580, 310));
        setMinimumSize(new java.awt.Dimension(580, 310));
        setOpaque(false);
        setLayout(null);

        jSpeelButton.setText("SPEEL");
        jSpeelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSpeelButtonActionPerformed(evt);
            }
        });
        add(jSpeelButton);
        jSpeelButton.setBounds(270, 216, 84, 30);

        jExceptionLabel.setBackground(new java.awt.Color(255, 0, 0));
        jExceptionLabel.setForeground(new java.awt.Color(255, 255, 255));
        jExceptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jExceptionLabel.setOpaque(true);
        add(jExceptionLabel);
        jExceptionLabel.setBounds(137, 264, 313, 16);

        jTextField1.setText("1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(225, 217, 27, 29);

        jVraagAantalSpelers.setForeground(new java.awt.Color(255, 255, 255));
        jVraagAantalSpelers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jVraagAantalSpelers.setText("Met hoeveel mensen wordt er gespeeld (1 tot en met 4 spelers) ?");
        add(jVraagAantalSpelers);
        jVraagAantalSpelers.setBounds(10, 191, 570, 14);

        jBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Grafisch/images/did-a-wheel-of-fortune-contestant-lose-unfairly.jpg"))); // NOI18N
        add(jBackground);
        jBackground.setBounds(0, 0, 580, 310);
    }// </editor-fold>//GEN-END:initComponents

    private void jSpeelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSpeelButtonActionPerformed
        int aantalSpelers = 0;
        String test = jTextField1.getText(); //via deze weg ontstaat er geen infinite exception loop
        boolean heeftException = false; //dit voorkomt een dubbele error melding: exception + fout aantal spelers

        try {
            jExceptionLabel.setVisible(false);
            aantalSpelers = Integer.parseInt(test);
        }
        catch (Exception e) {
            heeftException = true;
            jExceptionLabel.setText("Gelieve een geldige waarde in te geven!");
            jExceptionLabel.setVisible(true);
        }
        //als alle ingaves in orde zijn:
        if (!heeftException) {
            if (aantalSpelers == 1) { //naar PanelIDSolo
                parentFrame.setContentPane(new PanelIDSolo(parentFrame, db));
                parentFrame.validate();
            }
            else if (aantalSpelers > 1 && aantalSpelers <= 4) { //naar PanelIDTeam
                parentFrame.setContentPane(new PanelIDTeam(parentFrame, aantalSpelers, db));
                parentFrame.validate();
            }
            else {
                jExceptionLabel.setText("Gelieve een geldig aantal spelers in te geven!");
                jExceptionLabel.setVisible(true);
            }
        }
    }//GEN-LAST:event_jSpeelButtonActionPerformed
    //Automatisch gegenereerde code die niet te deleten is
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jBackground;
    private javax.swing.JLabel jExceptionLabel;
    private javax.swing.JButton jSpeelButton;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jVraagAantalSpelers;
    // End of variables declaration//GEN-END:variables
}
