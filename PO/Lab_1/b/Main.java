import javax.swing.*;

class myThread extends Thread {
    int inc = 0;
    public myThread(int inc){
        this.inc = inc;
    }

    @Override
    public void run(){
        while(Main.SEMAPHORE != 0);
        Main.SEMAPHORE = 1;
        while(!interrupted()) {
            int val = Main.Slider.getValue();
            if((val > 10 && inc < 0) || (val < 90 && inc > 0))
                Main.Slider.setValue(val + inc);
        }
        Main.SEMAPHORE = 0;
    }
}

public class Main {
    static volatile int SEMAPHORE = 0;
    static volatile JSlider Slider;
    static private myThread Tthread1;
    static private myThread TThread2;
    static private JButton Tthread1START;
    static private JButton TThread2START;
    static private JButton Tthread1STOP;
    static private JButton TThread2STOP;

    public static void main(String[] args) {
        JFrame Lab1_DP = new JFrame();
        Lab1_DP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Lab1_DP.setSize(1000,1000);
        JPanel Lab1_DPPanel = new JPanel();
        Lab1_DPPanel.setLayout(new BoxLayout(Lab1_DPPanel, BoxLayout.Y_AXIS));

        Slider = new JSlider(0,100);
        Lab1_DPPanel.add(Slider);

        JPanel Tthread1Panel = new JPanel();
        Tthread1START = new JButton("start1");
        Tthread1STOP = new JButton("stop1");
        Tthread1STOP.setEnabled(false);
        Tthread1START.addActionListener(e -> {
            Tthread1 = new myThread(+1);
            Tthread1.setPriority(Thread.MIN_PRIORITY);
            Tthread1STOP.setEnabled(true);
            Tthread1START.setEnabled(false);
            Tthread1.start();
        });
        Tthread1STOP.addActionListener(e -> {
            Tthread1.interrupt();
            Tthread1STOP.setEnabled(false);
            Tthread1START.setEnabled(true);
        });
        Tthread1Panel.add(Tthread1START);
        Tthread1Panel.add(Tthread1STOP);

        JPanel TThread2Panel = new JPanel();
        TThread2START = new JButton ("start2");
        TThread2STOP = new JButton("stop2");
        TThread2STOP.setEnabled(false);
        TThread2START.addActionListener(e-> {
            TThread2 = new myThread(-1);
            TThread2.setPriority(Thread.MAX_PRIORITY);
            TThread2STOP.setEnabled(true);
            TThread2START.setEnabled(false);
            TThread2.start();
        });
        TThread2STOP.addActionListener(e-> {
            TThread2.interrupt();
            TThread2STOP.setEnabled(false);
            TThread2START.setEnabled(true);
        });
        TThread2Panel.add(TThread2START);
        TThread2Panel.add(TThread2STOP);

        Lab1_DPPanel.add(Tthread1Panel);
        Lab1_DPPanel.add(TThread2Panel);

        Lab1_DP.setContentPane(Lab1_DPPanel);
        Lab1_DP.setVisible(true);
    }
}