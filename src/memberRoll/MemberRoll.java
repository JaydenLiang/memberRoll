package memberRoll;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemberRoll extends JFrame implements WindowListener, Runnable{

    /**
     * <p></p>
     */
    private static final long serialVersionUID = 1544879574232969988L;

    private Thread runningThread = null;
    
    private JLabel rolledMamberLabel;
    private JTextField memberListTextField;
    private final String fileName = "memberRoll.txt";
    private ArrayList<String> memberList;

    private final int concurrentTimeMS = 10;

    private JButton rollButton;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MemberRoll main = new MemberRoll();
                main.createAndShowGUI();
            }
        });
    }
    
    public MemberRoll() {
        //
    }
    
    private void createAndShowGUI(){
        //frame
        this.addWindowListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 300));
        this.setTitle("Member Roll");
        //content pane
        JLayeredPane contentPane = new JLayeredPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.setOpaque(true); //content panes must be opaque
        this.setContentPane(contentPane);
        
        //main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Member"));
        mainPanel.setPreferredSize(new Dimension(400, 80));
        mainPanel.setBounds(0, 0, 400, 80);
        this.add(mainPanel);
        
        //member area
        JLabel memberListLabel = new JLabel("Member List:");
        mainPanel.add(memberListLabel);
        
        this.memberListTextField = new JTextField();
        this.memberListTextField.setBounds(0, 0, 100, 10);
        this.memberListTextField.setPreferredSize(new Dimension(100, 20));
        this.memberListTextField.setText(this.readMemberList());
        mainPanel.add(this.memberListTextField);
        
        rollButton = new JButton("Roll it!");
        mainPanel.add(rollButton);
        rollButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
              //catch button click event
                if (e.getSource() instanceof JButton) {
                    roll();
                };
            }
        });
        
        //roll area
        JPanel rollPanel = new JPanel();
        rollPanel.setBorder(BorderFactory.createTitledBorder("Roll Area"));
        rollPanel.setPreferredSize(new Dimension(400, 120));
        rollPanel.setBounds(0, 0, 400, 120);
        this.add(rollPanel);
        
        JLabel rollLabel = new JLabel("The guy to answer your question is:");
        rollLabel.setPreferredSize(new Dimension(380, 20));
        rollPanel.add(rollLabel);
        rolledMamberLabel = new JLabel("");
        rolledMamberLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        rollPanel.add(rolledMamberLabel);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    private String readMemberList(){
        String text = "";
        try {
            File file = new File(fileName);
            if(file.exists()){
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
//                    stringBuffer.append("\n");
                }
                fileReader.close();
                text = stringBuffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FileInputStream fin = new 
        return text;
    }
    
    private void saveMemberList(){
        try {
            File file = new File(fileName);
            if(!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter buffw = new BufferedWriter(new OutputStreamWriter(fos));
         
            buffw.write(memberListTextField.getText());
            buffw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void roll(){
        String[] members = this.memberListTextField.getText().split(",");
        memberList = new ArrayList<String>();
        for(String member : members){
            memberList.add(member);
        }
        
        run();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        saveMemberList();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    public void startConcurrentThread() {
        if (this.runningThread == null) {
            this.runningThread = new Thread(this);
            this.runningThread.start();
        }
    }
    
    public void stopConcurrentThread(){
        if (this.runningThread != null){
            this.runningThread = null;
        }
    }

    @Override
    public void run() {
        //lock all input items to prevent rolling again when performing a roll
        this.memberListTextField.setEnabled(false);
        this.rollButton.setEnabled(false);
        //shuffle all members in the member list
        Collections.shuffle(memberList);
        //create a thread to perform 
        startConcurrentThread();
        Thread thisThread = Thread.currentThread();
        int index = 0;
        int sleep = this.concurrentTimeMS;
        int step = 1;
        this.rolledMamberLabel.setForeground(Color.BLACK);
        while (this.runningThread == thisThread) {
            //display a member name from the shuffled member list
            this.rolledMamberLabel.setText(memberList.get(index));
            //shift to next member name, reset when reached last member
            index = index == memberList.size() - 1 ? 0 : index + 1;
            sleep += step;
            if(sleep < 300){
                step += 1;
            }
            else if(sleep < 500){
                step += 80;
            }
            else {
                step += 500;
            }
            //break loop condition
            if(sleep > 1000){
                //stop thread
                stopConcurrentThread();
                //set display
                this.rolledMamberLabel.setForeground(Color.RED);
                this.memberListTextField.setEnabled(true);
                this.rollButton.setEnabled(true);
                break;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                
            }
        }
    }
}
