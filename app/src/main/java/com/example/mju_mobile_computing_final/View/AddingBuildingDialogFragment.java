package com.example.mju_mobile_computing_final.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mju_mobile_computing_final.R;

public class AddingBuildingDialogFragment extends DialogFragment {
    private AddingBuildingDialogListener callback;
    private EditText et_buildingName;
    private RadioGroup rg_dialog;
    private int selectedId;
    private RadioButton rb_selected;

    public interface AddingBuildingDialogListener {
        void onDialogPositiveClick(String buildingName, String size);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (AddingBuildingDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement AddingBuildingDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialog = inflater.inflate(R.layout.dialog_addition, null);

        return builder
            .setView(dialog)
            .setTitle(R.string.dialog_addition_title)
            .setPositiveButton(R.string.dialog_addition_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    et_buildingName = dialog.findViewById(R.id.et_buildingName);
                    rg_dialog = dialog.findViewById(R.id.rg_dialog);
                    selectedId = rg_dialog.getCheckedRadioButtonId();
                    rb_selected = dialog.findViewById(selectedId);
                    String size = rb_selected.getText().toString();

                    String buildingName = et_buildingName.getText().toString();
                    callback.onDialogPositiveClick(buildingName, size);
                }
            })
            .setNegativeButton(R.string.dialog_addition_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AddingBuildingDialogFragment.this.getDialog().cancel();
                }
            })
            .create();
    }
}
