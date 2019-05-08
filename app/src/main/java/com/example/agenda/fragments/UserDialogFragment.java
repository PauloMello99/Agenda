package com.example.agenda.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.models.User;
import com.example.agenda.utils.DatePickerDialogHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class UserDialogFragment extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(User user, int position);
    }

    public static final String CREATE_TAG = "CREATE";
    public static final String EDIT_TAG = "EDIT";

    private static final String EXTRA_TITLE = "TITLE";
    private static final String EXTRA_POSITIVE_BUTTON = "POSITIVE_BUTTON";
    private static final String EXTRA_ITEM = "SERIALIZABLE_ITEM";
    private static final String EXTRA_POSITION = "ITEM_POSITION";

    private NoticeDialogListener dialogListener;
    private User currentUser = null;

    private EditText nameEditText;
    private EditText dateEditText;

    @BindView(R.id.input_layout_name)
    TextInputLayout nameInputLayout;

    @BindView(R.id.input_layout_year)
    TextInputLayout dateInputLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogListener = (NoticeDialogListener) context;
    }

    public static UserDialogFragment newInstance(String title, String positiveButton){
        return newInstance(title,positiveButton,null,-1);
    }

    public static UserDialogFragment newInstance(String title,String positiveButton,User user, int position){
        UserDialogFragment dialogFragment = new UserDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_POSITIVE_BUTTON, positiveButton);
        bundle.putSerializable(EXTRA_ITEM, user);
        bundle.putInt(EXTRA_POSITION, position);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Context context = getActivity();
        final Bundle bundle = getArguments();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_user, null, true);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(bundle.getString(EXTRA_TITLE));
        builder.setPositiveButton(bundle.getString(EXTRA_POSITIVE_BUTTON), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentUser.setName(nameEditText.getText().toString().trim());
                currentUser.setDate(dateEditText.getText().toString().trim());
                if(checkFields()) dialogListener.onDialogPositiveClick(currentUser,bundle.getInt(EXTRA_POSITION));
                else Toast.makeText(context, "Campos inválidos", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(view);

        setView(view,bundle);

        DatePickerDialogHelper.setDatePickerDialog(dateEditText,context,new SimpleDateFormat("dd/MM/yyyy"));

        return builder.create();
    }

    private void setView(View view,Bundle bundle) {
        nameEditText = view.findViewById(R.id.name);
        dateEditText = view.findViewById(R.id.year);
        if(getTag().equals(EDIT_TAG)){
            currentUser = (User) bundle.getSerializable(EXTRA_ITEM);
            nameEditText.setText(currentUser.getName());
            dateEditText.setText(currentUser.getDate());
        } else currentUser = new User();
    }

    private boolean checkFields() {
        boolean check = true;
        if(currentUser.getName().isEmpty()){
            nameInputLayout.setError("Insira o nome");
            check = false;
        }
        if(currentUser.getDate().isEmpty()){
            dateInputLayout.setError("Insira o ano");
            check = false;
        }
        return check;
    }
}
