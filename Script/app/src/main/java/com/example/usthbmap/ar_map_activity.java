package com.example.usthbmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

    String[] items = {"100-200", "300-400", "Village", "Rectorat", "Bibliothèque", "Faculté informatique", "Faculté mathématique", "Auditorium", "Nouveaux blocs", "Hall technologique", "Les chalets", "Dépots"};

    AutoCompleteTextView autoCompleteTextViewPos;
    AutoCompleteTextView autoCompleteTextViewDest;

    public String item_pos = " ";
    public String item_des = " ";



    ArrayAdapter<String> adapterItems;

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
                Toast.makeText(getApplicationContext(), "Item: "+item_pos, Toast.LENGTH_SHORT).show();

            }

        });


        autoCompleteTextViewDest = findViewById(R.id.auto_Complete_dest_txt);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);

        autoCompleteTextViewDest.setAdapter(adapterItems);

        autoCompleteTextViewDest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                item_des = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Item: "+item_des, Toast.LENGTH_SHORT).show();

            }

        });

        //load building of correspondent position
        Button button_view_pos = findViewById(R.id.button_view_position);

        button_view_pos.setOnClickListener(new Button.OnClickListener() {

               @Override
               public void onClick(View view) {
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
                ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getApplicationContext());
                if (availability.isSupported()) {
                    Toast.makeText(ar_map_activity.this, "AR IS WORKING !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ar_map_activity.this, "AR IS NOT WORKING !", Toast.LENGTH_SHORT).show();
                }

                Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
                Uri intentUri =
                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/village%20to%20math/usthb_map_clone.gltf")

                                .appendQueryParameter("mode", "ar_preferred")
                                .appendQueryParameter("title", "Model3D")
                                .build();
                sceneViewerIntent.setData(intentUri);
                sceneViewerIntent.setPackage("com.google.ar.core");
                startActivity(sceneViewerIntent);
            }
        });

    }


    //load the building (item) and open it in 3D or AR
    private void openBuilding(String item){
        //Toast.makeText(getApplicationContext(), "item = "+item, Toast.LENGTH_SHORT).show();
        Log.d("item = ", item);

        if(!Objects.equals(item, " ")) {
            Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);

            switch (item) {
                case "100-200": {
                    Toast.makeText(getApplicationContext(), "is 100-200", Toast.LENGTH_SHORT).show();

                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/amphis%20et%20salles%20td/amphis%20et%20salles%20td.gltf")

                                    .appendQueryParameter("mode", "ar_preferred")
                                    .appendQueryParameter("title", "Model3D")
                                    .build();
                    sceneViewerIntent.setData(intentUri);
                    sceneViewerIntent.setPackage("com.google.ar.core");
                    startActivity(sceneViewerIntent);

                    break;
                }
                case "300-400": {
                    Toast.makeText(getApplicationContext(), "300-400", Toast.LENGTH_SHORT).show();
                    Uri intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                    .appendQueryParameter("file", "https://raw.githubusercontent.com/abdou-amir/usthb-ar-app/main/amphis%20et%20salles%20td/amphis%20et%20salles%20td.gltf")

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

}