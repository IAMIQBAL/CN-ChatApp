import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.*;

public class Client implements ActionListener{

    // GUI
    static JFrame f1;
    static JTextField jt1;
    static JTextArea ja1;
    static JButton jb1;
    static JLabel title;
    static Container c;
    static JLabel ipPort;
    static JLabel status;
    static JTextField tIpPort;
    static JButton connect;
    static JButton send;
    static JLabel inMsg;
    static JTextField outMsg;

    // Socket
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    // Vars
    static String ipAddr;
    static int portN;

    Client (){

        f1 = new JFrame();

        f1.setTitle("Client");
        f1.setBounds(300, 90, 900, 600);
        f1.setDefaultCloseOperation(f1.EXIT_ON_CLOSE);
        f1.setResizable(false);

        c = f1.getContentPane();
        c.setLayout(null);

        title = new JLabel("Client");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(400, 30);
        c.add(title);

        ipPort = new JLabel("Enter IP and Port:");
        ipPort.setFont(new Font("Arial", Font.PLAIN, 20));
        ipPort.setSize(200, 20);
        ipPort.setLocation(100, 100);
        c.add(ipPort);

        status = new JLabel("IDLE");
        status.setFont(new Font("Arial", Font.PLAIN, 20));
        status.setSize(200, 20);
        status.setLocation(300, 125);
        status.setBackground(Color.RED);
        status.setOpaque(true);
        c.add(status);

        tIpPort = new JTextField();
        tIpPort.setFont(new Font("Arial", Font.PLAIN, 15));
        tIpPort.setSize(190, 20);
        tIpPort.setLocation(300, 100);
        c.add(tIpPort);

        connect = new JButton("Connect");
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
        new Client();

        // tIpPort.getText()
        while (portN == 0 || ipAddr == ""){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e){}
        }
        try {
            // s = new Socket("127.0.0.1", 3000);
            s = new Socket(ipAddr, portN);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            status.setBackground(Color.GREEN);
            tIpPort.disable();
            
            String msgInput = "";
            while (true){
                msgInput = din.readUTF();
                inMsg.setText(msgInput);
                if (msgInput.equals("end")){
                        s.close();
                        break;
                }
            }
        } catch (Exception e){}
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == connect){
            String addr = tIpPort.getText();
            String[] x = addr.split(":");
            ipAddr = x[0];
            portN = Integer.parseInt(x[1]);
            System.out.println("IP: " + ipAddr);
            System.out.println("PORT: " + portN);

        }

        if (arg0.getSource() == send){
            try {
                String msg = "";
                msg = outMsg.getText();
                dout.writeUTF(msg);
                inMsg.setText(msg);
            } catch (Exception e) {};
            outMsg.setText("");
        }
    }
}