package com.example.depansmwen;import android.app.Activity;import android.app.AlertDialog;import android.content.DialogInterface;import android.content.Intent;import android.content.SharedPreferences;import android.content.res.Configuration;import android.support.design.widget.TextInputLayout;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.view.View;import android.widget.*;import android.widget.Button;import java.util.Locale;public class MainActivity extends AppCompatActivity {    private TextInputLayout txtInputUsername;    private TextInputLayout txtInputPassword;    Button btn_connect;    private static AccesLocal accesLocal;    static String username;    String language;    ProgressBar progressBar;    TextView    tx_creer_un_compte,tv_langue;    public static AccesLocal getAccesLocal() {        return accesLocal;    }    @Override    protected void onResume() {        loadLocale();        super.onResume();    }    @Override    protected void onCreate(Bundle savedInstanceState) {        loadLocale();        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        loadLocale();        txtInputUsername = (TextInputLayout) findViewById(R.id.txtInputUsername);        txtInputPassword = (TextInputLayout) findViewById(R.id.txtInputPassword);        btn_connect=findViewById(R.id.btn_connect);        accesLocal = new AccesLocal(MainActivity.this);        // getActionBar().hide();        //getSupportActionBar().hide();        //getSupportActionBar(); //.setTitle("Anrejistre");        progressBar=findViewById(R.id.progressBar);        progressBar.setMax(100);        progressBar.setProgress(0);        final Thread thread = new Thread(){            @Override            public void run(){                try{                    for(int i=0;i<100;i++){                        progressBar.setProgress(i);                         sleep(0);                    }                }catch(Exception e){e.printStackTrace();}                finally {                    startActivity(new Intent(MainActivity.this,accueil.class));                    finish();                }            }        };           tx_creer_un_compte =findViewById(R.id.tx_creer_un_compte);           tv_langue =findViewById(R.id.tv_langue);           tv_langue.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                View v = getLayoutInflater().inflate(R.layout.langues,null);                final AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);                               final ListView listv=v.findViewById(R.id.listv);                final String[] listItems ={"Francais","Anglais"};                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,listItems);                listv.setAdapter(adapter);                ad.setView(v);                final AlertDialog a=ad.create();                listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {                    @Override                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {                        switch(i){                            case 0:{                                if(language.equalsIgnoreCase("fr")){                                }                                else{                                    setLocale("fr");                                    recreate();                                }                            }break;                            case 1:{                                if(language.equalsIgnoreCase("en")){                                }else {                                    setLocale("en");                                    recreate();                                }                            }break;                        }                        a.cancel();                    }                });                a.show();//                ad.setSingleChoiceItems(listItems, itemId, new DialogInterface.OnClickListener() {////                    public void onClick(DialogInterface dialogInterface, int i) {//                            if(i==0){//                                setLocale("fr");//                                recreate();//                            }//                            else{//                                setLocale("en");//                                recreate();//                            }//                            dialogInterface.dismiss();//                        }//                });            }        });        btn_connect.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                username = txtInputUsername.getEditText().getText().toString();                String password = txtInputPassword.getEditText().getText().toString();                if (username.equals("") || password.equals("")){                    Toast.makeText(MainActivity.this, "Tous les champs sont obligatoires!!!", Toast.LENGTH_SHORT).show();                }else {                    Boolean login = accesLocal.login(username, password);                    if (login == true){                        userName();                        DesactivChamp();                        progressBar.setVisibility(View.VISIBLE);                        thread.start();                    }else {                        Toast.makeText(MainActivity.this, "Pseudo ou mot de passe incorrect!!!", Toast.LENGTH_SHORT).show();                    }                }            }        });        tx_creer_un_compte.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v)            {                startActivity(new Intent(MainActivity.this, inscription.class));            }        });    }    private void setLocale(String langue) {        Locale locale =new Locale(langue);        Locale.setDefault(locale);        Configuration config = new Configuration();        config.locale = locale;        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());        //save data sharedPreferences        SharedPreferences.Editor editor =getSharedPreferences("Settings",MODE_PRIVATE).edit();        editor.putString("My_lang",langue);        editor.apply();        // recreate();    }    public void loadLocale(){        SharedPreferences prefs = getSharedPreferences("Settings",  Activity.MODE_PRIVATE);        language = prefs.getString("My_lang","");        setLocale(language);    }    public void DesactivChamp(){        txtInputUsername.setEnabled(false);        txtInputPassword.setEnabled(false);        btn_connect.setEnabled(false);        tx_creer_un_compte.setEnabled(false);        tv_langue.setEnabled(false);    }    public static String userName() {        return username;    }}