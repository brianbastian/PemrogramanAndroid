package com.example.helloworld;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText noMhs;
    private EditText namaMhs;
    private EditText phoneMhs;
    private Button buttonSimpan;
    private Button buttonHapus;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore firebaseFirestoreDb;
    RecyclerView recyclerView;
    private TextView namamhs;
    private TextView nimmhs;
    private TextView phonemhs;


    public InputData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputData.
     */
    // TODO: Rename and change types and number of parameters
    public static InputData newInstance(String param1, String param2) {
        InputData fragment = new InputData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_data, container, false);
        noMhs = view.findViewById(R.id.noMhs);
        namaMhs = view.findViewById(R.id.namaMhs);
        phoneMhs = view.findViewById(R.id.phoneMhs);
        buttonSimpan = view.findViewById(R.id.simpanButton);
        buttonHapus = view.findViewById(R.id.btndelete);
        recyclerView = view.findViewById(R.id.readdata);
        firebaseFirestoreDb = firebaseFirestoreDb.getInstance();

        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        Query query = firebaseFirestoreDb.collection("DaftarMhs");
        FirestoreRecyclerOptions<DataMahasiswa> options = new FirestoreRecyclerOptions.Builder<DataMahasiswa>()
                .setQuery(query,DataMahasiswa.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<DataMahasiswa, InputData.MahasiswaViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position, @NonNull DataMahasiswa model) {
                holder.namamhs.setText(model.getNama());
                holder.nimmhs.setText(model.getNim());
                holder.phonemhs.setText(model.getPhone());
            }

            @NonNull
            @Override
            public InputData.MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design2,parent,false);
                return new InputData.MahasiswaViewHolder(view);
            }
        };
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!noMhs.getText().toString().isEmpty() && !namaMhs.getText().toString().isEmpty()) {
                    tambahMahasiswa();
                } else {
                    Toast.makeText(requireActivity(), "Nomor dan Nama Mahasiswa tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDataMahasiswa();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;

    }
    private class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        private TextView namamhs;
        private TextView nimmhs;
        private TextView phonemhs;
        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            namamhs = itemView.findViewById(R.id.namamhs);
            nimmhs = itemView.findViewById(R.id.nimmhs);
            phonemhs = itemView.findViewById(R.id.phonemhs);

        }
    }

    private void tambahMahasiswa() {
        DataMahasiswa mhs = new DataMahasiswa(noMhs.getText().toString(),
                namaMhs.getText().toString(),
                phoneMhs.getText().toString());

        firebaseFirestoreDb.collection("DaftarMhs").document().set(mhs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
    private void deleteDataMahasiswa() {
        firebaseFirestoreDb.collection("DaftarMhs").document("mhs1")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        noMhs.setText("");
                        namaMhs.setText("");
                        phoneMhs.setText("");
                        Toast.makeText(requireActivity(), "Mahasiswa berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

}