package com.example.muneer.emailsender;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Muneer on 31-03-2016.
 */
public class EmailSender {

    ProgressDialog progressDialog;
    Context context;

    EmailSender(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Sending...");
        progressDialog.setMessage("Please wait...");
    }

    public void send(String from, String username, String password, String to, String subject, String msgBody) {
        new EmailSenderAsync(context, from, username, password, to, subject, msgBody).execute();
    }

    public class EmailSenderAsync extends AsyncTask<Void, Void, Boolean> {
        Context context;
        String from, username, password, to, subject, msgBody;

        public EmailSenderAsync(Context context, String from, String username, String password, String to, String subject, String msgBody) {
            this.context = context;
            this.from = from;
            this.msgBody = msgBody;
            this.password = password;
            this.subject = subject;
            this.to = to;
            this.username = username;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            String host = "smtp.gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(msgBody);
                Transport.send(message);
            } catch (MessagingException e) {
                return false;
            }
            catch(Exception e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isEmailSent) {
            progressDialog.dismiss();
            if(isEmailSent) {
                Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Not Sent", Toast.LENGTH_SHORT).show();
            }
        }
    }
}