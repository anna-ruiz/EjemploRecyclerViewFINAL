package com.example.ejemplorecyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ejemplorecyclerview.Modelos.ToDo;
import com.example.ejemplorecyclerview.adapters.ToDoAdapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ejemplorecyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ToDo> todoList;
    private ToDoAdapter adapter;
    //Creamos un layaout para q se muestre+
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        todoList = new ArrayList<>();
       /// crearTareas();

        //Inicializamos el adapter pasandole la lista, la vista y dnd se muestra
        adapter = new ToDoAdapter(todoList,R.layout.todo_view_model, MainActivity.this);
        //Añade en el contenedor del contentMain el adaptador q hemos creado
        binding.contentMain.contenedor.setAdapter(adapter);

        //Creamos nuevo layaout manager asociado al contexto y lo añadimos
        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             createToDo().show();
            }
        });
    }

    private AlertDialog createToDo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear tarea");
        builder.setCancelable(false); //Si el user aprieta fuera no se cierra el cuadro de dialogo

        //Creamos la vista nueva
        View todoAlert = LayoutInflater.from(this).inflate(R.layout.todo_model_alert, null);
        //Recuperamos la info q ha escrito el user
        EditText txtTitulo = todoAlert.findViewById(R.id.txtTituloTodoModelAlert);
        EditText txtContenido = todoAlert.findViewById(R.id.txtContenidoTodoModelAlert);
        //le añadimos la vista a la alerta
        builder.setView(todoAlert);

        //creamos los btn del alert
        builder.setNegativeButton("Cancelar",null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtTitulo.getText().toString().isEmpty() && !txtContenido.getText().toString().isEmpty()){
                    todoList.add(new ToDo(txtTitulo.getText().toString(), txtContenido.getText().toString()));
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();

    }

    private void crearTareas() {

        for (int i = 0; i < 100; i++) {
            todoList.add(new ToDo("Titulo "+i,"Contenido "+i));



        }

    }
}
