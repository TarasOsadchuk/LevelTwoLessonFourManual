package com.geekbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
    private static final int POS_X = 100;
    private static final int POS_Y = 100;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private final ChatServer server = new ChatServer();
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");

    private ServerGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 2));
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        add(btnStart);
        add(btnStop);

        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("main started");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
        System.out.println("main ended");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStart) {
            server.start(80);
        } else if (src == btnStop) {
            server.stop();
        } else {
            throw new RuntimeException("Action for component unimplemented");
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg = "Exception in thread " + t.getName() +
                " " + e.getClass().getCanonicalName() +
                ": " + e.getMessage() +
                "\n\t" + e.getStackTrace()[0];
        JOptionPane.showMessageDialog(null, msg,
                "Exception", JOptionPane.ERROR_MESSAGE);
    }
}