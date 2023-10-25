package com.example.ejemplorecyclerview.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplorecyclerview.Modelos.ToDo;
import com.example.ejemplorecyclerview.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {
    //Lista de cosas q quiero mostrar
    private List<ToDo> objects;
    //La fila del elemento que queremos mostrar, lo trata como un entero
    private int resource;
    //El contexto/actividad en la que la voy a mostrar
    private Context context;

    //Creamos un constructor q recibe los 3 elementos
    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Instancia tantos elementos como quepan en pantalla

        //1.Creamos la vista, recibe el context/vista y las filas
        View todoView = LayoutInflater.from(context).inflate(resource, null);

        //2. Pasamos parametros para q la card ocupe todo el ancho de la pantalla
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        todoView.setLayoutParams(lp);

        //3. devolvemos la vista
        return new ToDoVH(todoView);
    }

    //para rellenar el objeto con sus propiedades
    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {
        ToDo toDo = objects.get(position);

        holder.lbTitulo.setText(toDo.getTitulo());
        holder.lbContenido.setText(toDo.getContenido());
        holder.lbFecha.setText(toDo.getFecha().toString());

        if (toDo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }else {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        //PARA EL BTN DEL CHECK
        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si clickea cambiamos al contrario
                //toDo.setCompletado(!toDo.isCompletado());
                //para q vuelva a comprobar el icono o asi
                //notifyDataSetChanged();

                confirmUpdate("¿Seguro que quieres cambiar?",toDo).show();
            }
        });

        //PARA EL BOTON DE ELIMINAR
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eliminar simple sin alert
             //   objects.remove(holder.getAdapterPosition());
             //   notifyItemRemoved(holder.getAdapterPosition());

                confirmDelete("¿Sequro que quieres eliminarlo?",holder.getAdapterPosition()).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        //Devolvemos cuantos elementos/objetos tenemos para mostrar(tamaño de la lista)
        return objects.size();
    }

    private AlertDialog confirmUpdate(String titulo, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context); //Le pasamos el contexto con la ventana en  la q queremos mostrarlo

        builder.setTitle(titulo); //le pasamos el titulo para q lo muestre
        builder.setCancelable(false); //para q deba darle a la X si quiere salir

        //Creamos 3 btns x defecto
        builder.setNegativeButton("NO",null); //El no
        //El de confirmar tiene un OnClickListener
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 toDo.setCompletado(!toDo.isCompletado());
                 notifyDataSetChanged();
            }
        });

        return builder.create(); //Le mandamos la ventana d vuelts
    }

    //Cuadro de dialogo para eliminar una nota
    private AlertDialog confirmDelete(String titulo, int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false);

        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                objects.remove(posicion);
                notifyItemRemoved(posicion);
            }
        });

        return builder.create();

    }

    public class ToDoVH extends RecyclerView.ViewHolder{
        TextView lbTitulo, lbContenido, lbFecha;
        ImageButton btnCompletado, btnEliminar;


        public ToDoVH(@NonNull View itemView) {
            super(itemView);

            lbTitulo = itemView.findViewById(R.id.lbTituloTodoViewModel);
            lbContenido = itemView.findViewById(R.id.lbContenidoTodoViewModel);
            lbFecha = itemView.findViewById(R.id.lbFechaTodoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoTodoViewModel);
            btnEliminar = itemView.findViewById(R.id.btnDeleteToDoViewModel);
        }
    }
}
