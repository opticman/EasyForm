package nakarin.birdssco.com.easyform.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import nakarin.birdssco.com.easyform.R;
import nakarin.birdssco.com.easyform.sqlite.MyManager;
import nakarin.birdssco.com.easyform.sqlite.MyOpenHelper;
import nakarin.birdssco.com.easyform.utility.MyAlertDialog;

/**
 * Created by opticman on 17-Sep-17.
 */

public class MainFragment extends Fragment{

    //Explicit ประกาศตัวแปร
    private String nameString, genderString, provinceString;
    private boolean genderABoolean = true;
    private int indexAnInt = 0;
    private String[] provinceStrings = new String[]{
            "โปรดเลือกจังหวัด",
            "กรุงเทพ",
            "กรุงศรี",
            "กรุงธน",
            "กรุงไทย"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //adddata controller

        adddataController();

        //redio controller
        redioController();

        //spener Controller
        spenerController();

        createListView();

    }

    private void spenerController() {
        Spinner spinner = getView().findViewById(R.id.spnProvince);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                provinceStrings
        );
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexAnInt = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                indexAnInt = 0;
            }
        });

    }

    private void redioController() {
        RadioGroup radioGroup = getView().findViewById(R.id.ragGendre);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                genderABoolean = false;
                switch (i) {
                    case R.id.ragMale:
                        genderString = "male";
                        break;
                    case R.id.ragFemale:
                        genderString = "Female";
                        break;
                }
            }
        });
    }

    private void adddataController() {
        Button button = getView().findViewById(R.id.btnAddData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get value From Edittext
                EditText editText = getView().findViewById(R.id.edtName);
                nameString = editText.getText().toString().trim();

                //check Spece

                if (nameString.equals("")) {
                    //have spece
                    MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                    myAlertDialog.myDialog("Have Spece", "Please Fill All Blank");
                } else if (genderABoolean) {

                    //non choose gender
                    MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                    myAlertDialog.myDialog("Non Choose gender",
                            "plaese Choose Male or Female?");
                } else if (indexAnInt == 0) {
                    MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity());
                    myAlertDialog.myDialog(getResources().getString(R.string.title),
                            getResources().getString(R.string.message)
                            );

                } else {
                    MyManager myManager = new MyManager(getActivity());
                    myManager.addNameToSQLite(
                            nameString,
                            genderString,
                            provinceStrings[indexAnInt]);

                    //Create listView
                    createListView();
                } //if


            } // onClick
        });

    }

    private void createListView() {

        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(
                    MyOpenHelper.datacase_name,
                    Context.MODE_PRIVATE,
                    null
            ); //read sqlite
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM nameTABLE", null);
            cursor.moveToFirst();
            String[] nameString = new String[cursor.getCount()];
            String[] genderString = new String[cursor.getCount()];
            String[] provinceString = new String[cursor.getCount()];

            for (int i=0; i<cursor.getCount(); i+=1) {
                nameString[i] = cursor.getString(1);
                genderString[i] = cursor.getString(2);
                provinceString[i] = cursor.getString(3);
                Log.d("17sepV1", "Name[" + i + "] ==>" + nameString[i]);
                cursor.moveToNext();
            } //for

            ListView listView = getView().findViewById(R.id.LivName);
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    nameString
            );
            listView.setAdapter(stringArrayAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}  //Main class
