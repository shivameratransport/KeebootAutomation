package com.keeboot.utils.emailReport;

public class SendReport {

    public void sendMail() {
        try {

            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", "cd \"" + "c:/grid/Orasi-Chameleon" + "\" && emailTrigger.vbs");
            builder.redirectErrorStream(true);
            builder.start();

        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

    }

    // [Govind]: To be moved to AfterSuite - Kept here for testing purpose only
    public static void main(String[] ar) throws Exception {
        SendReport sm = new SendReport();
        sm.sendMail();
    }
}