package assets;


import javax.swing.*;
import javax.tools.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



public class TheWindow extends JFrame {
    JavaCompiler jcr = ToolProvider.getSystemJavaCompiler();
    public final String author = "CFR_406";
    public final String ver = "1.1";
    JButton load = new JButton("載入");
    JButton run = new JButton("執行");
    JButton compile = new JButton("編譯");
    JButton save = new JButton("儲存");
    JTextField name = new JTextField("Main",10);
    JTextArea code = new JTextArea("public class "+name.getText()+"{\n    public static void main(String[] args)" +
            "    {\n\n\n    }\n}",50,50);
    JScrollPane codeBar = new JScrollPane(code);
    File f = null;
    private void saveAs(){
        JFileChooser jf = new JFileChooser();
        jf.showDialog(null,"Save");
        this.f = jf.getSelectedFile();
        File f = new File(this.f.getPath());
        if(!f.exists()){
            try{
                f.createNewFile();
            }catch (Throwable t){}
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
        } catch (IOException ignored) {}
        try {
            if (bw != null) {
                bw.write(code.getText());
            }
        } catch (IOException ex) {
            System.out.println("Error");
        }
        try {
            if (fw != null) {
                fw.close();
            }
            if (bw != null) {
                bw.close();
            }
        } catch (IOException ignored) {}
    }
    String fread(File ff){
        String str = "";
        StringBuffer d = new StringBuffer();
        FileReader fr;
        try {
            fr = new FileReader(ff);
        } catch (FileNotFoundException e) {
            return null;
        }
        BufferedReader bf = new BufferedReader(fr);
        while (true){
            try {
                if (!((str = bf.readLine()) != null)) break;
            } catch (IOException e) {

            }
            d.append(str);
            d.append("\n");
        }
        return d.toString();
    }
    private void compile(){
        int res = jcr.run(null,null,null,this.f.getPath());
        if (res == 0) {
            JOptionPane.showMessageDialog(null,"Compilation successful!","Java Compiler",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,"Compilation failed!","Java Compiler",JOptionPane.ERROR_MESSAGE);
        }
    }
    public TheWindow(){
        codeBar.setViewportView(code);
        this.setTitle("EFJ v"+ver+" by YouTube:"+author);
        code.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        run.addActionListener((ActionEvent e)->{
            CMDManager cmd = new CMDManager();
            cmd.Run("java "+this.f.getPath());
        } );
        this.setSize(800,500);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        load.addActionListener((ActionEvent e)->{
            JFileChooser jf = new JFileChooser();
            FileReader fr = null;
            do{
                jf.showDialog(null,"Open .java file");
                f = jf.getSelectedFile();
            }while (!f.getName().endsWith(".java"));
            try {
                /*
                fr = new FileReader(f);
                BufferedReader bf = new BufferedReader(fr);
                while ((str = bf.readLine()) != null){
                    d.append(str);
                    d.append("\n");
                }*/
            } catch (Exception ex) {}
            try {
                code.setText(fread(f));
            } catch (Exception ex) {}
            try {
                fr.close();
            } catch (Exception ex) {}
        });
        this.setLayout(new FlowLayout());
        codeBar.setVisible(true);
        codeBar.setLocation(this.getWidth() / 2,this.getHeight() / 2);
        this.add(codeBar);
        compile.addActionListener((ActionEvent e)->{
            compile();
        } );
        codeBar.setSize(500,Integer.MAX_VALUE-10);
        compile.setLocation(this.getWidth() / 2,this.getHeight());
        this.add(compile);
        code.setEditable(true);
        save.addActionListener((ActionEvent e)->{
            saveAs();
        });
        run.addActionListener((ActionEvent e)->{
            compile();
            CMDManager cmd = new CMDManager();
            cmd.open(f);
        });
        this.add(load);
        this.add(save);
        this.add(name);
        this.add(run);
        this.setVisible(true);

    }
}
