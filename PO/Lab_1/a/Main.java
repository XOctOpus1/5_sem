import javax.swing.*;

class Slider extends JSlider {

    public Slider() {
        super(0,100);
    }
    
    synchronized public void Increase(int increment){
        setValue((int)getValue() + increment);
    }
}

class myThread extends Thread {
    private int increment;
    private Slider myS;
    private int count;
    private static int BOUND = 1000000;
    private static int THREAD_COUNTER = 0;
    private int curNum;

    public myThread(Slider myS, int increment, int priority) {
        this.myS = myS;
        this.increment = increment;
        curNum = ++THREAD_COUNTER;
        setPriority(priority);
    }

    @Override
    public void run() {
        while(!interrupted()){
            ++count;
            if(count > BOUND){
                myS.Increase(increment);
                count = 0;
            }
        }
    }

    public JPanel GetJPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Thread #" + curNum );

        SpinnerModel sModel = new SpinnerNumberModel(getPriority(), Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, 1);


        JSpinner Spinner = new JSpinner(sModel);

        Spinner.addChangeListener(e->{setPriority((int)(Spinner.getValue()));});
        panel.add(label);
        panel.add(Spinner);

        return panel;
    }
}

public class Main {
    
        public static void main(String[] args) {
        JFrame Lab1 = new JFrame();
        Lab1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Lab1.setSize(1000, 1000);
        Slider mySS = new Slider();

        myThread Tthread1 = new myThread(mySS, +1, 1);
        myThread TThread2 = new myThread(mySS, -1, 1);

        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            Tthread1.start();
            TThread2.start();
            start.setEnabled(false);
        });
        JPanel Lab1_Panel = new JPanel();
        Lab1_Panel.setLayout(new BoxLayout(Lab1_Panel, BoxLayout.Y_AXIS));

        Lab1_Panel.add(Tthread1.GetJPanel());
        Lab1_Panel.add(TThread2.GetJPanel());
        Lab1_Panel.add(mySS);

        JPanel jPanel = new JPanel();
        jPanel.add(start);
        Lab1_Panel.add(jPanel);

        Lab1.setContentPane(Lab1_Panel);
        Lab1.setVisible(true);

    }
}