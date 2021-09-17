package com.example.knowledge_android.protector;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * Software protector.
 * 
 * @author bruce
 */
public class Protector {

    static private String machineUniqueID;

    private static final byte[] HEXBYTES = { (byte) '0', (byte) '1', (byte) '2', (byte) '3',
            (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a',
            (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };

    public static String byteArrayToHexString(byte[] b) {
        int len = b.length;
        char[] s = new char[len * 2];

        for (int i = 0, j = 0; i < len; i++) {
            int c = ((int) b[i]) & 0xff;

            s[j++] = (char) HEXBYTES[c >> 4 & 0xf];
            s[j++] = (char) HEXBYTES[c & 0xf];
        }

        return new String(s);
    }

    public static String getMAC() {
        Enumeration<NetworkInterface> en = null;
        try {
            en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = en.nextElement();
                byte[] mac = ni.getHardwareAddress();
                System.out.println(ni.getName() + ", " + ni.getDisplayName());
                if (mac != null && (ni.getName().contains("eth") || ni.getName().startsWith("en")))
                    return byteArrayToHexString(mac);
            }
            return "?";
        } catch (SocketException e) {
            return "?";
        }
    }

    /**
     * Get an unique machine ID.
     * 
     * @return String Machine ID.
     */
    static public String getMachineUniqueID() {
//        try {

            if (machineUniqueID != null && !"?".equals(machineUniqueID))
                return machineUniqueID;

            machineUniqueID = getMAC();
            if (!"?".equals(machineUniqueID))
                return machineUniqueID;
            else
                throw new IllegalArgumentException("Cannot get MAC address.");

//            boolean windows = (System.getProperties().get("os.name").toString().indexOf("Windows") != -1);
//            Process proc = Runtime.getRuntime().exec(windows ? "cmd /c vol c:" : "/sbin/ifconfig");
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    proc.getInputStream()));
//            String line;
//            String retLine = " ";
//            while ((line = in.readLine()) != null) {
//                if (windows || line.indexOf("eth0") != -1) { // grep eth0 on Linux
//                    retLine = line;
//                }
//            }
//            in.close();
//            retLine = retLine.trim();
//            machineUniqueID = retLine.substring(retLine.lastIndexOf(' ') + 1);
//            machineUniqueID = generateLicenseID(machineUniqueID);
//            return machineUniqueID;

//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
    }

    /**
     * Get license ID from a machine ID.
     * 
     * @return String License ID.
     */
    static public String generateLicenseID() {
        return generateLicenseID(getMachineUniqueID());
    }

    /**
     * Get license ID from a machine ID.
     * 
     * @param machineID Machine ID.
     * @return String License ID.
     */
    static public String generateLicenseID(String machineID) {
        try {
            String retString = "";

            MessageDigest md5Maker = MessageDigest.getInstance("MD5");
            md5Maker.update(machineID.getBytes());
            byte[] md5 = md5Maker.digest();

            MessageDigest shaMaker = MessageDigest.getInstance("SHA");
            shaMaker.update(machineID.getBytes());
            byte[] sha = shaMaker.digest();

            int[] r = new int[Math.max(md5.length, sha.length)];
            int i;
            for (i = 0; i < r.length; i++) {
                if (i < md5.length)
                    r[i] += md5[i];
                if (i < sha.length)
                    r[i] += sha[i];
            }
            for (i = 0; i < r.length / 2; i++) {
                r[i] += r[r.length - 1 - i];
                r[i] &= 0xff;
                int m = r[i] % 0x10;
                if (m > 9)
                    r[i] -= 6;
                if (r[i] >= 0xa0)
                    r[i] -= 0x60;
            }
            for (i = 0; i < r.length / 2; i++)
                retString += Integer.toHexString(r[i] & 0xff);
            return retString;
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    static public String getUserLicenseID() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("license.txt"));
            String line = r.readLine().trim();
            r.close();
            return line;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    static public boolean checkUserLicense() {
        return getUserLicenseID().equals(generateLicenseID());
    }

    static public void createUserLicenseFile(String id) {
        try {
            Writer w = new FileWriter("license.txt");
            w.write(id);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////
// FOLLOWING CODE CANNOT RELEASE TO CUSTOMER!!

//    class MainWindow extends Frame {
//        MainWindow(String title) {
//            super(title);
//            Panel backPanel = new Panel();
//            this.add(backPanel);
//            backPanel.setLayout(new GridLayout(2, 2, 6, 6));
//            Label machineIDLabel = new Label("Machine ID:", Label.RIGHT);
//            Label licenseIDLabel = new Label("License ID:", Label.RIGHT);
//            String machineID = getMachineUniqueID();
//            final TextField machineIDTextField = new TextField(machineID);
//            final TextField licenseIDTextField = new TextField(generateLicenseID(machineID));
//            machineIDTextField.setFont(new Font("Serif", Font.PLAIN, 15));
//            machineIDTextField.selectAll();
//            licenseIDTextField.setFont(new Font("Serif", Font.PLAIN, 15));
//            licenseIDTextField.setEditable(false);
//            backPanel.add(machineIDLabel);
//            backPanel.add(machineIDTextField);
//            backPanel.add(licenseIDLabel);
//            backPanel.add(licenseIDTextField);
//            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//            int width = 370;
//            int height = 90;
//            setSize(width, height);
//            setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
//            machineIDTextField.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    licenseIDTextField.setText(generateLicenseID(machineIDTextField.getText()));
//                }
//            });
//            this.addWindowListener(new WindowAdapter() {
//                public void windowClosing(WindowEvent e) {
//                    System.exit(0);
//                }
//            });
//        }
//    }
//
//    public void createUserInterface() {
//        MainWindow frame = new MainWindow("泓远ePOS授权码生成器");
//        frame.setVisible(true);
//    }

    public static void main(String[] args) {
        Protector protector = new Protector();
        //protector.createUserInterface();
        String m;
        System.out.println("Machine ID = " + (m = getMachineUniqueID()));
        System.out.println("License ID = " + generateLicenseID(m));
    }
}
