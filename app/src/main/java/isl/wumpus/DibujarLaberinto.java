package isl.wumpus;

import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

public class DibujarLaberinto extends AppCompatActivity implements View.OnClickListener {

    private static Lienzo lienzo;
    public Button bCueva;
    public Button bCamino;
    public Button bGuardar;
    public Button bBorrar;
    public Button bPoliedro;
    public Mapa mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dibujar_laberinto);
        bCamino = (Button) findViewById(R.id.btncamino);
        bCueva = (Button) findViewById(R.id.btncueva);
        bGuardar = (Button) findViewById(R.id.btnguardar);
        bBorrar =(Button) findViewById(R.id.btnborrar);
        bPoliedro=(Button) findViewById(R.id.btnPoliedro);
        lienzo = (Lienzo) findViewById(R.id.lienzo);
        bCamino.setOnClickListener(this);
        bPoliedro.setOnClickListener(this);
        bCueva.setOnClickListener(this);
        bGuardar.setOnClickListener(this);
        bBorrar.setOnClickListener(this);

       /* File fileDir = new File(getFilesDir().getAbsolutePath());
       fileDir.mkdirs();*/

    }

    public void guardarLaberinto(){
        AlertDialog.Builder salvarDibujo = new AlertDialog.Builder(this);
        salvarDibujo.setTitle("Salvar laberinto");
        salvarDibujo.setMessage("¿Salvar Laberinto a la galeria?");
        salvarDibujo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

                //Salvar dibujo
                lienzo.setDrawingCacheEnabled(true);

                //Para asociar PNG con TXT. Pedir nombre?

                String nombre;
                nombre = UUID.randomUUID().toString();

                //attempt to save
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), lienzo.getDrawingCache(),
                        //UUID.randomUUID().toString()+".png", "drawing");
                        nombre+".png", "drawing");

                //Guardar TXT
                GestionadorDeArchivos ga = new GestionadorDeArchivos();
                ga.write(nombre,ga.convertirObjetoAString(mapa),getApplicationContext());


                //Mensaje de todo correcto
                if(imgSaved!=null){
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "¡Laberinto salvado en la galeria!", Toast.LENGTH_SHORT);
                    savedToast.show();
                }
                else{
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "¡Error! El laberinto no ha podido ser salvado.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                lienzo.destroyDrawingCache();


            }
        });
        salvarDibujo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        salvarDibujo.show();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncueva:
                lienzo.modo(true);
                if(bCueva.getText().equals("NuevaCueva")) {
                    lienzo.nuevaCueva();
                }
                bCueva.setText("NuevaCueva");
                break;

            case R.id.btncamino:
                lienzo.modo(false);
                bCueva.setText("Cueva");
                break;
            case R.id.btnguardar:
                lienzo.modo(true);
                mapa = new Mapa(lienzo.cuevaX, lienzo.cuevaY, lienzo.caminoA,
                        lienzo.caminoB, lienzo.numCuevas, lienzo.contadorCamino);
                if(mapa.Validar()) {
                    guardarLaberinto();
                } else Toast.makeText(getApplicationContext(), "Mapa invalido.", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnborrar:
                lienzo.borrar();
                break;
            case R.id.btnPoliedro:
                lienzo.crearOctahedro();
                break;
        }

    }

}
