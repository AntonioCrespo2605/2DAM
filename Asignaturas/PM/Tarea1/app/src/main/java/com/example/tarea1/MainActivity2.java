package com.example.tarea1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private static int saludSergio=100;
    private static int saludDre=100;
    private static int precisionSergio=5;
    private static int precisionDre=5;
    private static int danhoSergio=4;
    private static int danhoDre=4;
    private static final int[] DANHOS ={4, 8, 12, 16, 20, 24, 28, 32, 36};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        saludSergio=100;
        saludDre=100;
        precisionSergio=5;
        precisionDre=5;
        danhoSergio=4;
        danhoDre=4;

        MediaPlayer mediaplayer = MediaPlayer.create(this, R.raw.battlemusic);
        mediaplayer.start();

        Random r=new Random();
        Handler handler = new Handler();

        ImageView iv=(ImageView) findViewById(R.id.green);
        iv.setVisibility(View.INVISIBLE);

        Button tuMadre=(Button)findViewById(R.id.mama);
        Button grito=(Button)findViewById(R.id.grito);
        Button encoger=(Button)findViewById(R.id.encoger);
        Button ego=(Button)findViewById(R.id.ego);

        TextView txtmain=(TextView)findViewById(R.id.textomain);
        TextView tsaludSergio=(TextView)findViewById(R.id.saludsergi);
        TextView tsaludDre=(TextView)findViewById(R.id.saluddre);
        TextView infop=(TextView)findViewById(R.id.infopress);

        infop.setText("");

        //Ataque tu madre

        tuMadre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (r.nextInt(precisionSergio)==0){
                    txtmain.setText("Sergio ha fallado el ataque");
                }else{
                saludDre=saludDre-DANHOS[danhoSergio];
                tsaludDre.setText(saludDre+"%");
                txtmain.setText("Sergio usó Tu Madre");

                    if(saludDre<=0){
                        mediaplayer.stop();
                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }

                handler.postDelayed(new Runnable() {
                    public void run() {
                        // acciones que se ejecutan tras los milisegundos
                        turnoDre(txtmain,tsaludSergio,iv);
                    }
                }, 2000);


                if(saludSergio<=0){
                    mediaplayer.stop();
                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Ataque encoger
        encoger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (precisionDre>2){
                    if (r.nextInt(precisionSergio)==0){
                        txtmain.setText("Sergio ha fallado el ataque");
                    }else{
                        txtmain.setText("La precisión de Xandre \nha disminuído");
                        precisionDre=precisionDre-1;
                    }
                }else{
                    txtmain.setText("No se puede bajar más \nla precisión de Xandre");
                }
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // acciones que se ejecutan tras los milisegundos
                        turnoDre(txtmain,tsaludSergio,iv);
                    }
                }, 2000);

                if(saludSergio<=0){
                    mediaplayer.stop();
                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Ataque grito
        grito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(danhoDre!=0){
                    if (r.nextInt(precisionSergio)==0){
                        txtmain.setText("Sergio ha fallado el ataque");
                    }else{
                        txtmain.setText("El daño de Xandre \nha disminuído");
                        danhoDre=danhoDre-1;
                    }
                }else{
                    txtmain.setText("No se puede bajar más \nel daño de Xandre");
                }
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // acciones que se ejecutan tras los milisegundos
                        turnoDre(txtmain,tsaludSergio,iv);
                    }
                }, 2000);

                if(saludSergio<=0){
                    mediaplayer.stop();
                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //ataque ego
        ego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (r.nextInt(precisionSergio)==0){
                    txtmain.setText("Sergio ha fallado el ataque");
                }else{
                    if (danhoSergio==8&&precisionSergio==9){
                        txtmain.setText("Las estadísticas de Sergio\nno pueden subir más");
                    } else if (danhoSergio==8){
                        precisionSergio++;
                        txtmain.setText("Sergio ha aumentado \nsu precisión");
                    }else if(precisionSergio==9){
                        danhoSergio++;
                        txtmain.setText("Sergio ha aumentado su ataque");
                    }else{
                        if(r.nextInt(2)==0){
                            danhoSergio++;
                            txtmain.setText("Sergio ha aumentado su ataque");
                        }else{
                            precisionSergio++;
                            txtmain.setText("Sergio ha aumentado \nsu precisión");
                        }
                    }
                }

                handler.postDelayed(new Runnable() {
                    public void run() {
                        // acciones que se ejecutan tras los milisegundos
                        turnoDre(txtmain,tsaludSergio,iv);
                    }
                }, 2000);

                if(saludSergio<=0){
                    mediaplayer.stop();
                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
    /********************/
    private static void turnoDre(TextView txtMain, TextView tsSergio, ImageView iv){
        Random r=new Random();
        int aux;

        //algoritmo de pelea de Xandre
        aux=r.nextInt(3);
        if (aux!=0){
        if(precisionDre<3)danza(txtMain);
        else if (danhoDre<2)danza(txtMain);
        else if (precisionSergio>6)humareda(txtMain,iv);
        else if (danhoSergio>6)quejica(txtMain);
        else if (danhoSergio==0&&precisionSergio==2&&danhoDre==8&&precisionDre==9)lanzabananas(txtMain, tsSergio);
        else if (danhoDre==8&&precisionDre==9&&danhoSergio==0){
            aux=r.nextInt(2);
            if (aux==0)lanzabananas(txtMain,tsSergio);
            else humareda(txtMain,iv);
        }else if (danhoDre==8&&precisionDre==9&&precisionSergio==2){
            aux=r.nextInt(2);
            if (aux==0)lanzabananas(txtMain,tsSergio);
            else quejica(txtMain);
        }else if(danhoDre==8&&precisionDre==9){
            aux=r.nextInt(3);
            if (aux==0)lanzabananas(txtMain,tsSergio);
            else if(aux==1)quejica(txtMain);
            else humareda(txtMain,iv);
        }else{
            aux=r.nextInt(4);
            if (aux==0)lanzabananas(txtMain,tsSergio);
            else if(aux==1)quejica(txtMain);
            else if(aux==2)humareda(txtMain,iv);
            else danza(txtMain);
        }
        }else lanzabananas(txtMain,tsSergio);

    };

    private static void lanzabananas(TextView txtMain, TextView tsSergio){
        Random r=new Random();

        if (r.nextInt(precisionDre)==0){
            txtMain.setText("Xandre ha fallado el ataque");
        }else{
            saludSergio=saludSergio-DANHOS[danhoDre];
            tsSergio.setText(saludSergio+"%");
            txtMain.setText("Xandre usó lanza-bananas");
        }
    };

    private static void danza(TextView txtMain){
        Random r=new Random();

        if (r.nextInt(precisionDre)==0){
            txtMain.setText("Xandre ha usado dança do \nmacaco pero ha fallado");
        }else{
            if (danhoDre==8&&precisionDre==9){
                txtMain.setText("Xandre ha usado dança do \nmacaco pero ha fallado");
            } else if (danhoDre==8){
                precisionDre++;
                txtMain.setText("Xandre ha usado dança do \nmacaco y ha aumentado \nsu precisión");
            }else if(precisionDre==9){
                danhoDre++;
                txtMain.setText("Xandre ha usado dança do \nmacaco y ha aumentado \nsu ataque");
            }else{
                if(r.nextInt(2)==0){
                    danhoDre++;
                    txtMain.setText("Xandre ha usado dança do \nmacaco y ha aumentado \nsu ataque");
                }else{
                    precisionDre++;
                    txtMain.setText("Xandre ha usado dança do \nmacaco y ha aumentado \nsu precisión");
                }
            }
        }
    };

    private static void quejica(TextView txtMain){
        Random r=new Random();

        if(danhoSergio!=0){
            if (r.nextInt(precisionDre)==0){
                txtMain.setText("Xandre ha usado quejica\npero ha fallado");
            }else{
                txtMain.setText("Xandre ha usado quejica\nEl daño de Sergio ha disminuído");
                danhoSergio--;
            }
        }else{
            txtMain.setText("Xandre ha usado quejica\npero ha fallado");
        }
    };

    private static void humareda(TextView txtMain, ImageView iv){
        Random r=new Random();
        Handler handler = new Handler();
        //iv.setVisibility(View.VISIBLE);

        if (precisionSergio>2){
            if (r.nextInt(precisionDre)==0){
                txtMain.setText("Xandre ha usado humareda\npero ha fallado");
            }else{
                iv.setVisibility(View.VISIBLE);
                txtMain.setText("Xandre ha usado humareda\n y la precisión de Sergio \nha disminuído");
                precisionSergio--;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // acciones que se ejecutan tras los milisegundos
                        iv.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        }else{
            txtMain.setText("Xandre ha usado humareda\npero ha fallado");
        }
    };


}