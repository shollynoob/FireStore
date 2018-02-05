package exabyte.firestore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String NTEXT = "NTEXT";
    private static final String IMAGE = "IMAGE";
    private EditText text;
    private Button send;
    private FirebaseFirestore mFireStore;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireStore = FirebaseFirestore.getInstance();

        text = findViewById(R.id.text);
        send = findViewById(R.id.send);
        result = findViewById(R.id.result);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ntext = text.getText().toString();

                Map<String, String> userMap= new HashMap<>();
                userMap.put(NTEXT, ntext);
                userMap.put(IMAGE, "image_link");

                mFireStore.collection("user").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Post Sucessful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String mes = e.getMessage();
                        Toast.makeText(MainActivity.this, "" +mes, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mFireStore.document("user").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                     if (documentSnapshot.exists()){
                         String text = documentSnapshot.getString(NTEXT);
                         String image_url = documentSnapshot.getString(IMAGE);
                         result.setText(text );
                     }
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                 }
             });
            }
        });
    }
}
