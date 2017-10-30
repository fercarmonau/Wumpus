package isl.wumpus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;

public class Indicios_Cuevas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicios__cuevas);
    }

    public void vibracion(){ //Indica el frio
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibra por x milisegundos
        v.vibrate(500);
    }

    public void sonido(){//Indica murcielagos

    }

    public void mensaje(){ //Indica Wumpus

    }
}
