package com.example.depansmwen;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class inscription extends AppCompatActivity {

    private TextInputLayout txtInputNom;
    private TextInputLayout txtInputPrenom;
    private TextInputLayout txtInputUsername;
    private TextInputLayout txtInputPassword;
    private TextInputLayout txtInputConfirm;
    private Button btn_signup,btn_log;
    private static AccesLocal accesLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
//        getSupportActionBar().hide();

        txtInputNom = (TextInputLayout) findViewById(R.id.txtInputNom);
        txtInputPrenom = (TextInputLayout) findViewById(R.id.txtInputPrenom);
        txtInputUsername = (TextInputLayout) findViewById(R.id.txtInputUsername);
        txtInputPassword = (TextInputLayout) findViewById(R.id.txtInputPassword);
        txtInputConfirm = (TextInputLayout) findViewById(R.id.txtInputConfirm);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        //btn_log = (Button) findViewById(R.id.btn_log);

        accesLocal = new AccesLocal(inscription.this);

//        btn_log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(inscription.this, MainActivity.class));
//            }
//        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtInputNom.getEditText().getText().toString();
                String prenom = txtInputPrenom.getEditText().getText().toString();
                String username = txtInputUsername.getEditText().getText().toString();
                String password = txtInputPassword.getEditText().getText().toString();
                String confirm = txtInputConfirm.getEditText().getText().toString();

                if (nom.equals("") || prenom.equals("") || username.equals("") || password.equals("") || confirm.equals("")){
                    Toast.makeText(inscription.this, R.string.remplirleschan, Toast.LENGTH_SHORT).show();
                }else{
                    if (password.equals(confirm)){
                        Boolean checkUsername = accesLocal.checkUsername(username);
                        if (checkUsername == true){
                            Boolean insert = accesLocal.signup(nom, prenom, username, password);
                            if (insert == true){
                                Boolean insertcompte = accesLocal.EnregistreCompte(username,"CASH","12345678","","1","0");
                                if (insertcompte == true) {
                                    Toast.makeText(inscription.this, R.string.enregis_succes, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(inscription.this, MainActivity.class));
                                }
                            }
                        }else {
                            Toast.makeText(inscription.this, R.string.lepseudo, Toast.LENGTH_SHORT).show();
                            txtInputUsername.getEditText().setText("");
                        }
                    }else {
                        Toast.makeText(inscription.this, R.string.motdepassdident, Toast.LENGTH_SHORT).show();
                        txtInputPassword.getEditText().setText("");
                        txtInputConfirm.getEditText().setText("");
                    }
                }
            }
        });
    }
}