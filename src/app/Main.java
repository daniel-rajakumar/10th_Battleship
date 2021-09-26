//Date: Mar 27, 2020
//Name: Daniel 
//Block: 3A
package app;

import java.net.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Main extends JFrame {
    private String ip;//[or in the game]CHANGE HERE IF YOU WANNA PLAY ONLINE(their device's ip adress "IPv4 Address")
    //how to find it? 
    //0) open cmd
    //1) type "ipconfig"
    //2) loock for the word "IPv4 Address: ";
    //3) copy and past it here(not your device's adress... your opponent's adress)
    //4) run it and have fun!
    /////////////////////////////
    //0)if same device type "localHost"

    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private int table_size = 7;
    private JPanel panel;

    private JButton[][] Battle_Table = new JButton[table_size][table_size];//battle ocean (page 2)
    private JButton[][] answer_Table = new JButton[table_size][table_size];//answer(battle ocean)(page 2)
    private JTextField[][] inputTable = new JTextField[table_size][table_size];//your ocean(page 1)
    private JLabel[][] table2 = new JLabel[table_size][table_size];//minimap(shows ur ocean during battle)(page 2)
    private JLabel status;//offline/online
    private JButton host, join, summit;
    private JPanel body2;

    // private boolean p1 = false, p2 = false;
    private int port = 1024; 
    private boolean host_turn = true;
    private boolean guest_turn = false;
    private JLabel turn = new JLabel();
    private JScrollPane textarea;
    JTextArea ipadress;
    JTextArea send;//for chat
    JTextArea msg;//for chat
    JLabel show_lives;//health
    int p1_lives = 7, p2_lives = 7;//lives

    public static void main(String[] args) throws Exception{
        new Main();
    }
    
    public Main() {
        JOptionPane.showMessageDialog(frame, 
            "<html> " + 
            " YOU WANT TO DO ONLINE BATTLE? (Unfortunately that is the only choice)<br>" +
            " - in the box below and type their device's IP adress <br>" +
            " - if you want to battle on the same device than type \"localHost\" (recommended) <br>" +
            " - [warning] do not try to run this code in a website... sometime you dont get networking access... <br>" +
            " - [TIP] run the code in IDE or codeEditor " +
            "How to play on the same devide? () <br>" + 
            "0) Open up this file in a IDE/TextEditor <br> " +
            "1) run it... <br>" +
            "2) run it again(without closing your previous window) <br>" +
            "3) have fun!! <br>" +
            "</html>");
        ACCSI_ART();
        GUI();
    }

    public void GUI() {
        frame();
    }

    private void frame() {
        frame = new JFrame();// setting up Jframe
        int size = 1000;
        int width = size;
        int height = size;
        frame.setSize(width, height);
        //System.out.println(frame.getWidth());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame_liveResizer(frame);
        mainPanel(width, height);
        frame.setResizable(false);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void mainPanel(int w, int h) {
        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(0, 0, w, h);
        page1(panel);
        panel.setLayout(null);
        panel.setVisible(true);
    }

    private void page1(JPanel p) {
        JPanel header = new JPanel();
        JPanel body = new JPanel();
        JPanel sidebar1 = new JPanel();
        JPanel sidebar2 = new JPanel();
        page1_header(header);
        page1_body(body);
        page1_sidebar1(sidebar1);
        page1_sidebar2(sidebar2);
        p.add(header);
        p.add(body);
        p.add(sidebar1);
        p.add(sidebar2);
    }

    private void page2(JPanel p, Socket s) {
        JPanel header = new JPanel();
        JPanel body1 = new JPanel();
        body2 = new JPanel();
        JPanel sidebar = new JPanel();
        JPanel footer = new JPanel();
        page2_body1(body1);
        page2_sidebar(sidebar);
        page2_header(header);
        page2_body2(body2);
        page2_footer(footer, s);
        p.add(sidebar);
        p.add(footer);
        p.add(header);
        p.add(body1);
        p.add(body2);
    }

    private void page1_header(JPanel h) {
        int x = (int)((panel.getWidth() / 100.0) * 1);
        int y = (int)((panel.getHeight() / 100.0) * 2);
        int width = (int)((panel.getWidth() / 100.0) * 96);
        int height = (int)((panel.getHeight() / 100.0) * 15);

        page1_header_text1(h);
        page1_header_text2(h);
        page1_header_text3(h);
        h.setBounds(x, y, width, height);
        h.setBackground(Color.RED);
        width = h.getWidth();
        height = h.getHeight();
        h.setLayout(null);
    }

    void page1_header_text1(JPanel h){
        JLabel text = new JLabel();
        int x = (int)((panel.getWidth() / 100.0) * 1);
        int y = -(int)((panel.getHeight() / 100.0) * 5);
        int width = (int)((panel.getWidth() / 100.0) * 96);
        int height = (int)((panel.getHeight() / 100.0) * 25);

        text.setText(
            "<html>" +
            "Hey there, Welcome to your Ocean! <br>" +
            "Your task is to win against <br>" +
            "...your opponent <br>" +
            "Rules: <br>" +
            "  - Dont cheat!!! " +
            "</html>"
        );
        text.setFont(new Font("", Font.BOLD, width/55));
        text.setBounds(x,y,width,height);
        h.add(text);
    }

    void page1_header_text2(JPanel h){
        JLabel text = new JLabel();
        int x = (int)((panel.getWidth() / 100.0) * 36);
        int y = -(int)((panel.getHeight() / 100.0) * 5);
        int width = (int)((panel.getWidth() / 100.0) * 96);
        int height = (int)((panel.getHeight() / 100.0) * 25);

        text.setText(
            "<html>" +
            "Steps: <br>" +
            "0) Either host or join a game <br>" +
            "1) place your ship(enter or press random) <br>" +
            "2) Press ready" +
            "</html>"
        );
        text.setFont(new Font("", Font.BOLD, width/55));
        text.setBounds(x,y,width,height);
        h.add(text);
    }

    void page1_header_text3(JPanel h){
        JLabel text = new JLabel();
        int x = (int)((panel.getWidth() / 100.0) * 75);
        int y = -(int)((panel.getHeight() / 100.0) * 9);
        int width = (int)((panel.getWidth() / 100.0) * 96);
        int height = (int)((panel.getHeight() / 100.0) * 25);

        text.setText("<html> " + 
                    " By: Daniel <br>" +
                    " Block: 3A <br>" +
                    "</html>"
        );
        text.setFont(new Font("", Font.BOLD, width/55));
        text.setBounds(x,y,width,height);
        h.add(text);
    }

    private void page1_sidebar1(JPanel s1) {
        int x = (int)((panel.getWidth() / 100.0) * 78);
        int y = (int)((panel.getHeight() / 100.0) * 39);
        int width = (int)((panel.getWidth() / 100.0) * 18);
        int height = (int)((panel.getHeight() / 100.0) * 55);
        
        
        s1.setBounds(x, y, width, height);
        s1.setBackground(Color.YELLOW);
        width = s1.getWidth();
        height = s1.getHeight();
        boat_status(s1);
        page1_summitButton(s1);
        page1_RandomlyAssignShipButton(s1);
        s1.setLayout(null);
    }

    void boat_status(JPanel s1) {
        int x = (int)((panel.getWidth() / 100.0) * 0);
        int y = -(int)((panel.getHeight() / 100.0) * 10);
        int width = (int)((panel.getWidth() / 100.0) * 18);
        int height = (int)((panel.getHeight() / 100.0) * 75);

        JLabel art = new JLabel("");

        art.setText(
            "<html>" + 
            "[size](symb)name <br> " + 
            "[1] (Y)ork <br> " + 
            "[2] (D)estroyer <br> " + 
            "[3] (S)ubmarine <br> " + 
            "[3] (C)ruiser <br> " +
            "[4] (B)attleShip <br> " + 
            "[5](A)ircraft-Carrier <br> " +
            "[6+] (T)itanic" + 
            "</html>"
        );

        art.setFont(new Font("", Font.BOLD, width/8));
        art.setBounds(x,y,width,height);
        s1.add(art);
    }
       

    private void page1_sidebar2(JPanel s2) {
        int x = (int)((panel.getWidth() / 100.0) * 78);
        int y = (int)((panel.getHeight() / 100.0) * 19);
        int width = (int)((panel.getWidth() / 100.0) * 18);
        int height = (int)((panel.getHeight() / 100.0) * 18);

        s2.setBounds(x, y, width, height);
        s2.setBackground(Color.BLUE);
        width = s2.getWidth();
        height = s2.getHeight();
        page1_netWorkingStatus(s2);
        page1_netWorkingButtons(s2);
        page1_netWorking_IPadress(s2);
        s2.setLayout(null);
    }

    private void page1_body(JPanel b) {
        int x = (int)((panel.getWidth() / 100.0) * 1);
        int y = (int)((panel.getHeight() / 100.0) * 19);
        int width = (int)((panel.getWidth() / 100.0) * 75);
        int height = (int)((panel.getHeight() / 100.0) * 75);

        b.setBounds(x, y, width, height);
        b.setBackground(Color.RED);
        width = b.getWidth();
        height = b.getHeight();
        page1_oceanFill();
        page1_oceanBounds(width, height);
        // page1_mouseListener();
        page1_oceanDisplay(b);
        b.setLayout(null);
    }

    private void page2_body1(JPanel b1) {
        int x = (int)((panel.getWidth() / 100.0) * 1);
        int y = (int)((panel.getHeight() / 100.0) * 15);
        int width = (int)((panel.getWidth() / 100.0) * 70);
        int height = (int)((panel.getHeight() / 100.0) * 70);

        b1.setBounds(x, y, width, height);
        b1.setBackground(Color.RED);
        width = b1.getWidth();
        height = b1.getHeight();
        page2_oceanFill1();
        page2_oceanBounds(width, height);
        //page2_Answers();//uncomment this for answer during battle| No cheating btw :) |
        page2_oceanDisplay(b1);
        b1.setLayout(null);
    }

    private void page2_header(JPanel h){
        int x = (int)((panel.getWidth() / 100.0) * 1);
        int y = (int)((panel.getHeight() / 100.0) * 2);
        int width = (int)((panel.getWidth() / 100.0) * 96);
        int height = (int)((panel.getHeight() / 100.0) * 12);

        h.setBounds(x, y, width, height);
        h.setBackground(Color.orange);
        width = h.getWidth();
        height = h.getHeight();
        page2_turn(h);
        page2_lives(h);
        h.setLayout(null);
    }

    private void page2_turn(JPanel h){
        int x = (int)((panel.getWidth() / 100.0) * 1);
        int y = (int)((panel.getHeight() / 100.0) * 2);
        int width = (int)((panel.getWidth() / 100.0) * 66);
        int height = (int)((panel.getHeight() / 100.0) * 12);

        turn.setText("Battle! (Host's turn)");
        turn.setFont(new Font("", Font.BOLD, width/10));
        turn.setBounds(x,y,width,height);
        h.add(turn);
    }

    private void page2_lives(JPanel h){
        show_lives = new JLabel();
        int x = (int)((panel.getWidth() / 100.0) * 55);
        int y = (int)((panel.getHeight() / 100.0) * 2);
        int width = (int)((panel.getWidth() / 100.0) * 36);
        int height = (int)((panel.getHeight() / 100.0) * 12);
        
        show_lives.setFont(new Font("", Font.BOLD, width/4));
        show_lives.setBounds(x,y,width,height);
        h.add(show_lives);
    }



    private void page2_footer(JPanel f, Socket s){
        int x = (int)((panel.getWidth() / 100.0) * 1);
        int y = (int)((panel.getHeight() / 100.0) * 86);
        int width = (int)((panel.getWidth() / 100.0) * 70);
        int height = (int)((panel.getHeight() / 100.0) * 9);

        f.setBounds(x, y, width, height);
        page2_MSG_sendbutton(f, s);
        page2_MSG_tpying(f);
        f.setBackground(Color.MAGENTA);
        f.setLayout(null);
    }

    public void page2_Answers() {
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                Battle_Table[y][x].setText(answer_Table[y][x].getText());
            }
        }
    }

    private void page2_sidebar(JPanel s) {
        int x = (int)((panel.getWidth() / 100.0) * 72);
        int y = (int)((panel.getHeight() / 100.0) * 42);
        int width = (int)((panel.getWidth() / 100.0) * 25);
        int height = (int)((panel.getHeight() / 100.0) * 53);

        s.setBounds(x, y, width, height);
        page2_addchatbox(s);
        s.setBackground(Color.BLUE);
        s.setLayout(null);
    }

    private void page2_body2(JPanel b2) {
        int x = (int)((panel.getWidth() / 100.0) * 72);
        int y = (int)((panel.getHeight() / 100.0) * 15);
        int width = (int)((panel.getWidth() / 100.0) * 25);
        int height = (int)((panel.getHeight() / 100.0) * 26);

        b2.setBounds(x, y, width, height);
        b2.setBackground(Color.GREEN);
        width = b2.getWidth();
        height = b2.getHeight();
        page2_oceanFill2();
        page2_oceanBounds2(width, height);
        page2_oceanDisplay2(b2);
        b2.setLayout(null);
    }

    private void page2_oceanFill1() {
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                Battle_Table[y][x] = new JButton("");
                Battle_Table[y][x].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }// ends

    private void page2_oceanFill2() {
        for (int y = 0; y < table2.length; y++) {
            for (int x = 0; x < table2[y].length; x++) {
                table2[y][x] = new JLabel("");
                table2[y][x].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }// ends

    private void page1_oceanFill() {
        for (int y = 0; y < inputTable.length; y++) {
            for (int x = 0; x < inputTable[y].length; x++) {
                inputTable[y][x] = new JTextField("", 1);
                inputTable[y][x].setBackground(Color.pink);
                inputTable[y][x].setFont(new Font("", Font.BOLD, 100));
                inputTable[y][x].setHorizontalAlignment(JTextField.CENTER);
                inputTable[y][x].setDocument(new JTextFieldLimit(1));//limiting to 1char for each tile 
            }
        }
    }// ends

    private class JTextFieldLimit extends PlainDocument {//limiting the text (ocean boat's initial)
        int limit;

        JTextFieldLimit(int limit) {
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;
            if ((getLength() + str.length()) <= limit)
                super.insertString(offset, str, attr);
        }
    }// ends class

    private void page1_summitButton(JPanel p) {
        summit = new JButton("Ready");
        int x = (int)((p.getWidth() / 100.0) * 5);
        int y = (int)((p.getHeight() / 100.0) * 82);                                
        int width = (int)((p.getWidth() / 100.0) * 90);
        int height = (int)((p.getHeight() / 100.0) * 15);

        summit.setFont(new Font("", Font.BOLD, (int)(width/5.0)));
        summit.setBounds(x, y, width, height);
        summit.setBackground(Color.green);
        p.add(summit);
    }

    private void page1_netWorkingStatus(JPanel p) {
        status = new JLabel("Offline");
        int x = (int)((p.getWidth() / 100.0) * 0);
        int y = (int)((p.getHeight() / 100.0) * 0);                                
        int width = (int)((p.getWidth() / 100.0) * 100);
        int height = (int)((p.getHeight() / 100.0) * 22);

        status.setFont(new Font("", Font.BOLD, (int)(width/7.0)));
        status.setBounds(x, y, width, height);
        status.setHorizontalAlignment(JTextField.CENTER);
        p.add(status);
    }

    private void page1_netWorkingButtons(JPanel p) {
        host = new JButton();
        join = new JButton();

        Runnable client = new Runnable() {
            public void run() {
                client foo = new client();
                foo.page1_joinButton(join);
            }
        };

        Runnable server = new Runnable() {
            public void run() {
                server foo = new server();
                foo.page1_hostButton(host);
            }
        };
    
        new Thread(client).start();////new thread 
        new Thread(server).start();//new thread
        p.add(host);
        p.add(join);
    }

    private void page1_netWorking_IPadress(JPanel s){
        ipadress = new JTextArea("Ipadress here");
        int x = (int)((s.getWidth() / 100.0) * 5);
        int y = (int)((s.getHeight() / 100.0) * 47);                                
        int width = (int)((s.getWidth() / 100.0) * 90);
        int height = (int)((s.getHeight() / 100.0) * 22);
        
        ipadress.setFont(new Font("", Font.BOLD, (int)(width/7.0)));
        ipadress.setBounds(x, y, width, height);
        ////////////////////////////////////////////////////////////////////////////
        y = (int)((s.getHeight() / 100.0) * 12);
        JLabel local_device_tip = new JLabel("same device?");
        local_device_tip.setFont(new Font("", Font.BOLD, (int)(width/8.0)));
        local_device_tip.setBounds(x, y, width, height);
        ////////////////////////////////////////////////////////////////////////////////////
        y = (int)((s.getHeight() / 100.0) * 24);
        JLabel local_device_tip2 = new JLabel("type: \"localHost\"");
        local_device_tip2.setFont(new Font("", Font.BOLD, (int)(width/8.0)));
        local_device_tip2.setBounds(x, y, width, height);
        //ipadress.setHorizontalAlignment(JTextField.CENTER);
        s.add(local_device_tip);
        s.add(local_device_tip2);
        s.add(ipadress);

    }

    

    private void page1_RandomlyAssignShipButton(JPanel p){
        JButton random = new JButton("Random");
        int x = (int)((p.getWidth() / 100.0) * 5);
        int y = (int)((p.getHeight() / 100.0) * 5);                                
        int width = (int)((p.getWidth() / 100.0) * 90);
        int height = (int)((p.getHeight() / 100.0) * 15);


        int[] ships = {5, 4, 3, 3, 2, 1};
        random.setFont(new Font("", Font.BOLD, (int)(width/5.0)));
        random.setBounds(x, y, width, height);
        random.setBackground(Color.orange);
        random.setHorizontalAlignment(JTextField.CENTER);
        random.addActionListener(new randomShipGenerator(ships));
        p.add(random);
    }


    private void page2_addchatbox(JPanel s){
        msg = new JTextArea(16,58);
        JScrollPane  scroll = new JScrollPane(msg);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        msg.setEditable(false);

        int x = (int)((s.getWidth() / 100.0) * 0);
        int y = (int)((s.getHeight() / 100.0) * 0);                                
        int width = (int)((s.getWidth() / 100.0) * 100);
        int height = (int)((s.getHeight() / 100.0) * 100);

        msg.setText("your message shows here");
        msg.setLineWrap(true);
        msg.setWrapStyleWord(true);
        scroll.setFont(new Font("", Font.BOLD, width/4));
        scroll.setBounds(x, y, width, height);
        
        s.add(scroll);
    }

    private void page2_MSG_tpying(JPanel f){
        send = new JTextArea("Type your message here... and send it to your opponent!");
        int x = (int)((f.getWidth() / 100.0) * 0.5);
        int y = (int)((f.getHeight() / 100.0) * 7);                                
        int width = (int)((f.getWidth() / 100.0) * 78);
        int height = (int)((f.getHeight() / 100.0) * 80);

        send.setFont(new Font("", Font.BOLD, width/16));
        send.setBounds(x, y, width, height);
        f.add(send);
    }

    private void page2_MSG_sendbutton(JPanel f, Socket s) {
        JButton send = new JButton("Send");
        int x = (int)((f.getWidth() / 100.0) * 79.5);
        int y = (int)((f.getHeight() / 100.0) * 7);                                
        int width = (int)((f.getWidth() / 100.0) * 20);
        int height = (int)((f.getHeight() / 100.0) * 85);

        send.setFont(new Font("", Font.BOLD, width/5));
        send.setBounds(x, y, width, height);
        send.addActionListener(new send_button(s));
        f.add(send);
    }

    String save = "Game: Hey you, Good luck! \n\n";//this saves the chat
    private class send_button implements ActionListener{
        Socket s;

        send_button(Socket s){this.s = s;}

        @Override
        public void actionPerformed(ActionEvent e) {
            String your_msg = send.getText();
            try {send(your_msg);} catch (Exception e1) {}
            save += "you: " + your_msg + "\n\n";
            send.setText("");
            System.out.println(your_msg);
            msg.setText(save);
        }
        
        private void send(String your_msg) throws Exception {
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());// 1
            chat_info obj = new chat_info(your_msg);
            os.writeObject(obj);// 3
        }
    }





    //server classs starts(this runs only for host)
    private class server {// player 1
        Socket s, s2;
        ServerSocket ss, ss2;
        boolean me_ready = false;
        boolean Opponent_ready = false;
        boolean hit;

        private void page1_hostButton(JButton b) {
            int x = (int)((panel.getWidth() / 100.0) * 1);
            int y = (int)((panel.getHeight() / 100.0) * 13);
            int width = (int)((panel.getWidth() / 100.0) * 7);
            int height = (int)((panel.getHeight() / 100.0) * 4);


            b.setText("Host");
            b.setFont(new Font("", Font.BOLD, (int)(frame.getWidth()/80.0)));
            b.setBounds(x, y, width, height);
            b.setHorizontalAlignment((int) JTextField.LEFT_ALIGNMENT);
            b.addActionListener(new host_button());// if I click the "host" button, then I am the host of this game
        }
        private class host_button implements ActionListener {// "host" button
            private void host_Connect() {
                try {
                    System.out.println("Server started");
                    ss = new ServerSocket(port);// creats a server
                    ss2 = new ServerSocket(port+2);// creats a server
                    System.out.println("server is waiting for client to accept");
                    s = ss.accept();// accepts the client
                    s2 = ss.accept();// accepts the client
                    System.out.println("client connected");
                } catch (Exception e2) {}
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                host_Connect();// connecting host
                status.setText("Online");
                frame.setTitle("Player 1 - server");// sets the tutle to player 1
                summit.addActionListener(new host_ready(s));// if the player 1 press the button
                backgroundrun();// waiting for my opponent to get ready(it notifies the user)
            }// ends actionlistener
        }// ends class

        private class host_ready implements ActionListener {// "ready" button(for guest)
            Socket s;

            public host_ready(Socket s) {
                this.s = s;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                me_ready = true;
                try {sending(s,true);} catch (Exception e1) {} // sending my data to my opponent
                if (me_ready && Opponent_ready) open_page2(s, s2, false);// if they are already ready than run else wait for them to ready
            }
        }// ends listener

        private class host_ButtonListener implements ActionListener {
            char symbol;//to get hit/miss symbol
            Socket s;
            boolean empty_spot = true;
            


            host_ButtonListener(Socket s){this.s = s;}
    
            @Override
            public void actionPerformed(ActionEvent e) {
                empty_spot = check_empty_spot(e);
                if (host_turn && empty_spot) {
                    if(p1_lives > 0 && p2_lives > 0){
                        attack(e, s, hit, symbol, false);
                        host_turn = false;
                        turn.setText("Waiting");
                        show_lives.setText("Lives: " + p1_lives+"");
                    }
                    //check_winner(p1_lives, p2_lives);
                }
                //check_winner(p1_lives, p2_lives);
                
            }// action
        }// ocean listener

        ///////////////////////////////////////////////////////////////////////////////////////////
        
        private void receive() throws Exception {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());// getting data from socket
            dataCollection obj1 = (dataCollection) in.readObject();// reading the Object my opponent send
            waiting obj2 = (waiting) in.readObject();// making an object so you can collect it as an object
            obj1.collectDetails(answer_Table);// collecting the data
            obj2.collectDetails(me_ready);// collecting the data
            frame.setTitle("opponent ready");// updating my title, so I know that my opponent is ready
            Opponent_ready = true;
            if (me_ready && Opponent_ready) open_page2(s, s2, false);
        }

        private void receive_battle() throws Exception {//receiving battle info
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());// getting data from socket
            battle_info obj1 = (battle_info) in.readObject();// reading the Object my opponent send
            table2[obj1.y][obj1.x].setText(obj1.symbol+"");  
            table2[obj1.y][obj1.x].setBackground(Color.BLUE);
            table2[obj1.y][obj1.x].setOpaque(true);
            if (!obj1.turn) host_turn = true;
            p2_lives = obj1.lives;
            check_winner(p1_lives, p2_lives);
            turn.setText("YOUR TURN!");
        }

        private void receive_chat() throws Exception {
            ObjectInputStream in = new ObjectInputStream(s2.getInputStream());// getting data from socket
            chat_info obj1 = (chat_info) in.readObject();// reading the Object my opponent send
            System.out.println("\nopponent: " + obj1.text + "\n");
            save += "Opponent: " + obj1.text + "\n\n";
            msg.setText(save);
        }

        
        private void backgroundrun(){//this is to run background... we can play can play while its wating for opponent's data in background
            Runnable chat = new Runnable() {
                public void run() {  
                    for (int i = 0; i < 1000; i++)
                        try {receive_chat();} catch (Exception e) {}
                       
                }
            };
            Runnable battle = new Runnable() {
                public void run() {  
                for (int i = 0; i < 70; i++) 
                        try {receive_battle();} catch (Exception e) {}   
                                   
                }
            };
            Runnable r = new Runnable() {
                public void run() {
                    try {receive();} catch (Exception e2) {}
                    new Thread(battle).start();
                    new Thread(chat).start();
                }
            };
            
            new Thread(r).start();
        }
    }//SERVER
    
    //server ends
 



    private boolean check_empty_spot(ActionEvent e){
        boolean same_place = true;
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {

                if (e.getSource() == Battle_Table[y][x]){
                    if (Battle_Table[y][x].getText().equals("")) {
                        same_place = true;
                    } else {
                        same_place = false;
                    }
                } 
            }
        }
        return same_place;
    }









    //client classs starts(this runs only for guest)

    private class client {// player 2
        Socket s, s2;
        boolean Opponent_ready = false;
        boolean me_ready = false;
        boolean hit;

        private void page1_joinButton(JButton b) {
            int x = (int)((panel.getWidth() / 100.0) * 10);
            int y = (int)((panel.getHeight() / 100.0) * 13);
            int width = (int)((panel.getWidth() / 100.0) * 7);
            int height = (int)((panel.getHeight() / 100.0) * 4);

            b.setText("Join");
            b.setFont(new Font("", Font.BOLD, (int)(panel.getWidth()/80.0)));
            b.setBounds(x, y, width, height);
            b.setHorizontalAlignment((int) JTextField.LEFT_ALIGNMENT);
            b.addActionListener(new join_button());
        }

        private class join_button implements ActionListener {// "join"" button
            private void join_Connect() {
                System.out.println("client is looking for the host");
                try {s = new Socket(ip, port);} catch (Exception e2) {}
                try {s2 = new Socket(ip, port);} catch (Exception e2) {}
                System.out.println("server connected");
            }

            private void get_Ipadress(){
                ip = ipadress.getText();
                ipadress.setText("");
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                get_Ipadress();
                join_Connect();
                status.setText("Online");
                frame.setTitle("Player 2 - client - joiner");
                summit.addActionListener(new guest_ready(s));
                backgroundrun();
            }// ends action listener
        }// ends class

        private class guest_ready implements ActionListener {// "ready" button(for guest)
            Socket s;

            public guest_ready(Socket s) {this.s = s;}

            @Override
            public void actionPerformed(ActionEvent e) {
                me_ready = true;
                try {sending(s,true);} catch (Exception e1) {}
                if (Opponent_ready && me_ready) open_page2(s, s2, true);
            }
        }//ends listener

        private class guest_ButtonListener implements ActionListener {
            char symbol;//to get hit/miss symbol
            Socket s;
            boolean empty_spot = true;

            guest_ButtonListener(Socket s){
                this.s = s;
            }
    
            @Override
            public void actionPerformed(ActionEvent e) {
                empty_spot = check_empty_spot(e);
                if (guest_turn && empty_spot) {
                    if((p2_lives) > 0 && (p1_lives > 0)){
                        attack(e, s, hit, symbol, true);
                        guest_turn = false;//making my turn false until oppent makes a move
                        turn.setText("Waiting");
                        show_lives.setText("Lives: " + p2_lives+"");
                    }
                    //check_winner(p2_lives, p1_lives);
                } ;
                
            }// action
        }// ocean listener

        //////////////////////////////////////////////////////////////////////////////////////////////
        private void receive() throws Exception {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            dataCollection obj1 = (dataCollection) in.readObject();
            waiting obj2 = (waiting) in.readObject();
            obj1.collectDetails(answer_Table);
            obj2.collectDetails(Opponent_ready);
            frame.setTitle("opponent ready");
            Opponent_ready = true;
            if (me_ready && Opponent_ready) open_page2(s, s2, true);
        }
        
        private void receive_battle() throws Exception {//reeiving
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());// getting data from socket
            battle_info obj1 = (battle_info) in.readObject();// reading the Object my opponent send//making the data to an object
            table2[obj1.y][obj1.x].setText(obj1.symbol+"");  
            table2[obj1.y][obj1.x].setBackground(Color.BLUE);
            table2[obj1.y][obj1.x].setOpaque(true);
            if (!obj1.turn) guest_turn = true;
            p1_lives = obj1.lives;
            check_winner(p2_lives, p1_lives);
            turn.setText("YOUR TURN!");// updating my title, so I know that my opponent is ready
        }

        private void receive_chat() throws Exception {
            ObjectInputStream in = new ObjectInputStream(s2.getInputStream());// getting data from socket
            chat_info obj1 = (chat_info) in.readObject();// reading the Object my opponent send
            System.out.println("opponent: " + obj1.text + "\n");
            save += "Opponent: " + obj1.text + "\n\n";
            msg.setText(save);
        }

        private void backgroundrun(){//this is to run background... we can play can play while its wating for opponent's data in background
            Runnable chat = new Runnable() {
                public void run() {  
                    for (int i = 0; i < 1000; i++) 
                        try {receive_chat();} catch (Exception e) {}
                }
            };
            Runnable battle = new Runnable() {
                public void run() { 
                    for (int i = 0; i < 70; i++)
                        try {receive_battle();} catch (Exception e) {}   
                }
            };
            Runnable r = new Runnable() {
                public void run() {
                    try {receive();} catch (Exception e2) {}
                    new Thread(battle).start();
                    new Thread(chat).start();
                }
            };
            
            new Thread(r).start();
        }
    }

    //client class ends

    

    private void attack(ActionEvent e, Socket s, boolean hit, char symbol, boolean client){
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                if (e.getSource() == Battle_Table[y][x]) 
                    attackingProcess(s, hit, symbol, y, x, client);
            } // x
        } // y

    }
    
    void attackingProcess(Socket s, boolean hit, char symbol, int y, int x, boolean client){
        int lives = 0;
        symbol = get_symbol(y, x);
        apply(symbol, y, x);
        if (symbol == 'x') hit = true;
        else hit = false;
        if (client){
            if (symbol == '~') p2_lives--;
            lives = p2_lives;
            check_winner(p2_lives, p1_lives);
        } else {
            if (symbol == '~') p1_lives--;
            lives = p1_lives;
            //System.out.println("this is working");
            check_winner(p1_lives, p2_lives);
        }

        

        try {sending_page2(s, false, symbol, y, x, lives, client);} catch (Exception e) {}
        
    }

    private void sending_page2(Socket s,boolean your_turn, char symbol, int y, int x, int lives, boolean client) throws Exception {
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());// 1
        battle_info obj = new battle_info(your_turn, symbol, y, x, lives, client);
        os.writeObject(obj);// 3
    }

    private void check_winner(int you, int opponent){
        //System.out.println("this is working 1");
        if (host_turn || guest_turn) {
            //System.out.println("this is working 2");
            if (you == 0 || opponent == 0) {
                //System.out.println("this is working 3");
                if (you > opponent){
                    frame.setTitle("you WIN!");
                    winner_art();
                    } 
                    
                    if(you < opponent){
                    frame.setTitle("you lose :(");
                    loser_art();
                }

                //making it false, no they can not take next turn
                host_turn = false;
                guest_turn = false;
            }
        }
    }



    class dataCollection implements Serializable {
        JTextField[][] table;

        dataCollection(JTextField[][] table){this.table = table;}

        void collectDetails(JButton[][] jb){
            for (int y = 0; y < jb.length; y++) {
                for (int x = 0; x < jb[y].length; x++) {
                    jb[y][x] = new JButton(table[y][x].getText());
                }
                //System.out.println();
            }
        }
    }//ends data collection
    
    class waiting implements Serializable {
        boolean ready;

        waiting(boolean ready){this.ready = ready;}

        void collectDetails(boolean ready){
            ready = this.ready;
            summit.setText("ready");
        }
    }//ends data collection


    class battle_info implements Serializable {
        int y, x;
        boolean turn;
        char symbol;
        int lives;
        boolean client;

        battle_info(boolean turn, char symbol, int y, int x, int lives, boolean client){
            this.y = y;
            this.x = x;
            this.turn = turn;
            this.symbol = symbol;
            this.lives = lives;
            this.client = client;

            //System.out.println("I sended");
            // if (client) {//p2
            //     check_winner(p2_lives, lives);
            // } else {//p1
            //     check_winner(p1_lives, lives);
            // }
        }
    }

    class chat_info implements Serializable {
        String text;

        chat_info(String text){
            this.text = text;
        }
    }


    private char get_symbol(int y, int x) {
        char symb;
        if (answer_Table[y][x].getText().equals("")) {
            symb = '~';// miss
        } else {
            symb = 'x';
        }
        return symb;
    }

    
    private void apply(char symbol, int y, int x) {
        Battle_Table[y][x].setText(Character.toString(symbol));
        Battle_Table[y][x].setFont(new Font("", Font.BOLD, 20));
    }
    


    private void sending(Socket s, boolean ready) throws Exception {
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());// creating an output area
        dataCollection obj = new dataCollection(inputTable);// making an Object to send it throught socket
        os.writeObject(obj);// sending it
        waiting obj2 = new waiting(ready);// making an object
        os.writeObject(obj2);// sending it
        frame.setTitle("Waiting for your opponent");
    }

    private void open_battlefield(Socket s) {
        panel.removeAll();// removing all the panels from the frame(page 1)
        panel.repaint();// refreshing the panel
        page2(panel, s);// adding page2
    }

    
    private void my_ocean() {// transfering my data to my mini map(to see my ocean during battle)
        for (int y = 0; y < inputTable.length; y++) {
            for (int x = 0; x < inputTable[y].length; x++) {
                String text = inputTable[y][x].getText();
                table2[y][x].setText(text);
            }
        }
    }

    private void mouseListener(Socket s, boolean areYouClient) {
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                if (areYouClient) Battle_Table[y][x].addActionListener(new client().new guest_ButtonListener(s));
                else Battle_Table[y][x].addActionListener(new server().new host_ButtonListener(s));
            }
        }
    }// ends

    private void open_page2(Socket s, Socket s2, boolean areYouClient) {
        frame.setTitle("Battle!!!!");
        open_battlefield(s2);// opening my opponent ocean, so i can attack
        mouseListener(s, areYouClient);
        my_ocean();// printing my ocean(so I can see what is happening on my ocean during battle)
        panel.repaint();// refreshing so all the buttons are set perfectly
    }

    


    private void page2_oceanBounds(int width, int height) {
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                Battle_Table[y][x].setBounds((x * (width / table_size)), (y * (height / table_size)), (width / table_size), (height / table_size));
            }
        }
    }// ends
    private void page2_oceanBounds2(int width, int height) {
        for (int y = 0; y < table2.length; y++) {
            for (int x = 0; x < table2[y].length; x++) {
                table2[y][x].setBounds((x * (width / table_size)), (y * (height / table_size)), (width / table_size), (height / table_size));
            }
        }
    }// ends
    private void page1_oceanBounds(int width, int height) {
        for (int y = 0; y < inputTable.length; y++) {
            for (int x = 0; x < inputTable[y].length; x++) {
                inputTable[y][x].setBounds((x * (width / table_size)), (y * (height / table_size)), (width / table_size), (height / table_size));
            }
        }
    }// ends
    


    private void page2_oceanDisplay(JPanel b1) {
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                b1.add(Battle_Table[y][x]);
            }
        }
    }// ends
    private void page2_oceanDisplay2(JPanel b2) {
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                b2.add(table2[y][x]);
            }
        }
    }// ends
    private void page1_oceanDisplay(JPanel b) {
        for (int y = 0; y < Battle_Table.length; y++) {
            for (int x = 0; x < Battle_Table[y].length; x++) {
                b.add(inputTable[y][x]);
            }
        }
    }// ends

    private void frame_liveResizer(JFrame f){//i did not add this
        f.getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                Component c = (Component)e.getSource();
                f.setTitle("W: " + c.getWidth() + "H: " + c.getHeight());
            }
        });
        
    }



    //for random ship generating button!
    class randomShipGenerator implements ActionListener{
        int[] ship;

        randomShipGenerator(int[] ship){
            this.ship = ship;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean perfect_ocean = true;
            int count = 0;

            do {
                count++;
                refresh_ocean();
                perfect_ocean = generate_ships1();
            } while (!perfect_ocean && (count < 1000000));
            
            print_ocean();
            //System.out.println("Trys: " + count);
        }

        
        boolean generate_ships1(){
            int size;
            boolean gudship = false;
            boolean perfect_ocean = true;
            
            for (int num = 0; num < ship.length; num++) {
                size = ship[num];//its size(length)
                int move = smartgenerator(size);

                if (random(0, 1) == 1) gudship = print(random(0, table_size-1), size, move, "x");
                else gudship = print(random(0, table_size-1), size, move, "y");
                
                if (perfect_ocean && !gudship) perfect_ocean = false;
            }//shp num

            return perfect_ocean;
        }
        
        boolean overlap_check(int num, int move, String axies){
            boolean overlapping = false;
            if (axies.equals("x")) {
                if (!(inputTable[num][move].getText().equals(""))) {
                    overlapping = true;
                }
            } else if (axies.equals("y")){
                if (!(inputTable[move][num].getText().equals(""))) {
                    overlapping = true;
                }
            }
                
            return overlapping;
        }
        

        
        boolean print(int num, int size, int move, String axies){
            boolean overlapping = false;
            boolean gudship = true;
            char letter = ' ';
            
            //check
            for (int start = 0; start < size; start++) {//printing the boat
                if (axies.equals("x")) overlapping = overlap_check(num, start+move, "x");
                else if (axies.equals("y")) overlapping = overlap_check(num, start+move, "y");
                if (gudship && overlapping) gudship = false;
            }//loop
            
            
            letter = changeletters(size);
            for (int start = 0; start < size; start++) {
                if (axies.equals("x")) inputTable[num][start+move].setText(letter+""); 
                else if (axies.equals("y"))inputTable[start+move][num].setText(letter+""); 
            }//loop
            
            
            
            return gudship;
        }//print

        
        int count = 0;
        char changeletters(int size){
            char letter = ' ';
            switch (size) {
                case 1: letter = 'Y';
                break;
                case 2: letter = 'D';
                break;
                case 3:letter = count%2 == 0 ? 'S':'C'; count++;
                break;
                case 4:letter = 'B';
                break;
                case 5:letter = 'A';
                break;
            
                default: letter = 'T';
                break;
            }

            return letter;
        }

        int smartgenerator(int size){
            int num = 0;
            for (int i = 1; i < table_size; i++) // i = ship number
                if (size == i) num = random(0, (table_size-i));
                
            return num;
        }

        int random(int from, int to){
            return (int)(Math.random()*(to-(from - 1))) + from;
        }


        void print_ocean(){
            //System.out.println("|-------------------------------------------|");
            for (int y = 0; y < inputTable.length; y++) {
                for (int x = 0; x < inputTable[y].length; x++) {
                    //System.out.print(inputTable[y][x].getText() + "\t");
                }//x
                //System.out.println();
            }//y
            //System.out.println("|-------------------------------------------|");
        }

        void refresh_ocean(){
            for (int y = 0; y < inputTable.length; y++) {
                for (int x = 0; x < inputTable[y].length; x++) {
                    inputTable[y][x].setText("");
                }//x
            }//y
        }

    }//class




    private void loser_art(){
        System.out.println("     _    _     ___     _    _        _  _ *  _   _                         ");
        System.out.println("    |:|  |:|   /:::\\   |:|  |:|     |_||_| | |   |_                 ");
        System.out.println("    |:|  |:|  /:/'\\:\\  |:|  |:|     |  |\\ | |_  |_                            ");
        System.out.println("    |:|__|:| |:|   |:| |:|__|:|           _  ___                            ");
        System.out.println("    |::::::| |:|   |:| |::::::|     |\\ | / \\  |                            ");
        System.out.println("    '\"\"\"\"|:| |:|   |:| '\"\"\"\"|:|     | \\| \\_/  |                            ");
        System.out.println("         |:| |:|   |:|      |:|   _  _            _                            ");
        System.out.println("         |:| |:\\   |:|      |:|  |_ / \\ | | |\\ | | \\                            ");
        System.out.println("         |:|  \\:\\_/:/       |:|  |  \\_/ \\_/ | \\| |_/                            ");
        System.out.println("         |:|   \\:::/        |:|                                        ");
        System.out.println("         '\"'    '\"'         '\"'                                      ");
        System.out.println("                                             BETTER LUCK NEXT TIME!");
    }





    private void winner_art(){
        System.out.println("                           .                      ");
        System.out.println("                        `:.                      ");
        System.out.println("                          `:.    Congratulations!                  ");
        System.out.println("                 .:'      ;:'                      ");
        System.out.println("                 ::      ;:'                      ");
        System.out.println("                  :    .:'                      ");
        System.out.println("                   `.  :.                      ");
        System.out.println("          _________________________                      ");
        System.out.println("         : _ _ _ _ _ _ _ _ _ _ _ _ :                      ");
        System.out.println("     ,---:\".\".\".\".\".\".\".\".\".\".\".\".\":                      ");
        System.out.println("    : ,'\"`::.:.:.:.:.:.:.:.:.:.:.::'                      ");
        System.out.println("    `.`.  `:-===-===-===-===-===-:'                      ");
        System.out.println("      `.`-._:    WINNER!!!      :                      ");
        System.out.println("       `-.__`.               ,' java.                      ");
        System.out.println("   ,--------`\"`-------------'--------.                    ");
        System.out.println("     `\"--.__                   __.--\"'               ");
        System.out.println("            `\"\"-------------\"\"'               ");
    }



    private void ACCSI_ART(){
        System.out.println("                                                                                                                                   _.");
        System.out.println("                                                                                                                            _.--\"' |");
        System.out.println("                        Welcome!!!!!!                                                                                 _.--\"'       |");
        System.out.println("                                                                                                                _.--\"'      _..,.  |");
        System.out.println("                                                                                                          _.--\"'            .==; '.|");
        System.out.println("           A ship is always safe at the                                                             _.--\"'                     :   |'.");
        System.out.println("             shore - but that is NOT                                                          _.--\"'                            ;  |  '.");
        System.out.println("                what is build for.                                                      _.--\"'                                  :  |    '.");
        System.out.println("                            -Albret Einstein                                      _.--\"'                                         ; |      '.");
        System.out.println("                                                                            _.--\"'                         _.                    : |        '.");
        System.out.println("                                                                      _.--\"'                         _.--^\"  :                   q I     --mmm--");
        System.out.println("                                                                _.--\"'                              ;      _,.;_                 |_I____._\\___/___._.__");
        System.out.println("          [#LetzGoBattle]                                _.--\"'                                    :_.--^\"   :_]                |______|     ==\" \" \"_|'");
        System.out.println("                                                  |__.--\"'                                           ;         ;|                |;I H| |_______'(|)|");
        System.out.println("                                              I   | ;             ,    \\                    \\         ;__ [_]___;                |||____________| '_|    \\|   ;\"\"         |");
        System.out.println("                       ______.---._    ______ I  /|:        \\     ;\\    \\                    \\      ,d.-^'|| '-.b.     ___       L| I|  |\"  |   |_[_|_X__[|___:_,.-^>_.---.______             /|");
        System.out.println(";                          \"\":\"|'|/    _\\--/  I_/_|;         \\    :/\\ __nm__                _nm   _d______||______b.__DANIEL      | I|__| m |___|__H_____|_ m__|'^|\"  \\|  ;\"\"                //|");
        System.out.println(";      ______.---._<^-.,_____;___|]__\\|____|_|I___|] .--_____nm____; |_dHH|_|.-           |dHH|_|,-======''==_===;===|====|______|_I|__|_W_|___|__H_____^__W__|__|____|___:___,.--._nnn__m__//_o");
        System.out.println(":\\         \"\":   |/ \"|  |   __ m ___ .d88b. H m m || |_|-|-|-|-|-|-|  H*''|  .mmmmmmmmm^^\" '|m[]H\"m\"\"\"\"\"\"|   |_| []  [_]   /*  *  * * * * *|_|'\"7 | *  *   *   *   *  *  *  *  *  *     .V.    ;");
        System.out.println(":_\\__,.,_n_m_;___|]_I|_[|__[__]W_____'Y88P'_H_W_W_||_|_|_|_|_|_|_|_|__H&[]|_____^MMMM^______|W__H%$&$__I_____ -'________.-'                | | /  |                                    ^(8)-  ;");
        System.out.println("|<    H  * * *  * *  * *  *  * *  * * * * * * * *  *  *  *  *  *   *   *  *  *  *                                                                                       *  *  *   *  *       :");
        System.out.println("|  _|_H_|_            [ERROR 404]                    ___________________________________________________________________________________           [Computer Science In The 21st Century]    ;");
        System.out.println("'-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------'");
    }



}