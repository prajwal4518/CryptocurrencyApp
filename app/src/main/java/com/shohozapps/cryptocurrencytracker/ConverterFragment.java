package com.shohozapps.cryptocurrencytracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class ConverterFragment extends Fragment {

    private EditText cryptoEditText, domesticEditText;
    private TextView cryptoTextView, domesticTextView;
    private Cryptocurrency localSelectedCrypto = null;


    public ConverterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_converter, container, false);

        attachIDs(view);
        interlinkEditTexts(cryptoEditText, domesticEditText, '*');
        interlinkEditTexts(domesticEditText, cryptoEditText, '/');

        return view;
    }

    private void attachIDs(View view) {
        cryptoEditText = view.findViewById(R.id.et_crypto);
        domesticEditText = view.findViewById(R.id.et_domestic);
        cryptoTextView = view.findViewById(R.id.tv_crypto);
        domesticTextView = view.findViewById(R.id.tv_domestic);
    }

    private void interlinkEditTexts(final EditText et1, final EditText et2, final char symb) {

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(et1.hasFocus() && !et1.getText().toString().equals("")) {

                    double holder = Double.valueOf(et1.getText().toString()
                            .replaceAll(",",""));

                    if(localSelectedCrypto == null)
                        return;

                    if(symb == '*') {
                        holder *= localSelectedCrypto.getCurrentPrice();
                        et2.setText(String.format("%,.2f", holder));
                    }
                    else if(symb == '/') {
                        holder /= localSelectedCrypto.getCurrentPrice();
                        et2.setText(String.format("%,.4f", holder));
                    }
                }
                else if(et1.hasFocus() && et1.getText().toString().equals("")) {
                    et2.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // consider adding commas to edit text user is interacting with
            }
        });
    }

    public void updateConverterFragment(Cryptocurrency cryptocurrency) {
        this.localSelectedCrypto = cryptocurrency;

        cryptoTextView.setText(localSelectedCrypto.getName());
        domesticTextView.setText(localSelectedCrypto.getDomesticCurrency());

        cryptoEditText.setHint("Enter " + localSelectedCrypto.getName());
        domesticEditText.setHint("Enter " + localSelectedCrypto.getDomesticCurrency());

        if(domesticEditText.hasFocus())
            domesticEditText.setText(domesticEditText.getText().toString());
        else
            cryptoEditText.setText(cryptoEditText.getText().toString());
    }

}
