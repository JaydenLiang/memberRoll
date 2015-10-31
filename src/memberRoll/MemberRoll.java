package memberRoll;

import java.awt.Dimension;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemberRoll extends JFrame implements WindowListener{

    /**
     * <p></p>
     */
    private static final long serialVersionUID = 1544879574232969988L;
    
    private JLabel rolledMamberLabel;
    private JTextField memberListTextField;
    private final String fileName = "memberRoll.txt";

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MemberRoll main = new MemberRoll();
                main.createAndShowGUI();
            }
        });
    }
    
    public MemberRoll() {
        
    }
    
    private void createAndShowGUI(){
        //frame
        this.addWindowListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, 200));
        this.setTitle("Member Roll");
        //content pane
        JLayeredPane contentPane = new JLayeredPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.setOpaque(true); //content panes must be opaque
        this.setContentPane(contentPane);
        
        //main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Member"));
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
        
        JButton rollButton = new JButton("Roll it!");
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
        rollPanel.setBounds(0, 0, 400, 80);
        this.add(rollPanel);
        
        JLabel rollLabel = new JLabel("The guy to answer your question is:");
        rollPanel.add(rollLabel);
        rolledMamberLabel = new JLabel("");
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
        int index = (int)(Math.random() * members.length);
        String member = members[index];
        this.rolledMamberLabel.setText(member.trim());
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
}
