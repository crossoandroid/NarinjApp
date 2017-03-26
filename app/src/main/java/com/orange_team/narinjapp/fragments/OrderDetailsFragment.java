package com.orange_team.narinjapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.MaskFilterSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.pinball83.maskededittext.MaskedEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.application.NApplication;
import com.orange_team.narinjapp.interfaces.RetrofitInterface;
import com.orange_team.narinjapp.model.Datark;
import com.orange_team.narinjapp.model.DishOrders;
import com.orange_team.narinjapp.model.Body;
import com.orange_team.narinjapp.utils.AlertDialogManager;
import com.orange_team.narinjapp.utils.InternetConnectionDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsFragment extends Fragment {

    private EditText mInputName, mInputSurname, mInputNumber, mInputAddress;
    private TextInputLayout mInputLayoutPrice, mInputLayoutComment, mInputLayoutNumber,mInputLayoutAddress;
    private Button mOrderBtn;
    RetrofitInterface mRetrofitInterface;
    Body body;
    List<Body> bodies;
    List<DishOrders> dishOrders;
    Boolean isInternetPresent = false;
    InternetConnectionDetector internetConnectionDetector;
    FirebaseDatabase fbdb;
    DatabaseReference ref;
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

        mInputLayoutPrice = (TextInputLayout) view.findViewById(R.id.input_layout_price);
        mInputLayoutComment = (TextInputLayout) view.findViewById(R.id.input_layout_comment);
        mInputLayoutNumber = (TextInputLayout) view.findViewById(R.id.input_layout_number);
        mInputLayoutAddress = (TextInputLayout) view.findViewById(R.id.input_layout_address);
        mInputName = (EditText) view.findViewById(R.id.input_price);
        mInputSurname = (EditText) view.findViewById(R.id.input_comment);
        mInputNumber = (MaskedEditText) view.findViewById(R.id.masked_edit_text);
        mInputAddress = (EditText) view.findViewById(R.id.input_address);

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

        mInputAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                internetConnectionDetector=new InternetConnectionDetector(getContext());
                isInternetPresent=internetConnectionDetector.isConnectingToInternet();
                if(isInternetPresent) {
                    sendPost();
                }
                else
                {
                    AlertDialogManager alertDialogManager=new AlertDialogManager();
                    alertDialogManager.showAlertDialog(getContext(),getString(R.string.enable_internet),getString(R.string.internet_access),true);
                }
            }
        });

    }

    public void sendPost()
    {
        bodies=new ArrayList<>();
        fbdb=FirebaseDatabase.getInstance();
        ref=fbdb.getReference().push();
        body =new Body();
        dishOrders=BasketFragment.listInstance();
        mRetrofitInterface = ((NApplication) getActivity().getApplication()).getRetrofitInterface();
        body.setPhone(mInputNumber.getText().toString());
        body.setComment(mInputSurname.getText().toString());
        body.setPrice(BasketFragment.inttotal);
        body.setLocation(mInputAddress.getText().toString());

        body.setDishOrders(dishOrders);
        bodies.add(body);
        ref.setValue(bodies);
        Call<Datark> call=mRetrofitInterface.sendItems(body);
        call.enqueue(new Callback<Datark>() {
            @Override
            public void onResponse(Call<Datark> call, Response<Datark> response) {
                Toast.makeText(getContext(),response.code()+""+response.message(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Datark> call, Throwable t) {
                Log.d("OrderDetailsFragment","onFailure:"+t.getMessage());
            }
        });
    }

    private void submitForm() {
        if (!validateName() && !validateEmail() && !validatePhoneNumber() && !validateAddress()) {
            return;
        }

    }

    private boolean validateName() {
        if (mInputName.getText().toString().trim().isEmpty()) {
            mInputLayoutPrice.setError(getString(R.string.err_msg_name));
            requestFocus(mInputName);
            return false;
        } else {
            mInputLayoutPrice.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String surname = mInputSurname.getText().toString().trim();

        if (surname.isEmpty() || !isValidSurname(surname)) {
            mInputLayoutComment.setError(getString(R.string.err_msg_email));
            requestFocus(mInputSurname);
            return false;
        } else {
            mInputLayoutComment.setErrorEnabled(false);
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

    private boolean validateAddress() {
        if (mInputNumber.getText().toString().trim().isEmpty()) {
            mInputLayoutAddress.setError(getString(R.string.address_error));
            requestFocus(mInputNumber);
            return false;
        } else {
            mInputLayoutAddress.setErrorEnabled(false);
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
}



