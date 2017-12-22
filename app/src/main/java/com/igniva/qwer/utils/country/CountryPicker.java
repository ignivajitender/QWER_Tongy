package com.igniva.qwer.utils.country;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.igniva.qwer.R;
import com.igniva.qwer.model.CountriesPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryPicker extends DialogFragment {

    private EditText searchEditText;
    private ListView countryListView;
    private CountryListAdapter adapter;
    private List<CountriesPojo> countriesList = new ArrayList<>();
    private List<CountriesPojo> selectedCountriesList = new ArrayList<>();
    private CountryPickerListener listener;
    private Context context;

    public CountryPicker() {
        setCountriesList(this.countriesList);
    }

    /**
     * To support show as dialog
     */
    public static CountryPicker newInstance(String dialogTitle) {
        CountryPicker picker = new CountryPicker();
        Bundle bundle = new Bundle();
        bundle.putString("dialogTitle", dialogTitle);
        picker.setArguments(bundle);
        return picker;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_picker, null);
        Bundle args = getArguments();
        if (args != null) {
            String dialogTitle = args.getString("dialogTitle");
            getDialog().setTitle(dialogTitle);

            int width = getResources().getDimensionPixelSize(R.dimen.dp_200);
            int height = getResources().getDimensionPixelSize(R.dimen.dp_300);
            getDialog().getWindow().setLayout(width, height);
        }
        searchEditText = (EditText) view.findViewById(R.id.country_code_picker_search);
        countryListView = (ListView) view.findViewById(R.id.country_code_picker_listview);

        selectedCountriesList = new ArrayList<>(countriesList.size());
        selectedCountriesList.addAll(countriesList);

        adapter = new CountryListAdapter(getActivity(), selectedCountriesList);
        countryListView.setAdapter(adapter);

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    CountriesPojo country = selectedCountriesList.get(position);
                    listener.onSelectCountry(country.getCountry(), country.getId(),
                            country.getCountry_flag());
                }
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });

        return view;
    }

    public void setListener(CountryPickerListener listener) {
        this.listener = listener;
    }

    @SuppressLint("DefaultLocale")
    private void search(String text) {
        selectedCountriesList.clear();
        for (CountriesPojo country : countriesList) {
            if (country.getCountry().toLowerCase(Locale.ENGLISH).contains(text.toLowerCase())) {
                selectedCountriesList.add(country);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setCountriesList(List<CountriesPojo> newCountries) {
        this.countriesList.clear();
        this.countriesList.addAll(newCountries);
    }

}