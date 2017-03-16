package com.orange_team.narinjapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.orange_team.narinjapp.R;



public class OrderDetailsFragment extends Fragment {

    private EditText mInputName, mInputSurname, mInputNumber;
    private TextInputLayout mInputLayoutName, mInputLayoutSurname, mInputLayoutNumber;
    private Button mOrderBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view) {

        mInputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        mInputLayoutSurname = (TextInputLayout) view.findViewById(R.id.input_layout_surname);
        mInputLayoutNumber = (TextInputLayout) view.findViewById(R.id.input_layout_number);
        mInputName = (EditText) view.findViewById(R.id.input_name);
        mInputSurname = (EditText) view.findViewById(R.id.input_surname);
        mInputNumber = (EditText) view.findViewById(R.id.input_number);

        mInputName.addTextChangedListener(new MyTextWatcher(mInputName));
        mInputSurname.addTextChangedListener(new MyTextWatcher(mInputSurname));
        mInputNumber.addTextChangedListener(new MyTextWatcher(mInputNumber));

        mOrderBtn = (Button) view.findViewById(R.id.toOrder);
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();

            }
        });


    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePhoneNumber()) {
            return;
        }

        Toast.makeText(getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (mInputName.getText().toString().trim().isEmpty()) {
            mInputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(mInputName);
            return false;
        } else {
            mInputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String surname = mInputSurname.getText().toString().trim();

        if (surname.isEmpty() || !isValidSurname(surname)) {
            mInputLayoutSurname.setError(getString(R.string.err_msg_email));
            requestFocus(mInputSurname);
            return false;
        } else {
            mInputLayoutSurname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhoneNumber() {
        if (mInputNumber.getText().toString().trim().isEmpty()) {
            mInputLayoutNumber.setError(getString(R.string.err_msg_password));
            requestFocus(mInputNumber);
            return false;
        } else {
            mInputLayoutNumber.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidSurname(String surname) {
        return !TextUtils.isEmpty(surname);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;
        private boolean backspacingFlag = false;
        private boolean editedFlag = false;

        private MyTextWatcher(View view) {

            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            String string = editable.toString();
            String phone = string.replaceAll("[^\\d]", "");

            if (!editedFlag) {

                if (phone.length() >= 6 && !backspacingFlag) {

                    editedFlag = true;

                    String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3,5) + "-" + phone.substring(5,7) + "-" + phone.substring(7);
                    mInputNumber.setText(ans);

                    mInputNumber.setSelection(mInputNumber.getText().length());

                }
            } else {
                editedFlag = false;
            }

            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_surname:
                    validateEmail();
                    break;
                case R.id.input_number:
                    validatePhoneNumber();
                    break;
            }
        }
    }
}



