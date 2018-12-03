package com.example.mju_mobile_computing_final.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.example.mju_mobile_computing_final.R;

public class AddingBuildingDialogFragment extends DialogFragment {
    private EditText et_buildingName;

    public interface AddingBuildingDialogListener {
        void onDialogPositiveClick(String buildingName);
    }

    AddingBuildingDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (AddingBuildingDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement NoticeDialogListener");
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
                    String buildingName = et_buildingName.getText().toString();
                    mListener.onDialogPositiveClick(buildingName);
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
