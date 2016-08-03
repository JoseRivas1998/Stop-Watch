package com.tcg.stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JoseR on 8/2/2016.
 */
public class StopWatchFrame extends JFrame {

    private JLabel time;

    private Font labelFont, fontAwesome;

    private JButton start, stop;

    private StopWatchThread stopWatchThread;

    public StopWatchFrame() {
        setTitle("Stop Watch");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        labelFont = new Font("Arial", Font.BOLD, 72);
        InputStream in = getClass().getResourceAsStream("fontawesome.ttf");
        try {
            fontAwesome = Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(12f);
            in.close();
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        time = new JLabel();
        time.setFont(labelFont);
        setTime(0, 0, 0, 0);

        stopWatchThread = new StopWatchThread(this);

        start = new JButton("\uf04b");
        start.setFont(fontAwesome);
        start.setFocusPainted(false);
        start.setForeground(Color.GREEN);
        start.addActionListener(e -> {
            if(!stopWatchThread.running) {
                stopWatchThread.startStopWatch();
                start.setForeground(Color.GRAY);
                start.setText("\uf04c");
            } else {
                stopWatchThread.pauseStopWatch();
                start.setForeground(Color.GREEN);
                start.setText("\uF04B");
            }
        });
        start.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(!stopWatchThread.running) {
                    start.setForeground(Color.GREEN);
                } else {
                    start.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!stopWatchThread.running) {
                    start.setForeground(Color.GREEN.darker());
                } else {
                    start.setForeground(Color.GRAY.darker());
                }
            }
        });

        stop = new JButton("\uf04d");
        stop.setFont(fontAwesome);
        stop.setFocusPainted(false);
        stop.setForeground(Color.RED.darker());
        stop.addActionListener(e -> {
            stopWatchThread.stopStopWatch();
            start.setForeground(Color.GREEN);
            start.setText("\uF04B");
        });
        stop.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                stop.setForeground(Color.RED);
            }

            @Override
            public void focusLost(FocusEvent e) {
                stop.setForeground(Color.RED.darker());
            }
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(start);
        buttons.add(stop);

        getContentPane().add(time, BorderLayout.NORTH);
        getContentPane().add(buttons, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));
        setIconImage(icon.getImage());
        setVisible(true);
    }

    public void addAll(Component... components) {
        for(Component component : components) {
            add(component);
        }
    }

    public void setTime(long hours, long minutes, long seconds, long millis) {
        time.setText(String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis));
    }
}
