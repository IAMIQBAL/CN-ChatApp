import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.*;

public class Server implements ActionListener{

    // GUI
    static JFrame f1;
    static JTextArea ja1;
    static JButton jb1;
    static JButton connect;
    static JButton send;
    static JLabel title;
    static Container c;
    static JLabel port;
    static JTextField tPort;
    static JTextField outMsg;
    static JTextField jt1;
    static JLabel inMsg;

    // Socket
    static ServerSocket skt;
    static Socket ser;
    static DataInputStream din;
    static DataOutputStream dout;

    // Vars
    static String portNum;
    static int portN;

    Server (){

        f1 = new JFrame();

        f1.setTitle("Server");
        f1.setBounds(300, 90, 900, 600);
        f1.setDefaultCloseOperation(f1.EXIT_ON_CLOSE);
        f1.setResizable(false);

        c = f1.getContentPane();
        c.setLayout(null);

        title = new JLabel("Server");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(400, 30);
        c.add(title);

        port = new JLabel("Port Number:");
        port.setFont(new Font("Arial", Font.PLAIN, 20));
        port.setSize(150, 20);
        port.setLocation(100, 100);
        c.add(port);

        tPort = new JTextField();
        tPort.setFont(new Font("Arial", Font.PLAIN, 15));
        tPort.setSize(190, 20);
        tPort.setLocation(300, 100);
        c.add(tPort);

        connect = new JButton("Start Listening");
        connect.setFont(new Font("Arial", Font.PLAIN, 15));
        connect.setSize(200, 40);
        connect.setLocation(550, 100);
        connect.addActionListener(this);
        c.add(connect);

        inMsg = new JLabel();
        inMsg.setFont(new Font("Arial", Font.PLAIN, 30));
        inMsg.setSize(700, 150);
        inMsg.setLocation(100, 150);
        inMsg.setBackground(Color.DARK_GRAY);
        inMsg.setForeground(Color.WHITE);
        inMsg.setOpaque(true);
        c.add(inMsg);

        outMsg = new JTextField();
        outMsg.setFont(new Font("Arial", Font.PLAIN, 15));
        outMsg.setSize(700, 70);
        outMsg.setLocation(100, 350);
        c.add(outMsg);

        send = new JButton("Send");
        send.setFont(new Font("Arial", Font.PLAIN, 15));
        send.setSize(200, 40);
        send.setLocation(350, 450);
        send.addActionListener(this);
        c.add(send);

        f1.setVisible(true);
    }

    public static void main(String[] args){
        new Server();

        // System.out.println("PORT: " + portN);
        while (portN == 0){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e){}
        }

        try {
            String msgInput = "";
            skt = new ServerSocket(portN);
            ser = skt.accept();
            din = new DataInputStream(ser.getInputStream());
            dout = new DataOutputStream(ser.getOutputStream());

            tPort.disable();
            while (true){
                msgInput = din.readUTF();
                inMsg.setText(msgInput);

                if (msgInput.equals("end")){
                    skt.close();
                    ser.close();
                    break;
                }
            }
        } catch(Exception e){}
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == connect){
            // String port = "";
            portN = Integer.parseInt(tPort.getText());
            // inMsg.setText(port);
            // System.out.println("PORT: " + portN);
        }
        
        if (arg0.getSource() == send){
            try {
                String msg = "";
                msg = outMsg.getText();
                dout.writeUTF(msg);
                inMsg.setText(msg);
                outMsg.setText("");
            } catch (Exception e) {}
        }
    }
}