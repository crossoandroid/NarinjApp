package com.orange_team.narinjapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.pinball83.maskededittext.MaskedEditText;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.model.ItemRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderDetailsFragment extends Fragment {

    private EditText mInputName, mInputSurname;
    private TextInputLayout mInputLayoutName, mInputLayoutSurname, mInputLayoutNumber;
    private Button mOrderBtn;
    MaskedEditText mInputNumber;
    Call<ItemRequest> itemRequestCall;

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
        mInputNumber = (MaskedEditText) view.findViewById(R.id.masked_edit_text);

        mInputName.addTextChangedListener(new MyTextWatcher(mInputName));
        mInputSurname.addTextChangedListener(new MyTextWatcher(mInputSurname));
//        mInputNumber.addTextChangedListener(new MyTextWatcher(mInputNumber));

        mInputName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        mInputSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        mInputNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        mOrderBtn = (Button) view.findViewById(R.id.toOrder);
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MainFragment mainFragment = new MainFragment();
                //fragmentTransaction.add(R.id.order_details_fragment,mainFragment);
                fragmentTransaction.replace(R.id.order_details_fragment,mainFragment);
                fragmentTransaction.commit();


            }
        });

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

    }

    private void submitForm() {
        if (!validateName() || !validateEmail() || !validatePhoneNumber()) {
            return;
        }
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setPhone("Android");
        itemRequest.setPrice(48448);
        itemRequest.setLocation("sss 2");
        itemRequest.setComment("hghuhu");
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

                Pattern pt = Pattern.compile("([0-9](.[0-9]*)?)");
                Matcher m = pt.matcher(phone);


                if (phone.length() >= 7 && !backspacingFlag) {

                    //editedFlag = true;
                    editedFlag = m.matches();
                    String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 5) + "-" + phone.substring(5, 7) + "-" + phone.substring(7);
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
//                case R.id.input_number:
//                    validatePhoneNumber();
//                    break;
            }
        }
    }
}



