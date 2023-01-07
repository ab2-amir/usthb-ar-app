package com.example.usthbmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;

import java.util.Objects;

public class ar_map_activity extends AppCompatActivity {

    public void verifyPermissions() {
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
        {
            String[] tabPermission = {Manifest.permission.CAMERA};
            //Fenetre de permission -- travail avec fct onRequestPermissionsResult()
            requestPermissions(tabPermission, 100);
        }
    }

    //itels pour position
    String[] items = {"Amphis", "Salles TD", "Village", "Rectorat", "Bibliothèque", "Faculté informatique", "Faculté mathématique", "Nouveaux blocs"};
    //items pour destination pour pour calculer distance
    String[] items_classroom_nvBloc_1 = {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8",
            "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8",
            "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8"};
    String[] items_classroom_amphis = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "V", "W", "X", "Z"};


    AutoCompleteTextView autoCompleteTextViewPos;
    AutoCompleteTextView autoCompleteTextViewDest;
    AutoCompleteTextView autoCompleteTextViewDestClassroom;


    public String item_pos = " ";
    public String item_des = " ";
    public String item_des_classroom = " ";
    public String[] items2 = {"Amphis", "Salles TD", "Village", "Rectorat", "Bibliothèque", "Faculté informatique", "Faculté mathématique", "Nouveaux blocs"};

    public ArrayAdapter<String> adapterItems;
    public ArrayAdapter<String> adapterItemsClassroom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ar_map);

        autoCompleteTextViewPos = findViewById(R.id.auto_Complete_pos_txt);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);

        autoCompleteTextViewPos.setAdapter(adapterItems);

        autoCompleteTextViewPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                item_pos = adapterView.getItemAtPosition(i).toString();
                //Display la distance qui correspond à la position choisie

            }
        });

        autoCompleteTextViewDest = findViewById(R.id.auto_Complete_dest_txt);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items2);

        autoCompleteTextViewDest.setAdapter(adapterItems);

        autoCompleteTextViewDest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                item_des = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Item dest: "+item_des, Toast.LENGTH_SHORT).show();
                openClassroom(item_des);
            }
        });


        //load building of correspondent position
        Button button_view_pos = findViewById(R.id.button_view_position);

        button_view_pos.setOnClickListener(new Button.OnClickListener() {

               @Override
               public void onClick(View view) {
                   verifyPermissions();
                   ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getApplicationContext());
                   if (availability.isSupported()) {
                       //Toast.makeText(ar_map_activity.this, "AR IS WORKING !", Toast.LENGTH_SHORT).show();

                       openBuilding(item_pos);
                   } else {
                       Toast.makeText(ar_map_activity.this, "AR IS NOT WORKING !", Toast.LENGTH_SHORT).show();
                   }
               }
        });

        //load building of correspondent destination
        Button button_view_des = findViewById(R.id.button_view_destination);

        button_view_des.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                verifyPermissions();
                ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getApplicationContext());
                if (availability.isSupported()) {
                    //Toast.makeText(ar_map_activity.this, "AR IS WORKING !", Toast.LENGTH_SHORT).show();

                    openBuilding(item_des);
                } else {
                    Toast.makeText(ar_map_activity.this, "AR IS NOT WORKING !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //load position and destination building to draw a path between them
        Button button_go = findViewById(R.id.button_go);

        button_go.setOnClickListener(new Button.OnClickListener()

        {

            @Override
            public void onClick (View view){
                verifyPermissions();
                ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getApplicationContext());
                if (availability.isSupported()) {
                    Toast.makeText(ar_map_activity.this, "AR IS WORKING !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ar_map_activity.this, "AR IS NOT WORKING !", Toast.LENGTH_SHORT).show();
                }

                Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
                String posAndDest = item_pos+" "+item_des;
                if(posAndDest.equals("Amphis Salles TD") || posAndDest.equals("Salles TD Amphis")){

                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/amphis%20to%20salles%20td/amphis%20to%20salles%20td.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                }
                else {
                    if (posAndDest.equals("Amphis Village") || posAndDest.equals("Village Amphis")) {

                        Uri intentUri =
                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/amphis%20to%20village/amphis%20to%20village.gltf")

                                        .appendQueryParameter("mode", "ar_preferred")
                                        .appendQueryParameter("title", "Model3D")
                                        .build();
                        sceneViewerIntent.setData(intentUri);
                        sceneViewerIntent.setPackage("com.google.ar.core");
                        startActivity(sceneViewerIntent);
                    } else {
                        if (posAndDest.equals("Amphis Rectorat") || posAndDest.equals("Rectorat Amphis")) {

                            Uri intentUri =
                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                            .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/amphis%20to%20rectorat/amphis%20to%20rectorat.gltf")

                                            .appendQueryParameter("mode", "ar_preferred")
                                            .appendQueryParameter("title", "Model3D")
                                            .build();
                            sceneViewerIntent.setData(intentUri);
                            sceneViewerIntent.setPackage("com.google.ar.core");
                            startActivity(sceneViewerIntent);
                        }
                        else {
                            if (posAndDest.equals("Amphis Bibliothèque") || posAndDest.equals("Bibliothèque Amphis")) {

                                Uri intentUri =
                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/amphis%20to%20bib/amphis%20to%20bib.gltf")

                                                .appendQueryParameter("mode", "ar_preferred")
                                                .appendQueryParameter("title", "Model3D")
                                                .build();
                                sceneViewerIntent.setData(intentUri);
                                sceneViewerIntent.setPackage("com.google.ar.core");
                                startActivity(sceneViewerIntent);
                            }
                            else {
                                if (posAndDest.equals("Amphis Faculté informatique") || posAndDest.equals("Faculté informatique Amphis")) {

                                    Uri intentUri =
                                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/amphis%20to%20info/amphis%20to%20info.gltf")

                                                    .appendQueryParameter("mode", "ar_preferred")
                                                    .appendQueryParameter("title", "Model3D")
                                                    .build();
                                    sceneViewerIntent.setData(intentUri);
                                    sceneViewerIntent.setPackage("com.google.ar.core");
                                    startActivity(sceneViewerIntent);
                                }
                                else {
                                    if (posAndDest.equals("Amphis Faculté mathématique") || posAndDest.equals("Faculté mathématique Amphis")) {

                                        Uri intentUri =
                                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/amphis%20to%20math/amphis%20to%20math.gltf")

                                                        .appendQueryParameter("mode", "ar_preferred")
                                                        .appendQueryParameter("title", "Model3D")
                                                        .build();
                                        sceneViewerIntent.setData(intentUri);
                                        sceneViewerIntent.setPackage("com.google.ar.core");
                                        startActivity(sceneViewerIntent);
                                    }
                                    else {
                                        if (posAndDest.equals("Amphis Nouveaux blocs") || posAndDest.equals("Nouveaux blocs Amphis")) {

                                            Uri intentUri =
                                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                            .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/amphis%20to%20nv%20blocs/amphis%20to%20nv%20blocs.gltf")

                                                            .appendQueryParameter("mode", "ar_preferred")
                                                            .appendQueryParameter("title", "Model3D")
                                                            .build();
                                            sceneViewerIntent.setData(intentUri);
                                            sceneViewerIntent.setPackage("com.google.ar.core");
                                            startActivity(sceneViewerIntent);
                                        }
                                        else {
                                            if (posAndDest.equals("Salles TD Village") || posAndDest.equals("Village Salles TD")) {

                                                Uri intentUri =
                                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/td%20to%20village/td%20to%20village.gltf")

                                                                .appendQueryParameter("mode", "ar_preferred")
                                                                .appendQueryParameter("title", "Model3D")
                                                                .build();
                                                sceneViewerIntent.setData(intentUri);
                                                sceneViewerIntent.setPackage("com.google.ar.core");
                                                startActivity(sceneViewerIntent);
                                            }
                                            else {
                                                if (posAndDest.equals("Salles TD Rectorat") || posAndDest.equals("Rectorat Salles TD")) {

                                                    Uri intentUri =
                                                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/td%20rectorat/td%20to%20vrectorat.gltf")

                                                                    .appendQueryParameter("mode", "ar_preferred")
                                                                    .appendQueryParameter("title", "Model3D")
                                                                    .build();
                                                    sceneViewerIntent.setData(intentUri);
                                                    sceneViewerIntent.setPackage("com.google.ar.core");
                                                    startActivity(sceneViewerIntent);
                                                }
                                                else {
                                                    if (posAndDest.equals("Salles TD Bibliothèque") || posAndDest.equals("Bibliothèque Salles TD")) {

                                                        Uri intentUri =
                                                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/td%20to%20bib/td%20to%20bib.gltf")

                                                                        .appendQueryParameter("mode", "ar_preferred")
                                                                        .appendQueryParameter("title", "Model3D")
                                                                        .build();
                                                        sceneViewerIntent.setData(intentUri);
                                                        sceneViewerIntent.setPackage("com.google.ar.core");
                                                        startActivity(sceneViewerIntent);
                                                    }
                                                    else {
                                                        if (posAndDest.equals("Salles TD Faculté informatique") || posAndDest.equals("Faculté informatique Salles TD")) {

                                                            Uri intentUri =
                                                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                            .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/td%20to%20info/td%20to%20info.gltf")

                                                                            .appendQueryParameter("mode", "ar_preferred")
                                                                            .appendQueryParameter("title", "Model3D")
                                                                            .build();
                                                            sceneViewerIntent.setData(intentUri);
                                                            sceneViewerIntent.setPackage("com.google.ar.core");
                                                            startActivity(sceneViewerIntent);
                                                        }
                                                        else {
                                                            if (posAndDest.equals("Salles TD Faculté mathématique") || posAndDest.equals("Faculté mathématique Salles TD")) {

                                                                Uri intentUri =
                                                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/td%20to%20math/td%20to%20math.gltf")

                                                                                .appendQueryParameter("mode", "ar_preferred")
                                                                                .appendQueryParameter("title", "Model3D")
                                                                                .build();
                                                                sceneViewerIntent.setData(intentUri);
                                                                sceneViewerIntent.setPackage("com.google.ar.core");
                                                                startActivity(sceneViewerIntent);
                                                            }
                                                            else {
                                                                if (posAndDest.equals("Salles TD Nouveaux blocs") || posAndDest.equals("Nouveaux blocs Salles TD")) {

                                                                    Uri intentUri =
                                                                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/td%20to%20nv%20blocs/td%20to%20nv%20blocs.gltf")

                                                                                    .appendQueryParameter("mode", "ar_preferred")
                                                                                    .appendQueryParameter("title", "Model3D")
                                                                                    .build();
                                                                    sceneViewerIntent.setData(intentUri);
                                                                    sceneViewerIntent.setPackage("com.google.ar.core");
                                                                    startActivity(sceneViewerIntent);
                                                                }
                                                                else {
                                                                    if (posAndDest.equals("Village Rectorat") || posAndDest.equals("Rectorat Village")) {

                                                                        Uri intentUri =
                                                                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/village%20to%20rectorat/village%20to%20rectorat.gltf")

                                                                                        .appendQueryParameter("mode", "ar_preferred")
                                                                                        .appendQueryParameter("title", "Model3D")
                                                                                        .build();
                                                                        sceneViewerIntent.setData(intentUri);
                                                                        sceneViewerIntent.setPackage("com.google.ar.core");
                                                                        startActivity(sceneViewerIntent);
                                                                    }
                                                                    else {
                                                                        if (posAndDest.equals("Village Bibliothèque") || posAndDest.equals("Bibliothèque Village")) {

                                                                            Uri intentUri =
                                                                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                            .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/village%20to%20bib/village%20to%20bib.gltf")

                                                                                            .appendQueryParameter("mode", "ar_preferred")
                                                                                            .appendQueryParameter("title", "Model3D")
                                                                                            .build();
                                                                            sceneViewerIntent.setData(intentUri);
                                                                            sceneViewerIntent.setPackage("com.google.ar.core");
                                                                            startActivity(sceneViewerIntent);
                                                                        }
                                                                        else {
                                                                            if (posAndDest.equals("Village Faculté informatique") || posAndDest.equals("Faculté informatique Village")) {

                                                                                Uri intentUri =
                                                                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/village%20to%20info/village%20to%20info.gltf")

                                                                                                .appendQueryParameter("mode", "ar_preferred")
                                                                                                .appendQueryParameter("title", "Model3D")
                                                                                                .build();
                                                                                sceneViewerIntent.setData(intentUri);
                                                                                sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                startActivity(sceneViewerIntent);
                                                                            }
                                                                            else {
                                                                                if (posAndDest.equals("Village Faculté mathématique") || posAndDest.equals("Faculté mathématique Village")) {

                                                                                    Uri intentUri =
                                                                                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/village%20to%20math/village%20to%20math.gltf")

                                                                                                    .appendQueryParameter("mode", "ar_preferred")
                                                                                                    .appendQueryParameter("title", "Model3D")
                                                                                                    .build();
                                                                                    sceneViewerIntent.setData(intentUri);
                                                                                    sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                    startActivity(sceneViewerIntent);
                                                                                }
                                                                                else {
                                                                                    if (posAndDest.equals("Village Nouveaux blocs") || posAndDest.equals("Nouveaux blocs Village")) {

                                                                                        Uri intentUri =
                                                                                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/village%20to%20nv%20blocs/village%20to%20nv%20b.gltf")

                                                                                                        .appendQueryParameter("mode", "ar_preferred")
                                                                                                        .appendQueryParameter("title", "Model3D")
                                                                                                        .build();
                                                                                        sceneViewerIntent.setData(intentUri);
                                                                                        sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                        startActivity(sceneViewerIntent);
                                                                                    }
                                                                                    else {
                                                                                        if (posAndDest.equals("Rectorat Bibliothèque") || posAndDest.equals("Bibliothèque Rectorat")) {

                                                                                            Uri intentUri =
                                                                                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                            .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/rectorat%20to%20bib/rectorat%20to%20bib.gltf")

                                                                                                            .appendQueryParameter("mode", "ar_preferred")
                                                                                                            .appendQueryParameter("title", "Model3D")
                                                                                                            .build();
                                                                                            sceneViewerIntent.setData(intentUri);
                                                                                            sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                            startActivity(sceneViewerIntent);
                                                                                        }
                                                                                        else {
                                                                                            if (posAndDest.equals("Rectorat Faculté informatique") || posAndDest.equals("Faculté informatique Rectorat")) {

                                                                                                Uri intentUri =
                                                                                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/rectorat%20info/rectorat%20to%20info.gltf")

                                                                                                                .appendQueryParameter("mode", "ar_preferred")
                                                                                                                .appendQueryParameter("title", "Model3D")
                                                                                                                .build();
                                                                                                sceneViewerIntent.setData(intentUri);
                                                                                                sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                startActivity(sceneViewerIntent);
                                                                                            }
                                                                                            else {
                                                                                                if (posAndDest.equals("Rectorat Faculté mathématique") || posAndDest.equals("Faculté mathématique Rectorat")) {

                                                                                                    Uri intentUri =
                                                                                                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/rectorat%20to%20math/rectorat%20to%20math.gltf")

                                                                                                                    .appendQueryParameter("mode", "ar_preferred")
                                                                                                                    .appendQueryParameter("title", "Model3D")
                                                                                                                    .build();
                                                                                                    sceneViewerIntent.setData(intentUri);
                                                                                                    sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                    startActivity(sceneViewerIntent);
                                                                                                }
                                                                                                else {
                                                                                                    if (posAndDest.equals("Rectorat Nouveaux blocs") || posAndDest.equals("Nouveaux blocs Rectorat")) {

                                                                                                        Uri intentUri =
                                                                                                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/rectorat%20to%20nv%20blocs/rectorat%20to%20nv%20blocs.gltf")

                                                                                                                        .appendQueryParameter("mode", "ar_preferred")
                                                                                                                        .appendQueryParameter("title", "Model3D")
                                                                                                                        .build();
                                                                                                        sceneViewerIntent.setData(intentUri);
                                                                                                        sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                        startActivity(sceneViewerIntent);
                                                                                                    }
                                                                                                    else {
                                                                                                        if (posAndDest.equals("Bibliothèque Faculté informatique") || posAndDest.equals("Faculté informatique Bibliothèque")) {

                                                                                                            Uri intentUri =
                                                                                                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                            .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/bibio%20to%20info/bib%20to%20info.gltf")

                                                                                                                            .appendQueryParameter("mode", "ar_preferred")
                                                                                                                            .appendQueryParameter("title", "Model3D")
                                                                                                                            .build();
                                                                                                            sceneViewerIntent.setData(intentUri);
                                                                                                            sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                            startActivity(sceneViewerIntent);
                                                                                                        }
                                                                                                        else {
                                                                                                            if (posAndDest.equals("Bibliothèque Faculté mathématique") || posAndDest.equals("Faculté mathématique Bibliothèque")) {

                                                                                                                Uri intentUri =
                                                                                                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                                .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/bibio%20to%20math/bib%20to%20math.gltf")

                                                                                                                                .appendQueryParameter("mode", "ar_preferred")
                                                                                                                                .appendQueryParameter("title", "Model3D")
                                                                                                                                .build();
                                                                                                                sceneViewerIntent.setData(intentUri);
                                                                                                                sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                                startActivity(sceneViewerIntent);
                                                                                                            }
                                                                                                            else {
                                                                                                                if (posAndDest.equals("Bibliothèque Nouveaux blocs") || posAndDest.equals("Nouveaux blocs Bibliothèque")) {

                                                                                                                    Uri intentUri =
                                                                                                                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/bibio%20to%20nv%20blocs/bib%20to%20nv%20blocs.gltf")

                                                                                                                                    .appendQueryParameter("mode", "ar_preferred")
                                                                                                                                    .appendQueryParameter("title", "Model3D")
                                                                                                                                    .build();
                                                                                                                    sceneViewerIntent.setData(intentUri);
                                                                                                                    sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                                    startActivity(sceneViewerIntent);
                                                                                                                }
                                                                                                                else {
                                                                                                                    if (posAndDest.equals("Faculté informatique Faculté mathématique") || posAndDest.equals("Faculté mathématique Faculté informatqiue")) {

                                                                                                                        Uri intentUri =
                                                                                                                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/info%20to%20math/info%20to%20math.gltf")

                                                                                                                                        .appendQueryParameter("mode", "ar_preferred")
                                                                                                                                        .appendQueryParameter("title", "Model3D")
                                                                                                                                        .build();
                                                                                                                        sceneViewerIntent.setData(intentUri);
                                                                                                                        sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                                        startActivity(sceneViewerIntent);
                                                                                                                    }
                                                                                                                    else {
                                                                                                                        if (posAndDest.equals("Faculté informatique Nouveaux blocs") || posAndDest.equals("Nouveaux blocs Faculté informatqiue")) {

                                                                                                                            Uri intentUri =
                                                                                                                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                                            .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/info%20to%20nv%20blocs/info%20to%20nv%20blocs.gltf")

                                                                                                                                            .appendQueryParameter("mode", "ar_preferred")
                                                                                                                                            .appendQueryParameter("title", "Model3D")
                                                                                                                                            .build();
                                                                                                                            sceneViewerIntent.setData(intentUri);
                                                                                                                            sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                                            startActivity(sceneViewerIntent);
                                                                                                                        }
                                                                                                                        else {
                                                                                                                            if (posAndDest.equals("Faculté mathématique Nouveaux blocs") || posAndDest.equals("Nouveaux blocs Faculté mathématique")) {

                                                                                                                                Uri intentUri =
                                                                                                                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                                                                                                                                .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/destinations/math%20to%20nv%20blocs/math%20to%20nv%20blocs.gltf")

                                                                                                                                                .appendQueryParameter("mode", "ar_preferred")
                                                                                                                                                .appendQueryParameter("title", "Model3D")
                                                                                                                                                .build();
                                                                                                                                sceneViewerIntent.setData(intentUri);
                                                                                                                                sceneViewerIntent.setPackage("com.google.ar.core");
                                                                                                                                startActivity(sceneViewerIntent);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    //load the building (item) and open it in 3D or AR
    private void openBuilding(String item){
        if(!Objects.equals(item, " ")) {
            Log.d("item_des_classroom in openBuilding", item_des_classroom);

            Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);

            switch (item) {
                case "Amphis": {
                    Toast.makeText(getApplicationContext(), "is 100-200", Toast.LENGTH_SHORT).show();

                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/amphis/amphis%20cote%20serpent.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);

                    break;
                }
                case "Salles TD": {
                    Toast.makeText(getApplicationContext(), "is 100-200", Toast.LENGTH_SHORT).show();

                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/ab2-amir/usthb-ar-app/main/salle%20td/salles%20TD.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);

                    break;
                }
                case "Faculté informatique": {
                    Toast.makeText(getApplicationContext(), "faculte informatique", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/faculte%20informatique/faculte%20info.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);

                    break;
                }
                case "Faculté mathématique": {
                    Toast.makeText(getApplicationContext(), "faculte math", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/faculte%20math/feculte%20math.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
                case "Nouveaux blocs": {
                    //open building witch contain the classroom
                    if (item_des_classroom.equals("C1") || item_des_classroom.equals("C2") || item_des_classroom.equals("C3") || item_des_classroom.equals("C4") || item_des_classroom.equals("C5") || item_des_classroom.equals("C6") || item_des_classroom.equals("C7") || item_des_classroom.equals("C8") || item_des_classroom.equals("D1") || item_des_classroom.equals("D2") || item_des_classroom.equals("D3") || item_des_classroom.equals("D4") || item_des_classroom.equals("D5") || item_des_classroom.equals("D6") || item_des_classroom.equals("D7") || item_des_classroom.equals("D8")){
                        Toast.makeText(getApplicationContext(), "Nouveaux blocs", Toast.LENGTH_SHORT).show();
                        Uri intentUri =
                                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                        .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/nouveaux%20blocs/nv%20blocs.gltf")

                                        .appendQueryParameter("mode", "ar_preferred")
                                        .appendQueryParameter("title", "Model3D")
                                        .build();
                        sceneViewerIntent.setData(intentUri);
                        sceneViewerIntent.setPackage("com.google.ar.core");
                        startActivity(sceneViewerIntent);
                    }
                    break;
                }
                case "Bibliothèque": {
                    Toast.makeText(getApplicationContext(), "Bibliothèque", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/bibliotheque%20et%20facultes/bibio%20et%20facultes.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
                case "Auditorium": {
                    Toast.makeText(getApplicationContext(), "auditorium", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/auditorum/auditoriom.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
                case "Village": {
                    Toast.makeText(getApplicationContext(), "Village", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/village/village.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
                case "Les chalets": {
                    Toast.makeText(getApplicationContext(), "chalets", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/chalets/chalets.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
                case "Dépots": {
                    Toast.makeText(getApplicationContext(), "depots", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/depots/depots.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
                case "Hall technologique": {
                    Toast.makeText(getApplicationContext(), "Hall technologique", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/hall%20technologique/hall%20tech.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
                case "Rectorat": {
                    Toast.makeText(getApplicationContext(), "Rectorat", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/rectorat/rectorat.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);
                    break;
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "Select item", Toast.LENGTH_SHORT).show();
            //Log.d("erreu item = ", item);
        }
    }


    //show classroom correspondent of selected destination
    private void openClassroom(@NonNull String item_des) {
        adapterItemsClassroom = null;

        autoCompleteTextViewDestClassroom = findViewById(R.id.auto_Complete_dest_classroom_txt);
        switch (item_des) {
            case "Nouveaux blocs": {
                adapterItemsClassroom = new ArrayAdapter<String>(this, R.layout.list_item, items_classroom_nvBloc_1);
                break;
            }
            case "Amphis": {
                adapterItemsClassroom = new ArrayAdapter<String>(this, R.layout.list_item, items_classroom_amphis);
                break;
            }
        }

        autoCompleteTextViewDestClassroom.setAdapter(adapterItemsClassroom);

        autoCompleteTextViewDestClassroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                item_des_classroom = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item_des_classroom, Toast.LENGTH_SHORT).show();
                Log.d("classroom in fct", item_des_classroom);

            }

        });

    }


}