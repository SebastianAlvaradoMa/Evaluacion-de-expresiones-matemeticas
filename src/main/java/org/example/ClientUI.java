package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI {
    private JPanel panelMain;
    private JTextField textField1;
    private JButton guardarButton;
    private JButton tomarImagenButton;
    private JLabel resultLabel;
    private JButton cerrarConexiónButton;
    private JList list1;
    private EchoClient client = new EchoClient();
    TakePicture takePicture = new TakePicture();
    ReadImage readImage = new ReadImage();
    DefaultListModel listModel = new DefaultListModel();

    public ClientUI() {
        tomarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.startConnection("127.0.0.1", 5555);

                takePicture.takePicture();
                String msg1 = client.sendMessage(client.name + "," + readImage.leerImagen());
                //String msg1 = client.sendMessage(client.name + "," + "5*3/8+(95%5-10)");  //prueba sin usar caputura o lectura de imagen

                String[] message = msg1.split(", ");
                String terminate = client.sendMessage("bye");
                resultLabel.setText(message[2]);

                listModel.addElement(msg1);
                list1.setModel(listModel);
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.name = textField1.getText();
            }
        });
        cerrarConexiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = listModel.getSize();
                for (int i = 0; i < size; i++) {
                    String item = listModel.getElementAt(i).toString();
                    LogFile file = new LogFile();
                    file.AppendStringToFile(item,client.name);
                }
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientUI");
        frame.setContentPane(new ClientUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
